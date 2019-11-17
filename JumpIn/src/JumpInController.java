/**
 * @author Benjamin Ransom
 */

/**
 * Handles the events that occur between the user and the view
 * Communicates between model and view
 * Updates the model when an event has occured
 * @author Benjamin
 */
public class JumpInController {
    private JumpInModel model;
    private JumpInView view;


    /**
     * The constructor which takes a model and view to control the logic of the game
     * @param model
     * @param view
     */
    public JumpInController(JumpInModel model, JumpInView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Initializes the button event listeners so when pressed it will handle the events
     */
    private void initController() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                int finalRow = row;
                int finalColumn = column;
                view.getButtons()[row][column].addActionListener(e -> selectSpace(finalRow, finalColumn));
            }
        }
    }

    /**
     * Two states for this method
     * The first state saves the row and column for the fox/rabbit to be moved
     * In state 2 it moves the pieces to the destination.
     * In state 2 if the move happens to be invalid an error will pop up and you are forced back into state 1
     * @param row
     * @param column
     */
    private void selectSpace(int row, int column) {
        model.setBadMove(false); //Clears bad move just to be safe

        //Moves the fox/rabbit to row and column to selected space
        if (!model.isPieceSelected()) {
            model.setMoveRow(row);
            model.setMoveCol(column);
            model.setPieceSelected(true);
        }
        //Checks to see if either the fox head or rabbit is moved and moves the space to an invalid space, otherwise shows an error
        else {
            model.takeTurn(row,column);
        }
        //Sets the game to completion when the game is done
        if (model.getBoard().getHolesFilled() == 3) {
            model.setGameDone(true);
        }
    }




    /**
     * Initializes the game
     * @param args
     */
    public static void main(String[] args) {
        JumpInModel game = new JumpInModel();
        JumpInView view = new JumpInView(game);
        JumpInController control = new JumpInController(game, view);
        control.initController(); //initialize the event handling for the controller
    }
}
