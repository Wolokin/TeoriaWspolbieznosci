package Lab05;

import java.util.concurrent.ExecutionException;

import static Lab05.Mandelbrot.HEIGHT;
import static Lab05.Mandelbrot.WIDTH;

public class Main {
    public static final int[] THREAD_COUNT = {1,6,12};
    public static final int[] JOBS = {1,10, WIDTH*HEIGHT, 6, 60, WIDTH*HEIGHT, 12,120,WIDTH*HEIGHT};
    public static final int REPS = 1;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatCollector statCollector = new StatCollector();
        for(int i = 0; i < THREAD_COUNT.length; i++) {
            for(int j = 0; j < THREAD_COUNT.length; j++) {
                for (int k = 0; k < REPS; k++) {
                    long start = System.nanoTime();
                    int threadcount = THREAD_COUNT[i];
                    int jobs = JOBS[i * THREAD_COUNT.length + j];
                    new Mandelbrot(threadcount, jobs);
                    long stop = System.nanoTime();
                    statCollector.add(threadcount, jobs, stop-start);
                }
            }
        }
        System.out.println(statCollector);
    }
}
