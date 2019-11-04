import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Lazar
 */
public class JIMView implements View{
    private JumpInModel model;
    private JFrame frame;
    private  JButton[][] buttons;
    private JLabel instruction;
    private JOptionPane occurance;

    public JIMView(JumpInModel m){
        Board b = m.getBoard();
        m.addView(this);
        this.buttons = new JButton[5][5];
        this.model = m;
        this.occurance = new JOptionPane();
        this.instruction = new JLabel("Instruction");
        instruction.setHorizontalAlignment(SwingConstants.LEFT);
        instruction.setVisible(true);

        for(int posY = 0 ; posY < 5 ; posY++){
            for(int posX = 0 ; posX < 5 ; posX++){
                buttons[posX][posY] = new JButton();
                buttons[posX][posY].setVisible(true);
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
        this.update(m);
    }

    public JButton[][] getButtons() {
        return buttons;
    }



    @Override
    public void update(JumpInModel j){
        Board board = j.getBoard();

        for(int posY = 0 ; posY < 5 ; posY++){
            for(int posX = 0 ; posX < 5 ; posX++){
                buttons[posX][posY].setText(board.getSpace(posX,posY).toString());
                buttons[posX][posY].setEnabled(true);

                if(j.isDestination()){
                    if(!(buttons[posX][posY].getText().equals("FT")
                       || buttons[posX][posY].getText().equals("ES")
                       || buttons[posX][posY].getText().equals("OH")))
                    {
                        buttons[posX][posY].setEnabled(false);
                    }
                }
                else{
                    if(buttons[posX][posY].getText().equals("FT")
                       || buttons[posX][posY].getText().equals("MU")
                       || buttons[posX][posY].getText().equals("ES")
                       || buttons[posX][posY].getText().equals("OH")
                       || buttons[posX][posY].getText().equals("CH")){
                        buttons[posX][posY].setEnabled(false);
                    }
                }
            }
        }

        if(j.isBadMove()){
            occurance.showMessageDialog(frame,
                    "This move cannot be made.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(j.isGameDone()){
            occurance.showMessageDialog(frame,
                    "You've won",
                    "Chicken Dinner",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if(j.isDestination()){
            instruction.setText("Choose a space to move to");
        }
        else{
            instruction.setText("Choose a piece to move");
        }


    }

}
