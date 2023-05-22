package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int cols) {

        Maze empMaze = new Maze(rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                empMaze.getMaze()[r][c] = 0;
            }
        }
        PickStartPosition(empMaze);
        PickExitPosition(empMaze);
        return empMaze;
    }
}
