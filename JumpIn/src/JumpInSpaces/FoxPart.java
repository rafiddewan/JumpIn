/**
 * Represents a fox on the game board.
 *
 * @author Rafid
 */
package JumpInSpaces;
public class FoxPart extends Space implements MoveableSpace {

    private boolean isVertical;
    private FoxPart otherFoxPart;
    private boolean isHead;
    private enum moveDirection {INVALID, HORIZONTAL, VERTICAL}


    /**
     * Constructor for a piece of a fox.
     *
     * @param row Row position
     * @param column Column position
     * @param isHead Whether this FoxPart is the head of the fox or not
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead){
        super(row, column);
        this.isVertical = vertical;
        this.isHead = isHead;
    }

    /**
     *  Constructor for the second piece of the fox.
     *
     * @param row Row position
     * @param column Column position
     * @param foxPart The other FoxPart
     * @param isHead Whether this FoxPart is the head of the fox or not
     */
    public FoxPart(int row, int column, boolean vertical, boolean isHead, FoxPart foxPart){
        super(row, column);
        this.isVertical = vertical;
        this.isHead = isHead;
        this.otherFoxPart = foxPart;
    }
    public FoxPart(FoxPart fox){
        super(fox.getRow(),fox.getColumn());
        this.isVertical = fox.getIsVertical();
        this.isHead = fox.getIsHead();
        this.otherFoxPart = fox.getOtherFoxPart();
    }

    /**
     * Getter for the orientation of the FoxPart. True if vertical, false if horizontal
     * @return boolean
     */
    public boolean getIsVertical() {
        return isVertical;
    }

    /**
     * Getter for the FoxPart that isn't this one
     * @return FoxPart
     */
    public FoxPart getOtherFoxPart() {
        return otherFoxPart;
    }

    /**
     * Set second FoxPart to another FoxPart
     * @param otherFoxPart Another FoxPart
     */
    public void setOtherFoxPart(FoxPart otherFoxPart) {
        this.otherFoxPart = otherFoxPart;
    }

    /**
     * Getter for whether this FoxPart is a head or not. True if head, false if not
     * @return boolean
     */
    public boolean getIsHead() {
        return isHead;
    }

    /**
     * Moves both FoxParts to desired spaces
     *
     * @param desiredRow Desired vertical location of first FoxPart
     * @param desiredColumn Desired horizontal location of first FoxPart
     * @param desiredOtherRow Desired vertical location of second FoxPart
     * @param desiredOtherColumn Desired horizontal location of second FoxPart
     */
    public void moveBoth(int desiredRow, int desiredColumn, int desiredOtherRow, int desiredOtherColumn) {
        this.move(desiredRow,desiredColumn);
        otherFoxPart.move(desiredOtherRow,desiredOtherColumn);
    }

    /**
     * Moves FoxPart to a desired space
     *
     * @param desiredRow Desired vertical location of this FoxPart
     * @param desiredColumn Desired horizontal location of this FoxPart
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
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
    public boolean canFoxMove(Space foxSpace, Space desiredSpace,Board board) {
        moveDirection direction = validFoxDirection(foxSpace, desiredSpace);

        switch (direction) {
            case INVALID:
                return false;
            case HORIZONTAL:
                return foxCheckHorizontally(foxSpace, desiredSpace,board);
            case VERTICAL:
                return foxCheckVertically(foxSpace, desiredSpace,board);
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
    private boolean foxCheckHorizontally(Space foxSpace, Space desiredSpace,Board board) {
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
                Space currSpace = board.getSpace(foxRow, foxColumn + i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving left
            if (fox.getIsHead() && desiredSpace.getColumn() == 0)//since fox heads are always initialized towards the right,
                return false;
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference+1; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = board.getSpace(foxRow, foxColumn - i);
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
    private boolean foxCheckVertically(Space foxSpace, Space desiredSpace,Board board) {
        FoxPart fox = (FoxPart) foxSpace;
        int foxRow = fox.getRow();
        int foxColumn = fox.getColumn();

        int difference = foxRow - desiredSpace.getRow();

        if (difference > 0) { //moving up
            if (fox.getIsHead() && desiredSpace.getRow() == 0)//since fox heads are always initialized towards the bottom, cant move a head to the top
                return false;
            //always initialize vertical foxes with the head downwards
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference + 1; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = board.getSpace(foxRow - i, foxColumn);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving down
            difference *= -1;
            if (!fox.getIsHead() && desiredSpace.getRow() == 4)//since fox tails are always initialized towards the top, cant move it to the bottom
                return false;
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = board.getSpace(foxRow + i, foxColumn);
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
    public void moveFoxParts(FoxPart fox, Space desiredSpace, Space otherDesiredSpace,Board board) {
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
            board.setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            board.setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
            //move head
            board.setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            board.setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
        } else {//moving downwards or right, move head first
            //move head
            board.setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            board.setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
            //move tail
            board.setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            board.setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
        }
        fox.moveBoth(desiredRow, desiredColumn, otherDesiredRow, otherDesiredColumn);//set the x and y variables of the fox's parts to be correct
    }

    /**
     * The board code for this FoxPart. "FT" is Fox Tail and "FH" is Fox Head
     * @return String
     */
    @Override
    public String toString() {
        return (isHead) ? "FH" : "FT";
    }

}
