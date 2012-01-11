package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class PlayerNotFoundException extends GameException {

	public PlayerNotFoundException(){
		super();
	}
	
	public PlayerNotFoundException(String message){
		super(message);
	}
	
	public PlayerNotFoundException(Throwable cause){
		super(cause);
	}
	
	public PlayerNotFoundException(String message, Throwable cause){
		super(message, cause);
	}
}
