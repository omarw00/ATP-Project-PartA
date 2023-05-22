package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }
    @Override
    public int read(byte[] b) throws IOException {
        int ind = 0;
        byte[] inBytes = in.readAllBytes();

        // data members
        for (ind = 0; ind < 24; ind++) {
            b[ind] = inBytes[ind];
        }
// the starts of the maze it self
        byte current = 0;
        int counter = 0;
        int index = ind;

        for(;ind< inBytes.length ; ind++){
            if (ind % 2 == 1)
                current = 1;
            else
                current = 0;
            counter = inBytes[ind];

            for (; counter>0; counter--){
                b[index] = current;
                index++;
            }
        }
        return 0;
    }
}
