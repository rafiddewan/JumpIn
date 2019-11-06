import javax.swing.*;
import java.awt.*;

/**
 * The framework of the game that is visible and interactive with the user
 * @author Lazar
 */
public class JumpInView implements View{
    private JumpInModel model;
    private JFrame frame;
    private  JButton[][] buttons;
    private JLabel instruction;
    private JOptionPane occurance;

    /**
     * Creates the JFrame for JumpIn and adds the necessary content (such as pieces, and instructions) when you boot up the game
     * @param model which is used to help initialize the board for the view
     */
    public JumpInView(JumpInModel model){
        Board b = model.getBoard(); //Get the board for the model
        model.addView(this); //Subscribes the view to the model
        //Initialize  contents for the game
        this.buttons = new JButton[5][5];
        this.model = model;
        this.occurance = new JOptionPane();
        this.instruction = new JLabel("Instruction");
        instruction.setHorizontalAlignment(SwingConstants.LEFT);
        instruction.setVisible(true);

        //Initialize the JButtons
        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                buttons[row][column] = new JButton();
                buttons[row][column].setVisible(true);
            }
        }

        //displays the frame of the game
        this.frame = new JFrame("JumpIn");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(500,500);
       // frame.add(validmove);

        //Instructions at the top
        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.LINE_AXIS));
        textContainer.add(instruction);
        frame.add(textContainer);

        //Creates a grid
        JPanel gridContainer = new JPanel();
        gridContainer.setSize(new Dimension(30,30));

        //Adds the JButtons to a grid layout
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5,5));
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                grid.add(buttons[i][j]);
            }
        }
        frame.add(grid);

        //Set the visibility of the frame
        frame.setVisible(true);
        //Notifies the model to the contents for the buttons
        this.update(model);
    }

    /**
     * Get the spaces on the board in form of JButtons
     * @return Buttons on the board
     */
    public JButton[][] getButtons() {
        return buttons;
    }

    /**
     * Updates the view of when a change occurs in the model
     * @param model which is the object that the view's change is based upon
     */
    @Override
    public void update(JumpInModel model){
        Board board = model.getBoard(); //gets the board for the view

        //Creates the contents for the buttons
        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                buttons[row][column].setText(board.getSpace(row,column).toString());
                buttons[row][column].setEnabled(true);

                //Moveable space
                if(model.isDestination()){
                    if(buttons[model.getMoveRow()][model.getMoveCol()].getText().equals("RA") && buttons[row][column].getText().equals("FT")){
                        buttons[row][column].setEnabled(false);
                    }
                    if(!(buttons[row][column].getText().equals("FT")
                       || buttons[row][column].getText().equals("ES")
                       || buttons[row][column].getText().equals("OH")))
                    {
                        buttons[row][column].setEnabled(false);
                    }
                }
                else{ //disable buttons that can't be used to move around
                    if(buttons[row][column].getText().equals("FT")
                       || buttons[row][column].getText().equals("MU")
                       || buttons[row][column].getText().equals("ES")
                       || buttons[row][column].getText().equals("OH")
                       || buttons[row][column].getText().equals("CH")){
                        buttons[row][column].setEnabled(false);
                    }
                }
            }
        }

        //Notifies user of the bad move in the option of a JOptionPane Error Message
        if(model.isBadMove()){
            occurance.showMessageDialog(frame,
                    "This move cannot be made.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        //Notifies the user of the game's completion in the option of a  JOptionPane confirmation message
        if(model.isGameDone()){
            occurance.showMessageDialog(frame,
                    "You've won",
                    "Chicken Dinner",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        //When the destination is false, it allows you to select a piece to move too and when the instruction is true it allows you to choose a space to move to
        if(model.isDestination()){
            instruction.setText("Choose a space to move to");
        }
        else{
            instruction.setText("Choose a piece to move");
        }


    }

}
