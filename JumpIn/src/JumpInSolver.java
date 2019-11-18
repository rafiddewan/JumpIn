import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class is used to provide a solution for the game provided BFS
 * @author Rafid Dewan
 */
public class JumpInSolver {

    private Board board; //passes in the board of the game
    private JumpInModel model; //used to verify if a move is legal or not
    private Stack<String> hints; //stack of hints throughout the game
    private String[] spaceTypes; //types of spaces that you should look at to move

    /**
     * Constructor for JumpInSolver
     * Takes in a board and creates a new model to use to check valid moves for the solver
     */
    public JumpInSolver(){
        this.model = new JumpInModel();
        this.board = this.model.getBoard();
        this.hints = new Stack<String>();
        this.spaceTypes = new String[4];
        for(int i = 0; i < spaceTypes.length; i++){
            switch(i)
            {
                case 0: spaceTypes[i] = "RA";
                        break;
                case 1: spaceTypes[i] = "RA";
                        break;
                case 2: spaceTypes[i] = "RA";
                        break;
                case 3: spaceTypes[i] = "FH";
                        break;
                case 4: spaceTypes[i] = "FH";
            }
        }
    }

    public boolean DFS(Space movablePiece){
        if(movablePiece instanceof  Rabbit) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (model.canRabbitMove(movablePiece, board.getSpace(i, j))) {

                    }
                }
            }
        }
        return false;
    }

    public boolean solver(){
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                if (board.getSpace(row, col) instanceof Rabbit) DFS(board.getSpace(row, col));
            }
        }

        return true;
    }

    /**
     * Addding a hint to the hintStack
     * @param hint
     */
    public void addHints(String hint){
        hints.push(hint); //add a hint on top of all the hints
    }

    /**
     * Remove hints from the hintStack
     */
    public void removeLastHint() {
        try {
            hints.pop(); //remove a hint
        } catch (EmptyStackException e) {
            System.out.println("Empty stack error"); //Should not remove a hint off a empty stack
        }
    }

    /**
     * Returns a String with a solution of what the board is
     * @return String containing the solution of the board
     */
    @Override
    public String toString(){
        if(hints.size() == 0) return "Unsolvable"; //returns a String indicating that the board in unsolvable since the solver cannot provide any hints
        ArrayList<String> printableHints = new ArrayList<String>(hints); //Convert stack into ArrayList of printableHints
        int hintSize = printableHints.size(); //amount of hints that is used to solve the game
        String hintString = "Hints in order of sequence of moves from start to finish: "; //String that will be returned with the hints solved by the solver
        for(int i = 0 ; i <= hintSize - 1; i++){
            if(i != hintSize - 1) hintString += printableHints.get(i) + ", "; //concatenate first and intermediate hints
            else hintString += printableHints.get(i); //concatenate last hint
        }
        return hintString;
    }
}