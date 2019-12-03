/**
 * Represents an empty space on the game board
 *
 * @author Rafid
 */
package JumpInBoardAPI;
public class EmptySpace  extends  Space {
    public EmptySpace(int row, int column) {
        super(row, column);
    }
    public EmptySpace(EmptySpace space){
        super(space.getRow(),space.getColumn());
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
