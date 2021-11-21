package Lab05;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    public static final int MAX_ITER = 5700;
    public static final double ZOOM = 150;
    private final BufferedImage I;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public Mandelbrot(int threadcount, int jobs) throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(threadcount);
        Set<Future<?>> set = new HashSet<>();
        int ys = 0;
        int xs = 0;
        for(int i = 1; i <= jobs; i++) {
            long pixelcount = ((long) WIDTH *HEIGHT*i)/jobs - ((long) WIDTH *HEIGHT*(i-1))/jobs;
            Runnable runnable = new MandelbrotTask(I, xs,ys,pixelcount);
            Future<?> future = pool.submit(runnable);
            set.add(future);
            xs += pixelcount;
            ys += xs / WIDTH;
            xs %= WIDTH;
        }
        for (Future<?> future : set) {
            future.get();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}