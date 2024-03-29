package org.zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Mandelbrot extends JFrame {
    private final int maxIter;
    private final BufferedImage img;
    private final int width;
    private final int height;

    public Mandelbrot(int max_iter) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.maxIter = max_iter;
        this.width = getWidth();
        this.height = getHeight();
        this.img = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
    }

    public void run(ExecutorService executorService) {
        LinkedList<Future<Void>> futures = new LinkedList<>();
        for (int y = 0; y < height; y++) {
            MandelbrotWorker task = new MandelbrotWorker(maxIter, width, y, img);
            futures.add(executorService.submit(task));
        }

        for (Future<Void> f : futures) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void display() {
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }
}