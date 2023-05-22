package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

    }
    @Override
    public void write(byte[] b) throws IOException {
        int ind=0;
        ArrayList<Byte> bytes = new ArrayList<>();
        int numOfAppearances = 0;

        // data members
        for (ind = 0; ind < 24 ; ind++){
            bytes.add(b[ind]);
        }
        byte last = 0;
        byte current = b[ind];
// the starts of the maze it self
        for( ;ind < b.length ;ind++){
            if( current != last){
                bytes.add((byte) numOfAppearances);
                numOfAppearances = 1;
            }else{
                numOfAppearances++;
                if( numOfAppearances > 255){
                    bytes.add((byte) 255);
                    bytes.add((byte) 0);
                    numOfAppearances = 1;
                }
            }
            last = current;
            if (ind+1 < b.length)
                current = b[ind + 1];
            else
                bytes.add((byte) numOfAppearances);
        }
        byte[] b1 = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++){
            b1[i] = bytes.get(i);
        }
        out.write(b1);
    }
}
