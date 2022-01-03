package random;

import org.jcsp.lang.*;

import static random.PCMain.COUNT;

/**
 * Producer class: produces one random integer and sends on
 * output channel, then terminates.
 */
public class Producer implements CSProcess {
    private final AltingChannelOutputInt[] out;

    public Producer(final AltingChannelOutputInt[] out) {
        this.out = out;
    } // constructor

    public void run() {
        Alternative alt = new Alternative(out);
        int n = COUNT;
        while(n-- > 0) {
            int index = alt.fairSelect();
            out[index].write(n);
        }
    } // run

} // class Producer
