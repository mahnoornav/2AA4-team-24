import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Executable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileTest {

    private Tile tile;

    // set up test tile that is for the resource brick and has dice roll 8
    @BeforeEach
    void setUp() {
        tile = new Tile(ResourceType.BRICK, 8);
    }

    // test getter for resource type
    @Test
    void getResource_returnsCorrectResource() {
        assertEquals(ResourceType.BRICK, tile.getResource(), "Tile resource should be BRICK.");
    }

    // test getter for dice roll
    @Test
    void getNumber_returnsCorrectNumber() {
        assertEquals(8, tile.getNumber(), "Tile number should be 8.");
    }

    // test if vertices match with the assigned ones
    @Test
    void setVertices_storesVerticesCorrectly() {
        int[] vertices = {12, 3, 2, 9, 10, 11};
        tile.setVertices(vertices);

        assertArrayEquals(vertices, tile.getVertices(), "Vertices should match the ones set.");
    }

    // test to see if array can be modified externally
    @Test
    void getVertices_returnsCloneNotOriginalArray() {
        int[] vertices = {12, 3, 2, 9, 10, 11};
        tile.setVertices(vertices);

        int[] returned = tile.getVertices();
        returned[0] = 99;

        assertNotEquals(99, tile.getVertices()[0], "Internal array should not be modified.");
    }

}