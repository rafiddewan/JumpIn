/**
 * Represents a fox on the game board.
 *
 * @author Rafid
 */
public class FoxPart extends Space implements MoveableSpace {

    private boolean isVertical;
    private FoxPart otherFoxPart;
    private boolean isHead;

    /**
     * Constructor for a piece of a fox.
     *
     * @param row Row position
     * @param column Column position
     * @param isHead Whether this FoxPart is the head of the fox or not
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead){
        super(row, column);
        this.isVertical = vertical;
        this.isHead = isHead;
    }

    /**
     *  Constructor for the second piece of the fox.
     *
     * @param row Row position
     * @param column Column position
     * @param foxPart The other FoxPart
     * @param isHead Whether this FoxPart is the head of the fox or not
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead, FoxPart foxPart){
        super(row, column);
        this.isVertical = vertical;
        this.isHead = isHead;
        this.otherFoxPart = foxPart;
    }
    public FoxPart(FoxPart fox){
        super(fox.getRow(),fox.getColumn());
        this.isVertical = fox.getIsVertical();
        this.isHead = fox.getIsHead();
        this.otherFoxPart = fox.getOtherFoxPart();
    }

    /**
     * Getter for the orientation of the FoxPart. True if vertical, false if horizontal
     * @return boolean
     */
    public boolean getIsVertical() {
        return isVertical;
    }

    /**
     * Getter for the FoxPart that isn't this one
     * @return FoxPart
     */
    public FoxPart getOtherFoxPart() {
        return otherFoxPart;
    }

    /**
     * Set second FoxPart to another FoxPart
     * @param otherFoxPart Another FoxPart
     */
    public void setOtherFoxPart(FoxPart otherFoxPart) {
        this.otherFoxPart = otherFoxPart;
    }

    /**
     * Getter for whether this FoxPart is a head or not. True if head, false if not
     * @return boolean
     */
    public boolean getIsHead() {
        return isHead;
    }

    /**
     * Moves both FoxParts to desired spaces
     *
     * @param desiredRow Desired vertical location of first FoxPart
     * @param desiredColumn Desired horizontal location of first FoxPart
     * @param desiredOtherRow Desired vertical location of second FoxPart
     * @param desiredOtherColumn Desired horizontal location of second FoxPart
     */
    public void moveBoth(int desiredRow, int desiredColumn, int desiredOtherRow, int desiredOtherColumn) {
        this.move(desiredRow,desiredColumn);
        otherFoxPart.move(desiredOtherRow,desiredOtherColumn);
    }

    /**
     * Moves FoxPart to a desired space
     *
     * @param desiredRow Desired vertical location of this FoxPart
     * @param desiredColumn Desired horizontal location of this FoxPart
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
    }

    /**
     * The board code for this FoxPart. "FT" is Fox Tail and "FH" is Fox Head
     * @return String
     */
    @Override
    public String toString() {
        return (isHead) ? "FH" : "FT";
    }

}
