/**
 * Allows the model to notify its views
 * @author Lazar
 */
public interface View {
    /**
     * Notifies the views
     * @param model notifies it's subscribers (the views) when a change is occured to it
     */
    public void update(JumpInModel model);
}
