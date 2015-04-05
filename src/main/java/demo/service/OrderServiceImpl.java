package demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import demo.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

	private final List <Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private AtomicLong orderId = new AtomicLong();
	
	@Override
	public Order processOrder(Order order) {
		
		Assert.notNull(order);
		
		// For this demo, there is not any process for the order
		
		// It just generates an internal id to simulate the order is stored
		order.setId(orderId.incrementAndGet());
		
		// It's just added to the internal list
		orders.add(order);
		
		return order;
	}

	@Override
	public List<Order> getOrders() {
		return Collections.unmodifiableList(orders);
	}

	@Override
	public Order getOrder(Long orderId) {
		Order order = null;
		for(Order existingOrder : orders){
			if(existingOrder.getId().equals(orderId)){
				order = existingOrder;
				break;
			}
		}
		return order;
	}
	
	

}
