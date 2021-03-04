/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

/**
 * For traceability, one log file should be written per game. Some important
 * information are stored here such as, number of detectives, current position
 * of each player and previous position, remaining tickets, starting points, AI
 * value and so on.
 *
 * @author Kobra
 */
public class Log {

    FileOutputStream f;
    OutputStreamWriter o;
    String toprint = "";
    String startValues = "";
    Player[] players;
    String endGame = "";
    List<String> logLists = new LinkedList<>();

    /**
     * default constructor
     *
     * @param s
     */
    public Log(String s) {
        try {
            URL url = IO.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            String parentPath = new File(jarPath).getParentFile().getPath();
            this.f = new FileOutputStream(parentPath + "/" + s);
            this.o = new OutputStreamWriter(this.f);
        } catch (IOException ex) {

        }
    }

    /**
     * There is an area in the gui which user can easily trace the game. Every
     * action is written in the log area. The information like the position of
     * laid die and the type of the cell is written there
     *
     * @param players is array of players
     * @param idxCurrPlayer is the id of current player
     */
    public void logging(Player[] players, int idxCurrPlayer) {
        try {
            String type = " ";
            if (players[idxCurrPlayer].getDropOut()) {
                toprint = players[idxCurrPlayer].getType() + " with id " + idxCurrPlayer + " is dropped out  \n";
            } else {
                if (players[idxCurrPlayer].getBoard().getBoard()[players[idxCurrPlayer].
                        getBoard().getLaidDice().getX()][players[idxCurrPlayer].getBoard().getLaidDice().getY()].getType() != Types.noType) {
                    type = " with type of " + players[idxCurrPlayer].getBoard().getBoard()[players[idxCurrPlayer].
                            getBoard().getLaidDice().getX()][players[idxCurrPlayer].getBoard().getLaidDice().getY()].getType().toString();

                }
                toprint = players[idxCurrPlayer].getType() + " with id " + idxCurrPlayer + " places dice  "
                        + players[idxCurrPlayer].getBoard().getCellValue(players[idxCurrPlayer].getBoard().getLaidDice()) + " at  "
                        + players[idxCurrPlayer].getBoard().getLaidDice() + " " + type + "\n";
            }
            this.logLists.add(toprint);
            o.append(toprint);
            o.flush();
        } catch (IOException ex) {
        }
    }

    /**
     * this method is written to log the game, when user clicks on the roll
     * button and the remained dices and the id of player is written in the log
     * area.
     *
     * @param idPlayer is the id of player
     * @param dices is a list of dice
     */
    protected void logRollAgain(int idPlayer, List<Integer> dices) {
        String player = " ";
        String dice = " ";
        String roll = " ";
        for (int i = 0; i < dices.size(); i++) {
            dice += dices.get(i).toString() + ", ";
        }
        if (idPlayer == 0) {
            player = playerType.HUMAN.toString();
        } else {
            player = playerType.BOT.toString();
        }
        roll += player + "with id " + idPlayer + " rolls again, new dices are " + dice;
        this.logLists.add(roll);
    }

    /**
     * closing the log file
     */
    public void closeLog() {
        try {
            if (f != null) {
                f.close();
            }
            if (o != null) {
                o.close();
            }
        } catch (IOException ex) {

        }
    }

}
