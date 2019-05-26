import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;


public class Main extends Application {
    private final static double animationDuration = 1;
    public volatile static Clock clock = new Clock();
    public static CrabFoodOperator cfOperator = new CrabFoodOperator();
    static Logger logger = new Logger();
    Scene menuScene, logScene;
    private static boolean processEnded = false;

    public static void main(String[] args) {
        launch(args);
    }

    // The method to access CSS
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

        if(CrabFoodOperator.copy_listOfProcesses.size==0 && !processEnded){
            processEnded=true;
            String endOfProcess ="";
            endOfProcess += clock.getTime() + " All customers are served and shops are closed";
            System.out.println(endOfProcess);
            cfOperator.appendToProcess(endOfProcess);
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
                                //System.out.println(hourString + ":" + minuteString);
                                digitalClock.setText(hourString + ":" + minuteString);
                                updateProcess();
                                clock.updateClock();
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(animationDuration))
        );


        //Start cycle for digital clock
        digitalTime.setCycleCount(Animation.INDEFINITE);
        digitalTime.play();

        BorderPane borderPane = new BorderPane();
        //CREATE VBOX TO STORE MENU BUTTONS
        VBox vbox = new VBox(10);
        vbox.setPrefWidth(180);
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
        Button btnViewLog = new Button("Log");
        btnViewLog.setMaxSize(150, 50);
        btnViewLog.setOnAction(e -> primaryStage.setScene(logScene));

        //BUTTON to view map
        Button btn_viewMap = new Button("Map");
        btn_viewMap.setMaxSize(150, 50);
        btn_viewMap.setOnAction(e -> showMap());

        //BUTTON to view daily order status
        Button btn_viewDailyStatus = new Button("Daily Status");
        btn_viewDailyStatus.setMaxSize(150, 50);
        btn_viewDailyStatus.setOnAction(e -> showOrderInformation());

        //BUTTONG to view Restaurant dish order details
        Button btn_viewRestaurantStatus = new Button("Restaurant Dish Status");
        btn_viewRestaurantStatus.setMaxSize(150, 150);
        btn_viewRestaurantStatus.setOnAction(e -> showDishInformation());

        vbox.getChildren().addAll(btnViewLog, btn_viewMap, btn_viewDailyStatus, btn_viewRestaurantStatus);

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
        BorderPane.setMargin(back_btn, new Insets(10, 0, 0, 0));
        BorderPane.setMargin(label, new Insets(0, 0, 10, 0));
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

    public void showOrderInformation() {
        Stage informationWindow = new Stage();
        informationWindow.setTitle("Order Information");

        BorderPane borderPane = new BorderPane();

        Scene scene = new Scene(new Group());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Set<String> keys = CrabFoodOperator.orderStatusList.keySet(); //get all keys

        for (String str : keys) {
            pieChartData.add(new PieChart.Data(str, CrabFoodOperator.orderStatusList.get(str)));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Orders from Restaurant");
        //chart.setLegendVisible(false);

        chart.getData().forEach(data -> {
            String percentage = (int) data.getPieValue() + " Order(s)";
            Tooltip toolTip = new Tooltip(percentage);
            toolTip.setFont(Font.font("Arial", 15));
            Tooltip.install(data.getNode(), toolTip);
        });

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            // System.out.println("Mouse pressed");
                            Timeline appear = new Timeline(new KeyFrame(
                                    Duration.seconds(1), new KeyValue(caption.opacityProperty(), 1, Interpolator.DISCRETE)
                            ));

                            FadeTransition fade = new FadeTransition(Duration.seconds(1), caption);
                            fade.setFromValue(1);
                            fade.setToValue(0);

                            SequentialTransition displayThenFade = new SequentialTransition(
                                    appear, fade
                            );

                            appear.setCycleCount(1);
                            displayThenFade.play();

                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf((int) data.getPieValue())
                                    + " orders");
                        }
                    });
        }

        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
        borderPane.setCenter(chart);
        borderPane.getStylesheets().add(getResource("DishInfo.css"));
        // borderPane.setBottom(new Label("Testing"));
        //((Group) scene.getRoot()).getChildren().addAll(chart, caption);

        informationWindow.setScene(new Scene(borderPane, 600, 600));
        informationWindow.show();

    }

    public void showDishInformation() {
        BorderPane borderPane = new BorderPane();


        // ComboBox combobox = new ComboBox<String>(FXCollections.observableArrayList("One", "Two", "Three"));
        ComboBox combobox = new ComboBox();
        for (Restaurant r : CrabFoodOperator.listOfRestaurant) {
            combobox.getItems().add(r.getName());
        }

        //combobox.getSelectionModel().select(0);  //automatically select the first index
        combobox.setId("changed");  //id for CSS
        combobox.setPromptText("Restaurant");


        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.CENTER);
        Label select_restaurant = new Label("Select Restaurant :");
        select_restaurant.setFont(Font.font("Arial", 16));
        topMenu.getChildren().add(select_restaurant);
        topMenu.setSpacing(10);
        topMenu.getChildren().add(combobox);

        BorderPane.setMargin(topMenu, new Insets(10, 0, 0, 0));
        borderPane.setTop(topMenu);
        BorderPane.setAlignment(combobox, Pos.CENTER);

        Label testLabel = new Label();

        borderPane.setCenter(testLabel);

        ObservableList<PieChart.Data> pieChartData_Dish = FXCollections.observableArrayList();

        final PieChart pchart = new PieChart(pieChartData_Dish);
        pchart.getData().forEach(data -> {
            String percentage = (int) data.getPieValue() + "Dish(es)";
            Tooltip toolTip = new Tooltip(percentage);
            toolTip.setFont(Font.font("Arial", 15));
            Tooltip.install(data.getNode(), toolTip);
        });

        VBox vbox = new VBox();
        Text dishes_Text = new Text("Dishes");
        dishes_Text.setFont(Font.font("Arial",FontWeight.BOLD,18));

        combobox.setOnAction(e -> {
            pchart.setTitle("Dishes from "+ combobox.getValue().toString());
            if (combobox.getValue() != null && !combobox.getValue().toString().isEmpty()) {
                pieChartData_Dish.clear();
                vbox.getChildren().clear();
                vbox.getChildren().add(dishes_Text);
                testLabel.setText(combobox.getValue().toString());

                Restaurant tempRestaurant = new Restaurant();
                for (Restaurant r : CrabFoodOperator.listOfRestaurant) {
                    if (combobox.getValue().toString().equalsIgnoreCase(r.getName()))
                        tempRestaurant = r;
                }

                Set<String> keys = tempRestaurant.getDishSet(); //get all keys
               // System.out.println(keys); //to check if the keys we get are correct to the restaurant or not

                for (String str : keys) {
                    pieChartData_Dish.add(new PieChart.Data(str, Restaurant.dishOrderList.get(str)));
                    Text tempLabel = new Text();
                    tempLabel.setText(Restaurant.dishOrderList.get(str) +" "+ str);
                    tempLabel.setFont(Font.font("Arial",15));
                    vbox.getChildren().add(tempLabel);
                }

            }
        });

        pchart.setStartAngle(90);

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(pchart);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        borderPane.setCenter(pchart);
        borderPane.setRight(vbox);

        borderPane.getStylesheets().add(getResource("DishInfo.css"));
        Stage dishWindow = new Stage();
        dishWindow.setResizable(false);
        dishWindow.setScene(new Scene(borderPane, 800, 600));
        dishWindow.setTitle("Dish Order Status");
        dishWindow.show();


    }

}

