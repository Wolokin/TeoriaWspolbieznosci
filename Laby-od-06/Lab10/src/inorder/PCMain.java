package inorder;

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
        CSProcess[] procList = new CSProcess[BUFSIZE +2];

        One2OneChannelInt channel = Channel.one2oneInt();
        Producer producer = new Producer(channel.out());
        for(int i = 0; i < BUFSIZE; i++) {
            One2OneChannelInt prev = channel;
            channel = Channel.one2oneInt();
            procList[i] = new Buffer(i, prev.in(), channel.out());
        }
        Consumer consumer = new Consumer(channel.in());
        procList[BUFSIZE] = producer;
        procList[BUFSIZE +1] = consumer;
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    } // PCMain constructor

} // class PCMain
