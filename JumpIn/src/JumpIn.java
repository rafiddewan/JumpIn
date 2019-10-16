/**
 * @author Nick
 */
public class JumpIn {
    private Board board;
    private boolean gameDone;

    private enum moveDirection { INVALID, HORIZONTAL, VERTICAL}
    public JumpIn(){
        this.board=new Board();
    }

    public Board getBoard(){
        return this.board;
    }

    private boolean canRabbitMove(Space rabbitSpace, Space desiredSpace){//move to JumpIn
        moveDirection direction = validRabbitDirection(rabbitSpace, desiredSpace);
        switch(direction){
            case INVALID:
                return false;
            case HORIZONTAL:
                return rabbitCheckHorizontally(rabbitSpace, desiredSpace);
            case VERTICAL:
                return rabbitCheckVertically(rabbitSpace,desiredSpace);
        }
        return false; //should never reach here, but get an error without it
    }



    private moveDirection validRabbitDirection(Space rabbitSpace, Space desiredSpace){ // move to JumpIn
        int currX = rabbitSpace.getPosX();
        int currY = rabbitSpace.getPosY();

        if((currX != desiredSpace.getPosX() && currY != desiredSpace.getPosY()) || (currX == desiredSpace.getPosX() && currY == desiredSpace.getPosY()))//same spot or diagonal is invalid
            return moveDirection.INVALID;
        else if(currX == desiredSpace.getPosX() && currY != desiredSpace.getPosY()){//moving horizontally
            return moveDirection.HORIZONTAL;
        }
        else{//else vertically
            return moveDirection.VERTICAL;
        }
    }

    private boolean rabbitCheckHorizontally(Space rabbitSpace, Space desiredSpace){//move to JumpIn?
        int rabbitX = rabbitSpace.getPosX();
        int rabbitY = rabbitSpace.getPosY();

        int difference = rabbitY - desiredSpace.getPosY();
        if(difference == 1 || difference == -1)
            return false;
        if(difference < 0){ //moving right
            difference *= -1;
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
               Space currSpace = getBoard().getSpace(rabbitX, rabbitY+i);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
        else{//moving left
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitX, rabbitY-i);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)//if the current hole is empty or a hole its not a valid move
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, otherwise its empty and the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
    }
    public boolean rabbitCheckVertically(Space rabbitSpace, Space desiredSpace){
        int rabbitX = rabbitSpace.getPosX();
        int rabbitY = rabbitSpace.getPosY();

        int difference = rabbitX - desiredSpace.getPosX();//difference in
        if(difference == 1 || difference == -1)
            return false;
        if(difference < 0){ //moving down
            difference *= -1;
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitX+i, rabbitY);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
        else{//moving up
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitX-i, rabbitY);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)//if the current hole is empty or a hole its not a valid move
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, otherwise its empty and the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
    }

    /*private boolean canFoxMove(Space foxSpace, Space desiredSpace){
        moveDirection direction = validFoxDirection(foxSpace, desiredSpace);
        switch(direction){
            case INVALID:
                return false;
            case HORIZONTAL:
                return foxCheckHorizontally(foxSpace, desiredSpace);
            case VERTICAL:
                return foxCheckVertically(foxSpace,desiredSpace);
        }
        return false; //should never reach here, but get an error without it
    }
    */


    private moveDirection validFoxDirection(Space foxSpace,Space desiredSpace){
        if(foxSpace.getPosX() == desiredSpace.getPosX() && foxSpace.getPosY() == desiredSpace.getPosY())//cant move to the spot it is at
            return moveDirection.INVALID;
        if(foxSpace instanceof FoxTail){//maybe always cast it as a FoxTail since they share practically the same stuff
            FoxTail fox = (FoxTail) foxSpace;

            if(fox.getIsVertical()) {//if the fox is vertical it can only change the row(aka x) position
                if (fox.getPosY() != desiredSpace.getPosY())//cant move horizontally
                    return moveDirection.INVALID;
                else {
                    return moveDirection.VERTICAL;
                }
            }
            else{//moving horizontally can change column (aka y) position
                if(fox.getPosX() != desiredSpace.getPosX())
                    return moveDirection.INVALID;
                else{
                    return moveDirection.HORIZONTAL;
                }

            }

        }
        else{//maybe should remove and always cast to a head
            FoxHead fox = (FoxHead) foxSpace;
            if(fox.getIsVertical()) {//if the fox is vertical it can only change the row(aka x) position
                if (fox.getPosY() != desiredSpace.getPosY())//cant move horizontally
                    return moveDirection.INVALID;
                else {
                    return moveDirection.VERTICAL;
                }
            }
            else{//moving horizontally can change column (aka y) position
                if(fox.getPosX() != desiredSpace.getPosX())
                    return moveDirection.INVALID;
                else{
                    return moveDirection.HORIZONTAL;
                }

            }

        }
    }

    private boolean foxCheckHorizontally(Space foxSpace, Space desiredSpace){
        int foxX = foxSpace.getPosX();
        int foxY = foxSpace.getPosY();

        int difference = foxY - desiredSpace.getPosY();

        //all below is copy pasted from rabbit, must apply to fox
        if(difference < 0){ //moving right
            difference *= -1;
            for(int i = 1; i < difference; i++){//check blocks between the fox and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitX, rabbitY+i);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
        else{//moving left
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitX, rabbitY-i);
                if(currSpace instanceof EmptySpace || currSpace instanceof Hole)//if the current hole is empty or a hole its not a valid move
                    return false;
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if(desiredSpace instanceof EmptySpace)
                return true;
            else if(desiredSpace instanceof Hole){
                Hole space = (Hole)desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, otherwise its empty and the rabbit can move into the hole
            }
            else{
                return false;
            }
        }
        return false;//change this
    }
    private boolean foxCheckVertically(){

    }



    public static void main(String[] args){
        JumpIn game=new JumpIn();
        Board board = game.getBoard();
        System.out.println(board.toString());
        System.out.println(board.getSpace(0,0) instanceof Mushroom);
        System.out.println(board.getSpace(0,0) instanceof Hole);
        System.out.println(game.canRabbitMove(board.getSpace(0,3),board.getSpace(3,3)));
        game.validFoxDirection(board.getSpace(1,1),board.getSpace(0,0));
    }
}
