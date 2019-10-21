/**
 * Represents the JumpIn game board and contains methods
 * for manipulating game spaces.
 *
 * @author Rafid
 * @version x.x
 */
public class Board {

    private Space[][] gameBoard;
    private int holesFilled;
    private static final int BOARD_DIMENSION = 5;

    /**
     *  Initializes class instance variables for an empty JumpIn game board.
     *  boardDimension used instead of int 5 for readability.
     */
    public Board(){
        this.gameBoard = new Space[BOARD_DIMENSION][BOARD_DIMENSION];
        this.holesFilled = 0;
        initializeBoard();
    }

    /**
     *  Fills empty JumpIn game board from constructor with pieces in predetermined locations.
     */
    private void initializeBoard(){
        //Holes  will always be the same
        gameBoard[0][0] = new Hole(0, 0, false);
        gameBoard[0][4] = new Hole(0, 4, false);
        gameBoard[2][2] = new Hole(2, 2, false);
        gameBoard[4][0] = new Hole(4, 0, false);
        gameBoard[4][4] = new Hole(4, 4, false);

        //Initialize mushrooms, rabbits, foxes, and empty spaces
        for(int i = 0; i < BOARD_DIMENSION ; i++) {
            for(int j = 0; j < BOARD_DIMENSION;  j++) {
                if(i == 4 && j == 2){
                    gameBoard[i][j] = new Mushroom(i,j);
                }
                else if(i == 1 && j == 3){
                    this.gameBoard[i][j] = new Mushroom(i,j);
                }
                else if(i == 0 && j == 3) {
                    gameBoard[i][j] = new Rabbit(i, j);
                }
                else if(i == 2 && j == 4){
                    gameBoard[i][j] = new Rabbit(i, j);
                }
                else if(i == 4 && j == 1){
                    gameBoard[i][j] = new Rabbit(i, j);
                }
                //Vertical
                else if(i == 1 && j == 1){
                    FoxPart foxTail = new FoxPart(i-1, j, true, false);
                    FoxPart foxHead = new FoxPart(i, j,true, true, foxTail);
                    foxTail.setOtherFoxPart(foxHead);
                    gameBoard[i][j] = foxHead;
                    gameBoard[i-1][j] = foxTail;
                }
                //Horizontal
                else if(i == 3 && j == 4){
                    FoxPart foxTail = new FoxPart(i, j-1 , false, false, null);
                    FoxPart foxHead = new FoxPart(i, j,false, true, foxTail);
                    foxTail.setOtherFoxPart(foxHead);
                    gameBoard[i][j] = foxHead;
                    gameBoard[i][j-1] = foxTail;
                }
                else if(gameBoard[i][j] == null){
                    gameBoard[i][j] = new EmptySpace(i, j);
                }
            }
        }
    }

    /**
     *
     * @return int
     */
    public int getHolesFilled() {
        return holesFilled;
    }

    /**
     *
     */
    public void incrementHolesFilled() {
        holesFilled += 1;
    }

    /**
     *
     * @param row
     * @param column
     * @return Space
     */
    public Space getSpace(int row, int column) {
        return gameBoard[row][column];
    }

    /**
     *
     * @param row
     * @param column
     * @param space
     */
    public void setSpace(int row, int column, Space space) {
        gameBoard[row][column] = space;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        String boardString = "    0  1  2  3  4\n";
        boardString += "    --------------\n";
        for(int i = 0; i < BOARD_DIMENSION ; i++ ){
            boardString += i + " | ";
            for(int j = 0 ; j < BOARD_DIMENSION; j++){
                boardString += gameBoard[i][j].toString();
                if(j != BOARD_DIMENSION - 1) boardString += " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    public String legendString() {
        return
                "CH - Filled Hole\n" +
                "OH - Open Hole\n" +
                "MU - Mushroom\n" +
                "ES - Empty Space\n" +
                "RA - Rabbit\n" +
                "FH - Fox Head\n" +
                "FT - Fox Tail";
    }
}