public class Hole extends Space {
    private boolean isFilled;

    /**
     *
     * @param posX
     * @param posY
     */
    public Hole(int posX, int posY) {
        super(posX, posY);
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
