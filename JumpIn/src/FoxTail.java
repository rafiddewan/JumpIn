public class FoxTail extends Space implements Fox, MoveableSpace {

    private boolean isVertical;
    private FoxHead foxHead;

    public FoxTail(int posX, int posY){
        super(posX, posY);
    }

    public FoxTail(int posX, int posY, FoxHead foxHead){
        super(posX, posY);
        this.foxHead = foxHead;
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
