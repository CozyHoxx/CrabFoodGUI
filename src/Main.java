import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Main extends Application {


    public volatile static Clock clock = new Clock();
    public static CrabFoodOperator cfOperator = new CrabFoodOperator();
    static Logger logger = new Logger();
    Scene menuScene, logScene;

    public static void main(String[] args) {
        launch(args);
    }

    //The method to access CSS
    static String getResource(String path) {
        return Main.class.getResource(path).toExternalForm();
    }

    public static String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);
        return sb.toString();
    }

    public static void updateProcess() {
        if (clock.getTime().equalsIgnoreCase("00:00")) {
            cfOperator.appendToProcess(clock.getTime() + " A new day has begun!");
        }

        if (CrabFoodOperator.copy_listOfProcesses.size != 0) {
            while (clock.getTime().equalsIgnoreCase(CrabFoodOperator.copy_listOfProcesses.getTime_Str(0))) {
                String str = CrabFoodOperator.copy_listOfProcesses.removeFirstNode();
                System.out.println(str);
                cfOperator.appendToProcess(str);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        //making the menu scene
        makeMenuScene(primaryStage);
        //makeing the log scene
        makeLogScene(primaryStage);


        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1100);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("CrabFood");
        primaryStage.setOnHidden(e -> {
            logger.endLog();
            Platform.exit();
            System.out.println("Performing system cleanup");
        });
        primaryStage.show();
    }

    public void makeMenuScene(Stage primaryStage) {
        /*  MAKING THE CLOCK   */
        final Label digitalClock = new Label();
        digitalClock.setId("digitalClock");

        // the digital clock updates once a second.
        final Timeline digitalTime = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {

                                String hourString = pad(2, '0', clock.getHOUR() + "");
                                String minuteString = pad(2, '0', clock.getMINUTE() + "");
                                //check if clock is correct or not
                                System.out.println(hourString + ":" + minuteString);
                                digitalClock.setText(hourString + ":" + minuteString);
                                updateProcess();
                                clock.updateClock();
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(0.5))
        );


        //Start cycle for digital clock
        digitalTime.setCycleCount(Animation.INDEFINITE);
        digitalTime.play();

        BorderPane borderPane = new BorderPane();
        //CREATE VBOX TO STORE MENU BUTTONS
        VBox vbox = new VBox(10);
        vbox.setPrefWidth(150);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10, 0, 0, 0));

        //CREATE HBOX TO PLACE TIMERS
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        Label timeString = new Label("TIME : ");
        timeString.setFont(new Font("Verdana", 20));
        hbox.getChildren().addAll(timeString, digitalClock);

        Label processLabel = new Label("Processes");
        processLabel.setFont(Font.font("Verdana", 18));

        //TEXT AREA
        TextArea textArea = new TextArea();
        textArea.setMinSize(600, 500);
        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", 17));
        textArea.textProperty().bind(CrabFoodOperator.getProcess());


        //GRIDPANE
        GridPane gridPane = new GridPane();
        GridPane.setHalignment(processLabel, HPos.CENTER);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setConstraints(processLabel, 0, 0);
        GridPane.setConstraints(textArea, 0, 1);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.getChildren().addAll(processLabel, textArea);

        //set clock to top of borderPane
        borderPane.setTop(hbox);
        //set menu buttons on the left
        borderPane.setLeft(vbox);
        //set update process in the center
        borderPane.setCenter(gridPane);

        //BUTTON to view log
        Button btnViewLog = new Button("View Log");
        btnViewLog.setMaxSize(80, 50);
        btnViewLog.setOnAction(e -> primaryStage.setScene(logScene));

        //BUTTON to view map
        Button btn_viewMap = new Button("View Map");
        btn_viewMap.setMaxSize(80, 50);
        btn_viewMap.setOnAction(e -> showMap());


        vbox.getChildren().addAll(btnViewLog, btn_viewMap);

        menuScene = new Scene(borderPane, 1200, 800, Color.TRANSPARENT);
        menuScene.getStylesheets().add(getResource("menu.css"));

    }

    public void makeLogScene(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        Label label = new Label("Log File");
        label.setFont(Font.font("Verdana", 20));
        label.setAlignment(Pos.CENTER);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFont(Font.font("Monospace", 16));

        File logFile = logger.getFile();

        try (BufferedReader br = new BufferedReader(new FileReader(logger.getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {

                textArea.appendText(line + "\n");
            }

        } catch (Exception e) {

        }


        Button back_btn = new Button("Back");
        back_btn.setOnAction(e -> primaryStage.setScene(menuScene));


        borderPane.setTop(label);
        borderPane.setCenter(textArea);
        borderPane.setBottom(back_btn);

        BorderPane.setAlignment(back_btn, Pos.CENTER);
        BorderPane.setMargin(back_btn,new Insets(10,0,0,0));
        BorderPane.setMargin(label, new Insets(0,0,10,0));
        BorderPane.setAlignment(label, Pos.CENTER);
        borderPane.setPadding(new Insets(10, 0, 10, 0));

        logScene = new Scene(borderPane, 600, 500);
        logScene.getStylesheets().add(getResource("log.css"));

    }

    public void showMap() {
        Stage window = new Stage();
        // window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Map");

        Button btn_closeWindow = new Button("Close");
        btn_closeWindow.setOnAction(e -> window.close());

        Label mapLabel = new Label("MAP");
        mapLabel.setFont(new Font("Verdana", 20));

        GridPane gridPane = Map.getMapPane();
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.getChildren().addAll(gridPane);

        BorderPane borderPane = new BorderPane();

        borderPane.setPadding(new Insets(10, 0, 10, 0));
        borderPane.setCenter(hbox);
        borderPane.setTop(mapLabel);
        borderPane.setBottom(btn_closeWindow);
        BorderPane.setAlignment(mapLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(btn_closeWindow, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(hbox, Pos.TOP_CENTER);
        borderPane.setPrefSize(400, 400);

        //window.setMinWidth(Map.getSize()*50);
        //window.setMinHeight(400);
        window.setResizable(false);
        window.initModality(Modality.NONE);
        int windowWidth = Map.getSize() * (Map.rectSize + 30);
        Scene mapScene = new Scene(borderPane, windowWidth, windowWidth - 20);
        mapScene.getStylesheets().add(getResource("map.css"));
        //window.setScene(new Scene(borderPane, windowWidth, windowWidth - 20));
        window.setScene(mapScene);
        window.show();

    }
}

