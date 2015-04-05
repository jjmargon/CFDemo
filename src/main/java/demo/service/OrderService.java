package demo.service;

import java.util.List;

import demo.model.Order;

public interface OrderService {
	
	public Order processOrder(Order order);
	
	public List<Order> getOrders();

	public Order getOrder(Long orderId);

}
