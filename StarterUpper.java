import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class StarterUpper extends Application {
    private MediaPlayer musicPlayer;
    private ArrayList<StartUpIdea> startUpList;
    private File saveFile;
    private ObservableList<String> theList = FXCollections.observableArrayList();
    private ListView<String> viewList = new ListView<>(theList);

    /**
     * This method creates a general music player that loads the given song,
     * and creates a play and pause button
     * @return an HBox that contains the play and pause button
     */
    private HBox setAudio() {
        try {
            Media music = new Media(new File("BARBIEDREAM.mp3").toURI().toString());
            musicPlayer = new MediaPlayer(music);

            Button pressPlay = new Button("▶");
            pressPlay.setOnAction(event -> {
                    musicPlayer.play();
                    musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
                }
            );

            Button pressPause = new Button("▐▐");
            pressPause.setOnAction(event -> musicPlayer.pause());

            HBox musicBox = new HBox();
            musicBox.setAlignment(Pos.CENTER);
            musicBox.getChildren().addAll(pressPlay, pressPause);

            return musicBox;
        } catch (MediaException e) {
            return null;
        }
    }

    /**
     * This method creates a chicken image and converts it to an image that can be viewed
     * It also creates a name label
     * @return an HBox that contains both the chicken image and name
     */
    private HBox chickenAndNameCreate() {
        Image chickenPic = null;
        try {
            FileInputStream image = new FileInputStream("chicken.png");
            chickenPic = new Image(image);
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading chicken");
        }
        ImageView chickenTOSEE = new ImageView(chickenPic);
        chickenTOSEE.setFitWidth(75);
        chickenTOSEE.setFitHeight(75);

        Label myName = new Label("Erin C. Tan\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        HBox chickenAndName = new HBox();
        chickenAndName.setAlignment(Pos.CENTER);
        chickenAndName.getChildren().addAll(chickenTOSEE, myName);

        return chickenAndName;
    }

    /**
     * This method creates an HBox with default settings for the question labels
     * @return the HBox with curated settings
     */
    private HBox setUpHBox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_LEFT);
        hbox.setSpacing(15);
        return hbox;
    }

    /**
     * This method creates a label with a specific string and font
     * @param string the text for the label
     * @return a label with a custom string and font
     */
    private Label setUpLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Courier New", 20));
        return label;
    }

    /**
     * This method displays an alert for the addIdea button when
     * there are no text in any of the textfields
     */
    private void addIdeaEmpty() {
        Alert emptyString = new Alert(Alert.AlertType.ERROR);
        emptyString.setTitle("Error with Input");
        emptyString.setHeaderText("Empty Field");
        emptyString.setContentText("You have not inserted any text in one of the boxes");
        emptyString.showAndWait();
    }

    /**
     * This method displays an alert for the addIdea button when
     * there is an out of range number for the textfields requiring
     * integers
     */
    private void addIdeaInvalidInt() {
        Alert invalidNum = new Alert(Alert.AlertType.ERROR);
        invalidNum.setTitle("Number Range");
        invalidNum.setHeaderText("Error with range of number");
        invalidNum.setContentText("You have inserted a number that doesn't make sense");
        invalidNum.showAndWait();
    }

    /**
     * This method displays an alert for the addIdea button when
     * there is an entry that is NOT an integer for the textfield
     * that requires an integer
     */
    private void addIdeaNumException() {
        Alert exception = new Alert(Alert.AlertType.ERROR);
        exception.setTitle("Error");
        exception.setHeaderText("Invalid Integer");
        exception.setContentText("There is an invalid integer entry.");
        exception.showAndWait();
    }

    /**
     * This method displays an alert for the sortIdea button when
     * there are no ideas saved to sort
     */
    private void sortIdeaEmpty() {
        Alert emptyListError = new Alert(Alert.AlertType.ERROR);
        emptyListError.setTitle("Empty List");
        emptyListError.setHeaderText("There are no ideas to sort");
        emptyListError.setContentText("Add more ideas to your list");
        emptyListError.showAndWait();
    }

    /**
     * This method is a more detailed music function that plays
     * music specifcally when there is a succesful item added
     * and pauses after 10 seconds
     */
    private void musicOnAdd() {
        musicPlayer.seek(Duration.ZERO);
        musicPlayer.play();
        Duration tenSeconds = Duration.seconds(10);
        KeyFrame keyframe = new KeyFrame(tenSeconds, e -> musicPlayer.pause());
        Timeline timeline = new Timeline(keyframe);
        timeline.play();
    }

    /**
     * This method create a confirmation alert to ask if user wants to reset ideas
     * @return a preset Alert Confirmation
     */
    private Alert resetConfirmation() {
        Alert resetWarning = new Alert(Alert.AlertType.CONFIRMATION);
        resetWarning.setTitle("Reset Warning");
        resetWarning.setHeaderText("This will delete and reset all ideas");
        resetWarning.setContentText("Are you absolutely sure you want to reset?");
        return resetWarning;
    }

    /**
     * This method prints the current ideas onto the scene
     */
    private void seeList() {
        theList.clear();
        for (int i = 0; i < startUpList.size(); i++) {
            theList.add(startUpList.get(i).toString());
        }
        viewList.setItems(theList);
        viewList.setStyle(("-fx-control-inner-background: #c6e4c3;"));
    }

    /**
     * This method sets all of the textfields to an empty one
     */
    private void setTextEmpty(TextField nameField, TextField problemField, TextField targetCustomerField,
                                TextField needField, TextField experienceField, TextField marketSizeField,
                                TextField competitorsField) {
        nameField.setText("");
        problemField.setText("");
        targetCustomerField.setText("");
        needField.setText("");
        experienceField.setText("");
        marketSizeField.setText("");
        competitorsField.setText("");
    }
    /**
     * This method begins the JavaFX program but initializing the GUI
     * Creates a Stage and puts in a scene containing labels, textfields,
     * buttons, audio, and images
     * @param mainStage main stage for the program
     */
    public void start(Stage mainStage) {
        startUpList = new ArrayList<>();
        saveFile = new File("saveFile.txt");
        HBox musicBox = setAudio();

        //creating title of the window
        mainStage.setTitle("Problem Ideation Form");

        //display questions and response
        Label nameLabel = setUpLabel("    What is the name of the Start Up?");
        TextField nameField = new TextField();
        HBox nameBox = setUpHBox();
        nameBox.getChildren().addAll(nameLabel, nameField);

        Label problemLabel = setUpLabel("    What is the problem?");
        TextField problemField = new TextField();
        HBox problemBox = setUpHBox();
        problemBox.getChildren().addAll(problemLabel, problemField);

        Label targetCustomerLabel = setUpLabel("    Who is the target customer?");
        TextField targetCustomerField = new TextField();
        HBox targetCustomerBox = setUpHBox();
        targetCustomerBox.getChildren().addAll(targetCustomerLabel, targetCustomerField);

        Label needLabel = setUpLabel("    How badly does the customer NEED this problem fixed (1-10)?");
        TextField needField = new TextField();
        HBox needBox = setUpHBox();
        needBox.getChildren().addAll(needLabel, needField);

        Label experienceLabel = setUpLabel("    How many people do you know who might experience this problem?");
        TextField experienceField = new TextField();
        HBox experienceBox = setUpHBox();
        experienceBox.getChildren().addAll(experienceLabel, experienceField);

        Label marketSizeLabel = setUpLabel("    How big is the target market?");
        TextField marketSizeField = new TextField();
        HBox marketSizeBox = setUpHBox();
        marketSizeBox.getChildren().addAll(marketSizeLabel, marketSizeField);

        Label competitorsLabel = setUpLabel("    Who are the competitors/existing solutions?");
        TextField competitorsField = new TextField();
        HBox competitorsBox = setUpHBox();
        competitorsBox.getChildren().addAll(competitorsLabel, competitorsField);

        //add button to list of startUpIdea
        Button addIdea = new Button("Add Idea");
        Button sortIdeas = new Button("Sort Ideas");
        Button resetIdeas = new Button("Reset Ideas");
        Button saveIdea = new Button("Save Ideas");

        //implementing addIdea button using anonymous inner class
        addIdea.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String nameString = nameField.getText();
                String problemString = problemField.getText();
                String targetCustomerString = targetCustomerField.getText();
                String needString = needField.getText();
                String experienceString = experienceField.getText();
                String marketSizeString = marketSizeField.getText();
                String competitorsString = competitorsField.getText();

                try {
                    if (nameString.isEmpty() || problemString.isEmpty() || targetCustomerString.isEmpty()
                        || needString.isEmpty() || experienceString.isEmpty() || marketSizeString.isEmpty()
                        || competitorsString.isEmpty()) {
                            addIdeaEmpty();
                    } else {
                        int needNum = Integer.parseInt(needString);
                        int expNum = Integer.parseInt(experienceString);
                        int marNum = Integer.parseInt(marketSizeString);
                        if (needNum < 1 || needNum > 10 || expNum < 0 || marNum < 0) {
                            addIdeaInvalidInt();
                        } else {
                            musicOnAdd();
                            StartUpIdea currentIdea = new StartUpIdea(nameString, problemString,
                                                                        targetCustomerString, needNum,
                                                                        expNum, marNum, competitorsString);
                            startUpList.add(currentIdea);
                            setTextEmpty(nameField, problemField, targetCustomerField,
                                        needField, experienceField, marketSizeField, competitorsField);
                            seeList();
                        }
                    }
                } catch (NumberFormatException e) {
                    addIdeaNumException();
                }
            }
        });

        //implementing sort button using lambda expressions
        sortIdeas.setOnAction((ActionEvent event) -> {
                if (startUpList.isEmpty()) {
                    sortIdeaEmpty();
                } else {
                    Collections.sort(startUpList);
                    seeList();
                }
            }
        );

        //implementing resetIdeas using lambda expressions
        resetIdeas.setOnAction((ActionEvent event) -> {
                Alert resetWarning = resetConfirmation();
                ButtonType yes = new ButtonType("Yes");
                ButtonType no = new ButtonType("No");
                resetWarning.getButtonTypes().setAll(yes, no);
                Optional<ButtonType> buttonChosen = resetWarning.showAndWait();

                if (buttonChosen.get() == yes) {
                    if (saveFile.exists()) {
                        saveFile.delete();
                    }
                    startUpList.clear();
                    setTextEmpty(nameField, problemField, targetCustomerField,
                                needField, experienceField, marketSizeField, competitorsField);
                    seeList();
                }
            }
        );

        //implementing saveIdea using anonymous inner class
        saveIdea.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileUtil.saveIdeasToFile(startUpList, saveFile);
            }
        });

        HBox chickenAndName = chickenAndNameCreate();
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.getChildren().addAll(addIdea, sortIdeas, resetIdeas, saveIdea);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        try {
            root.getChildren().addAll(chickenAndName, nameBox, problemBox, targetCustomerBox,
                                        needBox, experienceBox, marketSizeBox, competitorsBox,
                                        buttons, musicBox, viewList);
        } catch (NullPointerException e) {
            root.getChildren().addAll(chickenAndName, problemBox, targetCustomerBox,
                                        needBox, experienceBox, marketSizeBox, competitorsBox, buttons, viewList);
        }
        Scene scene = new Scene(root, 1035, 575);
        scene.setFill(Color.web("#fad4da"));
        root.setStyle("-fx-background-color: #fcc5cd;");
        mainStage.setScene(scene);
        mainStage.show();
    }
}
