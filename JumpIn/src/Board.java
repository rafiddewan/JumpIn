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
    private static final int boardDimension = 5;

    /**
     *  Initializes class instance variables for an empty JumpIn game board.
     *  boardDimension used instead of int 5 for readability.
     */
    public Board(){
        this.gameBoard = new Space[boardDimension][boardDimension];
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
        for(int i = 0; i < boardDimension ; i++) {
            for(int j = 0; j < boardDimension;  j++) {
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
                    FoxPart foxTail = new FoxPart(i, j, true, false);
                    FoxPart foxHead = new FoxPart(i, j,true, true, foxTail);
                    foxTail.setOtherFoxPart(foxHead);
                    gameBoard[i][j] = foxHead;
                    gameBoard[i-1][j] = foxTail;
                }
                //Horizontal
                else if(i == 3 && j == 4){
                    FoxPart foxTail = null;
                    FoxPart foxHead = new FoxPart(i, j,false, true, foxTail);
                    foxTail = new FoxPart(i, j-1 , false, false, foxHead);
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
     *  Increments number of holes filled by 1.
     */
    public void incrementHolesFilled() {
        holesFilled += 1;
    }

    /**
     *  Getter for space occupying indicated board position
     *
     * @param posX
     * @param posY
     * @return Space Type of space occupying coordinate indicated
     */
    public Space getSpace(int posX, int posY) {
        return gameBoard[posX][posY];
    }

    /**
     *  Sets board space indicated by parameters posX and posY to given Space type
     *
     * @param posX Vertical position of wanted space (row)
     * @param posY Horizontal position of wanted space (column)
     * @param space Type of Space to fill coordinate by other parameters
     */
    public void setSpace(int posX, int posY, Space space) {
        gameBoard[posX][posY] = space;
    }

    /**
     *  Print current game board; codes for spaces are in the toString methods
     *  of each subclass of Space.java
     *
     *  @return String
     */
    @Override
    public String toString() {
        String boardString = "";
        for(int i = 0; i < boardDimension ; i++ ){
            for(int j = 0 ; j < boardDimension; j++){
                boardString += gameBoard[i][j].toString();
                if(j != boardDimension - 1) boardString += " ";
            }
            boardString += "\n";
        }
        return boardString;
    }
}