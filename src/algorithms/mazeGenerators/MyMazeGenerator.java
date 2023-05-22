package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int cols) {
        Maze MyMaze = new Maze(rows,cols);
        for (int r = 0; r<rows;r++){
            for (int c = 0; c <cols;c++){
                MyMaze.getMaze()[r][c] = 1;
            }
        }
        PickStartPosition(MyMaze);
        ArrayList<Position> wallList = new ArrayList<>();
        Position startpos = MyMaze.getStartPosition();
        wallList.add(startpos);
        Random random = new Random();
        Position currentpos;
        while (!wallList.isEmpty()){
            currentpos = wallList.remove(random.nextInt(wallList.size()));
            if(oneVisitor(MyMaze,currentpos)<2){
                MyMaze.getMaze()[currentpos.getRowIndex()][currentpos.getColumnIndex()] = 0;
                getNeighbors(wallList,MyMaze,currentpos);
            }
        }
        PickExitPosition(MyMaze);
        return MyMaze;
    }

    private static int legal_Position(Maze maze, Position position){
        if (position.getRowIndex()<0 || position.getRowIndex()>= maze.getRows() || position.getColumnIndex() < 0 || position.getColumnIndex()>= maze.getColumns())
            return 0;
        return 1;
    }
    private static void getNeighbors( ArrayList<Position> wallList,Maze maze,Position pos){
        Position upindex,downindex,leftindex,rightindex;
        upindex = new Position(pos.getRowIndex()+1,pos.getColumnIndex());
        downindex = new Position(pos.getRowIndex()-1,pos.getColumnIndex());
        leftindex = new Position(pos.getRowIndex(),pos.getColumnIndex()-1);
        rightindex = new Position(pos.getRowIndex(),pos.getColumnIndex()+1);
        if(legal_Position(maze,upindex)==1 && maze.getMaze()[upindex.getRowIndex()][upindex.getColumnIndex()] ==1)
            wallList.add(upindex);
        if(legal_Position(maze,downindex)==1 && maze.getMaze()[downindex.getRowIndex()][downindex.getColumnIndex()] ==1)
            wallList.add(downindex);
        if(legal_Position(maze,leftindex)==1 && maze.getMaze()[leftindex.getRowIndex()][leftindex.getColumnIndex()] ==1)
            wallList.add(leftindex);
        if(legal_Position(maze,rightindex)==1 && maze.getMaze()[rightindex.getRowIndex()][rightindex.getColumnIndex()] ==1)
            wallList.add(rightindex);


    }
    private static int oneVisitor(Maze maze,Position pos){
        Position upindex,downindex,leftindex,rightindex;
        upindex = new Position(pos.getRowIndex()+1,pos.getColumnIndex());
        downindex = new Position(pos.getRowIndex()-1,pos.getColumnIndex());
        leftindex = new Position(pos.getRowIndex(),pos.getColumnIndex()-1);
        rightindex = new Position(pos.getRowIndex(),pos.getColumnIndex()+1);
        int numOfVisited = 0;
        if(legal_Position(maze,upindex)==1 && maze.getMaze()[upindex.getRowIndex()][upindex.getColumnIndex()] ==0)
            numOfVisited++;
        if(legal_Position(maze,downindex)==1 && maze.getMaze()[downindex.getRowIndex()][downindex.getColumnIndex()] ==0)
            numOfVisited++;
        if(legal_Position(maze,leftindex)==1 && maze.getMaze()[leftindex.getRowIndex()][leftindex.getColumnIndex()] ==0)
            numOfVisited++;
        if(legal_Position(maze,rightindex)==1 && maze.getMaze()[rightindex.getRowIndex()][rightindex.getColumnIndex()] ==0)
            numOfVisited++;
        return numOfVisited;
    }
}
