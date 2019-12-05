import JumpInSpaces.Board;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.*;

public class JumpInSolverTest {

    JumpInSolver solver;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor(){

        //Create the default board
        Board board = new Board();
        JumpInSolver solver = new JumpInSolver(new Board(board));
        //Ensure that the current board is 0
        assertEquals("Every time a solver is created the value of the current board should be the first board (0th board)", 0, solver.getCurrentBoard());

        //Test if the default board is null
        assertNotNull(solver.getBoard());

        //Test to see if the hints stack is working properly
        Stack stack = new Stack<String>();
        stack.add("hi");
        solver.setHints(stack);
        assertNotNull(solver.getHints());

        //Test to see if you can make a previous board
        ArrayList previousBoards = new ArrayList<Board>();
        previousBoards.add(board);
        solver.setPreviousBoards(previousBoards);
        assertNotNull(solver.getPreviousBoards());
        assertEquals(previousBoards.get(0), solver.getBoard()); //test to see if they are same board that has been copied
    }

    @Test
    public void testToString() {
        solver.addHints("Rabbit");
        solver.addHints("Fox");
        solver.addHints("Rabbit");
        solver.addHints("Rabbit");
        solver.addHints("Fox");
        String boardType1 = "Hints in order of sequence of moves from start to finish: Rabbit, Fox, Rabbit, Rabbit, Fox";
        assertEquals("Not the correct  solution", boardType1, solver.toString());
    }
//
//    @Test
//    public void solver() {
//        assertTrue(solver.solver());
//    }
}