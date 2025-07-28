# 🎨 Function Plotter

A Java-based application to visualize mathematical curves in **Cartesian** and **Polar (Radial)** coordinate systems. The app uses sliders for real-time control over parameters like **Amplitude (A)**, **Frequency (W)**, and **Phase Shift (PHI)**.

---

## 📷 Previews

### 📈 Cartesian Curves (`AppCartesian.java`)

| Curve Name            | Graph Preview                        | Formula Used                                  |
|----------------------|--------------------------------------|-----------------------------------------------|
| **Sine Wave (Blue)**         | ![Sine](./public/cartesian/sine_cosine.png)       | `Math.sin(W * x + PHI) * A`                    |
| **Cosine Wave (Magenta)**       | ![Cosine](./public/cartesian/sine_cosine.png)     | `Math.cos(W * x + PHI) * A`                    |
| **Tangent Wave**      | ![Tangent](./public/cartesian/tangent.png)        | `Math.tan(W * x + PHI) * A * 0.1`              |
| **Triangle Wave**     | ![Triangle](./public/cartesian/triangle_wave.png) | `2 * A / π * asin(sin(W * x + PHI))`          |
| **Damped Sine**       | ![Damped](./public/cartesian/damped_sine.png)     | `sin(W * x + PHI) * exp(-0.01 * x) * A`        |
| **Pulse Train**       | ![Pulse](./public/cartesian/pulse_train.png)      | `(sin(W * x + PHI) > 0 ? 1 : 0) * A`           |
| **Harmonic Blend**    | ![Harmonic](./public/cartesian/harmonic_blend.png)| `(sin(W * x + PHI) + sin(3W * x + PHI)) / 2 * A`|
| **Sine of Sine**      | ![SineOfSine](./public/cartesian/sine_of_sine.png)| `sin(W * x + PHI) * sin(0.8W * x + PHI) * A`   |

---

### 🌀 Radial Curves (`AppRadial.java`)

| Curve Name             | Graph Preview                          | Formula (r = ...)                             |
|-----------------------|----------------------------------------|-----------------------------------------------|
| **Cardiod**           | ![Cardiod](./public/radial/cardiod.png)           | `(1 - sin(θ)) * 100`                          |
| **Infinity (Lemniscate)**| ![Infinity](./public/radial/infinity.png)     | `sqrt(10000 * cos(2θ))`                       |
| **Rose Curve**        | ![Rose](./public/radial/rose.png)               | `100 * cos(4θ)`                               |
| **Archimedean Spiral**| ![Archimedean](./public/radial/archimedial.png) | `10 + 10 * θ`                                 |
| **Fermat Spiral**     | ![Fermat](./public/radial/femart.png)           | `10 * sqrt(θ)`                                |
| **Epicycloid**        | ![Epicycloid](./public/radial/epicycloid.png)   | `100 * (1 - cos(3θ))`                         |
| **Logarithmic Spiral**| ![Log](./public/radial/log.png)                | `10 * e^(0.15 * θ)`                           |

---

---

### 🧮 Parametric Curves (`AppParametric.java`)

| Curve Name               | Graph Preview                                   | Formula `(x(t), y(t))`                                                    |
|--------------------------|--------------------------------------------------|---------------------------------------------------------------------------|
| **Lissajous Curve**      | ![Lissajous](./public/parametric/Lissajous.png) | `x = A·sin(W·t + φ)`  <br> `y = A·sin(2·W·t)`                              |
| **Parametric Archimedean Spiral** | ![Archimedean](./public/parametric/parametric_archimedial.png) | `x = (t/30)·cos(W·t)` <br> `y = (t/30)·sin(W·t)`                     |
| **Hypotrochoid**         | ![Hypotrochoid](./public/parametric/Hypotrochoid.png) | `x = (A - 20)·cos(W·t) + 40·cos(((A - 20)/20)·W·t)` <br> `y = (A - 20)·sin(W·t) - 40·sin(((A - 20)/20)·W·t)` |
| **Helix (3D)**           | ![Helix (3D)](./public/parametric/3DParametric.gif) | `x = A·cos(W·t)` <br> `y = A·sin(W·t)` <br> `z = t`                        |


---


## 🎛️ Controls

The app provides interactive **sliders** to dynamically update curves:

- **Amplitude (A):** Controls the wave height.
- **Phase (PHI):** Controls horizontal shift.
- **Frequency (W):** Controls wave compression/stretch.
- **Delay (D):** Time delay between redraws (animation speed).
- **Step (S):** How far `x` moves per frame.
---

### Example Usage in Code

```java
    int function(float x){
        A = sliderA.getValue();
        W = sliderW.getValue()/1000.0;
        PHI = sliderPHI.getValue()/100.0;
        return (int)(Math.sin(W * x + PHI) * A); // Sine Wave
    }

    public AppCartesian(){

        points = new ArrayList<>();
        new Timer(sliderDelay.getValue(), e -> {
            x += (float)(sliderStep.getValue()/10f);        
add ->      points.add(new Point((int)x,function(x),(int)size,Color.BLUE));
            repaint();
        }).start();
    }

```