package random;

import org.jcsp.lang.*;

import static random.PCMain.COUNT;

/**
 * Consumer class: reads one int from input channel, displays it, then
 * terminates.
 */
public class Consumer implements CSProcess {
    private final AltingChannelInputInt[] in;

    public Consumer(final AltingChannelInputInt[] in) {
        this.in = in;
    } // constructor

    public void run() {
        Alternative alt = new Alternative(in);
        int n = COUNT;
        while(n-- > 0) {
            int index = alt.fairSelect();
            int item = in[index].read();
            System.out.println("I got " + item + (item != n ? ". Discrepancy!!!" : ""));
        }
    } // run

} // class Consumer
