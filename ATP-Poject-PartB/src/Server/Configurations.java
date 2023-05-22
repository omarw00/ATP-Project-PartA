package Server;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

public class Configurations {
    private static Properties properties = new Properties();
// func to get the configured num of thread pool
    public static int threadPoolSize(){
        try{
            return Integer.parseInt(properties.getProperty("threadPoolSize"));
        }catch (NumberFormatException error){
            error.printStackTrace();
        }
        return 2;
    }
// func to get the configured type of maze Generating Algorithm

    public static IMazeGenerator mazeGeneratingAlgorithm(){
        return switch (properties.getProperty("mazeGeneratingAlgorithm")) {
            case "MyMazeGenerator" -> new MyMazeGenerator();
            case "EmptyMazeGenerator" -> new EmptyMazeGenerator();
            case "SimpleMazeGenerator" -> new SimpleMazeGenerator();
            default -> new MyMazeGenerator();
        };
    }

// func to get the configured type of maze searching  Algorithm

    public static ISearchingAlgorithm mazeSearchingAlgorithm(){
        return switch (properties.getProperty("mazeSearchingAlgorithm")) {
            case "BreadthFirstSearch" -> new BreadthFirstSearch();
            case "DepthFirstSearch" -> new DepthFirstSearch();
            case "BestFirstSearch" -> new BestFirstSearch();
            default -> new BestFirstSearch();
        };
    }
    // to get the specific configuration file to properties field
    public static void start(){
        InputStream inputStream = null;

        try{
            inputStream = new FileInputStream("./resources/config.properties");
            properties.load(inputStream);
        }
        catch (IOException error){
            error.printStackTrace();
        }
        finally{
            if (inputStream != null){
                try {
                    inputStream.close();
                }
                catch (IOException error){
                    error.printStackTrace();
                }
            }
        }
    }

}
