package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderForm {

    private Long userId;
    private String rate;
    private String currencyFrom;
    private String currencyTo;
    private String amountSell;
    private String amountBuy;
    private String originatingCountry;
    private String timePlaced;
	
    public OrderForm(){}
    
    @JsonCreator
    public OrderForm(@JsonProperty("userId") Long userId, 
    		@JsonProperty("rate") String rate, 
    		@JsonProperty("currencyFrom") String currencyFrom,	
    		@JsonProperty("currencyTo") String currencyTo, 
    		@JsonProperty("amountSell") String amountSell, 
    		@JsonProperty("amountBuy") String amountBuy, 
    		@JsonProperty("originatingCountry") String originatingCountry,
    		@JsonProperty("timePlaced") String timePlaced) {
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

	public Long getUserId() {
		return userId;
	}

	public String getRate() {
		return rate;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public String getAmountSell() {
		return amountSell;
	}

	public String getAmountBuy() {
		return amountBuy;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public String getTimePlaced() {
		return timePlaced;
	}
    
    
}
