/**
 * Parent  class of EmptySpace, Fox, Holes, Mushroom, Rabbit, and Fox classes
 * @author Rafid
 */
/**
 * Parent  class of EmptySpace, Fox, Holes, Mushroom, Rabbit, and Fox classes
 * @author Rafid
 */
public class Space {

    private int row;
    private int column;

    /**
     *
     * @param row
     * @param column
     */
    public Space(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     *
     * @return int
     */
    public int getColumn() {
        return column;
    }

    /***
     *
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Space{" +
                "posX=" + row +
                ", posY=" + column +
                '}';
    }
}