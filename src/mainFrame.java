import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.image.*;
import javafx.scene.image.ImageView.*;
import sun.applet.Main;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class mainFrame extends Application{

    public static void main (String[] argv)
    {
        RGBTriangle.createRGBTriangle();
        launch(argv);
    }

    Button btn = new Button();
    Button btn2 = new Button();
    TextField textFieldY = new TextField();
    TextField textFieldX = new TextField();
    TextField textFieldsS1 = new TextField();
    TextField textFieldsS2 = new TextField();
    TextField textFieldsS3 = new TextField();
    Label lblX = new Label();
    Label lblY = new Label();
    Label s1 = new Label();
    Label s2 = new Label();
    Label s3 = new Label();
    Label caution = new Label();
    Label header = new Label();
    Label topNote = new Label();
    Button btnClose = new Button();



    @Override
    public void start (Stage primaryStage)
    {


        BorderPane root = new BorderPane();
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

        pane.setAlignment(Pos.CENTER_LEFT);
        pane.add(lblX, 0, 0); // Setting the positions of each according to row and column
        pane.add(lblY, 0, 1);
        pane.add(textFieldX, 1, 0);
        pane.add(textFieldY, 1, 1);
        pane.add(textFieldsS1, 1, 3);
        pane.add(textFieldsS2, 1, 4);
        pane.add(textFieldsS3, 1, 5);
        pane.add(caution, 1, 8);

        btn.setOnMouseClicked(event -> buttonClick());

        textFieldsS1.setEditable(false); // Don't want the results edited
        textFieldsS2.setEditable(false);
        textFieldsS3.setEditable(false);

        pane.add(s1, 0, 3); // Adding the labels
        pane.add(s2, 0, 4);
        pane.add(s3, 0, 5);
        pane.add(btn, 1, 6); // Adding the button


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        pane.getColumnConstraints().addAll(col1);

        Class<?> clazz = this.getClass();
        InputStream input = clazz.getResourceAsStream("triangle.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);


        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        GridPane setIm = new GridPane();
        setIm.add(imageView, 6,4);
        setIm.setAlignment(Pos.CENTER_RIGHT);

        GridPane setText = new GridPane();
        GridPane setClose = new GridPane();
        GridPane setName = new GridPane();

        header.setFont(Font.font("Bold"));
        header.setFont(Font.font(15));
        header.setText("Dennis Afanasev");
        setName.setAlignment(Pos.BOTTOM_RIGHT);

        topNote.setText("StokeMap");
        topNote.setFont(Font.font("Courier", 40));
        setText.add(topNote, 3,3);
        setText.setAlignment(Pos.TOP_CENTER);

        btnClose.setOnMouseClicked(e -> System.exit(1));
        btnClose.setText("Exit");
        setClose.setHgap(50);
        setClose.setPadding(new Insets(10, 10, 10, 10));
        setClose.add(btnClose,0,0);
        setClose.add(header, 10, 0);




        root.setRight(pane);
        root.setLeft(setIm);
        root.setTop(setText);
        root.setBottom(setClose);
       // root.setBottom(setName);
        root.backgroundProperty().setValue(Background.EMPTY);


      //  BorderPane setAll = new BorderPane();

        Scene scene = new Scene(root, 700, 550);
        primaryStage.setTitle("StokeMap");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(550);
        primaryStage.show();

    }

    public void buttonClick ()
    {
        String xText = textFieldX.getText();
        String yText = textFieldY.getText();

        String result = (RGBTriangle.getValueXY(xText + "," + yText)); // getting the result from the main code
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
}
