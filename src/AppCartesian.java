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


public class AppCartesian extends JPanel {

    static JSlider sliderA ;
    static JSlider sliderPHI;
    static JSlider sliderW;
    static JSlider sliderDelay;
    static JSlider sliderStep;

    static double A = 100;
    static double W = 1;
    static double PHI = 0;


    int function(float x){

        A = sliderA.getValue();
        W = sliderW.getValue()/1000.0;
        PHI = sliderPHI.getValue()/100.0;
        

        return (int)(Math.sin(W * x + PHI) * A); // Sine Wave
        // return (int)(Math.cos(W * x + PHI) * A); // Cosine Wave
        // return (int)(Math.tan(W * x + PHI) * A * 0.1); // Tangent Wave (scaled down)
        // return (int)(2 * A / Math.PI * Math.asin(Math.sin(W * x + PHI))); // Triangle Wave Approximation
        // return (int)(Math.sin(W * x + PHI) * Math.exp(-0.01 * x) * A); // Damped Sine Wave
        // return (int)((Math.sin(W * x + PHI) > 0 ? 1 : 0) * A); // Pulse Train Approximation
        // return (int)((Math.sin(W * x + PHI) + Math.sin(W * 3 * x + PHI)) * A / 2); // Harmonic blend
        // return (int)(Math.sin(W * x + PHI) * Math.sin(W * 0.8 * x + PHI) * A); // Sine of Sine

    }

    //You can use 2 functions at a time as well
    int function2(float x){

        A = sliderA.getValue();
        W = sliderW.getValue()/1000.0;
        PHI = sliderPHI.getValue()/100.0;  

       return (int)(Math.cos(W * x + PHI) * A); // Cosine Wave
    }

    float x = 0;
    float size = 4;
    List<Point> points;

    public AppCartesian(){
        points = new ArrayList<>();

        points.add(new Point((int)x,function(x),(int)size,Color.BLUE));
        //points.add(new Point((int)x,function2(x),(int)size,Color.magenta));

        new Timer(sliderDelay.getValue(), e -> {
            x += (float)(sliderStep.getValue()/10f);        
            points.add(new Point((int)x,function(x),(int)size,Color.BLUE));
            //points.add(new Point((int)x,function2(x),(int)size,Color.magenta));
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

        g2d.setColor(Color.RED);
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


        g2d.setColor(new Color(60, 60, 60));
        for (int i = -w / 2; i <= w / 2; i += 100) {
            g2d.drawLine(i, -h / 2, i, h / 2);
        }
        for (int i = -h / 2; i <= h / 2; i += 100) {
            g2d.drawLine(-w / 2, i, w / 2, i);
        }


        if(x>w/2){
            x = -w/2;
            points.clear();
        }

        for(Point point:points){

            g2d.setColor(point.color);
            g2d.fillOval(point.x, point.y, point.size, point.size);
        
        }
        

    }

    static{
        sliderA = new JSlider(0, 1000, (int)A);
        sliderPHI = new JSlider(0, 100, (int)PHI * 100);
        sliderW = new JSlider(0, 1000, (int)W * 100);
        sliderDelay = new JSlider(0,10000,1);
        sliderStep = new JSlider(0,100,0);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Function Plotter\nCartesian Coordinates");
        
        
        JFrame frame = new JFrame("Function Plotter : Cartesian Coordinates");

        frame.getContentPane().add(new AppCartesian());
        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels
        JLabel labelA = new JLabel("Amplitude (A): " + sliderA.getValue());
        JLabel labelPHI = new JLabel("Phase (PHI): " + (sliderPHI.getValue() / 10.0));
        JLabel labelW = new JLabel("Frequency (W): " + (sliderW.getValue() / 100.0));
        JLabel labelDelay = new JLabel("Delay (D): " + sliderDelay.getValue() + " ms");
        JLabel labelStep = new JLabel("Step (S): " + sliderStep.getValue());

        // Listeners to update labels on slider release
        sliderA.addChangeListener(e -> {
            if (!sliderA.getValueIsAdjusting()) {
                labelA.setText("Amplitude (A): " + sliderA.getValue());
            }
        });
        sliderPHI.addChangeListener(e -> {
            if (!sliderPHI.getValueIsAdjusting()) {
                labelPHI.setText("Phase (PHI): " + (sliderPHI.getValue() / 10.0));
            }
        });
        sliderW.addChangeListener(e -> {
            if (!sliderW.getValueIsAdjusting()) {
                labelW.setText("Frequency (W): " + (sliderW.getValue() / 100.0));
            }
        });
        sliderDelay.addChangeListener(e -> {
            if (!sliderDelay.getValueIsAdjusting()) {
                labelDelay.setText("Delay (D): " + sliderDelay.getValue() + " ms");
            }
        });
        sliderStep.addChangeListener(e -> {
            if (!sliderStep.getValueIsAdjusting()) {
                labelStep.setText("Step (S): " + sliderStep.getValue());
            }
        });

        // Panel layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(labelA);
        controlPanel.add(sliderA);
        controlPanel.add(labelPHI);
        controlPanel.add(sliderPHI);
        controlPanel.add(labelW);
        controlPanel.add(sliderW);
        controlPanel.add(labelDelay);
        controlPanel.add(sliderDelay);
        controlPanel.add(labelStep);
        controlPanel.add(sliderStep);

        // Add panel to bottom of frame
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


    } 
}
