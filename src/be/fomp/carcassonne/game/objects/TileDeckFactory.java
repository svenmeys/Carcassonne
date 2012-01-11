package be.fomp.carcassonne.game.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import be.fomp.carcassonne.exceptions.TileFactoryException;
import be.fomp.carcassonne.model.Area;
import be.fomp.carcassonne.model.AreaImpl;
import be.fomp.carcassonne.model.Tile;
import be.fomp.carcassonne.model.TileImpl;
import be.fomp.carcassonne.model.Zone;
import be.fomp.carcassonne.model.ZoneImpl;
/**
 * This class is used to construct tiles from xml input
 * @author sven
 *
 */
public class TileDeckFactory {

	private static TileDeckFactory instance;
	
	public static TileDeckFactory getInstance()
	{
		if (instance == null) instance = new TileDeckFactory();
		return instance;
	}
	private TileDeckFactory(){}
	
	private Map<String, Document> documentCache = new TreeMap<String, Document>();
	
	/**
	 * This method attempts to construct a TileDeck from a given xml file.
	 * When the xml file has already been read before, it will use its cache
	 *   to generate another one instead of reading the file again.
	 *   
	 * If multiple tiles of the same kind exist this method will clone them
	 * rather than rereading the tile data.
	 *   
	 * @param fileName The xml file where the deck is stored
	 * @return a tile deck
	 * @throws TileFactoryException when loading the deck fails
	 */
	public TileDeck buildDeck(String fileName) throws TileFactoryException
	{	
		TileDeckImpl returnValue = new TileDeckImpl();
		
		Document deckXML = loadDeck(fileName);		// Load the xml file
			
		List<Tile> tileList = new ArrayList<Tile>();
		
		NodeList listOfTiles = deckXML.getElementsByTagName("tile");
        int numTiles = listOfTiles.getLength();
        
        for(int i = 0; i < numTiles ; i++){
        	Element tileNode = (Element)listOfTiles.item(i);
        	
        	int ammount = Integer.parseInt(tileNode.getAttribute("ammount"));
        	
        	for(int t=0; t< ammount; t++){
        		//if(t==0)
        		tileList.add(buildTile(tileNode));
        		//else // Cloning the last one = less CO2 :)
        		//	tileList.add(tileList.get(tileList.size()-1).clone());
        	}
        }
        returnValue.setDeck(tileList);
		return returnValue;
	}
	
	/**
	 * This method builds a tile from XML data
	 * @param tileXML the xml reference containing the tile configuration
	 * @return the tile that was created
	 */
	private Tile buildTile(Element tileXML){
		int tileId			= Integer.parseInt(tileXML.getAttribute("id"));
		Tile returnValue = new TileImpl(tileId);
		Set<Zone> zones	= new HashSet<Zone>();
		
		returnValue.setBorder(new AreaImpl[13]);
		returnValue.setAreas(new HashSet<Area>());
		
		returnValue.getBorder()[12] = new AreaImpl(AreaType.UNKNOWN);//needed to initialize since not every tile has a center area it seems
		
		// fetch areas
		buildAreas(tileXML.getElementsByTagName("road" ), AreaType.ROAD , returnValue);
		buildAreas(tileXML.getElementsByTagName("city" ), AreaType.CITY , returnValue);
		buildAreas(tileXML.getElementsByTagName("city2"), AreaType.CITY2, returnValue);
		buildAreas(tileXML.getElementsByTagName("field"), AreaType.FIELD, returnValue);
		buildAreas(tileXML.getElementsByTagName("cloister"), AreaType.CLOISTER, returnValue);
		buildAreas(tileXML.getElementsByTagName("crossing"), AreaType.CROSSING, returnValue);
		
		for(Area a : returnValue.getAreas()){
			Zone zone = new ZoneImpl();
			
			zone.getAreas().add(a);
			a.setZone(zone);
			
			zones.add(zone);
		}
		
		Zone zoneCenter = returnValue.getBorder()[12].getZone();
		for(int i = 0 ; i <  returnValue.getBorder().length -1; i++)
		{
			Zone zone1 = returnValue.getBorder()[i].getZone();
			Zone zone2 = returnValue.getBorder()[i == 11 ? 0 : 1].getZone();
			
			if(zone1 != zone2) {
					zone1.getNeigboringZones().add(zone2);
					zone2.getNeigboringZones().add(zone1);
			}
			if(zoneCenter!= null && zone1 != zoneCenter) {
					zone1.getNeigboringZones().add(zoneCenter);
					zoneCenter.getNeigboringZones().add(zone1);
			}
		}
		return returnValue;
		
	}
	
	/**
	 * Builds the areas present in the nodelist and ads them to the List and border
	 * @param areaList Nodelist containing areas to be read
	 * @param type	The kind of area to be read
	 * @param tile Tile to map areas to
	 */
	private void buildAreas(NodeList areaList, AreaType type, Tile tile)
	{
		for(int i=0; i < areaList.getLength(); i++){
			Area area = new AreaImpl(type);
			
			int[] connections = getAreaConnections((Element)areaList.item(i));
			for(int b:connections){
				tile.getBorder()[b] = area;
			}
			area.setLocation(tile);
			tile.getAreas().add(area);
		}
	}
	
	/**
	 * Reads in a comma separated string from the area node containing the
	 * border mappings on the tile.
	 * @param area The area to be read
	 * @return array with all the border points
	 */
	private int[] getAreaConnections(Element area){
		String[] connectedBorders = area.getTextContent().split(",");
		int[] returnValue = new int[connectedBorders.length];
		
		for(int i=0; i< connectedBorders.length;i++)
			returnValue[i] = Integer.parseInt(connectedBorders[i]);
		return returnValue;
	}
	/**
	 * Loads the nodelist from the specified xml file and caches it.
	 * If the result existsi n cache, it will return the nodelist from cache.
	 * 
	 * @param fileName the xml file to read from.
	 * @return a nodelist containing TileDeck settings
	 * @throws TileFactoryException When loading the file failed
	 */
	private Document loadDeck(String fileName) throws TileFactoryException {
		Document returnValue;
		
		if(documentCache.containsKey(fileName))
			returnValue = documentCache.get(fileName);
		else{
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder;
				
				docBuilder  = docBuilderFactory.newDocumentBuilder();
				returnValue = docBuilder.parse(getClass().getResource(fileName).openStream());
				returnValue.getDocumentElement().normalize();
			
			} catch (ParserConfigurationException e) {
				throw new TileFactoryException("Docbuilder failed to initialize.");
			} catch (SAXException e) {
				throw new TileFactoryException("Failed to parse " + fileName + ".");
			} catch (IOException e) {
				throw new TileFactoryException("Failed to read " + fileName + ".");
			}
			this.documentCache.put(fileName, returnValue);
		}
		return returnValue;
	}
}
