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
     * @param posX Row position
     * @param posY Column position
     * @param isHead Whether this FoxPart is the head of the fox or not
     */
    public FoxPart(int posX, int posY, boolean vertical, boolean isHead){
        super(posX, posY);
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
     * @param desiredSpace Where one FoxPart needs to go
     * @param desiredOtherSpace Where one FoxPart needs to go
     */
    public void moveBoth(Space desiredSpace, Space desiredOtherSpace) {
        this.move(desiredSpace);
        otherFoxPart.move(desiredOtherSpace);
    }

    /**
     * Moves FoxPart to a desired space
     * @param desiredSpace The space where this FoxPart needs to go.
     */
    @Override
    public void move(Space desiredSpace) {
        this.setRow(desiredSpace.getRow());
        this.setColumn(desiredSpace.getColumn());
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
