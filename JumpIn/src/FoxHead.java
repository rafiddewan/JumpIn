public class FoxHead extends Space implements Fox, MoveableSpace {

    private boolean isVertical;
    private FoxTail foxTail;

    public FoxHead(int posX, int posY) {
        super(posX, posY);
    }

    public FoxHead(int posX, int posY, FoxTail foxTail) {
        super(posX, posY);
        this.foxTail = foxTail;
    }

    @Override
    public boolean getIsVertical() {
        return isVertical;
    }

    @Override
    public void setIsVertical(boolean vertical) {
        this.isVertical = vertical;
    }

    @Override
    public void move(Space desiredSpace) {

    }
}
