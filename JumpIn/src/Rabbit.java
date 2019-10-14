import java.util.InvalidPropertiesFormatException;

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

    @Override
    public void move(Space desiredSpace) {

    }

    @Override
    public String toString() {
        return "RA";
    }
}