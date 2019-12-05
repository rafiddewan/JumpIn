import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class is used to provide a solution for the game provided DFS
 * @author Rafid Dewan
 */
import JumpInSpaces.*;
public class JumpInSolver {

    private Board board; //passes in the board of the game
    private ArrayList<Board> previousBoards; //previous boards that were used for each turn the solver makes
    private JumpInModel model; //used to verify if a move is legal or not
    private Stack<String> hints; //stack of hints throughout the game
    private int currentBoard; //the current index of the board array list

    /**
     * Constructor for JumpInSolver
     * Takes in a board and creates a new model to use to check valid moves for the solver
     */
    public JumpInSolver(){
        this.model = new JumpInModel();
        this.board = this.model.getBoard();
        this.hints = new Stack<String>();
        this.previousBoards = new ArrayList<Board>();
        this.currentBoard = 0;
    }
//
//
//    /**
//     * Depth first search for solver
//     * Do not move the movable piece if it's visited the space. BACKTRACK back to the previous board and find another destination. Remove a hint as well.
//     * If the movable piece is a rabbit//fox and it can move to an empty space. Move it to that space and add a hint and add the board to the array list of boards
//     * @param movablePiece is a foxhead or a rabbit
//     * @param pass is the amount of times DFS is called by a piece
//     * @return boolean to determine if the DFS has traversed atleast one time
//     */
//    public boolean DFS(Space movablePiece, int pass, boolean complete)
//    {
//        if(movablePiece instanceof Rabbit || movablePiece instanceof FoxPart)
//        {
//            for (int row = 0; row < 5; row++)
//            {
//                for (int col = 0; col < 5; col++)
//                {
//                    //checks to see if the piece has already been moved to the spot
//                    if(hasBeenVisited(movablePiece, row, col))
//                    {
//                        board = previousBoards.get(currentBoard); //BACKTRACK get the previous board and start from there
//                        removeLastHint(); //removes the last hint of the
//                    }
//                    else
//                        {
//                            if (movablePiece instanceof Rabbit)  //if the movable piece is a rabbit
//                            {
//                                if (model.canRabbitMove(movablePiece, board.getSpace(row, col)))
//                                {
//                                    movePieces(movablePiece, row, col); //move the rabbit
//                                    addHints("Rabbit"); //add hint
//                                    pass++;
//                                    DFS(board.getSpace(row, col), pass, true); //recursion until rabbit is in the hole
//                                }
//                            }
//                            else
//                                {
//                                    if (((FoxPart) movablePiece).getIsHead()) //if the fox part is a fox head (because you can only move by the head)
//                                    {
//                                        if (model.canFoxMove(movablePiece, board.getSpace(row, col)))
//                                        {
//                                            movePieces(movablePiece, row, col); //move the fox head
//                                            addHints("Fox"); //add the hint for fox
//                                            pass++;
//                                            complete = true; //successfully completed a single pass
//                                            DFS(board.getSpace(row, col), pass, true); //recursion until fox moves to all places
//                                        }
//                                    }
//                                }
//                        }
//                    }
//                }
//            }
//        return complete;
//    }

    /**
     * Checks to see if a certain piece has visited  a certain space or not
     *
     * @param movableSpace is a space which you can move
     * @param row is the given row the player wants to move the piece too
     * @param column is the given row the player wants to move the piece too
     * @return boolean that indicates if the rabbit or fox has been  to that space
     */
    private boolean hasBeenVisited(Space movableSpace, int row, int column)
    {
        boolean check = false;
        int count = 0;
        if(previousBoards.size() == 0) return false; //will always be false when there are no previous boards
        for(Board prevBoard: previousBoards)
        {
            if(prevBoard.getSpace(row, column) instanceof Rabbit && movableSpace instanceof Rabbit)
            {
                check = true;
                currentBoard = count; //index of board arraylist
                break;
            }
            else if(prevBoard.getSpace(row, column) instanceof FoxPart && movableSpace instanceof FoxPart)
            {
                if(((FoxPart) prevBoard.getSpace(row, column)).getIsHead())
                {
                    check = true;
                    currentBoard = count; //index of board arraylist
                    break;
                }
            }
            count++;
        }
        return check;
    }

    /**
     * Moves the piece to the new location and adds the board onto the stack to keep in track of it
     * @param movablePiece is either the Rabbit or fox selected to move
     * @param row moves the current piece to the board
     * @param column moves the current column to the board
     */
    private void movePieces(Space movablePiece, int row, int column)
    {
        model.setMoveCol(movablePiece.getColumn()); //set the column for the movable piece is placed on
        model.setMoveRow(movablePiece.getRow()); //set the row for the movable piece is placed on
        model.setPieceSelected(true);
        model.takeTurn(row, column);
        previousBoards.add(new Board(board));
    }

//    /**
//     * If there are 5 movable pieces that you cannot move then the board is unsolvable then the solver will return false
//     * If all the rabbits go into the 3 holes then the solver will return true
//     * @return boolean, true indicating the puzzle is solved, false indicating the puzzle is unsolvable
//     */
//    public boolean solver()
//    {
//        while(!model.isGameDone()) //keep doing this until game is done
//        {
//            int unSolvedPieces = 0; //declare number of unsolved pieces in this iteration
//            for(int row = 0; row < 5; row++)
//            {
//                for(int col = 0; col < 5; col++)
//                {
//                    if(board.getSpace(row, col) instanceof Rabbit)
//                    {
//                        if(!DFS(board.getSpace(row, col), 0, false)) unSolvedPieces++;
//                        //check to see if the game is done
//                        if (model.getBoard().getHolesEmpty() == 0)
//                        {
//                            model.setGameDone(true);
//                            break;
//                        }
//                    }
//                    if(board.getSpace(row, col) instanceof FoxPart)
//                    {
//                        if(!DFS(board.getSpace(row, col), 0, false)) unSolvedPieces++; //if DFS fails first pass, increment number of unSolvedPieces
//                    }
//                }
//                if(unSolvedPieces == 5) return false; //if DFS fails first pass
//            }
//        }
//        return true;
//    }

    /**
     * Adding a hint to the hintStack
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
        }
        catch (EmptyStackException e)
        {
            System.out.println("Empty stack error"); //Should not remove a hint off a empty stack
        }
    }

    /**
     * Returns a String with a solution of what the board is
     * The hints are essentially teling the user what to move and where to move them
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

    public String toStringDefault(){
        return "Hints in order of sequence of moves from start to finish: Rabbit, Fox, Rabbit, Rabbit, Fox";
    }
}