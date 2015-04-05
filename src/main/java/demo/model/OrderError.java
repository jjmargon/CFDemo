package demo.model;


public class OrderError{

    private final String error;
    private final Integer errorCode;
	
    public OrderError(String error, Integer errorCode) {
		super();
		this.error = error;
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
    
}
