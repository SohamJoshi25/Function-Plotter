
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;


public class AppRadial extends JPanel {

    static JSlider sliderStep;


    float function(float theta){
        theta *= Math.PI/180f;

        //return (float)(1 - Math.sin(theta)) * 100; //Cardiod
        //return (float)(Math.sqrt(10000*Math.cos(theta*2))); // Infinity
        //return (float)(100*Math.cos(4*theta)); //Rose
        //return (float)(10 + 10 * theta); //Archi
        //return (float)(10 * Math.sqrt(theta)); //Spiral of Fermat 
        //return (float)(100 * (1 - Math.cos(3 * theta ))); //Epicycloid 
        return (float)(10 * Math.exp(0.15 * theta)); //Logarathmic spiral

    } 


    float[] polarToCart(float r, float theta){
        theta %= 360;
        theta *= Math.PI/180;
        return new float[]{ 
            (float) (r * Math.cos(theta)),
            (float) (r * Math.sin(theta))
        };
    }


    
    float theta;
    float r;
    
    float size = 4;
    List<Point> points;

    public AppRadial(){

        points = new ArrayList<>();
        theta = 0;

        float[] temp = polarToCart(r, theta);
    
        points.add(new Point((int)temp[0],(int)temp[1],(int)size,new Color(199, 80, 40)));

        new Timer(1, e -> {
            theta = theta + sliderStep.getValue()/1000f ;        
            r = function(theta);
            float[] newPoints = polarToCart(r, theta);
            
            points.add(new Point((int)newPoints[0],(int)newPoints[1],(int)size,new Color(199, 80, 40)));

            repaint();
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear background

        Graphics2D g2d = (Graphics2D) g;

        // Get current width and height of the panel
        int w = getWidth();
        int h = getHeight();

        // Translate the origin to the center of the panel
        g2d.translate( w / 2,  h / 2);
        g2d.scale(1, -1);

        // X and Y axes (centered)
        g2d.setColor(Color.BLACK);
        g2d.drawLine(-w / 2, 0, w / 2, 0); // horizontal
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, -h / 2, 0, h / 2); // vertical

        for (int i = 0; i <= h / 2; i += 100) {
            g2d.scale(1, -1);
            g2d.drawString(Integer.toString(i), 0, i);
            g2d.scale(1, -1);
            g2d.fillOval(-2, i - 2, 4, 4);
        }
        for (int i = 0; i >= -h / 2; i -= 100) {
            g2d.scale(1, -1);
            g2d.drawString(Integer.toString(i), 0, i);
            g2d.scale(1, -1);
            g2d.fillOval(-2, i - 2, 4, 4);
        }
        for (int i = 0; i <= w / 2; i += 100) {
            g2d.scale(1, -1);
            g2d.drawString(Integer.toString(i), i, 0);
            g2d.scale(1, -1);
            g2d.fillOval(i - 2, -2, 4, 4);
        }
        for (int i = 0; i >= -w / 2; i -= 100) {
            g2d.scale(1, -1);
            g2d.drawString(Integer.toString(i), i, 0);
            g2d.scale(1, -1);
            g2d.fillOval(i - 2, -2, 4, 4);
        }

        for(Point point:points){
            g2d.setColor(point.color);
            g2d.fillOval(point.x, point.y, point.size, point.size);
        }
        
    }

    static{
        sliderStep = new JSlider(0,1000,0);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Function Plotter\nRadial Coordinates");
        
        JFrame frame = new JFrame("Function Plotter : Radial Coordinates");

        frame.getContentPane().add(new AppRadial());
        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel labelStep = new JLabel("Step (S): " + sliderStep.getValue());

        // Listeners to update labels on slider release
        
        sliderStep.addChangeListener(e -> {
            labelStep.setText("Step (S): " + sliderStep.getValue());
        });

        // Panel layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(labelStep);
        controlPanel.add(sliderStep);

        // Add panel to bottom of frame
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


    } 
}
