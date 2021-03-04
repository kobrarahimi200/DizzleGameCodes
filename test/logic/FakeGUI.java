/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.util.List;

/**
 *
 * @author Reyhan
 */
class FakeGUI implements GUIConnector {

    @Override
    public void setLevelData(LevelMaker leveObj, int players, int level) {
    }

    @Override
    public void setDicesInGrid(List<Integer> dices) {
    }

    @Override
    public void highlightPos(Position pos) {
    }

    @Override
    public void removeHighlightPos(Position pos) {
    }

    @Override
    public void putCrossedInFilledPos(Position get, int id) {
    }

    @Override
    public void displayProtocol(List<String> states) {
    }

    @Override
    public void disableDropAndRollBtn(boolean canPlay) {
    }

    @Override
    public void showPenOnBoard(int idPlayer) {
    }

    @Override
    public void addDiceToClickedPos(Position pos, int value, int id) {
    }

    @Override
    public void clear() {
    }

    @Override
    public void removePenFromBoard(int idPlayer) {
    }

    @Override
    public void removeDiceFromBoard(Position pos, int id) {
    }

    @Override
    public void updateLabels(int round, int turn) {
    }

    @Override
    public void putExplodedPic(Position pos, int id) {
    }

    @Override
    public void addCounter(Types types, int point) {
    }

    @Override
    public void showMsg(int x) {
    }

    @Override
    public void gameWon(List<Integer> winnerInfo) {
    }

    @Override
    public void fillPointTable(int levelNumber) {
    }

    @Override
    public void passTotalScore(int x) {
    }

}
