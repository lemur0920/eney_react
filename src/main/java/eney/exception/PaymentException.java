package eney.exception;

public class PaymentException extends Exception {

	private static final long serialVersionUID = 4770713128547694225L;
	
	public PaymentException(String message){
		super(message);
	}
	
	public PaymentException(){
		super();
	}
}
