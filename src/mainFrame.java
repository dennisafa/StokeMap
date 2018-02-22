import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import java.io.InputStream;


public class mainFrame extends Application {

    Button btn = new Button();
    Button btnClose = new Button();
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



    public static void main (String[] argv)
    {
        RGBTriangle.createRGBTriangle();
        launch(argv);
    }

    @Override
    public void start (Stage primaryStage)
    {
        BorderPane root = new BorderPane(); // root pane, with grid pane insertions
        GridPane pane = new GridPane();


        pane.setHgap(10); // Spacings and the sort
        pane.setVgap(10);
        pane.setPadding(new Insets(10));

        btn.setText("Calculate Stokes Parameters");
        btn.setMinWidth(200);

        lblX.setText("X Axis:");
        lblY.setText("Y Axis:");
        caution.setText("Enter X and Y in values of +- 0.005");
        caution.setFont(Font.font("Helvica", 10));

        s1.setText("S1^2 = ");
        s2.setText("S2^2 = ");
        s3.setText("S3^2 = ");
        r.setText("Value of R = ");
        g.setText("Value of G = ");
        b.setText("Value of B = ");
        nameC.setText("Color = ");

        pane.setAlignment(Pos.CENTER_RIGHT);
        pane.add(lblX, 0, 0); // Setting the positions of each according to row and column
        pane.add(lblY, 0, 1);
        pane.add(textFieldX, 1, 0);
        pane.add(textFieldY, 1, 1);
        pane.add(textFieldsS1, 1, 3); 
        pane.add(textFieldsS2, 1, 4);
        pane.add(textFieldsS3, 1, 5);
        pane.add(caution, 1, 8);
        pane.add(valR, 1, 9);
        pane.add(r, 0, 9);
        pane.add(g, 0, 10);
        pane.add(b, 0, 11);
        pane.add(valG, 1, 10);
        pane.add(valB, 1, 11);
        pane.add(colorName, 1, 12);
        pane.add(nameC, 0, 12);

        btn.setOnMouseClicked(event -> calculateValues());

        textFieldsS1.setEditable(false); // Don't want the results edited
        textFieldsS2.setEditable(false);
        textFieldsS3.setEditable(false);

        pane.add(s1, 0, 3); // Adding the labels
        pane.add(s2, 0, 4);
        pane.add(s3, 0, 5);
        pane.add(btn, 1, 6); // Adding the button

        // Make columns neater
        ColumnConstraints col1 = new ColumnConstraints();
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
        GridPane setName = new GridPane();

        header.setFont(Font.font("Bold"));
        header.setFont(Font.font(15));
        header.setText("by Dennis Afanasev");
        //setName.setAlignment(Pos.BOTTOM_RIGHT);

        topNote.setText("StokesMap");
        topNote.setFont(Font.font("Courier", 40));
        setText.add(topNote, 3,3);
        setText.setAlignment(Pos.TOP_CENTER);

        btnClose.setOnMouseClicked(e -> primaryStage.close()); // Close functionality
        btnClose.setText("Exit");
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
        primaryStage.setMinHeight(550);
        primaryStage.setMaxHeight(850);
        primaryStage.setMaxWidth(650);
        primaryStage.show();

    }

    public void calculateValues ()
    {
        String xText = textFieldX.getText(); // Getting the text
        String yText = textFieldY.getText();
        String result = (RGBTriangle.xyRGB.get(xText + "," + yText)); // getting the result from the main code
        int currentR = RGBTriangle.getRColor.get(xText + "," + yText);
        int currentG = RGBTriangle.getGColor.get(xText + "," + yText);
        int currentB = RGBTriangle.getBColor.get(xText + "," + yText);

        if (result == null) {
            textFieldsS1.setText("Invalid input");
            textFieldsS2.setText("Invalid input");
            textFieldsS3.setText("Invalid input");
            valR.setText("Invalid input");
            valB.setText("Invalid input");
            valG.setText("Invalid input");
        }

        valR.setText(String.valueOf(currentR));
        valG.setText(String.valueOf(currentG));
        valB.setText(String.valueOf(currentB));

        colorName.setText(setColor(currentR, currentG, currentB));

        int length = result.length(); // setting its length for later
        int i = 0;
        StringBuffer s1 = new StringBuffer(); // Using three while loops to seperate the commas and put into appropriate text
        while (result.charAt(i) != ',') { // need to make more efficient
               s1.append(result.charAt(i));
               i++;
        }
        textFieldsS1.setText(s1.toString());

        StringBuffer s2 = new StringBuffer();
        while (result.charAt(i + 1) != ',') {
            s2.append(result.charAt(i + 1));
            i++;
        }
        textFieldsS2.setText(s2.toString());


        StringBuffer s3 = new StringBuffer();
        while (i + 2 < length) {
            s3.append(result.charAt(i + 2));
            i++;
        }
        textFieldsS3.setText(s3.toString());

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
}
