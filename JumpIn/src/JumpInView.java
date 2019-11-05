import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Lazar
 */
public class JumpInView implements View{
    private JumpInModel model;
    private JFrame frame;
    private  JButton[][] buttons;
    private JLabel instruction;
    private JOptionPane occurance;

    public JumpInView(JumpInModel model){
        Board b = model.getBoard();
        model.addView(this);
        this.buttons = new JButton[5][5];
        this.model = model;
        this.occurance = new JOptionPane();
        this.instruction = new JLabel("Instruction");
        instruction.setHorizontalAlignment(SwingConstants.LEFT);
        instruction.setVisible(true);

        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                buttons[row][column] = new JButton();
                buttons[row][column].setVisible(true);
            }
        }

        this.frame = new JFrame("JumpIn");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(500,500);
       // frame.add(validmove);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.LINE_AXIS));
        textContainer.add(instruction);

        frame.add(textContainer);

        JPanel gridContainer = new JPanel();
        gridContainer.setSize(new Dimension(30,30));

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5,5));
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                grid.add(buttons[i][j]);
            }
        }
        frame.add(grid);

        frame.setVisible(true);
        this.update(model);
    }

    public JButton[][] getButtons() {
        return buttons;
    }



    @Override
    public void update(JumpInModel model){
        Board board = model.getBoard();

        for(int column = 0 ; column < 5 ; column++){
            for(int row = 0 ; row < 5 ; row++){
                buttons[row][column].setText(board.getSpace(row,column).toString());
                buttons[row][column].setEnabled(true);

                if(model.isDestination()){
                    if(!(buttons[row][column].getText().equals("FT")
                       || buttons[row][column].getText().equals("ES")
                       || buttons[row][column].getText().equals("OH")))
                    {
                        buttons[row][column].setEnabled(false);
                    }
                }
                else{
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

        if(model.isBadMove()){
            occurance.showMessageDialog(frame,
                    "This move cannot be made.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(model.isGameDone()){
            occurance.showMessageDialog(frame,
                    "You've won",
                    "Chicken Dinner",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if(model.isDestination()){
            instruction.setText("Choose a space to move to");
        }
        else{
            instruction.setText("Choose a piece to move");
        }


    }

}
