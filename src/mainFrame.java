import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


public class mainFrame extends Application {

    Button btnCalc = new Button();
    Button btnClose = new Button();
    Button openStoke = new Button();
    Button saveImage = new Button();
    Hyperlink help = new Hyperlink();
    TextField textFieldY = new TextField();
    TextField textFieldX = new TextField();
    TextField textFieldsS1 = new TextField();
    TextField textFieldsS2 = new TextField();
    TextField textFieldsS3 = new TextField();
    TextField valR = new TextField();
    TextField valG = new TextField();
    TextField valB = new TextField();
    TextField colorName = new TextField();
    Label lblX = new Label();
    Label lblY = new Label();
    Label s1 = new Label();
    Label s2 = new Label();
    Label s3 = new Label();
    Label caution = new Label();
    Label header = new Label();
    Label topNote = new Label();
    Label r = new Label();
    Label g = new Label();
    Label b = new Label();
    Label nameC = new Label();

    public static HashMap<String, Double> stokesHash1 = new HashMap<>();
    public static HashMap<String, Double> stokesHash2 = new HashMap<>();
    public static HashMap<String, Double> stokesHash3 = new HashMap<>();
    public static double difference = 0;
    public File color = new File ("ColorMap.png");
    public static int fileVer = 0;



    public static void main (String[] argv)
    {
        launch(argv);
    }

    @Override
    public void start (Stage primaryStage)
    {
        BorderPane root = new BorderPane(); // root pane, with grid pane insertions
        GridPane pane = new GridPane();
        ClickHandler ch = new ClickHandler();

        pane.setHgap(10); // Spacings and the sort
        pane.setVgap(10);
        pane.setPadding(new Insets(10));

        btnCalc.setText("Calculate Stokes Parameters");
        btnCalc.setMinWidth(200);
        btnCalc.setDisable(true);
        openStoke.setOnAction(ch);
        openStoke.setPrefWidth(230);

        lblX.setText("X Axis:");
        lblY.setText("Y Axis:");
        caution.setFont(Font.font("Helvica", 10));

        s1.setText("S1^2 = ");
        s2.setText("S2^2 = ");
        s3.setText("S3^2 = ");
        r.setText("Value of R = ");
        g.setText("Value of G = ");
        b.setText("Value of B = ");
        nameC.setText("Color = ");
        openStoke.setText("Generate Color Map");
        help.setText("User Manual");
        header.setFont(Font.font("Bold"));
        header.setFont(Font.font(15));
        header.setText("by Dennis Afanasev");
        topNote.setText("StokesMap");
        topNote.setFont(Font.font("Courier", 40));
        btnClose.setText("Exit");
        help.setText("User Help");
        saveImage.setText("Download ColorMap Image");

        pane.setAlignment(Pos.CENTER_RIGHT);
        pane.add(lblX, 0, 1); // Setting the positions of each according to row and column
        pane.add(lblY, 0, 2);
        pane.add(textFieldX, 1, 1);
        pane.add(textFieldY, 1, 2);
        pane.add(textFieldsS1, 1, 3);
        pane.add(textFieldsS2, 1, 4);
        pane.add(textFieldsS3, 1, 5);
        pane.add(caution, 1, 8);
        pane.add(help, 1, 17);
        pane.add(openStoke, 1, 0);

        pane.add(valR, 1, 11);
        pane.add(r, 0, 11);
        pane.add(g, 0, 12);
        pane.add(b, 0, 13);
        pane.add(valG, 1, 12);
        pane.add(valB, 1, 13);
        pane.add(colorName, 1, 15);
        pane.add(nameC, 0, 15);
        pane.add(saveImage, 1, 16);

        pane.add(s1, 0, 3); // Adding the labels
        pane.add(s2, 0, 4);
        pane.add(s3, 0, 5);
        pane.add(btnCalc, 1, 6); // Adding the button

        openStoke.setOnMouseClicked(event -> {
            FileChooser chooseStoke = new FileChooser();
        //    chooseStoke.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*txt"));
            File stokeVals = chooseStoke.showOpenDialog(new Stage());
            calcWithStokes(stokeVals);
            caution.setText("Enter X and Y in values of : " + (double) Math.round(difference * 100) / 100);
        });

        btnCalc.setOnMouseClicked(event -> calculateValues());

        btnClose.setOnMouseClicked(event -> {
         //   primaryStage.close();
            System.exit(0);
            // Close functionality
        });

        help.setOnMouseClicked(event -> {
            getHostServices().showDocument("https://github.com/dennisafa/StokesMap/blob/master/README.md");
        });

        saveImage.setOnMouseClicked(event -> {
            try {
                color = new File ("ColorMap" + fileVer + ".png");
                DirectoryChooser choose = new DirectoryChooser();
                File directoryChoose = choose.showDialog(primaryStage);

                Alert confi = new Alert(Alert.AlertType.CONFIRMATION);
                confi.setHeaderText("Confirm download to " + directoryChoose.getAbsolutePath());
                confi.showAndWait();


            //    File colorMap = new File ("ColorMap.png");
                File outputColorMap = new File (directoryChoose.getAbsolutePath());
                FileUtils.copyFileToDirectory(color, outputColorMap);

                Alert downloaded = new Alert(Alert.AlertType.INFORMATION);
                downloaded.setHeaderText("Color Map downloaded to " + directoryChoose.getAbsolutePath());
                downloaded.showAndWait();


            } catch (IOException e) {
                System.out.println ("Not written image");
            }

        });


        textFieldX.setEditable(false); // Don't want the results edited
        textFieldY.setEditable(false);
        textFieldsS1.setEditable(false);
        textFieldsS2.setEditable(false);
        textFieldsS3.setEditable(false);
        valR.setEditable(false);
        valB.setEditable(false);
        valG.setEditable(false);
        colorName.setEditable(false);
        saveImage.setDisable(true);


        ColumnConstraints col1 = new ColumnConstraints(); // Make columns neater
        col1.setPercentWidth(29);
        pane.getColumnConstraints().addAll(col1);

        Class<?> clazz = this.getClass(); // Get the triangle image as a inputstream
        InputStream input = clazz.getResourceAsStream("triangle.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(400); // Setting the images dimensions
        imageView.setFitHeight(400);
        GridPane setIm = new GridPane();
        setIm.add(imageView, 6,4);
        setIm.setAlignment(Pos.CENTER_RIGHT);

        GridPane setText = new GridPane(); // Grids for button layouts
        GridPane setClose = new GridPane();


        //setName.setAlignment(Pos.BOTTOM_RIGHT);


        setText.add(topNote, 3,3);
        setText.setAlignment(Pos.TOP_CENTER);

        setClose.setHgap(50);
        setClose.setPadding(new Insets(10, 10, 10, 10));
        setClose.add(btnClose,0,0);
        setClose.add(header, 10, 0);

        // Setting properties to the root pane
        root.setRight(pane);
        root.setLeft(setIm);
        root.setTop(setText);
        root.setBottom(setClose);
        root.backgroundProperty().setValue(Background.EMPTY);


        // Setting the scene and the stage
        Scene scene = new Scene(root, 750, 550);
        primaryStage.setTitle("StokesMap");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(650);
        primaryStage.setMaxHeight(850);
        primaryStage.setMaxWidth(650);
        primaryStage.show();

    }

    private class ClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle (ActionEvent e){ // Once a file is uploaded, we may make the x and y selectable
            if (e.getSource() == openStoke) {
                textFieldX.setEditable(true);
                textFieldY.setEditable(true);
                btnCalc.setDisable(false);
                saveImage.setDisable(false);
            }
        }
    }

    public void calculateValues ()
    {
        String xText = textFieldX.getText(); // Getting the text
        String yText = textFieldY.getText();
        String inputCor = textFieldX.getText() + "," + textFieldY.getText();
        String result = (RGBTriangle.xyRGB.get(xText + "," + yText)); // getting the stokes parameter results from the main code

        if (result == null) {
            textFieldsS1.setText("Invalid input");
            textFieldsS2.setText("Invalid input");
            textFieldsS3.setText("Invalid input");
            valR.setText("Invalid input");
            valB.setText("Invalid input");
            valG.setText("Invalid input");
            colorName.setText("Invalid input");
        }
        int currentR = RGBTriangle.getRColor.get(xText + "," + yText);
        int currentG = RGBTriangle.getGColor.get(xText + "," + yText);
        int currentB = RGBTriangle.getBColor.get(xText + "," + yText);

        valR.setText(RGBTriangle.getRColor.get(inputCor).toString());
        valG.setText(RGBTriangle.getGColor.get(inputCor).toString());
        valB.setText(RGBTriangle.getBColor.get(inputCor).toString());

        colorName.setText(setColor(currentR, currentG, currentB));
        textFieldsS1.setText(stokesHash1.get(xText + "," + yText).toString());
        textFieldsS2.setText(stokesHash2.get(xText + "," + yText).toString());
        textFieldsS3.setText(stokesHash3.get(xText + "," + yText).toString());
    }

    public String setColor (int r, int g, int b)
    {
        if ((r > g) && (r > b)) {
            return "Red";
        }
        else if ((g > r) && (g > b)) {
            return "Green";
        }
        else {
            return "Blue";
        }
    }




    public void calcWithStokes(File checkStoke)
    {
        try {
        //    File checkStoke = new File ("src/StokesMap.txt");
        //    checkStoke.deleteOnExit();
            Scanner readStoke = new Scanner(checkStoke); // reading the formatted file
            int counter;
            double maxX = Double.NEGATIVE_INFINITY; // Compare for the max values to pass into the image creator
            double minX = Double.POSITIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;
            double stokes1 = 0;
            double stokes2 = 0;
            double stokes3 = 0;
            double[] diffBetweenX = new double[2];
            double[] diffBetweenY = new double[2];// This array will hold the values to store what to add and subtract
            int countAddorSubX = 0;
            int countAddorSubY = 0;
            while (readStoke.hasNextDouble()) {
                counter = 0; // Reset the counter
                StringBuffer hashCodeXY = new StringBuffer(); // Appending the xy values to the hash map
                StringBuffer hashStoke1 = new StringBuffer();
                StringBuffer hashStoke2 = new StringBuffer();
                StringBuffer hashStoke3 = new StringBuffer();
                while (counter < 2) { // Build the string for the two xy values (the first two doubles must be x and y)

                    if (counter < 1) {
                        double x = (double) Math.round(readStoke.nextDouble() * 100) / 100;
                        if (x > maxX) { // Check for the maxX and minX (this will be the parameter in the
                            maxX = x;
                        }
                        if (x < minX) {
                            minX = x;
                        }
                        hashCodeXY.append(x + ",");

                        if (countAddorSubX < 2) {
                            diffBetweenY[countAddorSubY] = x;
                        }
                        countAddorSubX++;
                    }

                    else {
                        double y = (double) Math.round(readStoke.nextDouble() * 100) / 100;
                        if (y > maxY) {
                            maxY = y;
                        }
                        if (y < minY) {
                            minY = y;
                        }
                        hashCodeXY.append(y);

                        if (countAddorSubY < 2) {
                            diffBetweenY[countAddorSubY] = y;
                        }
                        countAddorSubY++;
                    }
                    counter++; // increment the counter

                    if (counter == 2) { // Move on to the stokes values, which are the next 3 values after the X and Y
                        while (counter < 5) {
                            if (counter == 2) {
                                stokes1 = readStoke.nextDouble();
                                hashStoke1.append(stokes1);
                            } else if (counter == 3) {
                                stokes2 = readStoke.nextDouble();
                                hashStoke2.append(stokes2);
                            } else {
                                stokes3 = readStoke.nextDouble();
                                hashStoke3.append(stokes3);
                            }
                            counter++;
                        }
                    }
                }

                stokesHash1.put(hashCodeXY.toString(), stokes1);
                stokesHash2.put(hashCodeXY.toString(), stokes2);
                stokesHash3.put(hashCodeXY.toString(), stokes3);

            }
            double diffX = diffBetweenX[1] - diffBetweenX[0];
            double diffY = diffBetweenY[1] - diffBetweenY[0];

            difference = Math.max(diffX, diffY); // what we'll increment by

            RGBTriangle.diffTriangle(minX, maxX, minY, maxY, (double) Math.round(difference * 100) / 100); // finally, pass the parameters into drawtool
        } catch (Exception e) {
            System.out.println ("File not found");
        }
    }



}
