/**
 * @author Rafid
 */
public interface Fox {
    public boolean getIsVertical();
    public void setIsVertical(boolean vertical);
    public void moveBoth(Space desiredHeadSpace, Space desiredTailSpace);
}
