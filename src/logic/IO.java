package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;
import javafx.stage.FileChooser;

/**
 *
 * this class is responsible for input and output, reading the json file and
 * store it in an object of levelMaker. it also saves the game with given
 * parameters in a json file. and last one is loading the game with given
 * parameters and return the new game.
 *
 * @author Reyhan
 */
public class IO {

    FileOutputStream f = null;
    FileChooser fileChooser = null;//an instance of filechooser
    OutputStreamWriter o = null;
    GUIConnector gui;
    Game game;//an object of the game
    Object obj;
    int errorNum = 0;//is used for the error during reading the json file
    private String path = "logic/levels/Level";//path of the json file
    private String ext = ".json";

    /**
     * main constructor
     *
     * @param gui
     * @throws FileNotFoundException
     */
    public IO(GUIConnector gui) throws FileNotFoundException {
        this.gui = gui;
    }

    IO() {
    }

    /**
     * reads the json file values and it there is no error in the file , store
     * them in an an object otherwise return the error number.
     *
     * @param level
     * @return
     * @throws FileNotFoundException
     */
    public LevelMaker getJsonValues(int level) throws FileNotFoundException {

        Gson gson = new Gson();
        LevelMaker js = null;
        JsonValidation validation;
        InputStreamReader reader;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path + level + ext);
            reader = new InputStreamReader(is);
            js = gson.fromJson(reader, LevelMaker.class);
            validation = new JsonValidation();
            if (validation.validationOfMap(js) != 0) {//if the json file have error
                errorNum = validation.validationOfMap(js);
                gui.showMsg(errorNum);
                System.exit(0);
            }
        } catch (com.google.gson.JsonParseException e) {

            System.exit(0);
        }
        return js;
    }

    /**
     * open the file choose to choose the location and the file name to save.
     * Then creates an object of save and store the given parameters into this
     * object.
     *
     *
     * @param players is the players array
     * @param levelNO is the level number
     * @param idxPlayer is the id of player
     * @param round is the current round
     * @param dices is the list of dices
     */
    void save(Player[] players, int levelNO, int idxPlayer, int round, List<Integer> dices) {

        Save saveObj = new Save();
        int[] diceArray = new int[dices.size()];
        saveObj.setLevelNo(levelNO);
        saveObj.setRound(round);
        saveObj.setTurnOf(idxPlayer);
        Players[] savePlayers = new Players[players.length];
        for (int i = 0; i < diceArray.length; i++) {
            diceArray[i] = dices.get(i);
        }
        saveObj.setDice(diceArray);//save the dice list into the save object
        for (int i = 0; i < savePlayers.length; i++) {
            savePlayers[i] = new Players();
            savePlayers[i].setActive(!players[i].getDropOut());
            //fill the checked array for each player and then set it to the players object checked
            Position[] checked = new Position[players[i].getBoard().getCrossedList().size()];
            for (int j = 0; j < players[i].getBoard().getCrossedList().size(); j++) {
                checked[j] = players[i].getBoard().getCrossedList().get(j);
            }
            savePlayers[i].setChecked(checked);
            // fill the dice on array for each player and then set it to the players object diceOn
            Position[] diceOn = new Position[players[i].getBoard().getFilledList().size()];
            for (int j = 0; j < players[i].getBoard().getFilledList().size(); j++) {
                diceOn[j] = players[i].getBoard().getFilledList().get(j);
            }
            savePlayers[i].setDiceOn(diceOn);
            //fill the exploded array for each player and then set it to the players object dexploded
            Position[] exploded = new Position[players[i].getBoard().getExplodedPos().size()];
            for (int j = 0; j < players[i].getBoard().getExplodedPos().size(); j++) {
                exploded[j] = players[i].getBoard().getExplodedPos().get(j);
            }
            savePlayers[i].setExploded(exploded);
        }

        saveObj.setPlayers(savePlayers);
        /**
         * ***************************************************************
         */
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.setTitle("Choose location To Save Report");
        File selectedFile = null;
        if (selectedFile == null) {
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".json", ".json"),
                    new FileChooser.ExtensionFilter(".txt", ".txt"));
            selectedFile = chooser.showSaveDialog(null);
        }
        if (selectedFile != null) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(selectedFile.getPath())) {
                gson.toJson(saveObj, writer);
                writer.flush();
            } catch (IOException e) {
            }
        }
    }

    /**
     * read the file and convert each line of file to symbol/
     *
     * @param gui
     * @param game
     * @return
     * @throws IOException
     */
    public Game loadFile(GUIConnector gui, Game game) throws IOException {
        this.gui = gui;
        this.game = game;
        String line = "";
        String fileString = "";
        FileReader fr = null;
        BufferedReader br = null;
        InputStreamReader sr = null;
        Scanner sc = null;
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.setTitle("Choose your load file");
        File selectedFile = null;
        if (selectedFile == null) {
            selectedFile = chooser.showOpenDialog(null);
        }
        try {
            if (selectedFile != null) {
                fr = new FileReader(selectedFile.getPath());
                sr = new InputStreamReader(System.in);
                // work
                br = new BufferedReader(fr);
                sc = new Scanner(sr);
                while ((line = br.readLine()) != null) {
                    fileString = fileString + line + "\n";
                }
            }
        } finally {
            // close
            if (sc != null) {
                sc.close();
            }
            if (br != null) {
                br.close();
            }
            if (sr != null) {
                sr.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        return selectedFile != null ? loadAllFileString(fileString) : this.game;
    }

    /**
     * convert the given string to board by splitting each line.
     *
     * @param fileString is the given file to convert
     * @return a new game with all elements inside the converted board
     */
    private Game loadAllFileString(String fileString) throws IOException {
        JsonValidation loadValidation = new JsonValidation();
        Gson gson = new Gson();
        Save obj = gson.fromJson(fileString, Save.class);
        if (loadValidation.loadErrorHandling(gui, obj) == 0) {
            int round = obj.getRound();
            int level = obj.getLevelNo();
            this.gui.clear();
            this.game = new Game(0, level, obj.getPlayers(), round, obj.getTurnOf(), obj.getDice(), gui);

        } else {
            //if the laoded file has format problem then in the gui shows the error
            
            errorNum = loadValidation.loadErrorHandling(gui, obj);
            gui.showMsg(errorNum);
        }
        return this.game;
    }

}
