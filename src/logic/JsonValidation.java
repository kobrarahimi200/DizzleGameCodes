/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * validate the given json file value if they have error return a number and
 * send it to the gui to display the error message
 *
 * @author Reyhan
 */
class JsonValidation {

    private int identifierError;//error number 

    /**
     * main constructor
     *
     * @throws JSONException
     */
    public JsonValidation() {

        identifierError = 0;
    }

    /**
     * gets the id of error number
     *
     * @return
     */
    int getIDErrorNumber() {
        return this.identifierError;
    }

    /**
     * return a number corresponding the type of error in the net.sjon file
     *
     * @param obj
     * @return
     */
    int validationOfMap(LevelMaker obj) {

        boolean hasError = false;
        if (obj.getField()[0].length != 9 || obj.getField().length != 7) {
            hasError = true;
            identifierError = 1;
        }
        //checks the field length value
        for (int i = 0; i < obj.getField().length && !hasError; i++) {
            for (int j = 0; j < obj.getField()[i].length && !hasError; j++) {
                if (obj.getField()[i][j] != null) {
                    if (obj.getField()[i][j] < 0 || obj.getField()[i][j] > 7) {
                        hasError = true;
                        identifierError = 2;
                    }
                }

            }
        }

        if (obj.getJewels().length > 3 || obj.getJewels().length < 1) {
            hasError = true;
            identifierError = 3;
        }
        //checks the valid position value for jewel object
        for (int i = 0; i < obj.getJewels().length && !hasError; i++) {
            for (int j = 0; j < obj.getJewels()[i].getPositions().length && !hasError; j++) {
                if (!obj.getJewels()[i].getPositions()[j].equals(null) && !hasError) {
                    if (obj.getJewels()[i].getPositions()[j].getX() > 8 || obj.getJewels()[i].getPositions()[j].getX() < 0
                            || obj.getJewels()[i].getPositions()[j].getY() > 6 || obj.getJewels()[i].getPositions()[j].getY() < 0) {
                        hasError = true;
                        identifierError = 4;
                    }
                }
            }
        }
        //checks the valid position value for bomb object
        for (int i = 0; i < obj.getBombs().getPositions().length && !hasError; i++) {
            if (!obj.getBombs().getPositions()[i].equals(null)) {
                if (obj.getBombs().getPositions()[i].getX() > 8 || obj.getBombs().getPositions()[i].getX() < 0
                        || obj.getBombs().getPositions()[i].getY() > 6 || obj.getBombs().getPositions()[i].getY() < 0) {
                    hasError = true;
                    identifierError = 5;
                }
            }
        }
        //checks the valid position value for puzzle object
        for (int i = 0; i < obj.getPuzzles().length && !hasError; i++) {
            for (int j = 0; j < obj.getPuzzles()[i].getPositions().length && !hasError; j++) {
                if (!obj.getPuzzles()[i].getPositions()[i].equals(null)) {
                    if (obj.getPuzzles()[i].getPositions()[j].getX() > 8 || obj.getPuzzles()[i].getPositions()[j].getX() < 0
                            || obj.getPuzzles()[i].getPositions()[j].getY() > 6 || obj.getPuzzles()[i].getPositions()[j].getY() < 0) {
                        hasError = true;
                        identifierError = 6;
                    }
                }
            }
        }
        //checks the valid position value for horizontal lines object
        for (int i = 0; i < obj.getHorizontalLines().length && !hasError; i++) {
            for (int j = 0; j < obj.getHorizontalLines()[i].getPositions().length && !hasError; j++) {
                if (!obj.getHorizontalLines()[i].getPositions()[j].equals(null)) {
                    if (obj.getHorizontalLines()[i].getPositions()[j].getX() > 8 || obj.getHorizontalLines()[i].getPositions()[j].getX() < 0
                            || obj.getHorizontalLines()[i].getPositions()[j].getY() > 6 || obj.getHorizontalLines()[i].getPositions()[j].getY() < 0) {
                        hasError = true;
                        identifierError = 7;
                    }
                }
            }
        }
        //checks the valid position value for vertical lines object
        for (int i = 0; i < obj.getVerticalLines().length && !hasError; i++) {
            for (int j = 0; j < obj.getVerticalLines()[i].getPositions().length && !hasError; j++) {
                if (!obj.getVerticalLines()[i].getPositions()[j].equals(null)) {
                    if (obj.getVerticalLines()[i].getPositions()[j].getX() > 8 || obj.getVerticalLines()[i].getPositions()[j].getX() < 0
                            || obj.getVerticalLines()[i].getPositions()[j].getY() > 6 || obj.getVerticalLines()[i].getPositions()[j].getY() < 0) {
                        hasError = true;
                        identifierError = 8;
                    }
                }
            }
        }
        //checks the length of the point array of flag 
        if (obj.getFlag() != null && !hasError) {
            if (obj.getFlag().getPoints().length != 4) {
                hasError = true;
                identifierError = 9;
            }
        }
        return identifierError;
    }

    /**
     * return a number corresponding to the loaded file error
     *
     * @param gui
     * @param obj
     * @return
     */
    protected int loadErrorHandling(GUIConnector gui, Save obj) {
        boolean loadError = false;
        //checks the number of players validation
        if (obj.getPlayers().length < 1
                || obj.getPlayers().length > 4) {
            loadError = true;
            identifierError = 10;
        }
        //checks the number of level
        if (!loadError && (obj.getLevelNo() < 1
                || obj.getLevelNo() > 3)) {
            loadError = true;
            identifierError = 12;
        }
        //checks the right number of round for 2 players
        if (!loadError && obj.getPlayers().length == 2) {
            if (obj.getRound() > 6 || obj.getRound() < 0) {
                loadError = true;
                identifierError = 13;
            }
        }
        //checks the number of round for three players
        if (!loadError && obj.getPlayers().length == 3) {
            if (obj.getRound() > 4 || obj.getRound() < 0) {
                loadError = true;
                identifierError = 14;
            }
        }
        //chekcs the number of round for 4 players
        if (!loadError && obj.getPlayers().length == 4) {
            if (obj.getRound() > 3 || obj.getRound() < 0) {
                loadError = true;
                identifierError = 15;
            }
        }
        //checks the turn of the player value
        if (!loadError && (obj.getTurnOf() < 0 || obj.getTurnOf() > 3)) {
            loadError = true;
            identifierError = 16;
        }
        //checks the field length value
        for (int i = 0; i < obj.getPlayers().length && !loadError; i++) {
            for (int j = 0; j < obj.getPlayers()[i].getChecked().length && !loadError; j++) {
                if (obj.getPlayers()[i].getChecked()[j] != null) {
                    if (obj.getPlayers()[i].getChecked()[j].getX() > 8 || obj.getPlayers()[i].getChecked()[j].getX() < 0
                            || obj.getPlayers()[i].getChecked()[j].getY() > 6 || obj.getPlayers()[i].getChecked()[j].getY() < 0) {
                        loadError = true;
                        //errorLoadNum = 2;
                        identifierError = 17;
                    }
                }
            }
        }
        //checks the dice on array length value
        for (int i = 0; i < obj.getPlayers().length && !loadError; i++) {
            for (int j = 0; j < obj.getPlayers()[i].getDiceOn().length && !loadError; j++) {
                if (obj.getPlayers()[i].getDiceOn()[j] != null) {
                    if (obj.getPlayers()[i].getDiceOn()[j].getX() > 8 || obj.getPlayers()[i].getDiceOn()[j].getX() < 0
                            || obj.getPlayers()[i].getDiceOn()[j].getY() > 6 || obj.getPlayers()[i].getDiceOn()[j].getY() < 0) {
                        loadError = true;
                        //errorLoadNum = 2;
                        identifierError = 18;
                    }
                }
            }
        }
        return identifierError;
    }

}
