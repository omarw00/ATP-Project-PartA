package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchable;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
// this class slove maze by requested solving algorithm
public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private static int numOfSolvedMazes = 0;
    private HashMap<byte[],String> mazeSolutions;
// this func opens new file to save the solution of this new maze
    private void openSolutionFile(String tempDirectory, String solutionFile, Solution solution){
        try{
            File newFile = new File(tempDirectory,solutionFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(newFile));
            outputStream.writeObject(solution);
        }
        catch (IOException error){
            error.printStackTrace();
        }
    }
// this func returns solution path other returns null
    private Solution solutionFile(String tempDirectory, String solutionFile){
        try{
            Solution solution;
            File fileRead = new File(tempDirectory,solutionFile);
            FileInputStream fileInputStream = new FileInputStream(fileRead);
            ObjectInputStream InputStream = new ObjectInputStream(fileInputStream);
            solution = (Solution) InputStream.readObject();
            InputStream.close();
            return solution;
        }
        catch(ClassNotFoundException | IOException error){
            error.printStackTrace();
        }
        return null;
    }

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        ISearchingAlgorithm searchingAlgorithm = Configurations.mazeSearchingAlgorithm();
        mazeSolutions = new HashMap<>();
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            Solution solution;
            Maze maze = (Maze) fromClient.readObject();

            String tempDirectoryPath = System.getProperty("java.io.tmpdir");

            byte[] keyMaze = maze.toByteArray();
            System.out.println(Arrays.toString(keyMaze));

            if (!mazeSolutions.containsKey(keyMaze)){
                numOfSolvedMazes++;
                String solutionFileName = "maze solution" + numOfSolvedMazes + ".txt";
                mazeSolutions.put(keyMaze,solutionFileName);
                ISearchable searchable = new SearchableMaze(maze);
                solution = searchingAlgorithm.solve(searchable);
                openSolutionFile(tempDirectoryPath,solutionFileName,solution);
            }
            else{
                solution = solutionFile(tempDirectoryPath,mazeSolutions.get(keyMaze));
            }
            toClient.writeObject(solution);
            toClient.flush();
            toClient.close();
            fromClient.close();
        }
        catch (Exception error){
            error.printStackTrace();
        }

    }
}
