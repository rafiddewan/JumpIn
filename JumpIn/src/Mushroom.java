/**
 * Represents a mushroom on the game board
 * @author Rafid
 */
public class Mushroom extends Space {

    /**
     * Contructs a mushroom at a location on the game board
     * @param row Desired vertical position
     * @param column Desired horizontal position
     */
    public Mushroom(int row, int column) {
        super(row, column);
    }
    public Mushroom(Mushroom mushroom) {
        super(mushroom.getRow(), mushroom.getColumn());
    }

    /**
     * Board code for mushroom.
     * @return String
     */
    @Override
    public String toString() {
        return "MU";
    }
}
