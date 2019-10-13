import java.util.InvalidPropertiesFormatException;

/**
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {

    private int rabbitNumber;

    public Rabbit(int rabbitNumber, int posX, int posY){
        super(posX, posY);
        this.rabbitNumber = rabbitNumber;
    }

    public int getRabbitNumber() {
        return rabbitNumber;
    }

    public void setRabbitNumber(int rabbitNumber) {
        this.rabbitNumber = rabbitNumber;
    }

    @Override
    public void move(Space desiredSpace) {
        this.setPosX(desiredSpace.getPosX());
        this.setPosY(desiredSpace.getPosY());
    }


}