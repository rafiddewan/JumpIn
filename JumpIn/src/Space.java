/**
 * Parent  class of EmptySpace, Fox, Holes, Mushroom, Rabbit, and Fox classes
 * @author Rafid Dewan
 */
public class Space {

    private int posX;
    private int posY;

    public Space(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }


    public void setPosY(int posY) {
        this.posY = posY;
    }

}