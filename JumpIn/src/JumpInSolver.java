import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class is used to provide a solution for the game provided BFS
 * @author Rafid Dewan
 */
public class JumpInSolver {

    private Board board; //passes in the board of the game
    private ArrayList<Board> previousBoards; //previous boards that were used for each turn the solver makes
    private JumpInModel model; //used to verify if a move is legal or not
    private Stack<String> hints; //stack of hints throughout the game

    /**
     * Constructor for JumpInSolver
     * Takes in a board and creates a new model to use to check valid moves for the solver
     */
    public JumpInSolver(){
        this.model = new JumpInModel();
        this.board = this.model.getBoard();
        this.hints = new Stack<String>();
        this.previousBoards = new ArrayList<Board>();
    }

    /**
     *
     * @param movablePiece is a foxhead or a rabbit
     * @return boolean
     */
    public boolean DFS(Space movablePiece){
        if(movablePiece instanceof Rabbit || movablePiece instanceof FoxPart){
            for (int row = 0; row < 5; row++)
            {
                for (int col = 0; col < 5; col++)
                {
                    //checks to see if the piece has already been moved to the spot
                    if(hasBeenVisited(movablePiece, row, col)){
                        board = previousBoards.get(previousBoards.size()-1); //
                        previousBoards.remove(previousBoards.get(previousBoards.size()-1)); //removes the last board  since we will be going back to the previous one already
                        removeLastHint(); //removes the last hint of the
                    }
                    else {
                        if (movablePiece instanceof Rabbit) { //if the movable piece is a rabbit
                            if (model.canRabbitMove(movablePiece, board.getSpace(row, col))) {
                                movePieces(movablePiece, row, col); //move the rabbit
                                addHints("Rabbit"); //add hint
                                DFS(board.getSpace(row, col)); //recursion until rabbit is in the hole
                            }
                        } else {
                            if (((FoxPart) movablePiece).getIsHead()) { //if the fox part is a fox head (because you can only move by the head)
                                if (model.canFoxMove(movablePiece, board.getSpace(row, col))) {
                                    movePieces(movablePiece, row, col); //move the fox head
                                    addHints("Fox"); //add the hint for fox
                                    DFS(board.getSpace(row, col)); //recursion until fox moves to all places
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if a certain piece has visited  a certain space or not
     *
     * @param movableSpace
     * @param row is the given row the player wants to move the piece too
     * @param column is the given row the player wants to move the piece too
     * @return boolean that indicates if the rabbit or fox has been  to that space
     */
    private boolean hasBeenVisited(Space movableSpace, int row, int column)
    {
        boolean check = false;
        for(Board prevBoard: previousBoards)
        {
            if(prevBoard.getSpace(row, column) instanceof Rabbit && movableSpace instanceof Rabbit)
            {
                check = true;
                break;
            }
            else if(prevBoard.getSpace(row, column) instanceof FoxPart && movableSpace instanceof FoxPart)
            {
                if(((FoxPart) prevBoard.getSpace(row, column)).getIsHead())
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    /**
     * Moves the piece to the new location and adds the board onto the stack to keep in track of it
     * @param movablePiece is either the Rabbit or fox selected to move
     * @param row moves the current piece to the board
     * @param column moves the current column to the board
     */
    private void movePieces(Space movablePiece, int row, int column){
        model.setMoveCol(movablePiece.getColumn());
        model.setMoveRow(movablePiece.getRow());
        model.setPieceSelected(true);
        model.takeTurn(row, column);
        previousBoards.add(board);
    }

    public boolean solver()
    {
        while(!model.isGameDone()) //keep doing this until game is done
        {
            for(int row = 0; row < 5; row++)
            {
                for(int col = 0; col < 5; col++)
                {
                    int count = 0;
                    while(count != 3){ //try moving 3 rabbits first
                        if(board.getSpace(row, col) instanceof Rabbit){
                            DFS(board.getSpace(row, col));
                            //check to see if the game is done
                            if (model.getBoard().getHolesFilled() == 3) {
                                model.setGameDone(true);
                            }
                        }
                        count++;
                    }
                    count = 0;
                    while(count != 2){ //try moving 2 foxes
                        if(board.getSpace(row, col) instanceof FoxPart){
                            DFS(board.getSpace(row, col));
                            count++;
                        }
                    }
                }
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
    private void removeLastHint() {
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