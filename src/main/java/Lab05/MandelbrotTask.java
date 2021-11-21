package Lab05;

import java.awt.image.BufferedImage;

import static Lab05.Mandelbrot.*;

public class MandelbrotTask implements Runnable {
    private final BufferedImage I;
    private int xs, ys;
    private long pixelcount;

    MandelbrotTask(BufferedImage I, int xs, int ys, long pixelcount) {
        this.I = I;
        this.xs = xs;
        this.ys = ys;
        this.pixelcount = pixelcount;
    }

    @Override
    public void run() {
        while (pixelcount-- > 0) {
            double zx, zy, cX, cY;
            zx = zy = 0;
            cX = (xs - 400) / ZOOM;
            cY = (ys - 300) / ZOOM;
            int iter = MAX_ITER;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                double tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            I.setRGB(xs, ys, iter | (iter << 8));
            xs++;
            ys += xs / WIDTH;
            xs %= WIDTH;
        }
    }
}
