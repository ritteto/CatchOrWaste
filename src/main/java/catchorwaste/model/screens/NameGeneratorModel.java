package catchorwaste.model.screens;

import catchorwaste.model.variables.globalVariables;

public class NameGeneratorModel {
    private int activeLane = 1;
    private int letterLane1 = 65;
    private int letterLane2 = 65;
    private int letterLane3 = 65;
    private String playerName;


    public int getActiveLane() {
        return activeLane;
    }

    public void setActiveLane(int newActiveLane) {
        if(newActiveLane<1){
            activeLane = 4;
        }else if(newActiveLane>4){
            activeLane = 1;
        }else {
            activeLane = newActiveLane;
        }
    }

    public int getLetter(){
        switch (activeLane){
            case 1:
                return letterLane1;
            case 2:
                return letterLane2;
            default:
                return letterLane3;
        }
    }

    public void changeLetter(int position){
        if(activeLane<4){
            int letter;
            if(position < 65){
                letter = 90;
            } else if (position > 90) {
                letter = 65;
            }else{
                letter = position;
            }


            switch (activeLane){
                case 1:
                    letterLane1 = letter;
                    break;
                case 2:
                    letterLane2 = letter;
                    break;
                case 3:
                    letterLane3 = letter;
                    break;
            }

        }
    }


    public void setPlayerName(String playerName) {
        globalVariables.playerName = playerName;
    }

    public void setLetterLane1(int letterLane1) {
        this.letterLane1 = letterLane1;
    }

    public void setLetterLane2(int letterLane2) {
        this.letterLane2 = letterLane2;
    }

    public void setLetterLane3(int letterLane3) {
        this.letterLane3 = letterLane3;
    }

    public void saveChanges(){
        globalVariables.playerName = ""+(char) letterLane1+ (char) letterLane2+(char) letterLane3;
    }
}
