package eney.exception;


public class PaymentLackException extends PaymentException{
	
	private static final long serialVersionUID = 3604667052354611761L;
	
	public PaymentLackException(String message){
		super(message);
	}
}
