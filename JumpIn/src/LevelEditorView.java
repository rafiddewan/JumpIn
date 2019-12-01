import javax.swing.*;
import java.awt.*;

public class LevelEditorView implements View {
    private JLabel instructions;
    private JFrame frame;
    private JButton[][] buttons;
    private JButton[] pieces;
    private JButton save, load, play,reset;
    private JumpInModel model;
    private JOptionPane popup;


    public LevelEditorView(JumpInModel model){

        model.addView(this);

        this.model = model;
        Board board = this.model.getBoard();
        emptyBoard(board);

        this.buttons = new JButton[5][5];
        this.pieces = new JButton[5];
        this.popup = new JOptionPane();
        this.instructions = new JLabel("Instruction");
        instructions.setHorizontalAlignment(SwingConstants.LEFT);
        instructions.setVisible(true);

        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                buttons[row][column] = new JButton();
                buttons[row][column].setVisible(true);
            }
        }

        for(int i=0; i<5;i++){
            pieces[i] = new JButton();
            pieces[i].setVisible(true);
        }
        pieces[0].setText("Rabbits : 3");
        pieces[1].setText("Mushrooms : 2");
        pieces[2].setText("Foxes(Vertical) : 2");
        pieces[3].setText("Foxes(Horizontal) : 2");
        pieces[4].setText("Cancel");
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
                grid.add(buttons[i][j]);
            }
        }
        grid.setMaximumSize(new Dimension(450,400));

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(5,1));
        for( int i=0; i<5;i++){
            piecesPanel.add(pieces[i]);
        }
        piecesPanel.setMaximumSize(new Dimension(150,400));

        builder.add(grid);
        builder.add(piecesPanel);
        frame.add(builder);
        frame.setVisible(true);

        update(model);


    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public JButton[] getPieces() {
        return pieces;
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
                        buttons[row][column].setText(board.getSpace(row, column).toString());
                        if (buttons[row][column].getText().equals("ES")|| buttons[row][column].getText().equals("OH")||buttons[row][column].getText().equals("FT")){
                            buttons[row][column].setEnabled(false);
                        }else{
                            buttons[row][column].setEnabled(true);
                        }
                    }

                }

                if(model.getBuildRabbitLeft()>0){
                    pieces[0].setEnabled(true);

                }else{
                    pieces[0].setEnabled(false);
                }
                if(model.getBuildMushroomLeft()>0){
                    pieces[1].setEnabled(true);

                }else{
                    pieces[1].setEnabled(false);
                }

                if(model.getBuildFoxLeft()>0){
                    pieces[2].setEnabled(true);
                    pieces[3].setEnabled(true);
                }else{
                    pieces[2].setEnabled(false);
                    pieces[3].setEnabled(false);
                }
                pieces[0].setText("Rabbits : "+ model.getBuildRabbitLeft());
                pieces[1].setText("Mushrooms : "+ model.getBuildMushroomLeft());
                pieces[2].setText("Foxes(Vertical) : "+ model.getBuildFoxLeft());
                pieces[3].setText("Foxes(Horizontal) : "+ model.getBuildFoxLeft());


                pieces[4].setEnabled(false);

            } else {
                for(int column = 0 ; column < 5 ; column++) {
                    for (int row = 0; row < 5; row++) {
                        buttons[row][column].setText(board.getSpace(row, column).toString());
                        if(model.getBuildPiece().equals("RA")||model.getBuildPiece().equals("MU")) {
                            if (buttons[row][column].getText().equals("ES")) {
                                buttons[row][column].setEnabled(true);
                            } else {
                                buttons[row][column].setEnabled(false);
                            }
                        }else if(model.getBuildPiece().equals("FH")){
                            if (buttons[row][column].getText().equals("ES")&& column !=0 ) {
                                if(board.getSpace(row,column-1).toString().equals("ES")) {
                                    buttons[row][column].setEnabled(true);
                                }else{
                                    buttons[row][column].setEnabled(false);
                                }
                            } else {
                                buttons[row][column].setEnabled(false);
                            }
                        }else if(model.getBuildPiece().equals("FV")){
                            if (buttons[row][column].getText().equals("ES") && row !=0 ) {
                                if(board.getSpace(row-1,column).toString().equals("ES")) {
                                    buttons[row][column].setEnabled(true);
                                }else{
                                    buttons[row][column].setEnabled(false);
                                }
                            } else {
                                buttons[row][column].setEnabled(false);
                            }
                        }
                    }

                }

                for(int i=0;i<4;i++){
                    pieces[i].setEnabled(false);
                }
                pieces[0].setText("Rabbits : "+ model.getBuildRabbitLeft());
                pieces[1].setText("Mushrooms : "+ model.getBuildMushroomLeft());
                pieces[2].setText("Foxes(Vertical) : "+ model.getBuildFoxLeft());
                pieces[3].setText("Foxes(Horizontal) : "+ model.getBuildFoxLeft());


                pieces[4].setEnabled(true);

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
