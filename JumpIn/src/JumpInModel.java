import java.util.ArrayList;

/**
 * JumpIn playable game mechanics
 * @author Nick, Lazar
 */
public class JumpInModel {
    private Board board;
    private boolean gameDone; //Game done is true when the game is completed and false when the game is not done
    private boolean badMove; // Bad move is true then an error is triggered and when bad move is false an error does not occur and it is a valid move
    private boolean destination; //Destination is false when the player is choosing a piece to move and true when choosing that pieces destination
    private int moveRow, moveCol;
    private ArrayList<View> views; //An array list of all the views that are subscribed to the model

    /**
     * Constructor for a JumpIn game.
     */
    public JumpInModel() {
        this.board = new Board();
        this.gameDone = false;
        this.destination = false;
        this.badMove = false;
        this.views = new ArrayList<>();
    }

    /**
     * Getter for the current state of the board
     * @return The current state of the board
     */
    public Board getBoard() {
        return this.board;
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
     * @param destination is true when choosing a piece's destination and is false when the player is choosing a piece
     */
    public void setDestination(boolean destination) {
        this.destination = destination;
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
        notifyViews();
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

    /**
     * Returns the state if the game is prompting a player to select a piece or a space to move to
     * @return
     */
    public boolean isDestination() {
        return destination;
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
     * Adding a view to the ArrayList of views
     * @param newView is the view to be added in views
     */
    public void addView(View newView){
        views.add(newView);
    }

}
