import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JumpInSolverTest {

    JumpInSolver solver;
    @Before
    public void setUp() throws Exception {
        solver = new JumpInSolver();
        solver.addHints("Rabbit");
        solver.addHints("Fox");
        solver.addHints("Rabbit");
        solver.addHints("Rabbit");
        solver.addHints("Fox");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToString() {
        String boardType1 = "Hints in order of sequence of moves from start to finish: Rabbit, Fox, Rabbit, Rabbit, Fox";
        assertEquals("Not the correct  solution", boardType1, solver.toString());
    }
}