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
/*
        out.flush();
        out.close();
*/

/*
        byte last = b[ind];
        if (ind < bSize){
            last = b[ind];
            if (last != 0){
                bytes.add(numOfAppearances);
                last = 1;
                numOfAppearances++;
            }
            else{
                numOfAppearances++;
            }
            ind++;
        }
        boolean capacity255 = false;
        while (ind < bSize){
            currentByte = b[ind];
            if (currentByte != last){
                if (!capacity255)
                    bytes.add(numOfAppearances);
                else
                    capacity255 = false;
                numOfAppearances = 1;
                last = currentByte;
            }
            else{
                numOfAppearances++;
                if (capacity255){
                    bytes.add((byte) 0 );
                    capacity255 = false;
                }
                if (numOfAppearances == (byte) 255){
                    bytes.add((byte) 255);
                    numOfAppearances = 0;
                    capacity255 = true;
                }
            }
            ind++;
        }
        bytes.add(numOfAppearances);
        int bytesSize = bytes.size();
        byte[] compressedArr = new byte[bytesSize];
        int i = 0;
        while (bytesSize > 0){
            compressedArr[i] = bytes.remove(0);
            i++;
            bytesSize--;
        }
        out.write(compressedArr);
        out.flush();
        out.close();*/
    }
}
