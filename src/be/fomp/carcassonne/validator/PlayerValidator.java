package be.fomp.carcassonne.validator;

import be.fomp.carcassonne.exceptions.InvalidPlayerException;
import be.fomp.carcassonne.model.PlayerBean;
import be.fomp.carcassonne.utils.Color;

/**
 * Validatorclass for {@link PlayerBean} for use in the view.
 * @author sven
 *
 */
public final class PlayerValidator {
	private PlayerValidator(){}
	
	public static void validatePlayer(PlayerBean player) throws InvalidPlayerException
	{
		StringBuffer message = new StringBuffer();
		try {
			validateName 		(player.getName());
		} catch (InvalidPlayerException e) {
			message.append(e.getMessage());
		}
		try {
			validateAge  		(player.getAge());
		} catch (InvalidPlayerException e) {
			message.append(e.getMessage());
		}
		try {
			validateColor		(player.getColor());
		} catch (InvalidPlayerException e) {
			message.append(e.getMessage());
		}
		try {
				validateFollowers	(player.getFollowers());
		} catch (InvalidPlayerException e) {
		message.append(e.getMessage());
		}
		try {
			validateScore		(player.getScore());
		} catch (InvalidPlayerException e) {
			message.append(e.getMessage());
		}
		if (message.length() > 0) 
			throw new InvalidPlayerException(message.toString());
	}
	
	public static void validateName(String name) throws InvalidPlayerException
	{
		if(name == null) throw new InvalidPlayerException("Name can not be null\n");
		
		StringBuffer message = new StringBuffer();
		
		if(name.equals(""))
			message.append("Name can not be empty\n");
		
		if(message.length() > 0)
			throw new InvalidPlayerException(message.toString());
	}
	
	public static void validateAge(String age) throws InvalidPlayerException
	{
		if(age == null) throw new InvalidPlayerException("Age can not be null\n");
	
		StringBuffer message = new StringBuffer();
		
		if(age.equals(""))
			message.append("Age can not be empty\n");
		if(!age.matches("^([1-9]|[1-9][0-9])$")) 
			message.append("Age must be a positive number in range[1-99]\n");
		
		if(message.length() > 0)
			throw new InvalidPlayerException(message.toString());
	}
	
	public static void validateColor(String color) throws InvalidPlayerException
	{
		if(color == null) throw new InvalidPlayerException("Color can not be null\n");
		
		StringBuffer message = new StringBuffer();
		
		if(color.equals(""))
			message.append("Color can not be empty\n");
		try{Color.valueOf(color);}
		catch (Exception e){
			message.append("Color must be [");
			for(Color c : Color.values()) message.append(c).append(",");
			message.replace(message.length()-1, message.length(), "]\n");
		}
		
		if(message.length() > 0) 
			throw new InvalidPlayerException(message.toString());		
	}
	
	public static void validateFollowers(String followers) throws InvalidPlayerException
	{
		if(followers == null) throw new InvalidPlayerException("Followers can not be null\n");
		
		StringBuffer message = new StringBuffer();
				
		if(followers.equals(""))
			message.append("Followers can not be empty\n");
		if(!followers.matches("^([0-7])$")) 
			message.append("Followers must be a positive number in range[0-7]\n");
		
		if(message.length() > 0)
			throw new InvalidPlayerException(message.toString());
	}
	
	public static void validateScore(String score) throws InvalidPlayerException
	{
		if(score == null) throw new InvalidPlayerException("Score can not be null\n");
		
		StringBuffer message = new StringBuffer();
		
		if(score.equals(""))
			message.append("Score can not be empty\n");
		if(!score.matches("^([0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")) 
			message.append("Score must be a positive number in range[0-999]\n");
		
		if(message.length() > 0)
			throw new InvalidPlayerException(message.toString());		
	}
}
