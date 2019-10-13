import java.util.InvalidPropertiesFormatException;

/**
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {


    public Rabbit(int posX, int posY){
        super(posX, posY);
    }


    @Override
    public void move(Space desiredSpace) {
        this.setPosX(desiredSpace.getPosX());
        this.setPosY(desiredSpace.getPosY());
    }


}