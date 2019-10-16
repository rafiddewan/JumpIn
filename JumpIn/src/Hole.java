/**
 * @author Rafid
 */
public class Hole extends Space {
    private boolean isFilled;

    /**
     *
     * @param row
     * @param column
     */
    public Hole(int row, int column, boolean filled) {
        super(row, column);
        this.isFilled = filled;
    }

    /**
     *
     * @return boolean
     */
    public boolean getIsFilled() {
        return isFilled;
    }

    /**
     *
     * @param filled
     */
    public void setIsFilled(boolean filled) {
        this.isFilled = filled;
    }

    @Override
    public String toString() {
        return (isFilled) ? "CH" : "OH";
    }
}
