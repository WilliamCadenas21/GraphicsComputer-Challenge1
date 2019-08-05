import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Points extends JPanel {
    private Scanner sc = new Scanner(System.in);
    //varaibles privadas con las cuales funciona el algoritmo
    private double angulo = 0;
    private final int steps = 1;
    private int R = 100;
    private int numRombos = 5;

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
        
        System.out.println("Bienvenido antes de empezar por favor digite el angulo:");
        angulo = sc.nextInt();
        System.out.println("por favor digite el R :");
        R = sc.nextInt();
        int Sizex = getSize().width; //asumimos que es cuadrada
        while (R>= Sizex){
            System.out.println("el valor de R no puede pasar el tamaño de la ventana "+Sizex+", por favor digite R nuevamente:");
            R = sc.nextInt();
        }
        System.out.println("por favor digite el numero de rombos:");
        numRombos = sc.nextInt();

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

        Point[] points = new Point[4];
        points[0] = new Point(R, 0);
        points[1] = new Point(0, R);
        points[2] = new Point(-R, 0);
        points[3] = new Point(0, -R);

        //dibujado del primer rombo
        g2d.setColor(Color.blue);
        drawLine(g2d, R, 0, 0, R, w, h);
        drawLine(g2d, 0, R, -R, 0, w, h);
        drawLine(g2d, -R, 0, 0, -R, w, h);
        drawLine(g2d, 0, -R, R, 0, w, h);
        
        double ang;
        int newY = 0, newX = 0;
        g2d.setColor(Color.red);
        for (int j = 0; j < numRombos; j++) {
            ang =(j+1)*angulo;
            //calcula los nuevos puntos rotados
            for (int i = 0; i < 4; i++) {
                newY = (int) Math.round(R * Math.sin(Math.toRadians(ang)));
                newX = (int) Math.round(R * Math.cos(Math.toRadians(ang)));
                points[i] = new Point(newX, newY);
                ang = ang + 90;
            }
            //Dibuja las lineas entre los nuevos puntos
            for (int k = 0; k < 3; k++) {
                drawLine(g2d, points[k].x, points[k].y, points[k + 1].x, points[k + 1].y, w, h);
            }
            drawLine(g2d, points[3].x, points[3].y, points[0].x, points[0].y, w, h);
        }
        
        System.out.println("Finalizo el dibujo");
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2, int w, int h) {
        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? steps : -steps; // increment direction
        int iy = y1 < y2 ? steps : -steps;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
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

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Points());
        // Asignarle tamaño
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}
