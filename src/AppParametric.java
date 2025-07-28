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


public class AppParametric extends JPanel {

    static JSlider sliderA ;
    static JSlider sliderPHI;
    static JSlider sliderW;
    static JSlider sliderDelay;
    static JSlider sliderAlpha;
    static JSlider sliderBeta;
    static JSlider sliderGamma;
    static JSlider sliderStep;
    static JSlider sliderTX;
    static JSlider sliderTY;
    static JSlider sliderTZ;
    static JButton resetButton;

    static double A = 100;
    static double W = 1;
    static double PHI = 0;

    float p = 0;
    float size = 4;
    List<Point> points;    
    Timer timer;


    int functionX(float p) {
        A = sliderA.getValue();
        W = sliderW.getValue() / 1000.0;
        PHI = sliderPHI.getValue() / 100.0;
        

        //return (int)(A * Math.sin(W * p + PHI));//Lissajous
        //return (int)(p/30 * Math.cos(W * p));//parametric_archimedial
        //return (int)((A - 20) * Math.cos(W * p) + 40 * Math.cos(((A - 20) / 20.0) * W * p));//Hypotrochoid
        //return (int)((A + (A/3)) * Math.cos(W * p) - (A/3) * Math.cos(((A + (A/3)) / (A/3)) * W * p));//EpiCycloid
        return (int)(A * Math.cos(W * p));//Helix 
    }

    int functionY(float p) {
        A = sliderA.getValue();
        W = sliderW.getValue() / 1000.0;
        PHI = sliderPHI.getValue() / 100.0;

        //return (int)(A * Math.sin(2 * W * p)); //Lissajous
        // return (int)(p/30 * Math.sin(W * p));//parametric_archimedial
        //return (int)((A - 20) * Math.sin(W * p) - 40 * Math.sin(((A - 20) / 20.0) * W * p)); //Hypotrochoid
        //return (int)((A + (A/3)) * Math.sin(W * p) - (A/3) * Math.sin(((A + (A/3)) / (A/3)) * W * p));//EpiCycloid

        return (int)(A * Math.sin(W * p)); //Helix 
    }

    int functionZ(float p) {
        //return 0;
        return (int)p; //For Helix
    }


    public AppParametric() {
        points = new ArrayList<>();
        timer = new Timer(sliderDelay.getValue(), e -> {
            p += (float) (sliderStep.getValue() / 10f);
            points.add(new Point(functionX(p), functionY(p), functionZ(p), (int) size, new Color(13, 140, 40)));
            repaint();
        });
        timer.start();
    }

    void resetAnimation() {
        timer.stop();
        points.clear();
        p = 0;
        points.add(new Point(functionX(p), functionY(p), functionZ(p), (int) size, new Color(13, 140, 40)));
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

        for (Point point : points) {
            int[] projectedCords = Point.projectRotatedPoint(
                point.x, point.y, point.z,
                sliderAlpha.getValue(),
                sliderBeta.getValue(),
                sliderGamma.getValue(),
                sliderTX.getValue(),
                sliderTY.getValue(),
                sliderTZ.getValue()
            );
            g2d.setColor(point.color);
            g2d.fillOval(projectedCords[0], projectedCords[1], point.size, point.size);
        }

    } 

    static{
        sliderA = new JSlider(0, 1000, (int)A);
        sliderPHI = new JSlider(0, 100, (int)PHI * 100);
        sliderW = new JSlider(0, 1000, (int)W * 100);
        sliderDelay = new JSlider(0,10000,1);
        sliderStep = new JSlider(0,100,0);
        sliderAlpha = new JSlider(0,360,0);
        sliderBeta = new JSlider(0,360,0);
        sliderGamma = new JSlider(0,360,0);
        sliderTX = new JSlider(-5000, 5000, 0);
        sliderTY = new JSlider(-5000, 5000, 0);
        sliderTZ = new JSlider(-5000, 5000, 0);
        resetButton = new JButton("Reset Animation");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Function Plotter\nParametric Coordinates");
        

        JFrame frame = new JFrame("Function Plotter : Parametric Coordinates");

        AppParametric app = new AppParametric();

        frame.getContentPane().add(app);
        resetButton.addActionListener(e -> app.resetAnimation());

        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels
        JLabel labelA = new JLabel("Amplitude (A): " + sliderA.getValue());
        JLabel labelPHI = new JLabel("Phase (PHI): " + (sliderPHI.getValue() / 10.0));
        JLabel labelW = new JLabel("Frequency (W): " + (sliderW.getValue() / 100.0));
        JLabel labelStep = new JLabel("Step (S): " + sliderStep.getValue());
        JLabel labelDelay = new JLabel("Delay (D): " + sliderDelay.getValue() + " ms");
        JLabel labelAlpha = new JLabel("Alpha (α): " + (sliderAlpha.getValue()));
        JLabel labelBeta = new JLabel("Beta  (β): " + (sliderBeta.getValue()));
        JLabel labelGamma = new JLabel("Gamma (γ): " + sliderAlpha.getValue());

        JLabel labelTX = new JLabel("Translate X: " + sliderTX.getValue());
        JLabel labelTY = new JLabel("Translate Y: " + sliderTY.getValue());
        JLabel labelTZ = new JLabel("Translate Z: " + sliderTZ.getValue());

        
        // Listeners to update labels on slider release

        sliderTX.addChangeListener(e -> {
            if (!sliderTX.getValueIsAdjusting()) {
                labelTX.setText("Translate X: " + sliderTX.getValue());
            }
        });
        sliderTY.addChangeListener(e -> {
            if (!sliderTY.getValueIsAdjusting()) {
                labelTY.setText("Translate Y: " + sliderTY.getValue());
            }
        });
        sliderTZ.addChangeListener(e -> {
            if (!sliderTZ.getValueIsAdjusting()) {
                labelTZ.setText("Translate Z: " + sliderTZ.getValue());
            }
        });

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
        sliderAlpha.addChangeListener(e -> {
            if (!sliderAlpha.getValueIsAdjusting()) {
                labelAlpha.setText("DCS Alpha (α): " + (sliderAlpha.getValue()));
            }
        });
        sliderBeta.addChangeListener(e -> {
            if (!sliderBeta.getValueIsAdjusting()) {
                labelBeta.setText("DCS Beta  (β): " + (sliderBeta.getValue()));
            }
        });
        sliderGamma.addChangeListener(e -> {
            if (!sliderGamma.getValueIsAdjusting()) {
                labelGamma.setText("DCS Gamma (γ):  " + sliderGamma.getValue());
            }
        });

        // Panel layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
        controlPanel.add(labelStep);
        controlPanel.add(sliderStep);
        
        controlPanel.add(labelDelay);
        controlPanel.add(sliderDelay);

        controlPanel.add(labelAlpha);
        controlPanel.add(sliderAlpha);

        controlPanel.add(labelBeta);
        controlPanel.add(sliderBeta);

        controlPanel.add(labelGamma);
        controlPanel.add(sliderGamma);

        controlPanel.add(labelA);
        controlPanel.add(sliderA);

        controlPanel.add(labelPHI);
        controlPanel.add(sliderPHI);

        controlPanel.add(labelW);
        controlPanel.add(sliderW);

        controlPanel.add(labelTX);
        controlPanel.add(sliderTX);

        controlPanel.add(labelTY);
        controlPanel.add(sliderTY);

        controlPanel.add(labelTZ);
        controlPanel.add(sliderTZ);
        
        controlPanel.add(resetButton);


        // Add panel to bottom of frame
        frame.add(controlPanel, BorderLayout.EAST);

        frame.setVisible(true);


    } 
}
