package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    // MAZE GENERATORS!
    private EmptyMazeGenerator EmptyMazeGenerator = new EmptyMazeGenerator();
    private SimpleMazeGenerator SimpleMazeGenerator = new SimpleMazeGenerator();
    private MyMazeGenerator MyMazeGenerator = new MyMazeGenerator();

    //Best First Search!
    private BestFirstSearch bestFS = new BestFirstSearch();
    @Test
    void getName(){
        assertEquals("Best Frist Search",bestFS.getName());
    }


    @Test
    void solve() {
        assertEquals(1,checkroads(EmptyMazeGenerator,1,1));
        assertEquals(1,checkroads(EmptyMazeGenerator,5,5));
        assertEquals(1,checkroads(EmptyMazeGenerator,-5,5));
        assertEquals(1,checkroads(EmptyMazeGenerator,5,-5));
        assertEquals(1,checkroads(EmptyMazeGenerator,555,900));

        assertEquals(1,checkroads(SimpleMazeGenerator,1,1));
        assertEquals(1,checkroads(SimpleMazeGenerator,5,5));
        assertEquals(1,checkroads(SimpleMazeGenerator,-5,5));
        assertEquals(1,checkroads(SimpleMazeGenerator,5,-5));
        assertEquals(1,checkroads(SimpleMazeGenerator,555,900));

        assertEquals(1,checkroads(MyMazeGenerator,1,1));
        assertEquals(1,checkroads(MyMazeGenerator,5,5));
        assertEquals(1,checkroads(MyMazeGenerator,-5,5));
        assertEquals(1,checkroads(MyMazeGenerator,5,-5));
        assertEquals(1,checkroads(MyMazeGenerator,555,900));


    }

    private int checkroads(AMazeGenerator mazeGenerator,int row,int col){
        Maze maze = mazeGenerator.generate(row,col);
        SearchableMaze searchable = new SearchableMaze(maze);
        Solution sol = bestFS.solve(searchable);
        ArrayList<AState> path = sol.getSolutionPath();

        if (path.get(0) != searchable.getStartState()){
            return 0;
        }
        if (path.get(path.size()-1) != searchable.getGoalState()){
            return 0;
        }
        int i = 0;
        while (i<path.size()){
            MazeState checkstate = (MazeState) path.get(i);
            if (maze.getMaze()[checkstate.getRowIndex()][checkstate.getColIndex()] == 1){
                return 0;
            }
            i++;
        }
        return 1;
    }
}