import java.util.Scanner;

/**
 * @author Nick
 */
public class JumpIn {
    private Board board;
    private boolean gameDone;

    private enum moveDirection { INVALID, HORIZONTAL, VERTICAL}

    /**
     * Constructor for a JumpIn game.
     */
    public JumpIn(){
        this.board = new Board();
        this.gameDone = false;
    }

    /**
     * Gets the Board
     * @return the Board to be played on
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Method takes the current rabbit's space and the space desired to move to and determines if the rabbit can move there
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
        int currRow = rabbitSpace.getRow();
        int currCol = rabbitSpace.getColumn();

        if((currRow != desiredSpace.getRow() && currCol != desiredSpace.getColumn()) || (currRow == desiredSpace.getRow() && currCol == desiredSpace.getColumn()))//same spot or diagonal is invalid
            return moveDirection.INVALID;
        else if(currRow == desiredSpace.getRow() && currCol != desiredSpace.getColumn()){//moving horizontally
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
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitColumn - desiredSpace.getColumn();
        if(difference == 1 || difference == -1)
            return false;
        if(difference < 0){ //moving right
            difference *= -1;
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
               Space currSpace = getBoard().getSpace(rabbitRow, rabbitColumn + i);
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
                Space currSpace = getBoard().getSpace(rabbitRow, rabbitColumn - i);
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
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitRow - desiredSpace.getRow();//difference in
        if(difference == 1 || difference == -1)
            return false;
        if(difference < 0){ //moving down
            difference *= -1;
            for(int i = 1; i < difference; i++){//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitRow + i, rabbitColumn);
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
                Space currSpace = getBoard().getSpace(rabbitRow-i, rabbitColumn);
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
     * Method takes the current fox's space and the space desired to move to and determines if the move is valid or not
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
        if(foxSpace.getRow() == desiredSpace.getRow() && foxSpace.getColumn() == desiredSpace.getColumn())//cant move to the spot it is at
            return moveDirection.INVALID;

        if(fox.getIsVertical()) {//if the fox is vertical it can only change the row(aka x) position
            if (fox.getColumn() != desiredSpace.getColumn())//cant move horizontally
                return moveDirection.INVALID;
            else {
                return moveDirection.VERTICAL;
            }
        }
        else{//moving horizontally can change column (aka y) position
            if(fox.getRow() != desiredSpace.getRow())
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
        int foxRow = fox.getRow();
        int foxColumn = fox.getColumn();

        int difference = foxColumn - desiredSpace.getColumn();
        if(difference < 0){ //moving right
            difference *= -1;
                //always initialize horizontal foxes with the head to the right
            if(!fox.getIsHead() && desiredSpace.getColumn() == 4)//since fox tails are always initialized towards the left, cant move it to the right
                return false;
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow, foxColumn + i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        }
        else{//moving left
            if(fox.getIsHead() && desiredSpace.getColumn() == 0)//since fox heads are always initialized towards the right, cant move it to the left
                return false;
            for(int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++){//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow, foxColumn - i);
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
        int foxRow = fox.getRow();
        int foxColumn = fox.getColumn();

        int difference = foxRow - desiredSpace.getRow();

        if(difference > 0){ //moving up
            if(fox.getIsHead() && desiredSpace.getRow() == 0)//since fox heads are always initialized towards the bottom, cant move to the top
                return false;
            //always initialize vertical foxes with the head downwards
            for(int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++){//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow - i, foxColumn);
                if(!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        }
        else{//moving down
            difference *= -1;
            if(!fox.getIsHead() && desiredSpace.getRow() == 4)//since fox tails are always initialized towards the top, cant move it to the bottom
                return false;
            for(int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++){//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow + i, foxColumn);
                if(!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    public void play(){
        Scanner input = new Scanner(System.in);
        while(!gameDone){
            System.out.print(board.toString());
            System.out.println("What would you like to move. Enter row then column: ");
            int movingRow = input.nextInt();
            int movingColumn = input.nextInt();
            System.out.println("Current: "+ movingRow + " " + movingColumn);
            input.nextLine();
            if(movingRow < 0 || movingRow > 4 || movingColumn < 0|| movingColumn > 4) {
                System.out.println("Row and column must be values from 0 to 4 inclusive (0 1 2 3 4)");
                continue;
            }
            if(!(board.getSpace(movingRow,movingColumn) instanceof MoveableSpace)){
                System.out.println("That piece cannot be moved");
                continue;
            }

            System.out.println("Where would you like to move it to. Enter row then column: ");
            int desiredRow = input.nextInt();
            int desiredColumn = input.nextInt();
            System.out.println("Current: "+ desiredRow + " " + desiredColumn);
            input.nextLine();

            if(desiredRow < 0 || desiredRow > 4 || desiredColumn < 0|| desiredColumn > 4) {//out of bounds check
                System.out.println("Row and column must be values from 0 to 4 inclusive (0 1 2 3 4)");
                continue;
            }
            //need to check the space and if its a good move.
            Space movingSpace = board.getSpace(movingRow,movingColumn);
            Space desiredSpace = board.getSpace(desiredRow,desiredColumn);
            if(movingSpace instanceof Rabbit){//if trying to move a rabbit
                if(canRabbitMove(movingSpace,desiredSpace)){//if its a valid move
                    if(desiredSpace instanceof EmptySpace){//if moving to an empty space, swap the positions and change the Space's row and column
                        ((Rabbit) movingSpace).move(desiredSpace);
                        board.setSpace(desiredRow,desiredColumn,movingSpace);//set destination to be rabbit
                        board.setSpace(movingRow,movingColumn,new EmptySpace(movingRow,movingColumn));

                    }
                    else{//moving to a hole, remove rabbit, close hole, increment holes filled
                        ((Hole) desiredSpace).setIsFilled(true);//set the hole to filled
                        board.setSpace(movingRow,movingColumn,new EmptySpace(movingRow,movingColumn));//removes rabbit
                        board.setHolesFilled(board.getHolesFilled()+1);
                        if(board.getHolesFilled() == 3)
                            gameDone = true;
                    }
                }
                else{
                    System.out.println("Can't move rabbit to desired location");
                }

            }
            else if(movingSpace instanceof FoxPart){
                if(canFoxMove(movingSpace,desiredSpace)){//if the fox is moving to a valid space
                    if(((FoxPart) movingSpace).getIsHead()){//if the selected space was a fox head move both the head and tail
                        if(((FoxPart) movingSpace).getIsVertical()){//if its a vertical fox head
                            ((FoxPart) movingSpace).moveBoth(desiredSpace,board.getSpace(desiredRow-1,desiredColumn));//move the head to the desired spot and the tail 1 above
                            board.setSpace(desiredRow, desiredColumn, movingSpace);//set the board at desiredSpace to be the fox head
                            board.setSpace(desiredRow - 1, desiredColumn, ((FoxPart)movingSpace).getOtherFoxPart());//set the board at (desiredRow-1,desiredColumn) to be the fox tail
                            board.setSpace(movingRow, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                            board.setSpace(movingRow - 1, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                        }
                        else{//if its a horizontal fox head
                            ((FoxPart) movingSpace).moveBoth(desiredSpace,board.getSpace(desiredRow,desiredColumn - 1));//move the head to the desired spot and the tail 1 above
                            board.setSpace(desiredRow, desiredColumn, movingSpace);//set the board at desiredSpace to be the fox head
                            board.setSpace(desiredRow, desiredColumn - 1, ((FoxPart)movingSpace).getOtherFoxPart());//set the board at (desiredRow,desiredColumn-1) to be the fox tail
                            board.setSpace(movingRow, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                            board.setSpace(movingRow, movingColumn - 1, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                        }


                    }
                    else{//if the selected space was a fox tail
                        if(((FoxPart) movingSpace).getIsVertical()) {//if its a vertical fox tail
                            ((FoxPart) movingSpace).moveBoth(desiredSpace, board.getSpace(desiredRow + 1, desiredColumn));//move the tail to the desired spot and the head 1 below
                            board.setSpace(desiredRow, desiredColumn, movingSpace);//set the board at desiredSpace to be the fox tail
                            board.setSpace(desiredRow + 1, desiredColumn, ((FoxPart) movingSpace).getOtherFoxPart());//set the board at (desiredRow+1, desiredColumn) to be the fox tail
                            board.setSpace(movingRow, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                            board.setSpace(movingRow + 1, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                        }
                        else{//if its a horizontal Fox Tail
                            ((FoxPart) movingSpace).moveBoth(desiredSpace,board.getSpace(desiredRow,desiredColumn + 1));//move the tail to the desired spot and the head 1 to the right
                            board.setSpace(desiredRow, desiredColumn, movingSpace);//set the board at desiredSpace to be the fox tail
                            board.setSpace(desiredRow, desiredColumn + 1, ((FoxPart)movingSpace).getOtherFoxPart());//set the board at (desiredRow, desiredColumn+1) to be the fox tail
                            board.setSpace(movingRow, movingColumn, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                            board.setSpace(movingRow, movingColumn + 1, new EmptySpace(movingRow, movingColumn));//set the fox's old spots to be empty spaces
                        }
                    }

                }
                else{
                    System.out.println("Can't move fox to desired location");
                }
            }
        }
        if(board.getHolesFilled()==3){
            System.out.println("You win!");
        }
    }



    public static void main(String[] args){
        JumpIn game = new JumpIn();
        game.play();
    }
}
