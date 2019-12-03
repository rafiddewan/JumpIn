import java.util.ArrayList;
import java.util.Stack;
import JumpInSpaces.*;

/**
 * JumpIn playable game mechanics
 * @author Nick, Lazar, Ben, and Rafid
 */
public class JumpInModel {
    private Board board;
    private boolean gameDone; //Game done is  true when the game is completed and false when the game is not done
    private boolean badMove; // Bad move is true then an error is triggered and when bad move is false an error does not occur and it is a valid move
    private boolean isPieceSelected; //Destination is false when the player is choosing a piece to move and true when choosing that pieces destination
    private boolean build; //build is true when in the builder view and false when in the play view
    private String buildPiece; //Two character string indicating the piece that changes the current placeable piece on the board
    private int moveRow, moveCol;
    private int buildFoxLeft,buildRabbitLeft,buildMushroomLeft;
    private ArrayList<View> views; //An array list of all the views that are subscribed to the model

    private Stack<Board> previousMoves; //contains all the previous moves
    private Stack<Board> undoneMoves; //contains all the moves that were undone

    private enum moveDirection {INVALID, HORIZONTAL, VERTICAL}

    /**
     * Constructor for a JumpIn game.
     */
    public JumpInModel() {
        board = new Board();
        gameDone = false;
        isPieceSelected = false;
        buildPiece = "";
        badMove = false;
        views = new ArrayList<>();
        build = true;
        buildFoxLeft = 2;
        buildMushroomLeft = 3;
        buildRabbitLeft = 3;
        previousMoves = new Stack<>();
        undoneMoves = new Stack<>();
        previousMoves.push(new Board(board));
    }

    /**
     * Getter for the current state of the board
     * @return The current state of the board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Setter for board, used in load
     */
    public void setBoard(Board board){
        Board b = new Board(board);
        this.board = b;
        notifyViews();
    }

    /**
     * Gets the current build piece that on the board
     * @return String of two characters of the selected build piece at hand is
     */
    public String getBuildPiece(){
        return buildPiece;
    }

    /**
     * Changes the current build piece that is selected on the board
     * Notifies the view of the level editor of the view when there is a change in the build piece selected
     * @param buildPiece is a two character string indicating the piece that changes the current placeable piece on the board
     */
    public void setBuildPiece(String buildPiece){
        this.buildPiece = buildPiece;
        notifyViews();
    }

    /**
     * Number of mushrooms remaining to place on the board
     * @return an integer of the number of mushrooms to place on the board
     */
    public int getBuildMushroomLeft() {
        return buildMushroomLeft;
    }

    /**
     * Sets the number of mushrooms remaining to place on theo board
     * Notifies the level editor view when there is a change in the number of mushrooms to place on the board
     * @param buildMushroomLeft is an integer indicating the number of mushrooms that are left to be placed on the board
     */
    public void setBuildMushroomLeft(int buildMushroomLeft) {
        this.buildMushroomLeft = buildMushroomLeft;
        notifyViews();
    }

    /**
     * Number of rabbits remaining to place on the board
     * @return an integer of the number of rabbits to place on the board
     */
    public int getBuildRabbitLeft() {
        return buildRabbitLeft;
    }

    /**
     * Sets the number of rabbits remaining to place on theo board
     * Notifies the level editor view when there is a change in the number of rabbits to place on the board
     * @param buildRabbitLeft is an integer indicating the number of rabbits that are left to be placed on the board
     */
    public void setBuildRabbitLeft(int buildRabbitLeft) {
        this.buildRabbitLeft = buildRabbitLeft;
        notifyViews();
    }

    /**
     * Number of foxes remaining to place on the board
     * @return an integer of the number of foxes to place on the board
     */
    public int getBuildFoxLeft() {
        return buildFoxLeft;
    }

    /**
     * Sets the number of foxes remaining to place on theo board
     * Notifies the level editor view when there is a change in the number of foxes to place on the board
     * @param buildFoxLeft is an integer indicating the number of foxes that are left to be placed on the board
     */
    public void setBuildFoxLeft(int buildFoxLeft) {
        this.buildFoxLeft = buildFoxLeft;
        notifyViews();
    }

    /**
     * Returns the state if its in level editor view or play view
     * @return boolean value of build which determines if its in the level editor or paly view
     */
    public boolean isBuild(){return build;}

    /**
     * Sets the state to level editor or play view
     * Notifies view when change occurs
     * @param build is a boolean value that is set to
     */
    public void setBuild(boolean build) {
        this.build = build;
        notifyViews();
    }

    /**
     * Returns the boolean value if the game is done or not
     * @return boolean
     */
    public boolean isGameDone() {
        return gameDone;
    }

    /**
     * Sets the destination whether a player is choosing a space to move or selecting a piece to move
     * @param pieceSelected is true when choosing a piece's destination and is false when the player is choosing a piece
     */
    public void setPieceSelected(boolean pieceSelected) {
        this.isPieceSelected = pieceSelected;
        notifyViews();
    }

    /**
     * Changes the current status of the game to completion
     * @param gameDone is a boolean when true sets the game to be done and false if the game is still ongoing
     */
    public void setGameDone(boolean gameDone) {
        this.gameDone = gameDone;
        notifyViews();
    }

    /**
     * Returns the column for the piece to move towards
     * @return int which is the corresponding column of the new destination for that piece
     */
    public int getMoveCol() {
        return moveCol;
    }

    /**
     * Sets the move to be invalid or not and notifies the views once changed
     * @param badMove is a boolean parameter and when true, notifies the view to put out an error message
     */
    public void setBadMove(boolean badMove) {
        this.badMove = badMove;
    }

    /**
     * Returns a boolean if the move was invalid or not
     * @return boolean
     */
    public boolean isBadMove() {
        return badMove;
    }

    /**
     * Returns the row for the piece to move towards
     * @return int which is the corresponding row of the new destination for that piece
     */
    public int getMoveRow() {
        return moveRow;
    }

    /**
     * Moves the piece tot he selected column
     * @param moveCol
     */
    public void setMoveCol(int moveCol) {
        this.moveCol = moveCol;
        //notifyViews();
    }

    /**
     * Moves the piece to the selected row
     * @param moveRow
     */
    public void setMoveRow(int moveRow) {
        this.moveRow = moveRow;
        //notifyViews();
    }


    /* RABBIT METHODS
     * @author Nick
     */
    /**
     * Method takes the current rabbit's space and the space desired to move to and determines if the rabbit can move there
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid rabbit move, false if an invalid
     */
    public boolean canRabbitMove(Space rabbitSpace, Space desiredSpace) {//move to JumpIn
        moveDirection direction = validRabbitDirection(rabbitSpace, desiredSpace);
        switch (direction) {
            case INVALID:
                return false;
            case HORIZONTAL:
                return rabbitCheckHorizontally(rabbitSpace, desiredSpace);
            case VERTICAL:
                return rabbitCheckVertically(rabbitSpace, desiredSpace);
        }
        return false; //should never reach here, but get an error without it
    }


    /**
     * Helper method to check which direction the rabbit will move in
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return INVALID if the move isn't valid, HORIZONTAL if the rabbit is moving horizontally, VERTICAL if the rabbit is moving vertically
     */
    private moveDirection validRabbitDirection(Space rabbitSpace, Space desiredSpace) {
        int currRow = rabbitSpace.getRow();
        int currCol = rabbitSpace.getColumn();

        if ((currRow != desiredSpace.getRow() && currCol != desiredSpace.getColumn()) || (currRow == desiredSpace.getRow() && currCol == desiredSpace.getColumn()))//same spot or diagonal is invalid
            return moveDirection.INVALID;
        else if (currRow == desiredSpace.getRow() && currCol != desiredSpace.getColumn()) {//moving horizontally
            return moveDirection.HORIZONTAL;
        } else {//else vertically
            return moveDirection.VERTICAL;
        }
    }

    /**
     * Helper method to determine if the horizontal move is a valid move
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if rabbit can move to that spot horizontally, false if the horizontal move isn't valid
     */
    private boolean rabbitCheckHorizontally(Space rabbitSpace, Space desiredSpace) {
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitColumn - desiredSpace.getColumn();
        if (difference == 1 || difference == -1)
            return false;
        if (difference < 0) { //moving right
            difference *= -1;
            for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitRow, rabbitColumn + i);
                if (currSpace instanceof EmptySpace){
                    return false;
                }
                if(currSpace instanceof Hole && !((Hole) currSpace).getIsFilled()){
                    return false;
                }
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if (desiredSpace instanceof EmptySpace)
                return true;
            else if (desiredSpace instanceof Hole) {
                Hole space = (Hole) desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
            } else {
                return false;
            }
        } else {//moving left
            for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = board.getSpace(rabbitRow, rabbitColumn - i);
                if (currSpace instanceof EmptySpace) {//if the current hole is empty or a hole its not a valid move
                    return false;
                }
                if(currSpace instanceof Hole && !((Hole) currSpace).getIsFilled()){
                    return false;
                }
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if (desiredSpace instanceof EmptySpace)
                return true;
            else if (desiredSpace instanceof Hole) {
                Hole space = (Hole) desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, otherwise its empty and the rabbit can move into the hole
            } else {
                return false;
            }
        }
    }

    /**
     * Helper method to determine if the Vertical move is a valid move
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if rabbit can move to that spot vertically, false if the vertical move isn't valid
     */
    private boolean rabbitCheckVertically(Space rabbitSpace, Space desiredSpace) {
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitRow - desiredSpace.getRow();//difference in
        if (difference == 1 || difference == -1)
            return false;
        if (difference < 0) { //moving down
            difference *= -1;
            for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitRow + i, rabbitColumn);
                if (currSpace instanceof EmptySpace) {
                    return false;
                }
                if (currSpace instanceof Hole && !((Hole) currSpace).getIsFilled()) {
                    return false;
                }
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if (desiredSpace instanceof EmptySpace)
                return true;
            else if (desiredSpace instanceof Hole) {
                Hole space = (Hole) desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
            } else {
                return false;
            }
        } else {//moving up
            for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = getBoard().getSpace(rabbitRow - i, rabbitColumn);
                if (currSpace instanceof EmptySpace) {//if the current hole is empty or a hole its not a valid move
                    return false;
                }
                if (currSpace instanceof Hole && !((Hole) currSpace).getIsFilled()) {
                    return false;
                }
            }//now need to check if the desiredSpace is an empty hole or an empty space
            if (desiredSpace instanceof EmptySpace)
                return true;
            else if (desiredSpace instanceof Hole) {
                Hole space = (Hole) desiredSpace;
                return !space.getIsFilled();//if the hole is filled return false, otherwise its empty and the rabbit can move into the hole
            } else {
                return false;
            }
        }
    }


    /*FOX METHODS
     * @author Nick
     */
    /**
     * Method takes the current fox's space and the space desired to move to and determines if the move is valid or not
     *
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid fox move, false if an invalid
     */
    public boolean canFoxMove(Space foxSpace, Space desiredSpace) {
        moveDirection direction = validFoxDirection(foxSpace, desiredSpace);

        switch (direction) {
            case INVALID:
                return false;
            case HORIZONTAL:
                return foxCheckHorizontally(foxSpace, desiredSpace);
            case VERTICAL:
                return foxCheckVertically(foxSpace, desiredSpace);
        }
        return false; //should never reach here, but get an error without it
    }


    /**
     * Method to check which direction the fox will move in
     *
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return INVALID if the move isn't valid, HORIZONTAL if the fox is moving horizontally, VERTICAL if the fox is moving vertically
     */
    private moveDirection validFoxDirection(Space foxSpace, Space desiredSpace) {
        FoxPart fox = (FoxPart) foxSpace;
        if (foxSpace.getRow() == desiredSpace.getRow() && foxSpace.getColumn() == desiredSpace.getColumn())//cant move to the spot it is at
            return moveDirection.INVALID;

        if (fox.getIsVertical()) {//if the fox is vertical it can only change the row position
            if (fox.getColumn() != desiredSpace.getColumn())//cant move horizontally
                return moveDirection.INVALID;
            else {
                return moveDirection.VERTICAL;
            }
        } else {//moving horizontally can change column position
            if (fox.getRow() != desiredSpace.getRow())
                return moveDirection.INVALID;
            else {
                return moveDirection.HORIZONTAL;
            }
        }
    }

    /**
     * Method to determine if the horizontal move is a valid move
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return true if fox can move to that spot horizontally, false if the horizontal move isn't valid
     */
    private boolean foxCheckHorizontally(Space foxSpace, Space desiredSpace) {
        FoxPart fox = (FoxPart) foxSpace;
        int foxRow = fox.getRow();
        int foxColumn = fox.getColumn();

        int difference = foxColumn - desiredSpace.getColumn();
        if (difference < 0) { //moving right
            difference *= -1;
            //always initialize horizontal foxes with the head to the right
            if (!fox.getIsHead() && desiredSpace.getColumn() == 4)//since fox tails are always initialized towards the left, cant move it to the right
                return false;
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow, foxColumn + i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving left
            if (fox.getIsHead() && desiredSpace.getColumn() == 0)//since fox heads are always initialized towards the right,
                return false;
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference+1; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow, foxColumn - i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    /**
     * Method to determine if the vertical move is a valid move
     *
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return true if fox can move to that spot vertically, false if the vertical move isn't valid
     */
    private boolean foxCheckVertically(Space foxSpace, Space desiredSpace) {
        FoxPart fox = (FoxPart) foxSpace;
        int foxRow = fox.getRow();
        int foxColumn = fox.getColumn();

        int difference = foxRow - desiredSpace.getRow();

        if (difference > 0) { //moving up
            if (fox.getIsHead() && desiredSpace.getRow() == 0)//since fox heads are always initialized towards the bottom, cant move a head to the top
                return false;
            //always initialize vertical foxes with the head downwards
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference + 1; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow - i, foxColumn);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving down
            difference *= -1;
            if (!fox.getIsHead() && desiredSpace.getRow() == 4)//since fox tails are always initialized towards the top, cant move it to the bottom
                return false;
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = getBoard().getSpace(foxRow + i, foxColumn);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    /**
     * Method that will move the FoxPart and the associated other FoxPart to the desired spaces
     * Sets the spaces they move from to new Empty Spaces
     *
     * @param fox               the FoxPart that will be moved to desiredSpace, always pass a fox head
     * @param desiredSpace      the Space that fox head will move to
     * @param otherDesiredSpace the Space that the fox's associated tail will move to
     */
    private void moveFoxParts(FoxPart fox, Space desiredSpace, Space otherDesiredSpace) {
        int headColumn = fox.getColumn();//original fox column
        int headRow = fox.getRow();//original fox clear
        int tailColumn = fox.getOtherFoxPart().getColumn();//other fox's original column
        int tailRow = fox.getOtherFoxPart().getRow();//other fox's original row
        int desiredRow = desiredSpace.getRow();//the row head is moving to
        int desiredColumn = desiredSpace.getColumn();//the column head is moving to
        int otherDesiredRow = otherDesiredSpace.getRow();//the row tail is moving to
        int otherDesiredColumn = otherDesiredSpace.getColumn();//the column tail is moving to

        if ((fox.getIsVertical() && (desiredRow < headRow)) || (!fox.getIsVertical() && (desiredColumn < headColumn))) {// moving up or left, move tail first
            //move tail
            getBoard().setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            getBoard().setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
            //move head
            getBoard().setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            getBoard().setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
        } else {//moving downwards or right, move head first
            //move head
            getBoard().setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            getBoard().setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
            //move tail
            getBoard().setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            getBoard().setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
        }
        fox.moveBoth(desiredRow, desiredColumn, otherDesiredRow, otherDesiredColumn);//set the x and y variables of the fox's parts to be correct
    }

    /**
     * Selects or Moves the piece depending if the piece is selected
     * @param row is the row you would like to move
     * @param column is the column you would like to move
     */
    public void takeTurn(int row, int column){
        Space moveSpace = getBoard().getSpace(getMoveRow(), getMoveCol()); //Piece to move
        Space destSpace = getBoard().getSpace(row, column); //Space to move to
        //Move Rabbit Piece
        if (moveSpace instanceof Rabbit) {
            //Move rabbit if its a valid space
            if (canRabbitMove(moveSpace, destSpace)) {
                //Move the rabbit to an empty space
                if (destSpace instanceof EmptySpace) {
                    ((Rabbit) moveSpace).move(row, column);
                    getBoard().setSpace(row, column, moveSpace);
                }
                //move the rabbit to fill a hole
                else {
                    ((Hole) destSpace).setIsFilled(true);
                    getBoard().decrementHolesEmpty();
                }
                board.setSpace(getMoveRow(), getMoveCol(), new EmptySpace(getMoveRow(), getMoveCol()));
                previousMoves.push(new Board(board));
                undoneMoves.clear();//clear undone moves if a valid move is made
            }
            //Invalid space to move Rabbit otherwise
            else {
                setBadMove(true);
            }
        }
        //Move Fox Piece
        else if (moveSpace instanceof FoxPart) {
            //Check to see if the Fox's space for it to move is valid or not
            if (canFoxMove(moveSpace, destSpace)) {
                //Can move only the head of the fox at this time
                if (((FoxPart) moveSpace).getIsHead()) {
                    //Move the vertical moving fox
                    if (((FoxPart) moveSpace).getIsVertical()) {
                        moveFoxParts((FoxPart) moveSpace, destSpace, getBoard().getSpace(row - 1, column));
                    }
                    //Move the fox head horizontally
                    else {
                        moveFoxParts((FoxPart) moveSpace, destSpace, getBoard().getSpace(row, column - 1));
                    }
                }
                //Move tail otherwise (NOT WORKING)
                else {
                    //Move tail for moving fox vertically
                    if (((FoxPart) moveSpace).getIsVertical()) {
                        moveFoxParts(((FoxPart) moveSpace).getOtherFoxPart(), destSpace, getBoard().getSpace(row + 1, column));
                    }
                    //Moe tail for moving fox horizontally
                    else {
                        moveFoxParts(((FoxPart) moveSpace).getOtherFoxPart(), destSpace, getBoard().getSpace(row, column + 1));
                    }
                }
                previousMoves.push(new Board(board));
                undoneMoves.clear();//clear undone moves if a valid move is made
            }
            //Invalid space to move fox otherwise
            else {
                setBadMove(true);
            }
        }
        setPieceSelected(false);
    }

    /**
     * Method that changes the board to the previous board (undoing the last move made) if not on the original board
     * @return true if the board was changed to the previous board, false if cannot change the board since the stack only contains it
     */
    public boolean undoMove(){
        if(previousMoves.size() == 1){//stack only contains the original board
            return false;
        }
        else {
            undoneMoves.push(previousMoves.pop());//add board to be undone to the undone stack
            this.board = new Board(previousMoves.peek());
            notifyViews();
            return true;
        }
    }

    /**
     *  Method that changes the board to the last undone board (redoing the last undo) if undo's have been made
     * @return true if the board has changed to the last undone board, false if no moves have been undone or a move has been made since undoing the board
     */
    public boolean redoMove(){
        if(undoneMoves.isEmpty()){
            return false;
        }
        else{
            Board redone = undoneMoves.pop();
            board = redone;
            previousMoves.push(new Board(redone));
            notifyViews();
            return true;
        }
    }

    /**
     * Gets the previousMoves Stack which contains boards after each move
     * @return previousMoves Stack of Boards
     */
    public Stack<Board> getPreviousMoves(){
        return previousMoves;
    }

    /**
     * Gets the undoneMoves Stack (used for the redo operations) which contains each board that was undone
     * @return the undoneMoves Stack of Boards
     */
    public Stack<Board> getUndoneMoves(){
        return undoneMoves;
    }

    /**
     * Returns the state if the game is prompting a player to select a piece or a space to move to
     * @return
     */
    public boolean isPieceSelected() {
        return isPieceSelected;
    }

    /**
     * Essentially notifies all the views when a change has been done to the model using the update method from the view interface
     */
    public void notifyViews(){
        for(View view : views){
            view.update(this);
        }
    }

    /**
     * Provides the hints of what to move when solving the game
     * @return String which is the hints to solve the puzzle
     */
    public String solutionString(){
        JumpInSolver solver = new JumpInSolver();
        return solver.toStringDefault();
    }

    /**
     * Adding a view to the ArrayList of views
     * @param newView is the view to be added in views
     */
    public void addView(View newView){
        views.add(newView);
    }

    /**
     *
     */
    public void clearPlay(){
        this.setGameDone(false);
        this.setBadMove(false);
        this.setBuild(true);
        this.getPreviousMoves().clear();
        this.getUndoneMoves().clear();
    }

}
