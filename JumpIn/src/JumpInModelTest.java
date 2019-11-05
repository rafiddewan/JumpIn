import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author Nick
 */
public class JumpInModelTest {
    JumpInModel model;

    @Before
    public void setUp() {
       this.model = new JumpInModel();
    }

    @Test
    public void isGameDoneTest(){
        assertEquals(false,model.isGameDone());
    }
    @Test
    public void setGameDoneTest(){
        model.setGameDone(true);
        assertEquals(true,model.isGameDone());
    }
    @Test
    public void setMoveColTest(){
        model.setMoveCol(6);
        assertEquals(6,model.getMoveCol());
    }
    @Test
    public void setMoveRowTest(){
        model.setMoveRow(6);
        assertEquals(6,model.getMoveRow());
    }
    @Test
    public void isBadMoveTest(){
        assertEquals(false,model.isBadMove());
    }
    @Test
    public void setBadMoveTest(){
        model.setBadMove(true);
        assertEquals(true,model.isBadMove());
    }
    @Test
    public void isDestinationTest(){
        assertEquals(false,model.isDestination());
    }
    @Test
    public void setDestinationTest(){
        model.setDestination(true);
        assertEquals(true,model.isDestination());
    }
}