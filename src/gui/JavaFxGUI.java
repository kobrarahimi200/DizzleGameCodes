package gui;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.GUIConnector;
import logic.LevelMaker;
import logic.Position;
import logic.Types;

/**
 * This class is implementing the method which are defined in the gui connector.
 * all method for visualization that are required are written here.Displaying
 * the dices, highlighting the neighbors, updating the dice list, showing score
 * in the point table and displaying the pen on the board when the drop out is
 * activated.
 *
 * @author Reyhan
 */
class JavaFxGUI implements GUIConnector {

    private ImageView[][] imgVwsDices;//image view array od dices
    private ImageView[][] imgVewsHuman;//image view array for human board
    private ImageView[][] imgVewsA1;//image view array for AI1 board
    private ImageView[][] imgVewsA2;//image view array for ai2 board
    private ImageView[][] imgVewsA3;//image view array for ai3 board

    private Group[][] groupHuman;// array of group for grouping the images for human
    private Group[][] groupA1;//group array for AI1
    private Group[][] groupA2;//group array for the AI2
    private Group[][] groupA3;//group array for the AI3

    private GridPane grdPoints;//gridpane for point table board
    private GridPane grdHuman;//gridpane for human board
    private GridPane grdA1;//gridpane for bot1 board
    private GridPane grdA2;//gridpane for bot2 board
    private GridPane grdA3;//gridpane for bot3 board
    private GridPane grdDice;//gridpane for dices
    private Pane humanPane;// the pane of humab board
    private Pane paneA1;// the pane of the AI1(bot1)
    private Pane paneA2;// the pane of the AI2
    private Pane paneA3;//the pane of the AI3
    private Label round;// the current round label
    private Label turn;// the curent turn label
    private String path = "gui/img/";
    private String type = ".png";
    private TextArea logArea;// the log area text area
    private Button lblRoll;// the roll button
    private Button lblDropOut;// the drop out button
    private Image imgPen = new Image(path + "pen" + type);// iamge of bg pen
    ImageView[] imgVwPen = new ImageView[4];
    private Label[] lblPoints;// labels of point table

    private Label level;//cuurent level label
    int numOfRedJewel = 0;//number of red jewel score in the point table
    int numOfRightArrow = 0;//the horizontal point value in the point table
    int numOfUpArrow = 0;//the vertical point value in the point table
    int numOfPuzzleBlue = 0;//the puzzle blue point value in the point table
    int numOfBomb = 0;//the bomb point value in the point table
    int numOfYelJewel = 0;//the jewel yellow point value in the point table
    int numOfGreenPuz = 0;//the puzzle green point value in the point table
    int numOfFlag = 0;//the flag point value in the point table
    int winnerScore = 0;
    private int levelNum; //current level number

    /**
     * default constructor for setting all necessary element which is modified
     * during the game
     *
     * @param pnSelected
     * @param grdHuman is the human gird
     * @param a1 is the first AI grid pane
     * @param a2 is the second AI grid pane
     * @param a3 is the third AI grid pane
     * @param imgVwsNextBox
     * @param imgVwsCurrentBox
     * @param btn0
     * @param btn1
     * @param btn2
     * @param btn3
     *
     */
    public JavaFxGUI(GridPane grdHuman, GridPane a1, GridPane a2,
            GridPane a3, GridPane imgVwsDices, Label round, Label turn,
            TextArea logArea, Button roll, Button dropout, Pane human, Pane paneA1, Pane paneA2,
            Pane paneA3, Group[][] groupH, Group[][] groA1, Group[][] groA2,
            Group[][] groA3, GridPane grdPoint, Label level) {
        this.grdHuman = grdHuman;
        this.grdA1 = a1;
        this.grdA2 = a2;
        this.grdA3 = a3;
        this.grdDice = imgVwsDices;
        this.round = round;
        this.turn = turn;

        this.logArea = logArea;
        this.lblDropOut = dropout;
        this.lblRoll = roll;
        this.humanPane = human;
        this.paneA1 = paneA1;
        this.paneA2 = paneA2;
        this.paneA3 = paneA3;

        this.level = level;
        this.groupHuman = groupH;
        this.groupA1 = groA1;
        this.groupA2 = groA2;
        this.groupA3 = groA3;
        this.grdPoints = grdPoint;

        for (int i = 0; i < imgVwPen.length; i++) {
            imgVwPen[i] = new ImageView(imgPen);
        }
    }

    @Override
    public void setLevelData(LevelMaker leveObj, int numOfPlayers, int levelNumber) {

        for (int i = 0; i < numOfPlayers; i++) {
            switch (i) {
                case 0:
                    setDataToImgVw(leveObj, grdHuman, levelNumber, imgVewsHuman, groupHuman);
                    break;
                case 1:
                    setDataToImgVw(leveObj, grdA1, levelNumber, imgVewsA1, groupA1);
                    break;
                case 2:
                    setDataToImgVw(leveObj, grdA2, levelNumber, imgVewsA2, groupA2);
                    break;
                case 3:
                    setDataToImgVw(leveObj, grdA3, levelNumber, imgVewsA3, groupA3);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void updateLabels(int round, int turn) {
        this.round.setText(round + "");
        this.turn.setText(turn + "");
    }

    /**
     * gets the number of columns and rows of the given gridpane and create new
     * image view with the same size of the given gridpane, then inside the loop
     * goes and sets the image according to the given object values. when the
     * image is set to image view, then it is added to the grid. In last part,
     * for every indivitual object of the level maker one method is created to
     * set the images.
     *
     * @param leveObj is the json data for each level
     * @param grid is the grid pane for the player
     * @param level is the current level
     * @param imgVw is the image view for player board
     * @param group is the group array for the player
     */
    private void setDataToImgVw(LevelMaker leveObj, GridPane grid, int level, ImageView[][] imgVw, Group[][] group) {
        int colcount = grid.getColumnConstraints().size();
        int rowcount = grid.getRowConstraints().size();
        imgVw = new ImageView[colcount][rowcount];
        for (int i = 0; i < rowcount - 1; i++) {//8
            for (int j = 1; j < colcount; j++) {//10
                Image img = null;
                int id = 0;
                if (leveObj.getField()[i][j - 1] != null) {
                    if (null != leveObj.getField()[i][j - 1]) {
                        switch (leveObj.getField()[i][j - 1]) {
                            case 0:
                                img = new Image(path + "crossed" + type);
                                id = 0;
                                break;
                            case 1:
                                img = new Image(path + "bOne" + type);
                                id = 1;
                                break;
                            case 2:
                                img = new Image(path + "bTwo" + type);
                                id = 2;
                                break;
                            case 3:
                                img = new Image(path + "bThree" + type);
                                id = 3;
                                break;
                            case 4:
                                img = new Image(path + "bFour" + type);
                                id = 4;
                                break;
                            case 5:
                                img = new Image(path + "bFive" + type);
                                id = 5;
                                break;
                            case 6:
                                img = new Image(path + "bSix" + type);
                                id = 6;
                                break;
                            default:
                                break;
                        }
                    }
                    imgVw[j][i] = new ImageView();
                    imgVw[j][i].setImage(img);
                    group[j][i].getChildren().add(imgVw[j][i]);//add the image to the group

                    imgVw[j][i].fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
                    imgVw[j][i].fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
                } else {//if cells are null
                    Pane p = new Pane();
                    grid.add(p, j, i);
                    p.prefWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
                    p.prefWidthProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
                }
            }
        }
        setJewels(leveObj, grid, group);//sets the jewel images into the board
        setBombs(leveObj, grid, group);//sets the bomb images into board
        setPuzzles(leveObj, grid, group);//sets the puzzle images into board
        setKeys(leveObj, grid, group);//sets the key images into board
        setFlag(leveObj, grid, group);//sets the flag images into board
        setRocket(leveObj, grid, group);//sets the rocket images into board
        setPlanet(leveObj, grid, group);//sets the planet images into board
        setHorizentalAndVerticalImgs(leveObj, grid, level, group);//sets the horizontal lines images into board
    }

    /**
     * this method sets the required images for the jewel , here we have three
     * different color of jewels , red with point 3, yellow with point 2 and
     * blue with point 1. inside two loop it gets the point and position of the
     * jewels and set the suitable image for each of them. an finally, it calls
     * addImagesToGrid method to set the image into grid.
     *
     * @param leveObj is the an instance of the levelmaker
     * @param grid is the given grid pane
     * @param group is the given group array
     */
    private void setJewels(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        for (int i = 0; i < leveObj.getJewels().length; i++) {
            if (leveObj.getJewels()[i].getPositions().length > 0) {
                for (int j = 0; j < leveObj.getJewels()[i].getPositions().length; j++) {
                    Image img = null;
                    switch (leveObj.getJewels()[i].getPoints()) {
                        case 3:
                            img = new Image(path + "jewelRed" + type);
                            break;
                        case 2:
                            img = new Image(path + "jewelYellow" + type);
                            break;
                        case 1:
                            img = new Image(path + "jewelBlue" + type);
                            break;
                        default:
                            break;
                    }
                    addImagesToGrid(grid, imgVw, leveObj.getJewels()[i].getPositions()[j], img, group);
                }
            }
        }
    }

    /**
     * sets the image of bomb. It gets the position of the bomb from the given
     * object and then checks if we have position then inside the loops goes and
     * sets the image of bomb. In last step call addImagesToGrid to set the
     * image in given position in grid.
     *
     * @param leveObj
     * @param grid
     */
    private void setBombs(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        if (leveObj.getBombs().getPositions().length > 0) {
            for (int j = 0; j < leveObj.getBombs().getPositions().length; j++) {
                Image img = new Image(path + "bomb2" + type);
                addImagesToGrid(grid, imgVw, leveObj.getBombs().getPositions()[j], img, group);
            }
        }
    }

    /**
     * this method sets the required images for the puzzles , here we have two
     * different color of puzzles , blue with point 10 and green with point 15.
     * inside two loop it gets the point and position of the puzzles and set the
     * suitable image for each of them. an finally, it calls addImagesToGrid
     * method to set the image into grid.
     *
     * @param leveObj
     * @param grid
     */
    private void setPuzzles(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        for (int i = 0; i < leveObj.getPuzzles().length; i++) {
            if (leveObj.getPuzzles()[i].getPositions().length > 0) {
                for (int j = 0; j < leveObj.getPuzzles()[i].getPositions().length; j++) {
                    Image img = null;
                    if (leveObj.getPuzzles().length == 1) {
                        img = new Image(path + "puzzleBlue" + type);
                    } else { // we have more than one type puzzle
                        switch (i) {
                            case 0:
                                img = new Image(path + "puzzleGreen" + type);
                                break;
                            case 1:
                                img = new Image(path + "puzzleBlue" + type);
                                break;
                            default:
                                break;
                        }
                    }
                    addImagesToGrid(grid, imgVw, leveObj.getPuzzles()[i].getPositions()[j], img, group);
                }
            }
        }
    }

    /**
     * this method sets the required images for the keys. they are in two
     * colors, yellow for the first key and blue for others. inside two loop it
     * gets the keys and position of the keys and set the suitable image for
     * each of them. an finally, it calls addImagesToGrid method to set the
     * image into grid.At the end add the image of keys to the given group array
     *
     * @param leveObj
     * @param grid
     */
    private void setKeys(LevelMaker leveObj, GridPane grid, Group[][] group) {
        assert (leveObj.getKeys() != null);
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        for (int i = 0; i < leveObj.getKeys().length; i++) {
            Image img = null;
            if (i == 0) {
                img = new Image(path + "keyYellow" + type);
            } else {
                img = new Image(path + "keyBlue" + type);
            }
            addImagesToGrid(grid, imgVw, leveObj.getKeys()[i].getPosition(), img, group);
            if (leveObj.getKeys()[i].getHoles().length > 0) {
                for (int j = 0; j < leveObj.getKeys()[i].getHoles().length; j++) {
                    Image img2 = null;
                    if (i == 0) {
                        img2 = new Image(path + "keyholeYellow" + type);
                    } else {
                        img2 = new Image(path + "keyholeBlue" + type);
                    }
                    addImagesToGrid(grid, imgVw, leveObj.getKeys()[i].getHoles()[j], img2, group);
                }
            }
        }
    }

    /**
     * sets the flag image for the boards. it gets the position of the flag from
     * the given object, then it sets the image to given position.At the end add
     * the image of flag to the given group array
     *
     * @param leveObj
     * @param grid
     */
    private void setFlag(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        if (leveObj.getFlag() != null) {
            Image img = new Image(path + "flagBlue" + type);
            addImagesToGrid(grid, imgVw, leveObj.getFlag().getPosition(), img, group);
        }
    }

    /**
     * sets the rocket image for the boards. it gets the position of the rocket
     * from the given object, then it sets the image to given position. At the
     * end add the image of rocket to the given group array
     *
     * @param leveObj
     * @param grid
     */
    private void setRocket(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        if (leveObj.getRocket() != null) {
            Image img = new Image(path + "rocket" + type);
            addImagesToGrid(grid, imgVw, leveObj.getRocket(), img, group);
        }
    }

    /**
     * sets the planet image for the board. it gets the position of the planet
     * from the given object, then it sets the image to given position. At the
     * end add the image of planet to the given group array
     *
     * @param leveObj
     * @param grid
     */
    private void setPlanet(LevelMaker leveObj, GridPane grid, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        if (leveObj.getPlanet() != null) {
            Image img = new Image(path + "planet" + type);
            addImagesToGrid(grid, imgVw, leveObj.getPlanet(), img, group);
        }
    }

    /**
     * sets the horizontal and vertical point images to the board. it gets the
     * position of the horizontal point from the given object, then it sets the
     * image to given position (for vertical also). At first add the image to
     * the given group and then Add that to the given grid pane.
     *
     * @param leveObj
     * @param grid
     */
    private void setHorizentalAndVerticalImgs(LevelMaker leveObj, GridPane grid, int level, Group[][] group) {
        ImageView[][] imgVw = new ImageView[grid.getColumnConstraints().size()][grid.getRowConstraints().size()];
        Image img = null;
        Position pos = null;
        switch (level) {
            case 1://each level has different horizonta land vertial point images
                img = new Image(path + "15Right" + type);
                //go through the horizontal positions and sets the image of horizonal
                for (int i = 0; i < leveObj.getHorizontalLines().length; i++) {
                    img = new Image(path + "10Right" + type);
                    addHorizontalToGrid(grid, imgVw, leveObj.getHorizontalLines()[i].getPositions()[0], img, group);
                }
                //go through the horizontal positions and according to their points set the image
                for (int i = 0; i < leveObj.getVerticalLines().length; i++) {
                    if (leveObj.getVerticalLines()[i].getPoints() == 5) {
                        img = new Image(path + "5Up" + type);
                    } else {
                        img = new Image(path + "10Up" + type);
                    }
                    addVerticalToGrid(grid, imgVw, leveObj.getVerticalLines()[i].getPositions()[1], img, group);
                }
                break;
            case 2:
                img = new Image(path + "10Right" + type);
                //go through the horizontal positions and according to their points set the image
                for (int i = 0; i < leveObj.getHorizontalLines().length; i++) {
                    if (leveObj.getHorizontalLines()[i].getPoints() == 15) {//if the current point is 15
                        img = new Image(path + "15Right" + type);
                        pos = leveObj.getHorizontalLines()[i].getPositions()[0];
                    } else {
                        pos = new Position(leveObj.getHorizontalLines()[i].getPositions()[1].getX() + 2,
                                leveObj.getHorizontalLines()[i].getPositions()[1].getY());
                        img = new Image(path + "5Left" + type);
                    }
                    addHorizontalToGrid(grid, imgVw, pos, img, group);
                }
                //go through the vertical positions and according to their points set the image
                for (int i = 0; i < leveObj.getVerticalLines().length; i++) {
                    if (leveObj.getVerticalLines()[i].getPoints() == 5) {
                        img = new Image(path + "5Up" + type);
                    } else {
                        img = new Image(path + "10Up" + type);
                    }
                    addVerticalToGrid(grid, imgVw, leveObj.getVerticalLines()[i].getPositions()[1], img, group);
                }
                break;
            case 3:
                img = new Image(path + "5Left" + type);
                //go through the horizontal positions and according to their points set the image
                for (int i = 0; i < leveObj.getHorizontalLines().length; i++) {
                    if (i == 0 || leveObj.getHorizontalLines()[i].getPoints() == 3) {
                        if (leveObj.getHorizontalLines()[i].getPoints() == 3) {
                            //img = new Image("gui/img/threeRight.png");
                        } else {
                            img = new Image(path + "10Right" + type);
                        }
                        pos = leveObj.getHorizontalLines()[i].getPositions()[0];
                    } else {
                        pos = new Position(leveObj.getHorizontalLines()[i].getPositions()[1].getX() + 2,
                                leveObj.getHorizontalLines()[i].getPositions()[1].getY());
                        img = new Image(path + "5Left" + type);
                    }
                    addHorizontalToGrid(grid, imgVw, pos, img, group);
                }
                //in this loop sets the image of five up and ten up to their positions
                for (int i = 0; i < leveObj.getVerticalLines().length; i++) {
                    if (leveObj.getVerticalLines()[i].getPoints() == 5) {
                        img = new Image(path + "5Up" + type);
                    } else {
                        img = new Image(path + "10Up" + type);
                    }
                    addVerticalToGrid(grid, imgVw, leveObj.getVerticalLines()[i].getPositions()[1], img, group);
                }

                break;
            default:
                break;
        }
    }

    /**
     * creates new image view with the given position, creates group for old
     * image and the given image and add them into the given grid. Before adding
     * the width and height of each image binds to the grid.At the end groups
     * the images
     *
     * @param grid is the given grid pane
     * @param imgVw is the given image view array
     * @param pos is the given position to show
     * @param img is the image to set
     * @param groupArr is the group array which the image should add to this
     * group
     */
    private void addImagesToGrid(GridPane grid, ImageView[][] imgVw, Position pos, Image img, Group[][] groupArr) {

        int colcount = grid.getColumnConstraints().size();
        int rowcount = grid.getRowConstraints().size();
        imgVw[pos.getX() + 1][pos.getY()] = new ImageView();
        ImageView imgVw1 = new ImageView(imgVw[pos.getX() + 1][pos.getY()].getImage());

        imgVw[pos.getX() + 1][pos.getY()].setImage(img);
        ImageView imgVw2 = new ImageView(img);
        //set width and height property of the image
        imgVw1.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw1.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
        imgVw2.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw2.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
        //groups the images together
        imgVw2.setBlendMode(BlendMode.MULTIPLY);
        groupArr[pos.getX() + 1][pos.getY()].getChildren().add(imgVw1);
        groupArr[pos.getX() + 1][pos.getY()].getChildren().add(imgVw2);
        groupArr[pos.getX() + 1][pos.getY()].setStyle(" -fx-background-color: red \n; -fx-box-border: red ;");

    }

    /**
     * creates new image view with the given position, creates group for old
     * image and the given image and add them into the given grid. Before adding
     * the width and height of each image binds to the grid.
     *
     * @param grid is the given grid pane
     * @param imgVw is the given image view array
     * @param pos is the given position to show
     * @param img is the image to set
     * @param groupArr is the group array which the image should add to this
     * group
     */
    private void addHorizontalToGrid(GridPane grid, ImageView[][] imgVw, Position pos, Image img, Group[][] groupArr) {
        int colcount = grid.getColumnConstraints().size();
        int rowcount = grid.getRowConstraints().size();
        imgVw[pos.getX()][pos.getY()] = new ImageView();
        ImageView imgVw1 = new ImageView(imgVw[pos.getX()][pos.getY()].getImage());
        //Position temp = getGroupIndex(pos, groupArr);
        imgVw[pos.getX()][pos.getY()].setImage(img);
        ImageView imgVw2 = new ImageView(img);
        //set width and height property of the image
        imgVw1.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw1.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));

        imgVw2.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw2.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
        //groups the images
        imgVw2.setBlendMode(BlendMode.MULTIPLY);
        groupArr[pos.getX()][pos.getY()].getChildren().add(imgVw1);
        groupArr[pos.getX()][pos.getY()].getChildren().add(imgVw2);

    }

    /**
     * creates new image view with the given position, creates group for old
     * image and the given image and add them into the given grid. Before adding
     * the width and height of each image binds to the grid.
     *
     * @param grid is the given grid pane
     * @param imgVw is the given image view array
     * @param pos is the given position to show
     * @param img is the image to set
     * @param groupArr is the group array which the image should add to this
     * group
     */
    private void addVerticalToGrid(GridPane grid, ImageView[][] imgVw, Position pos, Image img, Group[][] groupArr) {
        int colcount = grid.getColumnConstraints().size();
        int rowcount = grid.getRowConstraints().size();
        imgVw[pos.getX() + 1][pos.getY() + 1] = new ImageView();
        ImageView imgVw1 = new ImageView(imgVw[pos.getX() + 1][pos.getY() + 1].getImage());

        imgVw[pos.getX() + 1][pos.getY() + 1].setImage(img);
        ImageView imgVw2 = new ImageView(img);
        //set width and height property of the image
        imgVw1.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw1.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));

        imgVw2.fitWidthProperty().bind(grid.widthProperty().divide(colcount).subtract(grid.getHgap()));
        imgVw2.fitHeightProperty().bind(grid.heightProperty().divide(rowcount).subtract(grid.getVgap()));
        //groups the images 
        imgVw2.setBlendMode(BlendMode.MULTIPLY);
        groupArr[pos.getX() + 1][pos.getY() + 1].getChildren().add(imgVw1);
        groupArr[pos.getX() + 1][pos.getY() + 1].getChildren().add(imgVw2);

    }

    @Override
    public void setDicesInGrid(List<Integer> dices) {

        int colcount = grdDice.getColumnConstraints().size();
        int rowcount = grdDice.getRowConstraints().size();
        imgVwsDices = new ImageView[colcount][rowcount];
        this.grdDice.getChildren().clear();
        //go though the dice list and sets the image dice to the iamge view od dices and then add the image view to the grid pane of dices
        for (int i = 0; i < dices.size(); i++) {
            Image img = getDiceImg(dices.get(i));
            int j = i / colcount;
            int k = i % colcount;
            imgVwsDices[k][j] = new ImageView();
            imgVwsDices[k][j].setImage(img);
            grdDice.add(imgVwsDices[k][j], k, j);
            imgVwsDices[k][j].fitWidthProperty().bind(grdDice.widthProperty().divide(colcount).subtract(grdDice.getHgap()));
            imgVwsDices[k][j].fitHeightProperty().bind(grdDice.heightProperty().divide(rowcount).subtract(grdDice.getVgap()));
        }
    }

    /**
     * gets the dice image which is matched with the given value
     *
     * @param value is the value of dice
     * @return
     */
    private Image getDiceImg(int value) {
        Image img = null;
        switch (value) {
            case 1:
                img = new Image(path + "dOne" + type);
                break;
            case 2:
                img = new Image(path + "dTwo" + type);
                break;
            case 3:
                img = new Image(path + "dThree" + type);
                break;
            case 4:
                img = new Image(path + "dFour" + type);
                break;
            case 5:
                img = new Image(path + "dFive" + type);
                break;
            case 6:
                img = new Image(path + "dSix" + type);
                break;
            default:
                break;
        }
        return img;
    }

    /**
     * add the dice image of the given value in the given position into the
     * given id player board. Before adding the image add that to the related
     * group and then added to the grid pane of the player
     *
     * @param pos is the position which the dice should lay
     * @param value is the value of dice to lay
     * @param id is the id of player
     */
    @Override
    public void addDiceToClickedPos(Position pos, int value, int id) {

        int colcount = getCurrGridPane(id).getColumnConstraints().size();
        int rowcount = getCurrGridPane(id).getRowConstraints().size();

        ImageView imgVw = new ImageView();
        Image img = getDiceImg(value);
        imgVw.setImage(img);
        getCurrGroupArray(id)[pos.getX() + 1][pos.getY()].getChildren().add(imgVw);
        imgVw.fitWidthProperty().bind(getCurrGridPane(id).widthProperty().divide(colcount).subtract(getCurrGridPane(id).getHgap()));
        imgVw.fitHeightProperty().bind(getCurrGridPane(id).heightProperty().divide(rowcount).subtract(getCurrGridPane(id).getVgap()));
    }

    @Override
    public void removeDiceFromBoard(Position pos, int id) {
        getCurrGroupArray(id)[pos.getX() + 1][pos.getY()].getChildren().remove(getCurrGroupArray(id)[pos.getX() + 1][pos.getY()].getChildren().size() - 1);
    }

    /**
     * gets the grid pane of the given id
     *
     * @param id is the id player
     * @return
     */
    private GridPane getCurrGridPane(int id) {
        GridPane temp;
        switch (id) {
            case 0:
                temp = grdHuman;
                break;
            case 1:
                temp = grdA1;
                break;
            case 2:
                temp = grdA2;
                break;
            case 3:
                temp = grdA3;
                break;
            default:
                temp = grdHuman;
        }
        return temp;
    }

    /**
     * gets the group array of the given id
     *
     * @param id is the id of player
     * @return
     */
    private Group[][] getCurrGroupArray(int id) {
        Group[][] temp;
        switch (id) {
            case 0:
                temp = groupHuman;
                break;
            case 1:
                temp = groupA1;
                break;
            case 2:
                temp = groupA2;
                break;
            case 3:
                temp = groupA3;
                break;
            default:
                temp = groupHuman;
        }
        return temp;
    }

    /**
     * gets the pane of the given id
     *
     * @param id is the id of player
     * @return
     */
    private Pane getCurrPane(int id) {

        switch (id) {
            case 0:
                return humanPane;
            case 1:
                return paneA1;
            case 2:
                return paneA2;
            case 3:
                return paneA3;
            default:
                return humanPane;
        }

    }

    /**
     * Highlights two cells (= place for a domino) on the game grid in red. I
     * have got help from last assignment
     *
     * @param pos position of the top-left half of the domino.
     */
    @Override
    public void highlightPos(Position pos) {
        ColorAdjust changeToGreen = new ColorAdjust();
        changeToGreen.setHue(0.75);
        changeToGreen.setSaturation(1.0);
        changeToGreen.setBrightness(0.5);
        addEffectToDicePos(pos, changeToGreen, groupHuman);
    }

    /**
     * Removes the highlight of a domino position on the game grid. I have got
     * help from last assignment
     *
     * @param pos position of the top-left half of the domino.
     */
    @Override
    public void removeHighlightPos(Position pos) {
        addEffectToDicePos(pos, null, groupHuman);
    }

    /**
     * add effect to given position with given effect
     *
     * @param pos is the given position to de displayed the effect
     * @param effect is the given effect
     */
    private void addEffectToDicePos(Position pos, ColorAdjust effect, Group[][] GrdPane) {

        Node result = null;
        for (int i = 0; i < GrdPane.length; i++) {
            for (int j = 0; j < GrdPane[i].length; j++) {
                //go through the childern of the grid pane and add effect to them
                for (int k = 0; k < GrdPane[i][j].getChildren().size(); k++) {
                    Node child = GrdPane[i][j].getChildren().get(k);
                    if (child.isManaged()) {
                        if (i == pos.getX() + 1 && j == pos.getY()) {
                            result = child;
                            result.setStyle(" -fx-background-color: white ;\n");

                            result.setEffect(effect);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void putCrossedInFilledPos(Position pos, int id) {
        int colcount = getCurrGridPane(id).getColumnConstraints().size();
        int rowcount = getCurrGridPane(id).getRowConstraints().size();
        ImageView imgVw = new ImageView();

        imgVw.setImage(null);
        Image img = new Image(path + "crossed" + type);
        imgVw.setImage(img);
        getCurrGroupArray(id)[pos.getX() + 1][pos.getY()].getChildren().add(imgVw);
        imgVw.fitWidthProperty().bind(getCurrGridPane(id).widthProperty().divide(colcount).subtract(getCurrGridPane(id).getHgap()));
        imgVw.fitHeightProperty().bind(getCurrGridPane(id).heightProperty().divide(rowcount).subtract(getCurrGridPane(id).getVgap()));
    }

    @Override
    public void putExplodedPic(Position pos, int id) {
        int colcount = getCurrGridPane(id).getColumnConstraints().size();
        int rowcount = getCurrGridPane(id).getRowConstraints().size();
        ImageView imgVw = new ImageView();
        imgVw.setImage(null);
        Image img = new Image(path + "exploded" + type);
        imgVw.setImage(img);
        getCurrGroupArray(id)[pos.getX() + 1][pos.getY()].getChildren().add(imgVw);
        imgVw.fitWidthProperty().bind(getCurrGridPane(id).widthProperty().divide(colcount).subtract(getCurrGridPane(id).getHgap()));
        imgVw.fitHeightProperty().bind(getCurrGridPane(id).heightProperty().divide(rowcount).subtract(getCurrGridPane(id).getVgap()));
    }

    @Override
    public void displayProtocol(List<String> states) {
        String s = " ";
        for (int i = 0; i < states.size(); i++) {
            s += states.get(i);
        }
        this.logArea.setText(s);
        this.logArea.selectPositionCaret(this.logArea.getLength());
        this.logArea.deselect();
    }

    @Override
    public void disableDropAndRollBtn(boolean canPlay) {
        this.lblRoll.setDisable(canPlay);
        this.lblDropOut.setDisable(canPlay);
    }

    /**
     * sets the big pen image on the board with the given id
     *
     * @param idPlayer
     */
    private void setPenOnBoard(int idPlayer) {
        getImgVwDropouById(idPlayer).fitWidthProperty().bind(getCurrPane(idPlayer).widthProperty());
        getImgVwDropouById(idPlayer).fitHeightProperty().bind(getCurrPane(idPlayer).heightProperty());
        getCurrPane(idPlayer).setBackground(Background.EMPTY);
        getImgVwDropouById(idPlayer).setBlendMode(BlendMode.MULTIPLY);
        getCurrPane(idPlayer).getChildren().add(getImgVwDropouById(idPlayer));
    }

    /**
     * This method is returning the image view of player based on given id
     *
     * @param player is the id of player
     * @return Image view of Pen for given player
     */
    private ImageView getImgVwDropouById(int player) {
        switch (player) {
            case 0:
                return imgVwPen[0];
            case 1:
                return imgVwPen[1];
            case 2:
                return imgVwPen[2];
            default:
                return imgVwPen[3];
        }
    }

    @Override
    public void removePenFromBoard(int idPlayer) {
        getCurrPane(idPlayer).getChildren().remove(getImgVwDropouById(idPlayer));
    }

    @Override
    public void showPenOnBoard(int idPlayer) {
        setPenOnBoard(idPlayer);
    }

    @Override
    public void clear() {
        clearCurrentGroup(1);
        clearCurrentGroup(2);
        clearCurrentGroup(3);
        clearCurrentGroup(0);
        humanPane.getChildren().remove(imgVwPen);
        paneA1.getChildren().remove(imgVwPen);
        paneA2.getChildren().remove(imgVwPen);
        paneA3.getChildren().remove(imgVwPen);

        this.logArea.setText(" ");

        // clear all childern of pane
        for (int i = 0; i < imgVwPen.length; i++) {
            getCurrPane(i).getChildren().remove(getImgVwDropouById(i));
        }
        // clearPointTableLabels();//clear all point table lables
        grdPoints.getChildren().clear();
        grdHuman.setDisable(false);
    }

    /**
     * clear the current group image array
     *
     * @param idx
     */
    private void clearCurrentGroup(int idx) {
        for (int i = 0; i < getCurrGroupArray(idx).length; i++) {
            for (int j = 0; j < getCurrGroupArray(idx)[i].length; j++) {
                getCurrGroupArray(idx)[i][j].getChildren().clear();
            }
        }
    }

    @Override
    public void gameWon(List<Integer> winnerIds) {

        winnerScore = winnerIds.get(0);
        String text = " ";
        String type = " ";
        if (winnerIds.get(1) == 0) {
            type = "Human";
        } else {
            type = "Bot";
        }

        if (winnerIds.size() == 2) {
            text = "game is ended  and winner is " + type + winnerIds.get(1) + ", with score : " + winnerIds.get(0);
        } else {
            text = "game is ended  " + ", with score : " + winnerIds.get(0) + " Winner are ";
            for (int i = 1; i < winnerIds.size(); i++) {
                text += winnerIds.get(i) + ",";
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(text);
        alert.showAndWait();
        grdHuman.setDisable(true);
        this.lblRoll.setDisable(true);
        this.lblDropOut.setDisable(true);
    }

    @Override
    public void addCounter(Types type, int point) {

        if (type == Types.JewelRed) {
            incCounterType(point, lblPoints[0]);
        }
        if (type == Types.HORIZONTALLINES) {
            incCounterType(point, lblPoints[1]);
        }
        if (type == Types.VERTICALLINES) {
            incCounterType(point, lblPoints[2]);
        }
        if (type == Types.PuzzleBlue) {
            incCounterType(point, lblPoints[3]);
        }
        if (type == Types.BOMBS) {
            incCounterType(point, lblPoints[4]);
        }
        if (type == Types.JewelYellow) {
            incCounterType(point, lblPoints[5]);
        }
        if (type == Types.PuzzleGreen || type == Types.JewelBlue) {
            incCounterType(point, lblPoints[6]);
        }
        if (type == Types.FLAG) {
            incCounterType(point, lblPoints[7]);
        }

    }

    /**
     * sets the given images to the given coordinate to the point table
     *
     * @param path is the path of the image
     * @param x is the x coordinate
     * @param y is the y coordinate
     */
    private void setImageToPointTable(String path, int x, int y) {
        int colcount = grdPoints.getColumnConstraints().size();
        int rowcount = grdPoints.getRowConstraints().size();
        ImageView imgVw1 = new ImageView();
        imgVw1.setImage(new Image(path));
        imgVw1.setStyle("-fx-border-color: black");
        imgVw1.fitWidthProperty().bind(grdPoints.widthProperty().divide(colcount).subtract(grdPoints.getHgap()));
        imgVw1.fitHeightProperty().bind(grdPoints.heightProperty().divide(rowcount).subtract(grdPoints.getVgap()));
        grdPoints.add(imgVw1, x, y);
    }

    /**
     * send the images of the point table to the setImageToPointTable method to
     * set the images into their coordinates. Each level has different point
     * table images(elements)
     */
    @Override
    public void fillPointTable(int levelNumber) {
        this.levelNum = levelNumber;
        this.level.setText(levelNum + "");//sets the level number
        //fill the point table with related images for each level
        setImageToPointTable("gui/img/jewelRed.png", 0, 0);
        setImageToPointTable("gui/img/r.png", 0, 1);
        setImageToPointTable("gui/img/u.png", 0, 2);
        setImageToPointTable("gui/img/puzzleBlue.png", 0, 3);
        setImageToPointTable("gui/img/exploded.png", 0, 4);
        setImageToPointTable("gui/img/total.png", 0, 5);
        if (levelNumber == 2) {
            setImageToPointTable("gui/img/diamond.png", 0, 5);
            setImageToPointTable("gui/img/puzzleGreen.png", 0, 6);
            setImageToPointTable("gui/img/total.png", 0, 7);
        } else if (levelNumber == 3) {
            setImageToPointTable("gui/img/jewelYellow.png", 0, 5);
            setImageToPointTable("gui/img/jewelBlue.png", 0, 6);
            setImageToPointTable("gui/img/flagBlue.png", 0, 7);
            setImageToPointTable("gui/img/total.png", 0, 8);
        }
        int size;//length of the point table 
        switch (levelNumber) {
            case 1:
                size = 6;
                break;
            case 2:
                size = 8;
                break;
            default:
                size = 9;
                break;
        }
        setScroreOfEachSymbol(size);//sets the score or point for each symbols for example sets the +3 for jewel red
        makesLabelsForPoint(size);//sets the labels whenever the earned point is upadated, the point is displayed in this label
    }

    /**
     * create an array of labels for showing the gained score for each symbol,
     * and add it to one column of the grid pane point table
     *
     * @param size
     */
    private void makesLabelsForPoint(int size) {

        lblPoints = new Label[size];

        for (int i = 0; i < lblPoints.length; i++) {

            lblPoints[i] = new Label("" + 0);
            lblPoints[i].minWidthProperty().bind(grdPoints.widthProperty().divide(6.3).multiply(2.0));
            lblPoints[i].minHeightProperty().bind(grdPoints.heightProperty().divide(
                    grdPoints.getRowConstraints().size()));

            lblPoints[i].setAlignment(Pos.CENTER);
            lblPoints[i].setStyle("-fx-font-size: 15px");
            lblPoints[i].setStyle("-fx-border-color: black");
            grdPoints.add(lblPoints[i], 2, i);
        }
    }

    /**
     * create an array of labels and add it to one column of the grid pane point
     * table. sets the label text manually for each level
     */
    private void setScroreOfEachSymbol(int size) {

        Label[] lblScores = new Label[size];

        for (int i = 0; i < lblScores.length; i++) {
            lblScores[i] = new Label("" + 0);
            lblScores[i].minWidthProperty().bind(grdPoints.widthProperty().divide(6.3).multiply(2.0));
            lblScores[i].minHeightProperty().bind(grdPoints.heightProperty().divide(
                    grdPoints.getRowConstraints().size()));

            lblScores[i].setAlignment(Pos.CENTER);
            lblScores[i].setStyle("-fx-font-size: 18px");
            lblScores[i].setStyle("-fx-border-color: black");
            grdPoints.add(lblScores[i], 1, i);
        }
        lblScores[0].setText("+3");
        lblScores[1].setText("?");
        lblScores[2].setText("?");
        lblScores[3].setText("+10");
        lblScores[4].setText("-2");
        lblScores[5].setText(" ");

        if (size == 8) {//level is two
            lblScores[5].setText("+2");
            lblScores[6].setText("+10");
            lblScores[7].setText(" ");

        } else if (size == 9) {//level is three
            lblScores[3].setText(" ");
            lblScores[3].setText("+15");
            lblScores[5].setText("+2");
            lblScores[6].setText("+1");
            lblScores[7].setText("+10");
            lblScores[8].setText(" ");
        }

    }

    /**
     * increment the given point and set this number to the given label
     *
     * @param point
     * @param lbl
     */
    private void incCounterType(int point, Label lbl) {
        lbl.setText(point + " ");
    }

    @Override
    public void showMsg(int x) {
        String s = "";
        switch (x) {
            case 1:
                s = "The length of the field is not correct";
                break;
            case 2:
                s = "The values of the cells are not correct";
                break;
            case 3:
                s = "The number of jewels in the board is not correct.";
                break;
            case 4:
                s = "Invalid position value of the jewel";
                break;
            case 5:
                s = "Invalid position value of the bomb .";
                break;
            case 6:
                s = "Invalid position value of the puzzle ";
                break;
            case 7:
                s = "Invalid position value of the horizontal lines ";
                break;
            case 8:
                s = "Invalid position value of the vertical lines";
                break;
            case 9:
                s = "The length of the flag array is not correct";
                break;
            case 10:
                s = "Invalid number of players in the loaded file";
                break;
            case 11:
                s = "Invalid round value in the loaded file";
                break;
            case 12:
                s = "Invalid level number in the loaded file";
                break;
            case 13:
                s = "Invalid round value when we have two players";
                break;
            case 14:
                s = "Invalid round value when we have 3 players";
                break;
            case 15:
                s = "Invalid round value when we have 4 players";
                break;
            case 16:
                s = "Invalid TurnOf value in the loaded file";
                break;
            case 17:
                s = "Invalid position values in the Checked array ";
                break;
            case 18:
                s = "Invalid position values in the DiceIn array";
                break;
            default:
                break;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("" + s);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        alert.showAndWait();

    }

    @Override
    public void passTotalScore(int x) {
        winnerScore = x;
        switch (this.levelNum) {
            case 1:
                lblPoints[5].setText(winnerScore + " ");
                break;
            case 2:
                lblPoints[7].setText(winnerScore + " ");
                break;
            default:
                lblPoints[8].setText(winnerScore + " ");
                break;
        }

    }

}
