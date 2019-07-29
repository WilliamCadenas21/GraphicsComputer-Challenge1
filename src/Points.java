/*
 * Ejemplo básico en Java2D
 * 
 * Tomado de el Tutorial de Java2D de ZetTutorial: http://zetcode.com/tutorials/java2dtutorial/
 * 
 * Java tiene un tutorial oficial para Java2D:   
 */

//package points;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.Random;

public class Points extends JPanel {

    private final int pixelSize = 3;
    private final double angulo = 30;

    /*
   * En esta función se dibuja.
   * La funci0n es llamada por Java2D.
   * Se recibe una variable Graphics, que contiene la información del contexto
   * gráfico.
   * Es necesario hacerle un cast a Graphics2D para trabajar en Java2D.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        // size es el tamaÃ±o de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los tÃ­tulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        g2d.drawLine(0, h / 2, w, h / 2);// eje x
        g2d.drawLine(w / 2, 0, w / 2, h);// eje y

        g2d.setColor(Color.blue);
        //octante(0, 0, 100, 50, g2d, w, h, 3);
        
        int num = 100;
        
        double value = Math.sin(angulo);
        
        drawLine(g2d, num, 0, 0, num, w, h);
        drawLine(g2d, -num, 0, 0, num, w, h);
        drawLine(g2d, -num, 0, 0, -num, w, h);
        drawLine(g2d, num, 0, 0, -num, w, h);
        
        //g2d.setColor(Color.black);
        //octante(0,0,100,50,g2d,w,h,10);
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2, int w, int h) {
        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                //plot(g, x, y);
                
                int xj = w / 2 + x; //transformar la cordenada en x     
                int yj = h / 2 - y; //transformar la cordenada en y
                g.drawLine(xj, yj, xj, yj); // dibuja el punto
                
                if (x == x2) {
                    break;
                }
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                //plot(g, x, y);
                int xj = w / 2 + x; //transformar la cordenada en x     
                int yj = h / 2 - y; //transformar la cordenada en y
                g.drawLine(xj, yj, xj, yj); // dibuja el punto
                
                if (y == y2) {
                    break;
                }
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }

    private void plot(Graphics g, int x, int y) {
        int w = (getWidth() - 1) / pixelSize;
        int h = (getHeight() - 1) / pixelSize;
        int maxX = (w - 1) / 2;
        int maxY = (h - 1) / 2;

        int borderX = getWidth() - ((2 * maxX + 1) * pixelSize + 1);
        int borderY = getHeight() - ((2 * maxY + 1) * pixelSize + 1);
        int left = (x + maxX) * pixelSize + borderX / 2;
        int top = (y + maxY) * pixelSize + borderY / 2;

        g.setColor(Color.black);
        //g.drawOval(left, top, pixelSize, pixelSize);

        g.drawLine(top, left, top, left);
    }

    public static void octante(int x1, int y1, int x2, int y2, Graphics2D g2d, int w, int h, int steps) {
        int xj, yj;

        //primer paso calcular el delta
        int dy = y2 - y1;
        int dx = x2 - x1;

        //segundo paso calcular los incrementos
        int incE = 2 * dy;
        int incNE = 2 * dy - 2 * dx;

        // se le da el valor inicial a delta, y
        int d = 2 * dy - dx;
        int y = y1;

        // se procede a realizar el recorrido por el eje de las x
        for (int x = x1; x < x2; x = x + steps) {

            xj = w / 2 + x; //transformar la cordenada en x     
            yj = h / 2 - y; //transformar la cordenada en y

            g2d.drawLine(xj, yj, xj, yj); // dibuja el punto

            //transforma el delta y y segun el valor de d
            if (d <= 0) {
                d += incE;
            } else {
                d += incNE;
                y += steps;
            }
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecuciÃ³n de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Points());
        // Asignarle tamaÃ±o
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}
