/**
 * @author Rafid
 */
public class FoxTail extends Space implements Fox, MoveableSpace {

    private boolean isVertical;
    private FoxHead foxHead;

    /**
     *
     * @param posX
     * @param posY
     */
    public FoxTail(int posX, int posY, boolean vertical){
        super(posX, posY);
        this.isVertical = vertical;
    }

    /**
     *
     * @param posX
     * @param posY
     * @param foxHead
     */
    public FoxTail(int posX, int posY, boolean vertical, FoxHead foxHead){
        super(posX, posY);
        this.foxHead = foxHead;
        this.isVertical = vertical;
    }

    /**
     *
     * @return boolean
     */
    @Override
    public boolean getIsVertical() {
        return isVertical;
    }

    /**
     *
     * @param vertical
     */
    @Override
    public void setIsVertical(boolean vertical) {
        this.isVertical = vertical;
    }

    /**
     *
     * @param desiredHeadSpace
     * @param desiredTailSpace
     */
    @Override
    public void moveBoth(Space desiredHeadSpace, Space desiredTailSpace) {
        this.move(desiredTailSpace);
        foxHead.move(desiredHeadSpace);
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
        return "FT";
    }
}