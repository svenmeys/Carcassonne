package be.fomp.carcassonne.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.fomp.carcassonne.utils.TileUtils;

public class TileUtilsTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetCoordinateLocation() {
		//corners
		
		assertEquals( 0, TileUtils.getCoordinateLocation( 7, 3,30,30));
		assertEquals(11, TileUtils.getCoordinateLocation( 3, 7,30,30));
		
		assertEquals( 2, TileUtils.getCoordinateLocation(23, 3,30,30));
		assertEquals( 3, TileUtils.getCoordinateLocation(27, 7,30,30));
		
		assertEquals( 5, TileUtils.getCoordinateLocation(27,23,30,30));
		assertEquals( 6, TileUtils.getCoordinateLocation(23,27,30,30));
		
		assertEquals( 8, TileUtils.getCoordinateLocation( 7,27,30,30));
		assertEquals( 9, TileUtils.getCoordinateLocation( 3,23,30,30));
		
		//Rest
		assertEquals(12, TileUtils.getCoordinateLocation(15,15,30,30));
		assertEquals(10, TileUtils.getCoordinateLocation (5,15,30,30));
		assertEquals( 4, TileUtils.getCoordinateLocation(25,15,30,30));
		assertEquals( 1, TileUtils.getCoordinateLocation(15, 5,30,30));
		assertEquals( 7, TileUtils.getCoordinateLocation(15,25,30,30));
	}
	
	@Test
	public final void getHalfRectanglePosition() {
		//Testing positive Slope
		assertEquals(true, TileUtils.getHalfRectanglePosition(  0,  0, 10, 10, 1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  3,  3, 10, 10, 1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  5,  5, 10, 10, 1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  7,  7, 10, 10, 1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 10, 10, 10, 10, 1) == 0);


		assertEquals(true, TileUtils.getHalfRectanglePosition( 3,  7, 10, 10, 1)  > 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 7,  3, 10, 10, 1)  < 0);

		assertEquals(true, TileUtils.getHalfRectanglePosition( 3,  5, 10, 10, 1)  > 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 5,  3, 10, 10, 1)  < 0);

		assertEquals(true, TileUtils.getHalfRectanglePosition( 7,  5, 10, 10, 1)  < 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 5,  7, 10, 10, 1)  > 0);
		
		//Testing negative Slope
		assertEquals(true, TileUtils.getHalfRectanglePosition(  0,  0, 10, 10, -1)  < 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  3,  3, 10, 10, -1)  < 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  5,  5, 10, 10, -1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition(  7,  7, 10, 10, -1)  > 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 10, 10, 10, 10, -1)  > 0);
		
		
		assertEquals(true, TileUtils.getHalfRectanglePosition( 3,  7, 10, 10, -1) == 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 7,  3, 10, 10, -1) == 0);
		
		assertEquals(true, TileUtils.getHalfRectanglePosition( 3,  5, 10, 10, -1)  < 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 5,  3, 10, 10, -1)  < 0);
		
		assertEquals(true, TileUtils.getHalfRectanglePosition( 7,  5, 10, 10, -1)  > 0);
		assertEquals(true, TileUtils.getHalfRectanglePosition( 5,  7, 10, 10, -1)  > 0);
		
	}
}
