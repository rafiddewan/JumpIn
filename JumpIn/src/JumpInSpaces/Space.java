/**
 * Superclass of EmptySpace, Fox, Holes, Mushroom, Rabbit, and Fox classes
 * @author Rafid
 */
package JumpInSpaces;
public class Space {

    private int row;
    private int column;

    /**
     * Constructs a space on the board.
     * @param row Vertical position
     * @param column Horizontal position
     */
    public Space(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for the horizontal position of this space
     * @return int
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter for the vertical position of this space
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets vertical position of this space
     * @param row New vertical position
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets horizontal position of this space
     * @param column New horizontal position
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Prints vertical and horizontal location of this space.
     * @return String
     */
    @Override
    public String toString() {
        return "Space{" +
                "posX=" + row +
                ", posY=" + column +
                '}';
    }
}