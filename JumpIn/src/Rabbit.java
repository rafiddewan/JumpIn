/**
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {

    /**
     *
     * @param posX
     * @param posY
     */
    public Rabbit(int posX, int posY){
        super(posX, posY);
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

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "RA";
    }
}