package algorithms.mazeGenerators;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class Maze implements Serializable {
    private int Rows;
    private int Columns;
    private Position Start;
    private Position Exit;
    private int[][] Maze;

    public int getRows() {
        return Rows;
    }

    public int getColumns() {
        return Columns;
    }

    public Maze(int Rows, int Columns) {
        //// returining an exception( columns and rows has to be bigger than 1)
        if (Columns < 2 || Rows < 2){
            this.Rows = 3;
            this.Columns = 3;
            this.Maze = new int[3][3];

        }
        else {
            this.Rows = Rows;
            this.Columns = Columns;
            this.Maze = new int[this.Rows][this.Columns];
        }
        this.Exit = null;
        this.Start = null;
    }

    public Position getGoalPosition() {
        return Exit;
    }

    public Position getStartPosition() {
        return Start;
    }

    public int[][] getMaze() {
        return Maze;
    }

    public void setMaze(int[][] maze) {
        Maze = maze;
    }

    public void setStart(Position start) {
        Start = start;
    }

    public void setExit(Position exit) {
        Exit = exit;
    }
    public void print(){
        System.out.println("{");
        for(int r = 0;r<this.getRows();r++){
            System.out.print("{");
            for( int c = 0 ; c<this.getColumns();c++){
                if(this.getStartPosition().getRowIndex() == r && this.getStartPosition().getColumnIndex() == c){
                    if (c== this.getRows()-1)
                        System.out.print("S");
                    else
                        System.out.print("S,");

                }
                else if(this.getGoalPosition().getRowIndex() == r && this.getGoalPosition().getColumnIndex() == c){
                    if (c== this.getRows()-1)
                        System.out.print("E");
                    else
                        System.out.print("E,");

                }
                else{
                    if (c==this.getRows()-1)
                        System.out.print("" + this.Maze[r][c]);
                    else
                        System.out.print("" + this.Maze[r][c]+",");

                }
            }
            //
            if (r == this.getRows()-1)
                System.out.println("}");
            else
                System.out.println("},");

        }
        System.out.println("};");

    }
    public int legal_Pos(Position position){
        if ((position.getRowIndex()<0 || position.getRowIndex()>= this.getRows() || position.getColumnIndex() < 0 || position.getColumnIndex()>= this.getColumns())|| this.Maze[position.getRowIndex()][position.getColumnIndex()] == 1)
            return 0;
        return 1;
    }
    public Maze(byte[] ByteArray){
        byte[] value = new byte[4];
        // calculating number of the rows
        value[0] = ByteArray[0];
        value[1] = ByteArray[1];
        value[2] = ByteArray[2];
        value[3] = ByteArray[3];
        int numOfRows = ByteBuffer.wrap(value).getInt();

        // calculating number of columns
        value[0] = ByteArray[4];
        value[1] = ByteArray[5];
        value[2] = ByteArray[6];
        value[3] = ByteArray[7];
        int numOfCols = ByteBuffer.wrap(value).getInt();

        // calculating Start Position row
        value[0] = ByteArray[8];
        value[1] = ByteArray[9];
        value[2] = ByteArray[10];
        value[3] = ByteArray[11];
        int startR = ByteBuffer.wrap(value).getInt();

        //calculating Start position column
        value[0] = ByteArray[12];
        value[1] = ByteArray[13];
        value[2] = ByteArray[14];
        value[3] = ByteArray[15];
        int startC = ByteBuffer.wrap(value).getInt();

        // calculating goal position row
        value[0] = ByteArray[16];
        value[1] = ByteArray[17];
        value[2] = ByteArray[18];
        value[3] = ByteArray[19];
        int goalR = ByteBuffer.wrap(value).getInt();

        //calculating goal position column
        value[0] = ByteArray[20];
        value[1] = ByteArray[21];
        value[2] = ByteArray[22];
        value[3] = ByteArray[23];
        int goalC = ByteBuffer.wrap(value).getInt();

        this.Rows = numOfRows;
        this.Columns = numOfCols;
        this.Start = new Position(startR,startC);
        this.Exit = new Position(goalR,goalC);
        this.Maze = new int[numOfRows][numOfCols];

        int index = 24;
        for (int r = 0; r< numOfRows;r++){
            for (int c = 0; c< numOfCols;c++){
                this.Maze[r][c] = ByteArray[index];
                index++;
            }
        }

    }
    public byte[] toByteArray(){
        int size = 24 + (this.Rows * this.Columns);
        byte[] byteArray = new byte[size];

        int rows = getRows();
        int cols = getColumns();
        int startR = getStartPosition().getRowIndex();
        int startC = getStartPosition().getColumnIndex();
        int goalR = getGoalPosition().getRowIndex();
        int goalC = getGoalPosition().getColumnIndex();

        byte[] byteR = ByteBuffer.allocate(4).putInt(rows).array();
        byte[] byteC = ByteBuffer.allocate(4).putInt(cols).array();
        byte[] byteStartR = ByteBuffer.allocate(4).putInt(startR).array();
        byte[] byteStartC = ByteBuffer.allocate(4).putInt(startC).array();
        byte[] byteGoalR = ByteBuffer.allocate(4).putInt(goalR).array();
        byte[] byteGoalC = ByteBuffer.allocate(4).putInt(goalC).array();

        putData24(byteArray,byteR,0);
        putData24(byteArray,byteC,4);
        putData24(byteArray,byteStartR,8);
        putData24(byteArray,byteStartC,12);
        putData24(byteArray,byteGoalR,16);
        putData24(byteArray,byteGoalC,20);

        int i = 24;
        for (int r = 0; r< rows;r++){
            for (int c = 0; c< cols;c++){
                byteArray[i] = (byte) this.Maze[r][c] ;
                i++;
            }
        }
        return byteArray;
    }

    public void putData24(byte[] mainArray, byte[] putinto, int index){
        for(int i = 0; i< 4;i++){
            mainArray[index] = putinto[i];
            index++;
        }

    }
}
