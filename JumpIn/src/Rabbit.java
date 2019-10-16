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
     * @param desiredSpace
     */
    @Override
    public void move(Space desiredSpace) {
        this.setRow(desiredSpace.getRow());
        this.setColumn(desiredSpace.getColumn());
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