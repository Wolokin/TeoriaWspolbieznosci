package inorder;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;

public class Buffer implements CSProcess {
    private final int index;
    private final ChannelInputInt in;
    private final ChannelOutputInt out;

    public Buffer(int index, ChannelInputInt in, ChannelOutputInt out) {
        this.index = index;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        while(true) {
            out.write(in.read());
        }
    }
}
