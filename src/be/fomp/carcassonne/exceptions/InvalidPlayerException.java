package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class InvalidPlayerException extends GameException {

	public InvalidPlayerException(){
		super();
	}
	
	public InvalidPlayerException(String message){
		super(message);
	}
	
	public InvalidPlayerException(Throwable cause){
		super(cause);
	}
	
	public InvalidPlayerException(String message, Throwable cause){
		super(message, cause);
	}
}
