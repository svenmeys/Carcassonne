package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class PlayerLimitReachedException extends GameException {

	public PlayerLimitReachedException(){
		super();
	}
	
	public PlayerLimitReachedException(String message){
		super(message);
	}
	
	public PlayerLimitReachedException(Throwable cause){
		super(cause);
	}
	
	public PlayerLimitReachedException(String message, Throwable cause){
		super(message, cause);
	}
}
