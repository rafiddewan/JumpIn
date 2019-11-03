/**
 * @author Benjamin Ransom
 */

import javax.swing.*;
import java.awt.*;

public class JIMController {
    private JumpInModel model;
    private JIMView view;

    private enum moveDirection {INVALID, HORIZONTAL, VERTICAL}

    public JIMController(JumpInModel m, JIMView v) {
        this.model = m;
        this.view = v;
    }



    private void initController() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                int finalRow = row;
                int finalColumn = column;
                view.getButtons()[row][column].addActionListener(e -> selectSpace(finalRow, finalColumn));

            }
        }
    }

    private void handlePopup(){
        if(model.isGameDone()){
            System.exit(0);
        }else if(model.isBadMove()){
            model.setBadMove(false);
            model.setDestination(false);
        }
    }

    private void selectSpace(int row, int column) {
        model.setBadMove(false);

        if (!model.isDestination()) {
            model.setMoveRow(row);
            model.setMoveCol(column);
            model.setDestination(true);

        } else {
            Space moveSpace = model.getBoard().getSpace(model.getMoveRow(), model.getMoveCol());
            Space destSpace = model.getBoard().getSpace(row, column);
            if (moveSpace instanceof Rabbit) {
                if (canRabbitMove(moveSpace, destSpace)) {
                    if (destSpace instanceof EmptySpace) {
                        ((Rabbit) moveSpace).move(row, column);
                        model.getBoard().setSpace(row, column, moveSpace);
                        model.getBoard().setSpace(model.getMoveRow(), model.getMoveCol(), new EmptySpace(model.getMoveRow(), model.getMoveCol()));
                    } else {
                        ((Hole) destSpace).setIsFilled(true);
                        model.getBoard().setSpace(model.getMoveRow(), model.getMoveCol(), new EmptySpace(model.getMoveRow(), model.getMoveCol()));
                        model.getBoard().incrementHolesFilled();
                    }
                } else {
                    model.setBadMove(true);
                }
            } else if (moveSpace instanceof FoxPart) {
                if (canFoxMove(moveSpace, destSpace)) {
                    if (((FoxPart) moveSpace).getIsHead()) {
                        if (((FoxPart) moveSpace).getIsVertical()) {
                            moveFoxParts((FoxPart) moveSpace, destSpace, model.getBoard().getSpace(row - 1, column));
                        } else {
                            moveFoxParts((FoxPart) moveSpace, destSpace, model.getBoard().getSpace(row, column - 1));
                        }
                    } else {
                        if (((FoxPart) moveSpace).getIsVertical()) {
                            moveFoxParts(((FoxPart) moveSpace).getOtherFoxPart(), destSpace, model.getBoard().getSpace(row + 1, column));
                        } else {
                            moveFoxParts(((FoxPart) moveSpace).getOtherFoxPart(), destSpace, model.getBoard().getSpace(row, column + 1));
                        }
                    }


                } else {
                    model.setBadMove(true);
                }

            }


            model.setDestination(false);
        }
        if (model.getBoard().getHolesFilled() == 3) {
            model.setGameDone(true);
        }
    }


/** RABBIT METHODS
 * @author Nick
 */


    /**
     * Method takes the current rabbit's space and the space desired to move to and determines if the rabbit can move there
     *
     * @param rabbitSpace  current rabbit's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid rabbit move, false if an invalid
     */
    private boolean canRabbitMove(Space rabbitSpace, Space desiredSpace) {//move to JumpIn
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
    private moveDirection validRabbitDirection(Space rabbitSpace, Space desiredSpace) { // move to JumpIn
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
    private boolean rabbitCheckHorizontally(Space rabbitSpace, Space desiredSpace) {//move to JumpIn?
        int rabbitRow = rabbitSpace.getRow();
        int rabbitColumn = rabbitSpace.getColumn();

        int difference = rabbitColumn - desiredSpace.getColumn();
        if (difference == 1 || difference == -1)
            return false;
        if (difference < 0) { //moving right
            difference *= -1;
            for (int i = 1; i < difference; i++) {//check blocks between the rabbit and desiredSpace to see if they can be jumped
                Space currSpace = model.getBoard().getSpace(rabbitRow, rabbitColumn + i);
                if (currSpace instanceof EmptySpace || currSpace instanceof Hole)
                    return false;
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
                Space currSpace = model.getBoard().getSpace(rabbitRow, rabbitColumn - i);
                if (currSpace instanceof EmptySpace || currSpace instanceof Hole)//if the current hole is empty or a hole its not a valid move
                    return false;
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
                Space currSpace = model.getBoard().getSpace(rabbitRow + i, rabbitColumn);
                if (currSpace instanceof EmptySpace || currSpace instanceof Hole)
                    return false;
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
                Space currSpace = model.getBoard().getSpace(rabbitRow - i, rabbitColumn);
                if (currSpace instanceof EmptySpace || currSpace instanceof Hole)//if the current hole is empty or a hole its not a valid move
                    return false;
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


/**FOX METHODS
 * @author Nick
 */

    /**
     * Method takes the current fox's space and the space desired to move to and determines if the move is valid or not
     *
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return true if the move is a valid fox move, false if an invalid
     */
    private boolean canFoxMove(Space foxSpace, Space desiredSpace) {
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
     * Helper method to check which direction the fox will move in
     *
     * @param foxSpace     current fox's space
     * @param desiredSpace space desired to move to
     * @return INVALID if the move isn't valid, HORIZONTAL if the fox is moving horizontally, VERTICAL if the fox is moving vertically
     */
    private moveDirection validFoxDirection(Space foxSpace, Space desiredSpace) {
        FoxPart fox = (FoxPart) foxSpace;
        if (foxSpace.getRow() == desiredSpace.getRow() && foxSpace.getColumn() == desiredSpace.getColumn())//cant move to the spot it is at
            return moveDirection.INVALID;

        if (fox.getIsVertical()) {//if the fox is vertical it can only change the row(aka x) position
            if (fox.getColumn() != desiredSpace.getColumn())//cant move horizontally
                return moveDirection.INVALID;
            else {
                return moveDirection.VERTICAL;
            }
        } else {//moving horizontally can change column (aka y) position
            if (fox.getRow() != desiredSpace.getRow())
                return moveDirection.INVALID;
            else {
                return moveDirection.HORIZONTAL;
            }
        }
    }

    /**
     * Helper method to determine if the horizontal move is a valid move
     *
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
                Space currSpace = model.getBoard().getSpace(foxRow, foxColumn + i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving left
            if (fox.getIsHead() && desiredSpace.getColumn() == 0)//since fox heads are always initialized towards the right, cant move it to the left
                return false;
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = model.getBoard().getSpace(foxRow, foxColumn - i);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    /**
     * Helper method to determine if the vertical move is a valid move
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
            if (fox.getIsHead() && desiredSpace.getRow() == 0)//since fox heads are always initialized towards the bottom, cant move to the top
                return false;
            //always initialize vertical foxes with the head downwards
            for (int i = (fox.getIsHead() ? 2 : 1); i <= difference; i++) {//check blocks between the fox and desiredSpace to make sure they are empty spaces
                Space currSpace = model.getBoard().getSpace(foxRow - i, foxColumn);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;//it checked if all spaces between the fox tail and desired space are empty
        } else {//moving down
            difference *= -1;
            if (!fox.getIsHead() && desiredSpace.getRow() == 4)//since fox tails are always initialized towards the top, cant move it to the bottom
                return false;
            for (int i = (fox.getIsHead() ? 1 : 2); i <= difference; i++) {//check blocks between the fox and desiredSpace to see if they are empty spaces
                Space currSpace = model.getBoard().getSpace(foxRow + i, foxColumn);
                if (!(currSpace instanceof EmptySpace))//if a space between isn't an empty space return false
                    return false;
            }
            return true;
        }
    }

    /**
     * Helper method that will move the FoxPart and the associated other FoxPart to the desired spaces
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
            model.getBoard().setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            model.getBoard().setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
            //move head
            model.getBoard().setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            model.getBoard().setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
        } else {//moving downwards or right, move head first
            //move head
            model.getBoard().setSpace(desiredRow, desiredColumn, fox);//set the head's location on the board to be at the desired location
            model.getBoard().setSpace(headRow, headColumn, new EmptySpace(headRow, headColumn));//set the fox head's old spot to be an EmptySpace
            //move tail
            model.getBoard().setSpace(otherDesiredRow, otherDesiredColumn, fox.getOtherFoxPart());//set the tail's location on the board to be at the other desired location
            model.getBoard().setSpace(tailRow, tailColumn, new EmptySpace(tailRow, tailColumn));//set the fox tail's old spot to be an EmptySpace
        }
        fox.moveBoth(desiredRow, desiredColumn, otherDesiredRow, otherDesiredColumn);//set the x and y variables of the fox's parts to be correct
    }


    public static void main(String[] args) {
        JumpInModel game = new JumpInModel();
        JIMView view = new JIMView(game);
        JIMController control = new JIMController(game, view);
        control.initController();
    }
}
