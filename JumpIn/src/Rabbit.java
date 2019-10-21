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
     * @param desiredSpace Where the rabbit is called to move.
     */
    @Override
    public void move(Space desiredSpace) {
        this.setRow(desiredSpace.getRow());
        this.setColumn(desiredSpace.getColumn());
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