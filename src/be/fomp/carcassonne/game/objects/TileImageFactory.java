package be.fomp.carcassonne.game.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public final class TileImageFactory {
	private static TileImageFactory instance = new TileImageFactory();
	
	private TileImageFactory(){}
	
	private Map<Integer, BufferedImage> imageCache = new HashMap<Integer, BufferedImage>();
	
	public static BufferedImage getTileImage(int id)
	{
		return instance.getImage((Integer)id);
	}
	
	private BufferedImage getImage(Integer id){
		if(imageCache.get(id) == null)
			try {                
	          BufferedImage image = ImageIO.read(getClass().getResource("/data/images/"+id+".jpg"));
	     
	          imageCache.put(id, image);
			} catch (IOException ex) {
				return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			}

		return imageCache.get(id);
	}
}
