import java.awt.Color;

public class Point {
    int x;
    int y;
    int z;
    Color color;
    int size;
    public Point(int x, int y, int size, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }
    public Point(int x, int y, int z, int size, Color color){
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.color = color;
    }

    static int[] projectRotatedPoint(float x, float y, float z, float alphaDeg, float betaDeg, float gammaDeg,  float tx, float ty, float tz) {
        
        //Translate Coordinates
        x += tx;
        y += ty;
        z += tz;

        // Convert degrees to radians
        double alpha = Math.toRadians(alphaDeg); // rotate around X
        double beta  = Math.toRadians(betaDeg);  // rotate around Y
        double gamma = Math.toRadians(gammaDeg); // rotate around Z

        // --- 1. Rotation around X (Pitch) ---
        double y1 = y * Math.cos(alpha) - z * Math.sin(alpha);
        double z1 = y * Math.sin(alpha) + z * Math.cos(alpha);

        // --- 2. Rotation around Y (Yaw) ---
        double x2 = x * Math.cos(beta) + z1 * Math.sin(beta);
        double z2 = -x * Math.sin(beta) + z1 * Math.cos(beta);

        // --- 3. Rotation around Z (Roll) ---
        double x3 = x2 * Math.cos(gamma) - y1 * Math.sin(gamma);
        double y3 = x2 * Math.sin(gamma) + y1 * Math.cos(gamma);

        // --- 4. 2D Orthographic Projection ---
        int screenX = (int) (x3);
        int screenY = (int) (y3);
        int screenZ = (int) (z2);

        return new int[]{screenX, screenY,screenZ};
    }

    static float[] polarToCart(float r, float theta){
        theta %= 360;
        theta *= Math.PI/180;
        return new float[]{ 
            (float) (r * Math.cos(theta)),
            (float) (r * Math.sin(theta))
        };
    }

}
