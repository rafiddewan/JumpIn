/**
 * @author Nick
 */
public class JumpIn {
    private Board board;
    int holesFilled;//maybe should be in Board

    private enum moveDirection { INVALID, HORIZONTAL, VERTICAL}


    /*private boolean canMove(Space desiredPosition){//move to JumpIn
        moveDirection direction = validSpace(desiredPosition);
        switch(direction){
            case INVALID:
                return false;
            case HORIZONTAL:
                return true;
            case VERTICAL:
                return true;
        }
    }*/

    /*private moveDirection validSpace(Space desiredSpace){ // move to JumpIn
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
        int difference = getPosX() - desiredSpace.getPosX();
        if(difference < 0){//moving right
            for(int i = 1; i <= difference; i++){//check difference blocks to the right to see if they are valid

            }
        }
        else{//moving left

        }

        return false;
    }
*/

    public static void main(String[] args){

    }
}
