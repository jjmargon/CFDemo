package demo.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order {
	
	private final static String DATE_FORMAT = "dd-MMM-yy HH:mm:ss";

    private Long id;
	private final Long userId;
    private final BigDecimal rate;
    private final Currency currencyFrom;
    private final Currency currencyTo;
    private final BigDecimal amountSell;
    private final BigDecimal amountBuy;
    private final String originatingCountry;
    
    @JsonFormat(pattern = DATE_FORMAT)
    private final Date timePlaced;
    
	
    public Order(Long userId, BigDecimal rate, Currency currencyFrom, Currency currencyTo, BigDecimal amountSell, BigDecimal amountBuy, String originatingCountry, Date timePlaced) {
		super();
		this.userId = userId;
		this.rate = rate;
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.amountSell = amountSell;
		this.amountBuy = amountBuy;
		this.originatingCountry = originatingCountry;
		this.timePlaced = timePlaced;
	}
    
    public static Order getInstanceByOrder(OrderForm orderForm){
    	// Asserts not null
    	Assert.notNull(orderForm);
    	Assert.notNull(orderForm.getUserId());
    	
    	// Check Date
    	Assert.notNull(orderForm.getTimePlaced());
    	DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.UK);
    	Date timePlaced;
		try {
			timePlaced = dateFormat.parse(orderForm.getTimePlaced());
		} catch (ParseException e) {
			throw new IllegalArgumentException("timePlaced format is different from " + DATE_FORMAT);
		}
    	
    	// Checks rates and sell, buy
    	Assert.notNull(orderForm.getRate());
    	Assert.notNull(orderForm.getAmountBuy());
    	Assert.notNull(orderForm.getAmountSell());
    	BigDecimal bdRate = new BigDecimal(orderForm.getRate());
    	BigDecimal bdBuy = new BigDecimal(orderForm.getAmountBuy());
    	BigDecimal bdSell = new BigDecimal(orderForm.getAmountSell());
    	if(bdRate.multiply(bdSell).compareTo(bdBuy) != 0){
    		throw new IllegalArgumentException("rate x AmountSell is different than AmountBuy");
    	}
    	
    	// If order currencies are not ISO standard, an IllegalArgumentException will be thrown
   		checkNotNullAndProperCurrency(orderForm.getCurrencyTo());
   		checkNotNullAndProperCurrency(orderForm.getCurrencyFrom());
    	
    	// Locale - Operations country assignment
    	Assert.notNull(orderForm.getOriginatingCountry());
    	// Checks if the country code is compliant with ISO 3166
    	if(!CollectionUtils.contains(Arrays.asList(Locale.getISOCountries()).iterator(), orderForm.getOriginatingCountry())){
    		throw new IllegalArgumentException("Country Code is not an ISO 3166 code");
    	}
    	
    	return new Order(orderForm.getUserId(), bdRate, Currency.getInstance(orderForm.getCurrencyFrom()),
    			Currency.getInstance(orderForm.getCurrencyTo()), bdSell, bdBuy, orderForm.getOriginatingCountry(), timePlaced);
    }

	private static void checkNotNullAndProperCurrency(String currency) {
		Assert.notNull(currency);
		try{
			Currency.getInstance(currency);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(currency + " is not an ISO standard currency",e);
		}
	}

	public Long getUserId() {
		return userId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Currency getCurrencyFrom() {
		return currencyFrom;
	}

	public Currency getCurrencyTo() {
		return currencyTo;
	}

	public BigDecimal getAmountSell() {
		return amountSell;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}
	
	public Date getTimePlaced() {
		return timePlaced;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
