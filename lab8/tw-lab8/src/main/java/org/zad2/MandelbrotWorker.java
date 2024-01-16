package org.zad2;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

class MandelbrotWorker implements Callable<Void> {
    private final double ZOOM = 150;

    private final int iterations;
    private final BufferedImage img;
    private final int width;
    private final int height;

    public MandelbrotWorker(int iterations, int width, int height, BufferedImage img) {
        this.iterations = iterations;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    @Override
    public Void call() {
        double cY = (height - 300) / ZOOM;
        for (int x = 0; x < width; x++) {
            double zy = 0;
            double zx = 0;
            double cX = (x - 400) / ZOOM;
            int iter = iterations;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                double tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            img.setRGB(x, height, iter | (iter << 8));
        }
        return null;
    }
}