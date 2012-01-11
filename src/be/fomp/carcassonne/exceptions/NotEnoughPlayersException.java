package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class NotEnoughPlayersException extends GameException {

	public NotEnoughPlayersException(){
		super();
	}
	
	public NotEnoughPlayersException(String message){
		super(message);
	}
	
	public NotEnoughPlayersException(Throwable cause){
		super(cause);
	}
	
	public NotEnoughPlayersException(String message, Throwable cause){
		super(message, cause);
	}
}
