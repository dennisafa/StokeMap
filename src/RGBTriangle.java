import javafx.scene.paint.Color;

import java.util.HashMap;
public class RGBTriangle {

    public static HashMap<String, String> xyRGB = new HashMap<String, String>();
    public static HashMap<String, Integer> getRColor = new HashMap<>();
    public static HashMap<String, Integer> getGColor = new HashMap<>();
    public static HashMap<String, Integer> getBColor = new HashMap<>();
    public static double r;
    public static double g;
    public static double b;
    public static int r1;
    public static int g1;
    public static int b1;


    public static void createRGBTriangle ()
    {
        for (double x = 0; x < 1.155; x = x + 0.005) {
            for (double y = 0; y < 1; y = y + 0.005) {
                if ((y < Math.sqrt(3) * x) && (y < -Math.sqrt(3) * (x - 1.155))) {
                    r = (1.732 * x - y) * 250 / 2;
                    g = (-1.732 * x - y + 2) * 250 / 2;
                    b = y * 250;
                    r1 = (int) r;
                    g1 = (int) g;
                    b1 = (int) b;
                    String position = String.format("%.3f", x) + "," + String.format("%.3f", y);
                    xyRGB.put(position, r/250 + "," + g/250 + "," + b/250);
                    getRColor.put(position, r1);
                    getGColor.put(position, g1);
                    getBColor.put(position, b1);
                }
            }

        }
    }

    public static void diffTriangle ()
    {
        DrawTool.display();
        DrawTool.setXYRange(-0.1, 1.2, -0.1, 1.1);


        for (double x = 0; x < 0.5; x = x + 0.005) {
            for (double y = 0; y < 0.5; y = y + 0.005) {
                if ((y < Math.sqrt(3) * x) && (y < -Math.sqrt(3) * (x - 0.5))) {
                    r = (1.732 * x - y) * 250 / 2;
                    g = (-1.732 * x - y + 2) * 250 / 2;
                    b = y * 250;
                    r1 = (int) r;
                    g1 = (int) g;
                    b1 = (int) b;
                    DrawTool.drawPointTriangle(x, y);
                }
            }

        }

    }

}
