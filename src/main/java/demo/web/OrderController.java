package demo.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.model.Order;
import demo.model.OrderError;
import demo.model.OrderForm;
import demo.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	private SimpMessagingTemplate messagingTemplate;
	private OrderService orderService;
	
	@Autowired
    public OrderController(SimpMessagingTemplate template, OrderService orderService) {
        this.messagingTemplate = template;
        this.orderService = orderService;
    }

	@RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody OrderForm orderForm, HttpServletResponse response) throws Exception {
        Order order = Order.getInstanceByOrder(orderForm);
        Order orderProcessed = orderService.processOrder(order);
		this.messagingTemplate.convertAndSend("/topic/outcome", orderProcessed);
		response.setStatus(HttpStatus.CREATED.value());
		response.flushBuffer();
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Order> getAllOrders(){
		return orderService.getOrders();
	} 
	
	@RequestMapping(value="/{orderId}", method = RequestMethod.GET)
	public @ResponseBody Order getOrder(@PathVariable String orderId){
		return orderService.getOrder(Long.valueOf(orderId));
	}
    
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleBadRequests(IllegalArgumentException e, HttpServletResponse response) throws Exception{
    	String errorMessage = e.getMessage() == null || e.getMessage().isEmpty() ? "No error message available" : e.getMessage();
    	OrderError orderError = new OrderError(errorMessage, HttpStatus.BAD_REQUEST.value());
    	this.messagingTemplate.convertAndSend("/topic/error", orderError);
    	response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.flushBuffer();
    }
    
}