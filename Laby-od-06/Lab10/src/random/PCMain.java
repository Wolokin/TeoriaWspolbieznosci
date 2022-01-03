package random;

import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channel, creates one of each process then
 * executes them in parallel, using JCSP.
 */
public final class PCMain {
    public static final int COUNT = 1000;
    public static final int BUFSIZE = 7;
    public static void main(String[] args) {
        new PCMain();
    } // main

    public PCMain() { // Create channel object

        // Create and run parallel construct with a list of processes
        AltingChannelInputInt[] ins = new AltingChannelInputInt[BUFSIZE];
        AltingChannelOutputInt[] outs = new AltingChannelOutputInt[BUFSIZE];
        CSProcess[] procList = new CSProcess[BUFSIZE +2];

        for(int i = 0; i < BUFSIZE; i++) {
            One2OneChannelSymmetricInt prodChannel = Channel.one2oneSymmetricInt();
            One2OneChannelSymmetricInt conChannel = Channel.one2oneSymmetricInt();
            procList[i] = new Buffer(i, prodChannel.in(), conChannel.out());
            ins[i] = conChannel.in();
            outs[i] = prodChannel.out();
        }
        Producer producer = new Producer(outs);
        Consumer consumer = new Consumer(ins);
        procList[BUFSIZE] = producer;
        procList[BUFSIZE +1] = consumer;
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    } // PCMain constructor

} // class PCMain
