/**
 * @author Nick
 */
public class JumpIn {
    private Board board;
    private boolean gameDone;

    private enum moveDirection { INVALID, HORIZONTAL, VERTICAL}

    /**
     * Constructor for a JumpIn game
     */
    public JumpIn(){
        this.board=new Board();
        this.gameDone=false;
    }

    /**
     * Gets the Board
     * @return the Board to be played on
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Method takes the current rabbit's space and the space desired to move to
     * @param rabbitSpace current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid rabbit move, false if an invalid
     */
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


    /**
     * Helper method to check which direction the rabbit will move in
     * @param rabbitSpace current rabbit's space
     * @param desiredSpace space desired to move to
     * @return INVALID if the move isn't valid, HORIZONTAL if the rabbit is moving horizontally, VERTICAL if the rabbit is moving vertically
     */
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

    /**
     * Helper method to determine if the horizontal move is a valid move
     *@param rabbitSpace current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if rabbit can move to that spot horizontally, false if the horizontal move isn't valid
     */
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

    /**
     * Helper method to determine if the Vertical move is a valid move
     *@param rabbitSpace current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if rabbit can move to that spot vertically, false if the vertical move isn't valid
     */
    private boolean rabbitCheckVertically(Space rabbitSpace, Space desiredSpace){
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

    /**
     * Method takes the current fox's space and the space desired to move to
     * @param foxSpace current fox's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid fox move, false if an invalid
     */
    private boolean canFoxMove(Space foxSpace, Space desiredSpace){
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


    /**
     * Helper method to check which direction the fox will move in
     * @param foxSpace current fox's space
     * @param desiredSpace space desired to move to
     * @return INVALID if the move isn't valid, HORIZONTAL if the fox is moving horizontally, VERTICAL if the fox is moving vertically
     */
    private moveDirection validFoxDirection(Space foxSpace,Space desiredSpace){
        FoxPart fox = (FoxPart) foxSpace;
        if(foxSpace.getPosX() == desiredSpace.getPosX() && foxSpace.getPosY() == desiredSpace.getPosY())//cant move to the spot it is at
            return moveDirection.INVALID;

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

    /**
     * Helper method to determine if the horizontal move is a valid move
     *@param foxSpace current fox's space
     * @param desiredSpace space desired to move to
     * @return true if fox can move to that spot horizontally, false if the horizontal move isn't valid
     */
    private boolean foxCheckHorizontally(Space foxSpace, Space desiredSpace){
        FoxPart fox = (FoxPart) foxSpace;
        int foxX = fox.getPosX();
        int foxY = fox.getPosY();

        int difference = foxY - desiredSpace.getPosY();
        if(difference < 0){ //moving right
            difference *= -1;
                //always initialize horizontal foxes with the head to the right
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxX, foxY + i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        }
        else{//moving left
            for(int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++){//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxX, foxY - i);
                if(!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    /**
     * Helper method to determine if the vertical move is a valid move
     * @param foxSpace current fox's space
     * @param desiredSpace space desired to move to
     * @return true if fox can move to that spot vertically, false if the vertical move isn't valid
     */
    private boolean foxCheckVertically(Space foxSpace, Space desiredSpace){
        FoxPart fox = (FoxPart) foxSpace;
        int foxX = fox.getPosX();
        int foxY = fox.getPosY();

        int difference = foxX - desiredSpace.getPosX();

        if(difference > 0){ //moving up
            //always initialize vertical foxes with the head downwards
            for(int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++){//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxX - i, foxY);
                if(!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        }
        else{//moving down
            difference *= -1;
            for(int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++){//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxX + i, foxY);
                if(!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }



    public static void main(String[] args){
        JumpIn game = new JumpIn();
        Board board = game.getBoard();
        System.out.print(board.toString());
        System.out.println(game.canFoxMove(board.getSpace(3,4),board.getSpace(3,0)));
        System.out.println(game.canFoxMove(board.getSpace(3,4),board.getSpace(3,1)));
        System.out.println(game.canFoxMove(board.getSpace(3,4),board.getSpace(3,2)));
        System.out.println(game.canFoxMove(board.getSpace(3,4),board.getSpace(3,3)));

    }
}
