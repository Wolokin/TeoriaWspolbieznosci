package inorder;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;

import static inorder.PCMain.COUNT;

/**
 * Consumer class: reads one int from input channel, displays it, then
 * terminates.
 */
public class Consumer implements CSProcess {
    private final ChannelInputInt in;

    public Consumer(final ChannelInputInt in) {
        this.in = in;
    } // constructor

    public void run() {
        int n = COUNT;
        while(n-- > 0) {
            int item = in.read();
            System.out.println("I got " + item + (item != n ? ". Discrepancy!!!" : ""));
        }
    } // run

} // class Consumer
