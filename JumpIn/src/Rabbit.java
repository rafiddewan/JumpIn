/**
 * Represents a rabbit on the game board.
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {

    /**
     * Constructs a rabbit onto the game board
     * @param row Vertical position
     * @param column Horizontal position
     */
    public Rabbit(int row, int column){
        super(row, column);
    }

    /**
     * Moves the rabbit to a desired space on the board
     *
     * @param desiredRow Desired vertical position
     * @param desiredColumn Desired horizontal position
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
    }

    /**
     * The board code for a Rabbit
     * @return String
     */
    @Override
    public String toString() {
        return "RA";
    }
}