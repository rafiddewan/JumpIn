/**
 * Represents a rabbit on the game board.
 * @author Nick, Rafid
 */
package JumpInSpaces;
public class Rabbit extends Space implements MoveableSpace {
    private enum moveDirection {INVALID, HORIZONTAL, VERTICAL}

    /**
     * Constructs a rabbit onto the game board
     * @param row Vertical position
     * @param column Horizontal position
     */
    public Rabbit(int row, int column) {
        super(row, column);
    }
    public Rabbit(Rabbit rabbit) {
        super(rabbit.getRow(), rabbit.getColumn());
    }


    /**
     * Moves the rabbit to a desired space on the board
     *
     * @param desiredRow Desired vertical position
     * @param desiredColumn Desired horizontal position
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
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
    public boolean canRabbitMove(Space rabbitSpace, Space desiredSpace, Board board) {//move to JumpIn
        moveDirection direction = validRabbitDirection(rabbitSpace, desiredSpace);
        switch (direction) {
            case INVALID:
                return false;
            case HORIZONTAL:
                return rabbitCheckHorizontally(rabbitSpace, desiredSpace, board);
            case VERTICAL:
                return rabbitCheckVertically(rabbitSpace, desiredSpace, board);
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
    private boolean rabbitCheckHorizontally(Space rabbitSpace, Space desiredSpace, Board board) {
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitColumn - desiredSpace.getColumn();
        int direction = -1;
        if (difference == 1 || difference == -1) return false;
        if (difference < 0) { //moving right
            difference *= -1;
            direction = 1;
        }
        for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
            Space currSpace = board.getSpace(rabbitRow, rabbitColumn + (i * direction));
            if (currSpace instanceof EmptySpace) return false;
        }//now need to check if the desiredSpace is an empty hole or an empty space
        if (desiredSpace instanceof EmptySpace)
            return true;
        else if (desiredSpace instanceof Hole) {
            Hole space = (Hole) desiredSpace;
            return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
        }
        else {
            return false;
        }
    }

    /**
     * Helper method to determine if the Vertical move is a valid move
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if rabbit can move to that spot vertically, false if the vertical move isn't valid
     */
    private boolean rabbitCheckVertically(Space rabbitSpace, Space desiredSpace, Board board) {
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitRow - desiredSpace.getRow();//difference in
        if (difference == 1 || difference == -1)
            return false;
        int direction = -1;
        if (difference < 0) { //moving down
            difference *= -1;
            direction = 1;
        }
        for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
            Space currSpace = board.getSpace(rabbitRow + (i * direction), rabbitColumn);
            if (currSpace instanceof EmptySpace)
                return false;
        }//now need to check if the desiredSpace is an empty hole or an empty space
        if (desiredSpace instanceof EmptySpace)
            return true;
        else if (desiredSpace instanceof Hole) {
            Hole space = (Hole) desiredSpace;
            return !space.getIsFilled();//if the hole is filled return false, else the rabbit can move into the hole
        }
        else {
            return false;
        }
    }

    /**
     * The board code for a Rabbit
     * @return String
     */
    @Override
    public String toString() {
        return "RA";
    }
}