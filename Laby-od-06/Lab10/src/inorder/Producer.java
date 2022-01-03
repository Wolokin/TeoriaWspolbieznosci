package inorder;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelOutputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;

import static inorder.PCMain.COUNT;

/**
 * Producer class: produces one random integer and sends on
 * output channel, then terminates.
 */
public class Producer implements CSProcess {
    private final ChannelOutputInt out;

    public Producer(final ChannelOutputInt out) {
        this.out = out;
    } // constructor

    public void run() {
        int n = COUNT;
        while(n-- > 0) {
            out.write(n);
        }
    } // run

} // class Producer
