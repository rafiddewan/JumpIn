/**
 * @author Rafid
 */
public class FoxPart extends Space implements MoveableSpace {

    private boolean isVertical;
    private FoxPart otherFoxPart;
    private boolean isHead;

    /**
     *
     * @param posX
     * @param posY
     * @param  isHead
     */
    public FoxPart(int posX, int posY, boolean vertical, boolean isHead){
        super(posX, posY);
        this.isVertical = vertical;
        this.isHead = isHead;
    }

    /**
     *
     * @param row
     * @param column
     * @param foxPart
     * @param isHead
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead, FoxPart foxPart){
        super(row, column);
        this.isVertical = vertical;
        this.isHead = isHead;
        this.otherFoxPart = foxPart;
    }

    /**
     *
     * @return boolean
     */
    public boolean getIsVertical() {
        return isVertical;
    }

    /**
     *
     * @return FoxPart
     */
    public FoxPart getOtherFoxPart() {
        return otherFoxPart;
    }

    /**
     *
     * @param otherFoxPart
     */
    public void setOtherFoxPart(FoxPart otherFoxPart) {
        this.otherFoxPart = otherFoxPart;
    }

    /**
     *
     * @return boolean
     */
    public boolean getIsHead() {
        return isHead;
    }

    /**
     *
     * @param desiredSpace
     * @param desiredSpace
     */
    public void moveBoth(Space desiredSpace, Space desiredOtherSpace) {
        this.move(desiredSpace);
        otherFoxPart.move(desiredOtherSpace);
    }

    /**
     *
     * @param desiredSpace
     */
    @Override
    public void move(Space desiredSpace) {
        this.setRow(desiredSpace.getRow());
        this.setColumn(desiredSpace.getColumn());
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return (isHead) ? "FH" : "FT";
    }

}
