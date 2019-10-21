/**
 * @author Nick, Rafid
 */
public class Rabbit extends Space implements MoveableSpace {

    /**
     *
     * @param row
     * @param column
     */
    public Rabbit(int row, int column){
        super(row, column);
    }

    /**
     *
     * @param desiredRow
     * @param desiredColumn
     */
    @Override
    public void move(int desiredRow, int desiredColumn) {
        this.setRow(desiredRow);
        this.setColumn(desiredColumn);
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "RA";
    }
}