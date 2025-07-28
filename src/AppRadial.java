
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;


public class AppRadial extends JPanel {

    static JSlider sliderStep;
    static JSlider sliderA;
    static JSlider sliderB;
    static JSlider sliderC;
    static JSlider sliderD;
    static JButton resetButton;

    float theta = 0;
    float size = 4;
    List<Point> points;    
    Timer timer;

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

    public AppRadial(){
        points = new ArrayList<>();
        timer = new Timer(1, e -> {
            theta = theta + sliderStep.getValue()/1000f ;        

            float r = function(theta);
            float[] newPoints = Point.polarToCart(r, theta);
            points.add(new Point((int)newPoints[0],(int)newPoints[1],(int)size,new Color(199, 80, 40)));

            repaint();
        });
        timer.start();
    }

    void resetAnimation() {
        timer.stop();
        points.clear();
        theta = 0;

        float r = function(0);
        float[] newPoints = Point.polarToCart(r, theta);
        points.add(new Point((int)newPoints[0],(int)newPoints[1],(int)size,new Color(199, 80, 40)));

        timer.start();
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
        sliderA = new JSlider(0,360,0);
        sliderB = new JSlider(0,360,0);
        sliderC = new JSlider(0,360,0);
        sliderD = new JSlider(0,360,0);

        resetButton = new JButton("Reset Animation");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Function Plotter\nRadial Coordinates");
        

        JFrame frame = new JFrame("Function Plotter : Radial Coordinates");

        AppRadial app = new AppRadial();

        frame.getContentPane().add(app);
        resetButton.addActionListener(e -> app.resetAnimation());

        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel labelStep = new JLabel("Step (S): " + sliderStep.getValue());
        JLabel labelA = new JLabel("Var A (A): " + sliderA.getValue());
        JLabel labelB = new JLabel("Var B (B): " + sliderB.getValue());
        JLabel labelC = new JLabel("Var C (C): " + sliderC.getValue());
        JLabel labelD = new JLabel("Var D (D): " + sliderD.getValue());

        // Listeners to update labels on slider release
        
        sliderStep.addChangeListener(e -> {
            labelStep.setText("Step (S): " + sliderStep.getValue());
        });
        
        sliderA.addChangeListener(e -> {
            if (!sliderA.getValueIsAdjusting()) {
                labelA.setText("Var A (A): " + sliderA.getValue());
            }
        });

        sliderB.addChangeListener(e -> {
            if (!sliderB.getValueIsAdjusting()) {
                labelB.setText("Var B (B): " + sliderB.getValue());
            }
        });

        sliderC.addChangeListener(e -> {
            if (!sliderC.getValueIsAdjusting()) {
                labelC.setText("Var C (C): " + sliderC.getValue());
            }
        });

        sliderD.addChangeListener(e -> {
            if (!sliderD.getValueIsAdjusting()) {
                labelD.setText("Var D (D): " + sliderD.getValue());
            }
        });

        // Panel layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        controlPanel.add(labelStep);
        controlPanel.add(sliderStep);

        controlPanel.add(labelA);
        controlPanel.add(sliderA);
        
        controlPanel.add(labelB);
        controlPanel.add(sliderB);
        
        controlPanel.add(labelC);
        controlPanel.add(sliderC);
        
        controlPanel.add(labelD);
        controlPanel.add(sliderD);

        controlPanel.add(resetButton);

        // Add panel to bottom of frame
        frame.add(controlPanel, BorderLayout.EAST);

        frame.setVisible(true);

    } 
}
