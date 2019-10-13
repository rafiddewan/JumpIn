import java.util.InvalidPropertiesFormatException;

/**
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {


    private enum moveDirection { INVALID, HORIZONTAL, VERTICAL }
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

    }
    public boolean canMove(Space desiredPosition){
        moveDirection direction = validSpace(desiredPosition);
        boolean move = false;
        switch(direction){
            case INVALID:
                move = false;
            case HORIZONTAL:
                move = true;
            case VERTICAL:
                move = true;
            default:
                move = false;
        }
        return move;
    }

    private moveDirection validSpace(Space desiredSpace){ // move to JumpIn
        int currX = this.getPosX();
        int currY = this.getPosY();

        if((currX != desiredSpace.getPosX() && currY != desiredSpace.getPosY()) || (currX == desiredSpace.getPosX() && currY == desiredSpace.getPosY()))//same spot or diagonal is invalid
            return moveDirection.INVALID;
        else if(currX == desiredSpace.getPosX() && currY != desiredSpace.getPosX()){//moving horizontally
            return moveDirection.HORIZONTAL;
        }
        else{//else vertically
            return moveDirection.VERTICAL;
        }
    }

    private boolean checkHorizontally(Space desiredSpace){//move to JumpIn?
        int difference = this.getPosX() - desiredSpace.getPosX();
        if(difference < 0){//moving right
            for(int i = 1; i <= difference; i++){//check difference blocks to the right to see if they are valid

            }
        }
        else{//moving left

        }

        return false;
    }

}