package demo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import demo.OrderTestHelper;
import demo.model.Order;

public class OrderServiceImplTests {
	
	private OrderServiceImpl classUnderTest;
	
	@Before
	public void init(){
		classUnderTest = new OrderServiceImpl();
	}
	
	@Test
	public void processOrder(){
		// GIVEN a well-constructed order
		OrderTestHelper orderTestHelper = new OrderTestHelper();
		Order order = Order.getInstanceByOrder(orderTestHelper.getWellConstructedOrderForm());
		
		// WHEN processOrder is called
		Order orderSaved = classUnderTest.processOrder(order);
		
		// THEN, the orderSaved has the id
		assertNotNull(orderSaved.getId());
	}
	
	@Test
	public void getOrders(){
		// GIVEN a saved order
		OrderTestHelper orderTestHelper = new OrderTestHelper();
		Order order = Order.getInstanceByOrder(orderTestHelper.getWellConstructedOrderForm());
		Order orderSaved = classUnderTest.processOrder(order);
		
		// WHEN getOrders is called
		List<Order> orders = classUnderTest.getOrders();
		
		// THEN, the list contains 1 item and that item is orderSaved
		assertEquals(1, orders.size());
		assertEquals(orderSaved, orders.get(0));
	}
	
	@Test
	public void getOrder(){
		// GIVEN a saved order
		OrderTestHelper orderTestHelper = new OrderTestHelper();
		Order order = Order.getInstanceByOrder(orderTestHelper.getWellConstructedOrderForm());
		Order orderSaved = classUnderTest.processOrder(order);
		
		// WHEN getOrder is called
		Order orderReturned = classUnderTest.getOrder(orderSaved.getId());
		
		// THEN, orderReturned is equals to orderSaved
		assertEquals(orderSaved, orderReturned);
	}

}
