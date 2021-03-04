package gui;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Game;
import logic.Position;

/**
 *
 * This class has main role in gui representation.This class also handles the
 * user interaction with the GUI. It defines several handler methods for mouse
 * clicks on buttons, gridpanes and menu items.
 *
 * @author Reyhan
 */
public class FXMLDocumentController implements Initializable {

    JavaFxGUI gui;//an instance of the gui
    Game game;//an instance of the game
    private int numOfPlayers;// number of players
    private int levelNumber; // level number
    @FXML
    private Pane paneA3;// pane for AI3
    @FXML
    private GridPane grdA1;//gridpane A1
    @FXML
    private GridPane grdA2;//gridpane for AI2
    @FXML
    private GridPane grdA3;//gridpane for AI3
    @FXML
    private GridPane grdHuman;//gridpane for human
    @FXML
    private GridPane grdDice;//gridpane for dices
    @FXML
    private Pane paneHuman;//pane for human
    @FXML
    private Pane paneA2;//pane for AI2
    @FXML
    private Pane paneA1;//pane for AI1

    Group[][] groupHuman;//group array for human board
    Group[][] groupA1;//group array for AI1
    Group[][] groupA2;//group array for AI2
    Group[][] groupA3;//group array for AI3

    @FXML
    private Label lblRound;//label of the current round
    @FXML
    private Label lblTurn;//label of hte current turn
    @FXML
    private Label lblLevel;//label of the current level
    @FXML
    private TextArea txtLog;// text area for the log
    @FXML
    private GridPane grdPoint;//gridpane for the point table
    @FXML
    private Button lblRoll;//button of the roll
    @FXML
    private Button lblDropOut;//button of the drop out

    /**
     * Add the group array to each grid pane for easier displaying the images ,
     * adjust the size of the screen to the width and height of the human
     * board(square shape when resizing) and calls the first window to display
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        firstWindowsPlayersSetting();
        groupHuman = addGroupToGrid(grdHuman);
        groupA1 = addGroupToGrid(grdA1);
        groupA2 = addGroupToGrid(grdA2);
        groupA3 = addGroupToGrid(grdA3);
        //adjust the windows size remain square when it is resized
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {

            int diffWidth = (int) (paneHuman.getWidth() - paneHuman.getMinWidth());
            int diffHeight = (int) (paneHuman.getHeight() - paneHuman.getMinHeight());

            grdHuman.setPrefWidth(grdHuman.getMinWidth() + Math.min(diffWidth, diffHeight));
            grdHuman.setPrefHeight(grdHuman.getMinHeight() + Math.min(diffWidth, diffHeight));
        };

        paneHuman.widthProperty().addListener(stageSizeListener);
        paneHuman.heightProperty().addListener(stageSizeListener);

        this.lblRoll.setDisable(true);
        this.lblDropOut.setDisable(true);

    }

    /**
     * creates an array of Group corresponding to the gridPane. Each Group
     * becomes a child of the gridPane and fills a cell.
     *
     * @return an array of Group added to the gridPane
     */
    private Group[][] addGroupToGrid(GridPane grid) {

        int colcount = grid.getColumnConstraints().size();
        int rowcount = grid.getRowConstraints().size();
        Group[][] group = new Group[colcount][rowcount];
        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                group[x][y] = new Group();
                grid.add(group[x][y], x, y);
            }
        }
        return group;
    }

    /**
     * when user from the menu bar clicks on the 'New Game', this method is
     * called and the previous game is cleared and initializer window is
     * displayed. The point table labels are cleared
     *
     * @param event
     */
    @FXML
    private void onClickBtnStart(ActionEvent event) {
        if (game != null) {
            this.game.clear();
        }
        firstWindowsPlayersSetting();

    }

    /**
     * when the drop out button us activated and human player clicks on this
     * button, this method invoke an click event and calls the clickOnDropOut
     * method from the game
     *
     * @param event
     */
    @FXML
    private void onClickBtnDropOut(ActionEvent event) {
        if (game != null) {
            this.game.clickOnDropOut();
        }
    }

    /**
     * when the roll button us activated and human player clicks on this button,
     * this method invoke an click event and calls the clickRoll method from the
     * game
     *
     * @param event
     */
    @FXML
    private void onClickBtnRoll(ActionEvent event) {
        if (game != null) {
            this.game.clickRoll();
        }
    }

    /**
     * when human player clicks on the board, the x and y coordinates sent to
     * the logic package if these values are valid
     *
     * @param event
     */
    @FXML
    private void onClickOnHumanField(MouseEvent event) {//from last semester
        int x = -1;
        int y = -1;
        boolean leftClicked = event.getButton() == MouseButton.PRIMARY;

        for (Node node : grdHuman.getChildren()) {
            if (node instanceof Group) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {

                    x = GridPane.getColumnIndex(node);//get x coordinate
                    y = GridPane.getRowIndex(node);//get y coordiante
                }
            }
        }
        if (x >= 0 && y >= 0 && leftClicked) {
            this.game.setOnBoard(new Position(x - 1, y));
        }

    }

    /**
     * with this action event exit from the game and windows is closed.This
     * method is called when player select 'Exit' from the menu bar. The current
     * window is closed.
     *
     * @param event
     */
    @FXML
    private void exit(ActionEvent event) {
        Dizzle_MrsRahimi.mainLoginScene.getWindow().hide();
    }

    /**
     * save the game in the json file. This method is called when player select
     * 'Save' from the menu bar.
     *
     * @param event
     */
    @FXML
    private void save(ActionEvent event) {
        this.game.save();
    }

    /**
     * this game is set to the loaded game. This method is called when player
     * select 'Load' from the menu bar.A new instance of the game is called and
     * the previous game is cleared
     *
     * @param event
     */
    @FXML
    private void load(ActionEvent event) throws FileNotFoundException {
        //  this.innerPane.setDisable(false);
        if (this.game != null) {
            this.game = this.game.load(game);
        }

    }

    /**
     * create seperate stage for the initial setting of the game.Two drop down
     * list are created one for the number of player and the second for the
     * level number. in this window the number of players and the level number
     * are selected by the player. When player clicks on the 'OK' button the
     * selected values are sent to the game and new instances of the JavaFxGUI
     * and game are created and the required elements are set.
     */
    private void firstWindowsPlayersSetting() {
        Label firstLabel = new Label("Number of players :");
        ComboBox FirstDropDown = new ComboBox();
        FirstDropDown.getItems().addAll("2", "3", "4");
        FirstDropDown.setValue("2");
        Label level = new Label("Select Level :");
        ComboBox levelSelection = new ComboBox();
        levelSelection.getItems().addAll("1", "2", "3");
        levelSelection.setValue("1");

        Button btnOk = new Button("OK");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(firstLabel, FirstDropDown, level, levelSelection);
        secondaryLayout.getChildren().add(btnOk);
        btnOk.setPrefSize(75, 25);
        firstLabel.setTranslateX(-40);
        firstLabel.setTranslateY(-50);
        FirstDropDown.setTranslateX(60);
        FirstDropDown.setTranslateY(-50);
        level.setTranslateX(-20);
        level.setTranslateY(0);
        levelSelection.setTranslateX(60);
        levelSelection.setTranslateY(0);
        btnOk.setTranslateX(0);
        btnOk.setTranslateY(50);
        Scene secondScene = new Scene(secondaryLayout, 250, 150);
        // New window (Stage) is created
        Stage smallWindow = new Stage();
        smallWindow.setTitle("Initializer");
        smallWindow.setScene(secondScene);
        // Set position of second window, related to primary window.
        smallWindow.setX(700);
        smallWindow.setY(350);
        smallWindow.setAlwaysOnTop(true);
        smallWindow.show();
        btnOk.setOnAction((ActionEvent e) -> {
            switch (FirstDropDown.getValue().toString()) {
                case "3":
                    numOfPlayers = 3;
                    break;
                case "4":
                    numOfPlayers = 4;
                    break;
                default:
                    numOfPlayers = 2;
                    break;
            }
            switch (levelSelection.getValue().toString()) {
                case "2":
                    levelNumber = 2;
                    break;
                case "3":
                    levelNumber = 3;
                    break;
                default:
                    levelNumber = 1;

            }
            this.lblLevel.setText(levelNumber + " ");
            smallWindow.close();
            // create an instance of the JavaFxGUI for the gui of the game
            gui = new JavaFxGUI(grdHuman, grdA1, grdA2, grdA3, grdDice, lblRound,
                    lblTurn, txtLog, lblRoll, lblDropOut, paneHuman,
                    paneA1, paneA2, paneA3, groupHuman,
                    groupA1, groupA2, groupA3, grdPoint, lblLevel);

            try {
                //create an instance of game to hanlde the rule game
                this.game = new Game(numOfPlayers, levelNumber, gui);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
