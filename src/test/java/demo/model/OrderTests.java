package demo.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import demo.OrderTestHelper;

public class OrderTests {
	
	private final static Long USER_ID = Long.valueOf(OrderTestHelper.USER_ID_STR);
	private final static BigDecimal AMOUNT_SELL = new BigDecimal(OrderTestHelper.AMOUNT_SELL_STR);
	private final static BigDecimal AMOUNT_BUY = new BigDecimal(OrderTestHelper.AMOUNT_BUY_STR);
	private final static BigDecimal RATE = new BigDecimal(OrderTestHelper.RATE_STR);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void getInstanceByOrderSuccessful() throws Exception{
		// GIVEN a well-constructed OrderForm
		OrderTestHelper orderTestHelper = new OrderTestHelper();
		OrderForm orderForm = orderTestHelper.getWellConstructedOrderForm();
		
		// WHEN getInstanceByOrder static method is called
		Order order = Order.getInstanceByOrder(orderForm);
		
		// THEN, the Order returned is filled properly
		assertEquals(USER_ID, order.getUserId());
		assertEquals(RATE, order.getRate());
		assertEquals(Currency.getInstance(OrderTestHelper.CURRENCY_FROM_STR), order.getCurrencyFrom());
		assertEquals(Currency.getInstance(OrderTestHelper.CURRENCY_TO_STR), order.getCurrencyTo());
		assertEquals(AMOUNT_SELL, order.getAmountSell());
		assertEquals(AMOUNT_BUY, order.getAmountBuy());
		assertEquals(OrderTestHelper.ORIGINATING_COUNTRY_STR, order.getOriginatingCountry());
		DateFormat dateFormat = new SimpleDateFormat(OrderTestHelper.DATE_FORMAT, Locale.UK);
		assertEquals(dateFormat.parse(OrderTestHelper.TIME_PLACED_STR),order.getTimePlaced());
	}
	
	@Test
	public void getInstanceByOrderWithWrongDateFormat() throws Exception{
		// GIVEN an OrderForm with a bad formatted date
		OrderForm orderForm = new OrderForm(Long.valueOf(OrderTestHelper.USER_ID_STR), OrderTestHelper.RATE_STR, 
				OrderTestHelper.CURRENCY_FROM_STR, OrderTestHelper.CURRENCY_TO_STR, 
				OrderTestHelper.AMOUNT_SELL_STR, OrderTestHelper.AMOUNT_BUY_STR, 
				OrderTestHelper.ORIGINATING_COUNTRY_STR, "25/12/15");
		
		// WHEN getInstanceByOrder static method is called
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("timePlaced");
		Order.getInstanceByOrder(orderForm);
		
		// THEN, an IllegalArgumentException is thrown and the message contains the word timePlaced
		// (code of THEN is placed before WHEN due to JUnit approach of ExpectedException)
	}
	
	@Test
	public void getInstanceByOrderWithWrongBuyAndSellAmounts() throws Exception{
		// GIVEN an OrderForm with a rate x sellAmount different than buyAmount
		OrderForm orderForm = new OrderForm(Long.valueOf(OrderTestHelper.USER_ID_STR), "1", 
				OrderTestHelper.CURRENCY_FROM_STR, OrderTestHelper.CURRENCY_TO_STR, OrderTestHelper.AMOUNT_SELL_STR, 
				OrderTestHelper.AMOUNT_BUY_STR, OrderTestHelper.ORIGINATING_COUNTRY_STR, OrderTestHelper.TIME_PLACED_STR);
		
		// WHEN getInstanceByOrder static method is called
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("rate x AmountSell is different than AmountBuy");
		Order.getInstanceByOrder(orderForm);
		
		// THEN, an IllegalArgumentException is thrown and the message contains "rate x AmountSell is different than AmountBuy"
		// (code of THEN is placed before WHEN due to JUnit approach of ExpectedException)
	}
	
	@Test
	public void getInstanceByOrderWithWrongCountryOriginatingCode() throws Exception{
		// GIVEN an OrderForm with a wrong originating country code
		OrderForm orderForm = new OrderForm(Long.valueOf(OrderTestHelper.USER_ID_STR), OrderTestHelper.RATE_STR, 
				OrderTestHelper.CURRENCY_FROM_STR, OrderTestHelper.CURRENCY_TO_STR, OrderTestHelper.AMOUNT_SELL_STR,
				OrderTestHelper.AMOUNT_BUY_STR, "ZZ", OrderTestHelper.TIME_PLACED_STR);
		
		// WHEN getInstanceByOrder static method is called
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Country Code is not an ISO 3166 code");
		Order.getInstanceByOrder(orderForm);
		
		// THEN, an IllegalArgumentException is thrown and the message contains "Country Code is not an ISO 3166 code"
		// (code of THEN is placed before WHEN due to JUnit approach of ExpectedException)
	}

}
