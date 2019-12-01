import javax.swing.*;
import java.awt.*;

/**
 * @author Benjamin Ramson
 */
public class LevelEditorView implements View {
    private JLabel instructions; //Instruction menu for JLabel
    private JFrame frame; //JFrame for the level editor
    private JButton[][] boardSpaces; //Spaces on the board
    private JButton[] piecesToPlace; //Pieces that are needed to be placed
    private JButton save, load, play,reset; //buttons that are on the menu bar
    private JumpInModel model; //Model that the game is associated with
    private JOptionPane popup; //popup option when the game is going to be reset


    public LevelEditorView(JumpInModel model){

        model.addView(this);

        this.model = model;
        Board board = this.model.getBoard();
        emptyBoard(board);

        this.boardSpaces = new JButton[5][5];
        this.piecesToPlace = new JButton[5];
        this.popup = new JOptionPane();
        this.instructions = new JLabel("Instruction");
        instructions.setHorizontalAlignment(SwingConstants.LEFT);
        instructions.setVisible(true);

        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                boardSpaces[row][column] = new JButton();
                boardSpaces[row][column].setVisible(true);
            }
        }

        for(int i=0; i<5;i++){
            piecesToPlace[i] = new JButton();
            piecesToPlace[i].setVisible(true);
        }
        piecesToPlace[0].setText("Rabbits : 3");
        piecesToPlace[1].setText("Mushrooms : 2");
        piecesToPlace[2].setText("Foxes(Vertical) : 2");
        piecesToPlace[3].setText("Foxes(Horizontal) : 2");
        piecesToPlace[4].setText("Cancel");
        this.frame = new JFrame("LevelEditor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setSize(600,500);
        //Instructoins
        JPanel instructionContainer = new JPanel();
        instructionContainer.setLayout(new BoxLayout(instructionContainer, BoxLayout.LINE_AXIS));
        instructionContainer.add(instructions);
        frame.add(instructionContainer);
        //options
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.LINE_AXIS));
        this.save = new JButton("Save Level");
        this.save.setVisible(true);
        this.load = new JButton("Load Template");
        this.load.setVisible(true);
        this.play = new JButton("Play");
        this.play.setVisible(true);
        this.reset = new JButton("Reset");
        this.reset.setVisible(true);
        this.reset.addActionListener(e-> resetBuilder());
        optionPanel.add(save);
        optionPanel.add(load);
        optionPanel.add(play);
        optionPanel.add(reset);
        frame.add(optionPanel);

        //actual buttons
        JPanel builder = new JPanel();
        builder.setLayout(new BoxLayout(builder, BoxLayout.LINE_AXIS));

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5,5));
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                grid.add(boardSpaces[i][j]);
            }
        }
        grid.setMaximumSize(new Dimension(450,400));

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(5,1));
        for( int i=0; i<5;i++){
            piecesPanel.add(piecesToPlace[i]);
        }
        piecesPanel.setMaximumSize(new Dimension(150,400));

        builder.add(grid);
        builder.add(piecesPanel);
        frame.add(builder);
        setFrameVisiblity(true);

        update(model);


    }

    public void setFrameVisiblity(boolean isVisible) {
        frame.setVisible(isVisible);
    }
    public JOptionPane getPopup() {
        return popup;
    }

    public JButton[][] getBoardSpaces() {
        return boardSpaces;
    }

    public JButton[] getPiecesToPlace() {
        return piecesToPlace;
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public JButton getPlay() {
        return play;
    }

    @Override
    public void update(JumpInModel model) {
      if(model.isBuild()) {
            Board board = model.getBoard();
            if (!model.isPieceSelected()) {
                for(int column = 0 ; column < 5 ; column++) {
                    for (int row = 0; row < 5; row++) {
                        boardSpaces[row][column].setText(board.getSpace(row, column).toString());
                        if (boardSpaces[row][column].getText().equals("ES")|| boardSpaces[row][column].getText().equals("OH")|| boardSpaces[row][column].getText().equals("FT")){
                            boardSpaces[row][column].setEnabled(false);
                        }else{
                            boardSpaces[row][column].setEnabled(true);
                        }
                    }

                }

                if(model.getBuildRabbitLeft()>0){
                    piecesToPlace[0].setEnabled(true);

                }else{
                    piecesToPlace[0].setEnabled(false);
                }
                if(model.getBuildMushroomLeft()>0){
                    piecesToPlace[1].setEnabled(true);

                }else{
                    piecesToPlace[1].setEnabled(false);
                }

                if(model.getBuildFoxLeft()>0){
                    piecesToPlace[2].setEnabled(true);
                    piecesToPlace[3].setEnabled(true);
                }else{
                    piecesToPlace[2].setEnabled(false);
                    piecesToPlace[3].setEnabled(false);
                }
                piecesToPlace[0].setText("Rabbits : "+ model.getBuildRabbitLeft());
                piecesToPlace[1].setText("Mushrooms : "+ model.getBuildMushroomLeft());
                piecesToPlace[2].setText("Foxes(Vertical) : "+ model.getBuildFoxLeft());
                piecesToPlace[3].setText("Foxes(Horizontal) : "+ model.getBuildFoxLeft());
                instructions.setText("Select a piece to place, remove a piece from the board or load in an existing level");


                piecesToPlace[4].setEnabled(false);

            } else {
                for(int column = 0 ; column < 5 ; column++) {
                    for (int row = 0; row < 5; row++) {
                        boardSpaces[row][column].setText(board.getSpace(row, column).toString());
                        if(model.getBuildPiece().equals("RA")||model.getBuildPiece().equals("MU")) {
                            if(model.getBuildPiece().equals("RA")){
                                instructions.setText("Select a spot for the rabbit");
                            }else{
                                instructions.setText("Select a spot for the mushroom");
                            }
                            if (boardSpaces[row][column].getText().equals("ES")) {
                                boardSpaces[row][column].setEnabled(true);
                            } else {
                                boardSpaces[row][column].setEnabled(false);
                            }
                        }else if(model.getBuildPiece().equals("FH")){
                            instructions.setText("Select a spot for the foxes head, its tail will be to the left of it");
                            if (boardSpaces[row][column].getText().equals("ES")&& column !=0 ) {
                                if(board.getSpace(row,column-1).toString().equals("ES")) {
                                    boardSpaces[row][column].setEnabled(true);
                                }else{
                                    boardSpaces[row][column].setEnabled(false);
                                }
                            } else {
                                boardSpaces[row][column].setEnabled(false);
                            }
                        }else if(model.getBuildPiece().equals("FV")){
                            instructions.setText("Select a spot for the foxes head, its tail will be above it");
                            if (boardSpaces[row][column].getText().equals("ES") && row !=0 ) {
                                if(board.getSpace(row-1,column).toString().equals("ES")) {
                                    boardSpaces[row][column].setEnabled(true);
                                }else{
                                    boardSpaces[row][column].setEnabled(false);
                                }
                            } else {
                                boardSpaces[row][column].setEnabled(false);
                            }
                        }
                    }

                }

                for(int i=0;i<4;i++){
                    piecesToPlace[i].setEnabled(false);
                }
                piecesToPlace[0].setText("Rabbits : "+ model.getBuildRabbitLeft());
                piecesToPlace[1].setText("Mushrooms : "+ model.getBuildMushroomLeft());
                piecesToPlace[2].setText("Foxes(Vertical) : "+ model.getBuildFoxLeft());
                piecesToPlace[3].setText("Foxes(Horizontal) : "+ model.getBuildFoxLeft());


                piecesToPlace[4].setEnabled(true);

            }


        }


    }

    private void emptyBoard(Board board){
        for(int row = 0; row<5; row++){
            for(int column = 0; column<5;column++){
                board.setSpace(row, column, new EmptySpace(row, column));
            }
        }
        board.setSpace( 0,0, new Hole(0, 0, false));
        board.setSpace( 0,4, new Hole(0, 0, false));
        board.setSpace( 2,2, new Hole(0, 0, false));
        board.setSpace( 4,0, new Hole(0, 0, false));
        board.setSpace( 4,4, new Hole(0, 0, false));
        board.setHolesEmpty(0);
    }

    public void resetBuilder() {
        if(model.isBuild()){
            if(popup.showConfirmDialog(null,"Are you sure you want to reset the board?","WARNING",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            }else{
                return;
            }
        }
        Board board = model.getBoard();
        emptyBoard(board);
        model.setBuildRabbitLeft(3);
        model.setBuildMushroomLeft(3);
        model.setBuildFoxLeft(2);
        model.setPieceSelected(false);

    }



    public static void main(String[] args){
        JumpInModel model  = new JumpInModel();
        LevelEditorView test = new LevelEditorView(model);
    }
}
