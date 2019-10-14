/**
 * Parent  class of EmptySpace, Fox, Holes, Mushroom, Rabbit, and Fox classes
 * @author Rafid
 */
public class Space {

    private int posX;
    private int posY;

    /**
     *
     * @param posX
     * @param posY
     */
    public Space(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     *
     * @return int
     */
    public int getPosY() {
        return posY;
    }

    /***
     *
     * @return int
     */
    public int getPosX() {
        return posX;
    }

    /**
     *
     * @param posX
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     *
     * @param posY
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Space{" +
                "posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}