package be.fomp.carcassonne.game.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Fetches images for the different tiles and caches them in memory so there
 * are less disk reads necessary.
 * @author sven
 *
 */
public final class TileImageFactory {
	private static TileImageFactory instance = new TileImageFactory();
	
	private TileImageFactory(){}
	
	private Map<Integer, BufferedImage> imageCache = new HashMap<Integer, BufferedImage>();
	
	public static BufferedImage getTileImage(int id)
	{
		return instance.getImage((Integer)id);
	}
	
	/**
	 * Fetches an image from the disk or cache
	 * @param id The image id as specified in xml config
	 * @return the image
	 */
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
