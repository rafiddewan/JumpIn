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
     * @param posX
     * @param posY
     * @param foxPart
     * @param isHead
     */
    public FoxPart(int posX, int posY, boolean vertical, boolean isHead, FoxPart foxPart){
        super(posX, posY);
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
     * @param vertical
     */
    public void setIsVertical(boolean vertical) {
        this.isVertical = vertical;
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
    public boolean isHead() {
        return isHead;
    }

    /**
     *
     * @param desiredSpace
     * @param desiredSpace
     */
    public void moveBoth(Space desiredSpace, Space desiredOtherSpace) {
        this.move(desiredSpace);
        otherFoxPart.move(desiredSpace);
    }

    /**
     *
     * @param desiredSpace
     */
    @Override
    public void move(Space desiredSpace) {
        this.setPosX(desiredSpace.getPosX());
        this.setPosY(desiredSpace.getPosY());
    }

    @Override
    public String toString() {
        return (isHead) ? "FH" : "FT";
    }

}
