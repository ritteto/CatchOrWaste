package catchorwaste.controller.screens;

import catchorwaste.model.screens.NameGeneratorModel;
import catchorwaste.view.screens.NameGeneratorView;

public class NameGeneratorController {

    private NameGeneratorModel nameGeneratorModel;
    private NameGeneratorView nameGeneratorView;
    public NameGeneratorController(NameGeneratorModel nameGeneratorModel){
        this.nameGeneratorModel = nameGeneratorModel;
        this.nameGeneratorView = new NameGeneratorView(nameGeneratorModel);
    }
    public void initNameGenerator(){
        nameGeneratorView.initNameGeneratorView();
    }

    public int getActiveLane(){
        return nameGeneratorModel.getActiveLane();
    }

    public void setActiveLane(int lane){
        nameGeneratorModel.setActiveLane(lane);
        nameGeneratorView.modifyLane();
    }

    public int getLetter(){
        return nameGeneratorModel.getLetter();
    }

    public void changeLetter(int position){
        nameGeneratorModel.changeLetter(position);
        nameGeneratorView.highlightArrow();
        nameGeneratorView.changeLetterView();
    }

    public void setPlayerName(String playerName){
        nameGeneratorModel.setPlayerName(playerName);
    }


    public void setLetterLane1(int letter){
        nameGeneratorModel.setLetterLane1(letter);
    }

    public void setLetterLane2(int letter){
        nameGeneratorModel.setLetterLane2(letter);
    }

    public void setLetterLane3(int letter){
        nameGeneratorModel.setLetterLane3(letter);
    }

    public void saveChanges(){
        nameGeneratorModel.saveChanges();
    }

}
