import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
public class RGBTriangle {

    public static HashMap<String, String> xyRGB = new HashMap<String, String>();
    public static HashMap<String, Integer> getRColor = new HashMap<>();
    public static HashMap<String, Integer> getGColor = new HashMap<>();
    public static HashMap<String, Integer> getBColor = new HashMap<>();
    public static HashMap<String, String> getStokes = new HashMap<>();
    public static HashMap<String, String> getStokesRGB = new HashMap<>();
    public static double r;
    public static double g;
    public static double b;
    public static int r1;
    public static int g1;
    public static int b1;
    public static DrawTool generateMap = new DrawTool();
    public static BufferedImage img;

    public static void diffTriangle (double xStart, double xEnd, double yStart, double yEnd, double diff)
    {
        xyRGB = new HashMap<>();
        getRColor = new HashMap<>();
        getBColor = new HashMap<>();
        getGColor = new HashMap<>();
        getStokes = new HashMap<>();
        getStokesRGB = new HashMap<>();
        generateMap = new DrawTool();
        generateMap.setXYRange(xStart-1, xEnd+1, yStart-1, yEnd+1);
        //find ranges, xmax and xmin, ymax and ymin.


        for (double x = xStart; x < xEnd; x = x + diff) { // Xmax is the max x from file input
            for (double y = yStart; y < yEnd; y = y + diff) { // ymax is the max y from file input
                    double fixedY = Math.floor(y * 100 + 0.5) / 100;
                    double fixedX = Math.floor(x * 100 + 0.5) / 100;
                    String XandY = fixedX + "," + fixedY;
                    r = mainFrame.stokesHash1.get(XandY) * 250; // r is S1
                    g = mainFrame.stokesHash2.get(XandY) * 250;
                    b = mainFrame.stokesHash3.get(XandY) * 250;
                    r1 = (int) r;
                    g1 = (int) g;
                    b1 = (int) b;
                    String stokesValue = String.format("%.3f", r/250) + "," + String.format("%.3f", g/250) + "," + String.format("%.3f", b/250);
                    xyRGB.put(XandY, r/250 + "," + g/250 + "," + b/250);
                    getStokesRGB.put(stokesValue, r1 + "," + g1 + "," + b1);
                    getStokes.put(stokesValue, XandY);
                    getRColor.put(XandY, r1);
                    getGColor.put(XandY, g1);
                    getBColor.put(XandY, b1);
                    generateMap.drawPointTriangle(x, y);
                }
            }
            generateMap.display();


        try {
            mainFrame.fileVer++; // increment the file version
            img = new BufferedImage(DrawTool.frame.getWidth(), DrawTool.frame.getHeight(), BufferedImage.TYPE_INT_RGB); // get a screenshot of the frame
            DrawTool.frame.paint(RGBTriangle.img.getGraphics());
            File outputfile = new File("ColorMap" + mainFrame.fileVer + ".png");
            ImageIO.write(RGBTriangle.img, "png", outputfile); // write the file to the output
        } catch (IOException e) {
            System.out.println ("Created the image did not work");
        }
        /*
        Once the map is generated, we may set these functions to work in the mainFrame.
         */
        mainFrame.textFieldY.setEditable(true);
        mainFrame.textFieldX.setEditable(true);
        mainFrame.btnCalc.setDisable(false);
        mainFrame.saveImage.setDisable(false);
        }

    }


