/**
 * @author Rafid
 */
public class FoxPart extends Space implements MoveableSpace {

    private boolean isVertical;
    private FoxPart otherFoxPart;
    private boolean isHead;

    /**
     *
     * @param row
     * @param column
     * @param  isHead
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead){
        super(row, column);
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
     * @param desiredRow
     * @param desiredColumn
     * @param desiredOtherRow
     * @param desiredOtherColumn
     */
    public void moveBoth(int desiredRow, int desiredColumn, int desiredOtherRow, int desiredOtherColumn) {
        this.move(desiredRow,desiredColumn);
        otherFoxPart.move(desiredOtherRow,desiredOtherColumn);
    }

    /**
     *
     * @param desiredRow
     * @param desiredColumn
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
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
