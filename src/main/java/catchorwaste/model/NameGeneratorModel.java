package catchorwaste.model;

import static catchorwaste.view.NameGeneratorView.modifyLane;
import static catchorwaste.view.NameGeneratorView.highlightArrow;
import static catchorwaste.view.NameGeneratorView.changeLetterView;

public class NameGeneratorModel {
    private static int activeLane = 1;
    private static int letterLane1 = 65;
    private static int letterLane2 = 65;
    private static int letterLane3 = 65;
    private static String playerName;

    public static int getActiveLane() {
        return activeLane;
    }

    public static void setActiveLane(int newActiveLane) {
        if(newActiveLane<1){
            activeLane = 4;
        }else if(newActiveLane>4){
            activeLane = 1;
        }else {
            activeLane = newActiveLane;
        }
        modifyLane(activeLane);
    }

    public static int getLetter(){
        switch (activeLane){
            case 1:
                return letterLane1;
            case 2:
                return letterLane2;
            default:
                return letterLane3;
        }
    }

    public static void changeLetter(int position){
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


            highlightArrow();
            changeLetterView();
        }
    }

    public static String getPlayerName(){
        return playerName;
    }
    public static void saveChanges(){
        playerName = ""+(char) letterLane1+ (char) letterLane2+(char) letterLane3;
    }
}
