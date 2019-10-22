/**
 * Represents an empty space on the game board
 *
 * @author Rafid
 */
public class EmptySpace  extends  Space {
    public EmptySpace(int row, int column) {
        super(row, column);
    }

    /**
     * Space code for and empty space on the game board
     * @return String
     */
    @Override
    public String toString() {
        return "ES";
    }
}
