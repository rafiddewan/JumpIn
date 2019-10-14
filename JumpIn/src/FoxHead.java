/**
 * @author Rafid
 */
public class FoxHead extends Space implements Fox, MoveableSpace {

    private boolean isVertical;
    private FoxTail foxTail;

    /**
     *
     * @param posX
     * @param posY
     * @param vertical
     */
    public FoxHead(int posX, int posY, boolean vertical) {
        super(posX, posY);
        this.isVertical = vertical;
    }

    /**
     *
     * @param posX
     * @param posY
     * @param vertical
     * @param foxTail
     */
    public FoxHead(int posX, int posY, boolean vertical, FoxTail foxTail) {
        super(posX, posY);
        this.foxTail = foxTail;
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
        this.move(desiredHeadSpace);
        foxTail.move(desiredTailSpace);
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
}