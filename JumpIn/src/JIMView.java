import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Lazar
 */
public class JIMView {
    private JumpInModel model;
    private  JButton[][] buttons;
    private JLabel validmove;
    private JLabel instruction;


    public JIMView(JumpInModel m){
        Board b = m.getBoard();
        this.buttons = new JButton[5][5];
        this.model = m;
        this.instruction = new JLabel("Instruction");
        instruction.setHorizontalAlignment(SwingConstants.LEFT);
        instruction.setVisible(true);

        this.validmove = new JLabel("Error"); //Technically don't need error if disabling invalid buttons
        validmove.setVisible(true);

        for(int posY = 0 ; posY < 5 ; posY++){
            for(int posX = 0 ; posX < 5 ; posX++){
                buttons[posX][posY] = new JButton();
                buttons[posX][posY].setVisible(true);
            }
        }

        JFrame frame = new JFrame("JumpIn");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(500,500);
       // frame.add(validmove);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.LINE_AXIS));
        textContainer.add(validmove);
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

    public void update(JumpInModel j){
        Board board = j.getBoard();
        for(int posY = 0 ; posY < 5 ; posY++){
            for(int posX = 0 ; posX < 5 ; posX++){
                buttons[posX][posY].setText(board.getSpace(posX,posY).toString());
            }
        }
    }



}
