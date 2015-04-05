package demo;

import demo.model.OrderForm;

public class OrderTestHelper {
	
	public final static String USER_ID_STR = "123456";
	public final static String RATE_STR = "0.7471";
	public final static String CURRENCY_FROM_STR = "EUR";
	public final static String CURRENCY_TO_STR = "GBP";
	public final static String AMOUNT_SELL_STR = "1000";
	public final static String AMOUNT_BUY_STR = "747.10";
	public final static String TIME_PLACED_STR = "24-JAN-15 10:27:44";
	public final static String ORIGINATING_COUNTRY_STR = "FR";
	public final static String DATE_FORMAT = "dd-MMM-yy HH:mm:ss";
	
	public OrderForm getWellConstructedOrderForm(){
		return new OrderForm(Long.valueOf(OrderTestHelper.USER_ID_STR), OrderTestHelper.RATE_STR, 
				OrderTestHelper.CURRENCY_FROM_STR, OrderTestHelper.CURRENCY_TO_STR, OrderTestHelper.AMOUNT_SELL_STR, 
				OrderTestHelper.AMOUNT_BUY_STR, OrderTestHelper.ORIGINATING_COUNTRY_STR, OrderTestHelper.TIME_PLACED_STR);
	}
	

}
