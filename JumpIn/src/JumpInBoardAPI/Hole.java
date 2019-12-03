/**
 * Represents one of 5 holes on the game board
 * @author Rafid
 */
package JumpInBoardAPI;
public class Hole extends Space {
    private boolean isFilled;

    /**
     * Constructs a hole onto the game board
     * @param row Vertical position
     * @param column Horizontal Position
     * @param filled Whether this hole has a rabbit inside or not
     */
    public Hole(int row, int column, boolean filled) {
        super(row, column);
        this.isFilled = filled;
    }
    public Hole(Hole hole){
        super(hole.getRow(),hole.getColumn());
        this.isFilled=hole.getIsFilled();
    }

    /**
     * Getter for whether this hole has a rabbit in it
     * @return boolean
     */
    public boolean getIsFilled() {
        return isFilled;
    }

    /**
     * Sets whether this hole is filled or not
     * @param filled Whether this hole is filled or not
     */
    public void setIsFilled(boolean filled) {
        this.isFilled = filled;
    }

    /**
     * The board code for this Hole. "CH" is Closed Hole and "OH" is Open Hole
     * @return String
     */
    @Override
    public String toString() {
        return (isFilled) ? "CH" : "OH";
    }
}
