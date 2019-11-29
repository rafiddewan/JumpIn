import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JumpInModelRabbitMoveTests {
    JumpInModel model;

    @Before
    public void setUp() {
        this.model = new JumpInModel();
    }
    @Test
    public void testRabbitMoveToSameSpot(){
        Rabbit r = (Rabbit) model.getBoard().getSpace(0,3);
        Space destination  = model.getBoard().getSpace(0,3);
        assertEquals(false, model.canRabbitMove(r,destination));
    }
    @Test
    public void testRabbitMoveToValidEmptySpot(){//testing movement with jumping to a valid space
        Rabbit r = (Rabbit) model.getBoard().getSpace(0,3);
        Space destination  = model.getBoard().getSpace(2,3);
        assertEquals(true, model.canRabbitMove(r,destination));
    }

    @Test
    public void testRabbitMoveToInvalidOpenHole(){//testing movement without jumping
        Rabbit r = (Rabbit) model.getBoard().getSpace(0,3);
        Space destination  = model.getBoard().getSpace(0,4);
        assertEquals(false, model.canRabbitMove(r,destination));
    }
    @Test
    public void testRabbitMoveToInvalidEmptySpace(){//testing movement without jumping
        Rabbit r = (Rabbit) model.getBoard().getSpace(0,3);
        Space destination  = model.getBoard().getSpace(0,2);
        assertEquals(false, model.canRabbitMove(r,destination));
    }
    @Test
    public void testRabbitMoveToAnOpenHole(){//testing movement into an empty hole
        model.setMoveRow(0);
        model.setMoveCol(3);
        model.takeTurn(2,3);
        Rabbit r = (Rabbit) model.getBoard().getSpace(2,4);
        Space destination  = model.getBoard().getSpace(2,2);
        assertEquals(true, model.canRabbitMove(r,destination));
    }
    @Test
    public void testRabbitMoveJumpingOverEmptySpaces(){//testing movement over empty spaces
        Rabbit r = (Rabbit) model.getBoard().getSpace(4,1);
        Space destination  = model.getBoard().getSpace(2,1);
        assertEquals(false, model.canRabbitMove(r,destination));
    }
    @Test
    public void testRabbitMoveJumpingOntoFox(){//testing movement into a fox
        model.setMoveRow(0);
        model.setMoveCol(1);
        model.takeTurn(2,1);
        Rabbit r = (Rabbit) model.getBoard().getSpace(4,1);
        Space destination  = model.getBoard().getSpace(2,1);
        assertEquals(false, model.canRabbitMove(r,destination));
    }

}
