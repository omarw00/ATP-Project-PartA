package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;

import java.io.OutputStream;
// this class generate maze by requested generating algorithm
public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {

            Maze mazeToClient;
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            ByteArrayOutputStream bytesToClient = new ByteArrayOutputStream();
            MyCompressorOutputStream toClientCompressed = new MyCompressorOutputStream(bytesToClient);

            Object data = fromClient.readObject();
            toClient.flush();
            IMazeGenerator mazeGenerator = Configurations.mazeGeneratingAlgorithm();

            if (data instanceof int[]){
                if(((int[]) data).length == 2){
                    int row = ((int[]) data)[0];
                    int col = ((int[]) data)[1];
                    mazeToClient = mazeGenerator.generate(row,col);
                }else
                    mazeToClient = mazeGenerator.generate(10,10);
            }else
                mazeToClient = mazeGenerator.generate(10,10);
            byte[] byteMaze = mazeToClient.toByteArray();
            toClientCompressed.write(byteMaze);
            toClient.writeObject(bytesToClient.toByteArray());
            toClient.flush();
        }
        catch(Exception error){
            error.printStackTrace();

        }
    }
}
