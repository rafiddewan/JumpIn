import javax.swing.*;
import java.awt.*;

/**
 * This View implements a level  builder that appears at the start of the game
 * @author Benjamin Ramson and Rafid Dewan
 */
public class LevelEditorView implements View {
    private JLabel instructions; //Instruction menu for JLabel
    private JFrame frame; //JFrame for the level editor
    private JButton[][] boardSpaces; //Spaces on the board
    private JButton[] placeablePieces; //Pieces that are needed to be placed
    private JButton load, play,reset; //buttons that are on the menu bar
    private JumpInModel model; //Model that the game is associated with
    private JOptionPane popup; //popup option when the game is going to be reset


    /**
     * Constructs the view level editor when the game starts up
     * @param model
     */
    public LevelEditorView(JumpInModel model){

        //subscribe this view to the model
        model.addView(this);

        //Emptying the board so we can add mushrooms, foxes, and rabbits onto the board
        this.model = model;
        Board board = this.model.getBoard();
        board.emptyBoard();

        //Create the frame
        this.frame = new JFrame("LevelEditor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setSize(800,500);


        //5 by 5 JButton of spaces on the board
        this.boardSpaces = new JButton[5][5];

        //5 pieces total that you could place on the board
        this.placeablePieces = new JButton[5];

        //Pop up  for the spaces
        this.popup = new JOptionPane();

        //Instruction Menu
        this.instructions = new JLabel("Instruction");
        instructions.setHorizontalAlignment(SwingConstants.LEFT);
        instructions.setVisible(true);

        //Set the visibility of boardSpaces to true
        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                boardSpaces[row][column] = new JButton();
                boardSpaces[row][column].setVisible(true);
            }
        }

        //Display the placeable pieces
        for(int i=0; i<5;i++){
            placeablePieces[i] = new JButton();
            placeablePieces[i].setVisible(true);
        }

        //Set the text of the buttons for the placeable pieces
        placeablePieces[0].setText("Rabbits : 3");
        placeablePieces[1].setText("Mushrooms : 2");
        placeablePieces[2].setText("Foxes(Vertical) : 2");
        placeablePieces[3].setText("Foxes(Horizontal) : 2");
        placeablePieces[4].setText("Cancel");

        //Instructions
        JPanel instructionContainer = new JPanel();
        instructionContainer.setLayout(new BoxLayout(instructionContainer, BoxLayout.LINE_AXIS));
        instructionContainer.add(instructions);
        frame.add(instructionContainer);

        //Main Menu bar of options to choose from
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.LINE_AXIS));
        this.load = new JButton("Load Template");
        this.load.setVisible(true);
        this.play = new JButton("Play");
        this.play.setVisible(true);
        this.reset = new JButton("Reset");
        this.reset.setVisible(true);
        this.reset.addActionListener(e-> resetBuilder());
        optionPanel.add(load);
        optionPanel.add(play);
        optionPanel.add(reset);
        frame.add(optionPanel);


        JPanel builder = new JPanel();
        builder.setLayout(new BoxLayout(builder, BoxLayout.LINE_AXIS));

        //Create the grid of spaces as the board which you are building your level on
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5,5));
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                grid.add(boardSpaces[i][j]);
            }
        }
        grid.setMaximumSize(new Dimension(450,400));

        //Create a vertical panel of pieces
        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(5,1));
        for( int i=0; i<5;i++){
            piecesPanel.add(placeablePieces[i]);
        }
        piecesPanel.setMaximumSize(new Dimension(200,400));

        //Adds the board and the list of placeable pieces to the level builder panel
        builder.add(grid);
        builder.add(piecesPanel);
        //Adds the level builder panel to the frame
        frame.add(builder);
        //Sets the frame visibility to true since this will be the first thing that appears when starting the game
        setFrameVisiblity(true);

        //updates the view with the current model
        update(model);
    }

    /**
     * Displays or hides the frame
     * @param isVisible
     */
    public void setFrameVisiblity(boolean isVisible) {
        frame.setVisible(isVisible);
    }

    /**
     *
     * @return the popup
     */
    public JOptionPane getPopup() {
        return popup;
    }

    /**
     * Load button getter
     * @return Load button
     */
    public JButton getLoad() { return load; }

    /**
     * Returns a reference of a 2-D array of the level builder's board spaces
     * @return the spaces on the board
     */
    public JButton[][] getBoardSpaces() {
        return boardSpaces;
    }

    /**
     * Sets the visibility of certain buttons on the board depending if a placeable piece was selected or not
     * Takes a row and column to determine if a buttons should be enabled or disalbed
     * @param row of the space
     * @param column of the space
     */
    public void setBoardSpacesVisiblity(int row, int column, String buildPiece) {

        if (!model.isPieceSelected()) { //When a placeable piece is not selected
            if (boardSpaces[row][column].getText().equals("ES")|| boardSpaces[row][column].getText().equals("OH")|| boardSpaces[row][column].getText().equals("FT"))
                boardSpaces[row][column].setEnabled(false); //Disable the option to move empty space, open hole, and fox tail to
            else boardSpaces[row][column].setEnabled(true); //placeable pieces can be selected to move to another space if already placed the board
        }
        else {
            switch(buildPiece) { //Using buildPiece to determine which piece is selected and what buttons to disable & enable since it is selected
                case "RA":
                case "MU":
                    if (boardSpaces[row][column].getText().equals("ES")) boardSpaces[row][column].setEnabled(true); //enable all empty spaces (no restrictions for mushrooms and rabbits)
                    else
                        boardSpaces[row][column].setEnabled(false); //Disable the option to move empty space, open hole, and fox tail to
                    break;
                case "FH":
                    if (boardSpaces[row][column].getText().equals("ES") && column !=0 ) { //Horizontal fox head can only be put on a space that has two empty spaces and it's head not at column 0
                        if(boardSpaces[row][column-1].getText().equals("ES")) { //check to see if the space to the left is also empty
                            boardSpaces[row][column].setEnabled(true);  //both spaces are empty so you can put the fox head and tail here
                        }else{
                            boardSpaces[row][column].setEnabled(false); //the space to the left is not empty so you can't place a horizontal fox here
                        }
                    } else {
                        boardSpaces[row][column].setEnabled(false); //Disable the spaces on the leftmost column since the fox head should never be placed on the leftmost column
                    }
                    break;
                case "FV":
                    if (boardSpaces[row][column].getText().equals("ES") && row != 0) { //Vertical fox head can only be put on a space that has two empty spaces and it's head not at row 0
                        if (boardSpaces[row-1][column].getText().equals("ES")) { //check to see if the space below is also empty
                            boardSpaces[row][column].setEnabled(true); //both spaces are empty so you can put the fox head and tail here
                        } else {
                            boardSpaces[row][column].setEnabled(false); //the space below is not empty so you can't place a vertical fox here
                        }
                    } else {
                        boardSpaces[row][column].setEnabled(false); //Disable the spaces on the leftmost column since the fox head should never be placed on the top column
                    }
                    break;
            }
        }
    }

    /**
     * Sets the visbility of the placeable pieces depending on if it's bee selected or not
     */
    public void setPlaceablePiecesVisibility(){
        if(!model.isPieceSelected()){
            //Give option to place rabbits if all of them haven't been placed
            if(model.getBuildRabbitLeft() > 0) placeablePieces[0].setEnabled(true);
            else placeablePieces[0].setEnabled(false);

            //Give option to place mushrooms if all of them haven't been placed
            if(model.getBuildMushroomLeft() > 0) placeablePieces[1].setEnabled(true);
            else placeablePieces[1].setEnabled(false);

            //Give option to place foxes if all of them haven't been placed
            if(model.getBuildFoxLeft() > 0){
                placeablePieces[2].setEnabled(true);
                placeablePieces[3].setEnabled(true);
            }
            else{
                placeablePieces[2].setEnabled(false);
                placeablePieces[3].setEnabled(false);
            }

            placeablePieces[4].setEnabled(false); //Cannot select cancel if a placeable piece hasn't been selected
        }
        else{
            for(int i=0;i<4;i++){
                placeablePieces[i].setEnabled(false);
            }
            placeablePieces[4].setEnabled(true); //Can select cancel since a placeable piece hasn't been selected
        }
    }
    /**
     * Sets the current placeablePieces buttons' strings
     */
    public void setPlaceablePiecesString(){
        placeablePieces[0].setText("Rabbits : "+ model.getBuildRabbitLeft());
        placeablePieces[1].setText("Mushrooms : "+ model.getBuildMushroomLeft());
        placeablePieces[2].setText("Foxes(Vertical) : "+ model.getBuildFoxLeft());
        placeablePieces[3].setText("Foxes(Horizontal) : "+ model.getBuildFoxLeft());
    }

    /**
     * Returns an array of JButtons of PlaceablePieces
     * @return array of JButtons
     */
    public JButton[] getPlaceablePieces() {
        return placeablePieces;
    }

    public JFrame getFrame(){
        return this.frame;
    }

    /**
     * Returns the play JButton
     * @return JButton
     */
    public JButton getPlay() {
        return play;
    }

    /**
     * Notified by the model of an update or a change of state in the board, all the pieces respond
     * @param model notifies it's subscribers (the views) when a change is occured to it
     */
    @Override
    public void update(JumpInModel model) {
        //Update the level builder view if its in the build state
        if(model.isBuild()) {
            Board board = model.getBoard();
            /*If piece is not selected deselect empty space, open hole, and fox tail
            * since a placeable piece wasn't selected
            * Any other placeable piece can be selected so that it can move to another space
            */
            if (!model.isPieceSelected()) {
                for(int column = 0 ; column < 5 ; column++) {
                    for (int row = 0; row < 5; row++) {
                        boardSpaces[row][column].setText(board.getSpace(row, column).toString());
                        setBoardSpacesVisiblity(row, column, null);
                    }

                }

                //set instructions to select a placeable piece
                instructions.setText("Select a piece to place, remove a piece from the board or load in an existing level");
            }
            /* If piece is selected deselect the buttons in the placeable pieces list
             * since a placeable piece was selected
             */
            else {
                for(int column = 0 ; column < 5 ; column++) {
                    for (int row = 0; row < 5; row++) {
                        boardSpaces[row][column].setText(board.getSpace(row, column).toString());
                        String buildPiece = model.getBuildPiece(); //String containing the
                        setBoardSpacesVisiblity(row, column, buildPiece); //sets the boardSpaces to place a piece on the board
                        switch (buildPiece) {
                            case "RA":
                            case "MU":
                                if (model.getBuildPiece().equals("RA"))
                                    instructions.setText("Select a spot for the rabbit"); //instructions tell you select a space from the grid of boardSpaces to place the rabbit
                                else
                                    instructions.setText("Select a spot for the mushroom"); //instructions tell you select a mushroom from the grid of boardSpaces to place the rabbit
                                break;
                            case "FH":
                                instructions.setText("Select a spot for the foxes head, its tail will be to the left of it"); //instructions tell you select a horizontal fox from the grid of boardSpaces to place the rabbit
                                break;
                            case "FV":
                                instructions.setText("Select a spot for the foxes head, its tail will be above it"); //instructions tell you select a vertical fox from the grid of boardSpaces to place the rabbit
                                break;
                            }
                    }
                }
            }
        }
        setPlaceablePiecesString();
        setPlaceablePiecesVisibility();
    }

    /**
     * Resets the builder back to default status
     */
    public void resetBuilder() {
        if(model.isBuild()){
            if(popup.showConfirmDialog(null,"Are you sure you want to reset the board?","WARNING",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                Board board = model.getBoard();
                board.emptyBoard();
                model.setBuildRabbitLeft(3);
                model.setBuildMushroomLeft(3);
                model.setBuildFoxLeft(2);
                model.setPieceSelected(false);
            }
        }
    }

}
