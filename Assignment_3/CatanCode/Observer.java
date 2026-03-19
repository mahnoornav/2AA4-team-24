/**
 * This is the Observer interface that implements the Observer design pattern.
 * Any class that needs to be notified of game state chnages must implement this class.
 */

public interface Observer {
    // This method is called when the Board changes state
    public void update(Board board);
}