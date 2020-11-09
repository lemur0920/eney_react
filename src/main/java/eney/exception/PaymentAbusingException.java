package eney.exception;

public class PaymentAbusingException extends PaymentException{
	
	private static final long serialVersionUID = 5293834921218538442L;
	
	public PaymentAbusingException(String message){
		super(message);
	}

}
