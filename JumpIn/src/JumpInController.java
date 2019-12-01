/**
 * @author Benjamin Ransom
 */

import javax.swing.*;

/**
 * Handles the events that occur between the user and the view
 * Communicates between model and view
 * Updates the model when an event has occured
 * @author Benjamin
 */
public class JumpInController {
    private JumpInModel model;
    private JumpInView view;
    private LevelEditorView levelEditor;


    /**
     * The constructor which takes a model and view to control the logic of the game
     * @param model
     * @param view
     */
    public JumpInController(JumpInModel model, JumpInView view, LevelEditorView levelEditor) {
        this.model = model;
        this.view = view;
        this.levelEditor = levelEditor;

    }

    /**
     * Initializes the button event listeners so when pressed it will handle the events
     */
    private void initController() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                int finalRow = row;
                int finalColumn = column;
                levelEditor.getBoardSpaces()[row][column].addActionListener(e -> buildSpace(finalRow,finalColumn));
                view.getButtons()[row][column].addActionListener(e -> selectSpace(finalRow, finalColumn));
            }
        }
        levelEditor.getPiecesToPlace()[0].addActionListener(e -> selectBuildPiece("RA"));
        levelEditor.getPiecesToPlace()[1].addActionListener(e -> selectBuildPiece("MU"));
        levelEditor.getPiecesToPlace()[2].addActionListener(e -> selectBuildPiece("FV"));
        levelEditor.getPiecesToPlace()[3].addActionListener(e -> selectBuildPiece("FH"));
        levelEditor.getPiecesToPlace()[4].addActionListener(e -> cancelPiece());
        levelEditor.getPlay().addActionListener(e-> viewEditorToPlay());
        view.getBuild().addActionListener(e -> viewPlayToEditor());
        view.getUndo().addActionListener(e -> model.undoMove());
        view.getRedo().addActionListener(e -> model.redoMove());
    }

    private void buildSpace(int finalRow, int finalColumn) {
        if(model.isPieceSelected()){
            if(model.getBuildPiece().equals("FV")){
                FoxPart head = new FoxPart(finalRow,finalColumn,true,true);
                FoxPart tail = new FoxPart(finalRow-1,finalColumn,true,false, head);
                head.setOtherFoxPart(tail);
                model.getBoard().setSpace(finalRow,finalColumn, head);
                model.getBoard().setSpace(finalRow-1,finalColumn, tail);
                model.setBuildFoxLeft(model.getBuildFoxLeft()-1);
                model.setPieceSelected(false);

            }else if(model.getBuildPiece().equals("FH")){
                FoxPart head = new FoxPart(finalRow,finalColumn,false,true);
                FoxPart tail = new FoxPart(finalRow,finalColumn-1,false,false, head);
                head.setOtherFoxPart(tail);
                model.getBoard().setSpace(finalRow,finalColumn, head);
                model.getBoard().setSpace(finalRow,finalColumn-1, tail);
                model.setBuildFoxLeft(model.getBuildFoxLeft()-1);
                model.setPieceSelected(false);

            }else if(model.getBuildPiece().equals("RA")){
                model.getBoard().setSpace(finalRow,finalColumn, new Rabbit(finalRow,finalColumn));
                model.getBoard().incrementHolesEmpty();
                model.setBuildRabbitLeft(model.getBuildRabbitLeft()-1);
                model.setPieceSelected(false);
            }else if(model.getBuildPiece().equals("MU")){

                model.getBoard().setSpace(finalRow,finalColumn, new Mushroom(finalRow,finalColumn));
                model.setBuildMushroomLeft(model.getBuildMushroomLeft()-1);
                model.setPieceSelected(false);
            }
        }else{
            if(model.getBoard().getSpace(finalRow,finalColumn).toString().equals("FH")){
                if(((FoxPart) model.getBoard().getSpace(finalRow,finalColumn)).getIsVertical()){
                    model.getBoard().setSpace(finalRow,finalColumn, new EmptySpace(finalRow,finalColumn));
                    model.getBoard().setSpace(finalRow-1,finalColumn, new EmptySpace(finalRow-1,finalColumn));
                }else{
                    model.getBoard().setSpace(finalRow,finalColumn, new EmptySpace(finalRow,finalColumn));
                    model.getBoard().setSpace(finalRow,finalColumn-1, new EmptySpace(finalRow,finalColumn-1));
                }
                model.setBuildFoxLeft(model.getBuildFoxLeft()+1);
            }else if(model.getBoard().getSpace(finalRow,finalColumn).toString().equals("RA")){
                model.getBoard().setSpace(finalRow,finalColumn, new EmptySpace(finalRow,finalColumn));
                model.getBoard().decrementHolesEmpty();
                model.setBuildRabbitLeft(model.getBuildRabbitLeft()+1);
            }else{
                model.getBoard().setSpace(finalRow,finalColumn, new EmptySpace(finalRow,finalColumn));
                model.setBuildMushroomLeft(model.getBuildMushroomLeft()+1);
            }

        }

    }

    private void selectBuildPiece(String piece){

        model.setBuildPiece(piece);
        model.setPieceSelected(true);
    }

    private void cancelPiece(){

        model.setBuildPiece("");
        model.setPieceSelected(false);
    }

    /**
     * Two states for this method
     * The first state saves the row and column for the fox/rabbit to be moved
     * In state 1 it moves the pieces to the destination.
     * In state 2 if the move happens to be invalid an error will pop up and you are forced back into state 1
     * @param row
     * @param column
     */
    private void selectSpace(int row, int column) {
        model.setBadMove(false); //Clears bad move just to be safe

        //Moves the fox/rabbit to row and column to selected s  pace
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
        if (model.getBoard().getHolesEmpty() == 0) {
            model.setGameDone(true);
        }
        if (model.isGameDone()) {
            view.getPopUp().showMessageDialog(view.getFrame(),
                    "You've won",
                    "Chicken Dinner",
                    JOptionPane.INFORMATION_MESSAGE);
            viewPlayToEditor();
        }
    }


    private void viewEditorToPlay(){
        if(levelEditor.getPopup().showConfirmDialog(null,"You are about to play the currently loaded level, if you have not yet saved your level, it will not be saved. would you like to continue?","WARNING", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
        }else{
            return;
        }

        model.setBuild(false);
        view.setFrameVisibility(true);
        levelEditor.setFrameVisiblity(false);
    }
    private void viewPlayToEditor(){
        levelEditor.resetBuilder();
        model.clearPlay();
        levelEditor.setFrameVisiblity(true);
        view.setFrameVisibility(false);
    }

    /**
     * Initializes the game
     * @param args
     */
    public static void main(String[] args) {
        JumpInModel game = new JumpInModel();

        LevelEditorView editor = new LevelEditorView(game);
        JumpInView view = new JumpInView(game);
        JumpInController control = new JumpInController(game, view, editor);

        control.initController(); //initialize the event handling for the controller
    }
}
