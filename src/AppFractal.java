// https://darkeclipz.github.io/fractals/paper/Fractals%20&%20Rendering%20Techniques.html -- Reference

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class AppFractal extends JPanel {

    static JSlider SliderScaleX;
    static JSlider SliderScaleY;
    static JSlider SliderScaleN;
    static JSlider SliderScaleXOffset;
    static JSlider SliderScaleYOffset;
    
    final double B = 2;
    final double N = 64;   

    static{
        SliderScaleX = new JSlider(0, 200, 28);
        SliderScaleY = new JSlider(0, 200, 20);
        SliderScaleXOffset = new JSlider(0, 3000, 1500);
        SliderScaleYOffset = new JSlider(0,3000,1500);
        SliderScaleN = new JSlider(0,1000,888);

    }


    Color getMandelBrotColor(ComplexNumber C){
        ComplexNumber Z = new ComplexNumber(0, 0);
        int i = 0;

        for(i = 0; i<N; i++){
            if((Z.getReal()*Z.getReal() + Z.getImaginary()*Z.getImaginary()) > B*B){
                break;
            }
            Z = Z.multiply(Z).add(C);
        }
        if (i == N) return Color.BLACK;

        double dot =  (Z.getReal()*Z.getReal() + Z.getImaginary()*Z.getImaginary());
        if (dot <= 1e-5) dot = 1e-5;

        double log_zn = Math.log(dot) / 2;
        double nu = Math.log(log_zn / Math.log(B)) / Math.log(2);
        double smooth = i + 1 - nu;

        float normalized = (float)(smooth / N);
        normalized = Math.max(0f, Math.min(1f, normalized));

        return new Color(normalized, normalized,normalized);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        g2d.translate( w / 2,  h / 2);
        g2d.scale(1, -1);
 
       
        for(int x = -w/2; x<=w/2; x++){
            for(int y = -h/2; y<= h/2; y++){
                double zx = ((double)x / (w / SliderScaleN.getValue() * 1000 )) * SliderScaleX.getValue()/10.0 + (SliderScaleXOffset.getValue() - 1500) /100.0;
                double zy = ((double)y / (h / SliderScaleN.getValue() * 1000)) * SliderScaleY.getValue()/10.0 + (SliderScaleYOffset.getValue() - 1500) /100.0;
                ComplexNumber cn = new ComplexNumber(zx, zy);
                Color color = getMandelBrotColor(cn);
                g2d.setColor(color);
                g2d.drawLine(x, y, x, y); 
            }
        }

    } 


    public static void main(String[] args) throws Exception {
        System.out.println("Function Plotter\nFractals");
        
        JFrame frame = new JFrame("Function Plotter : Fractals");

        AppFractal app = new AppFractal();
        frame.getContentPane().add(app);
        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelX = new JLabel("X: " + SliderScaleX.getValue()/10.0);
        JLabel labelY = new JLabel("Y: " + (SliderScaleY.getValue())/10.0);
        JLabel labelN = new JLabel("Zoom: " + (SliderScaleN.getValue())/10.0);
        JLabel labelXOffset = new JLabel("X Off: " + SliderScaleXOffset.getValue()/10.0);
        JLabel labelYOffset = new JLabel("Y Off: " + SliderScaleXOffset.getValue()/10.0);

        SliderScaleX.addChangeListener(e -> {
            labelX.setText("X: " + SliderScaleX.getValue()/10.0);
            app.repaint();
        });
        SliderScaleY.addChangeListener(e -> {
            labelY.setText("Y: " + SliderScaleY.getValue()/10.0);
            app.repaint();
        });
        SliderScaleN.addChangeListener(e -> {
            labelN.setText("Zoom: " + SliderScaleN.getValue()/10.0);
            app.repaint();
        });
        SliderScaleXOffset.addChangeListener(e -> {
            labelXOffset.setText("X Off: " + SliderScaleXOffset.getValue()/10.0);
            app.repaint();
        });
        SliderScaleYOffset.addChangeListener(e -> {
            labelYOffset.setText("Y Off: " + SliderScaleYOffset.getValue()/10.0);
            app.repaint();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
        controlPanel.add(labelX);
        controlPanel.add(SliderScaleX);
        
        controlPanel.add(labelY);
        controlPanel.add(SliderScaleY);

        controlPanel.add(labelN);
        controlPanel.add(SliderScaleN);

        controlPanel.add(labelXOffset);
        controlPanel.add(SliderScaleXOffset);

        controlPanel.add(labelYOffset);
        controlPanel.add(SliderScaleYOffset);


        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    } 
}
