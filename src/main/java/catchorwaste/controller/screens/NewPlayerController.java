package catchorwaste.controller.screens;

import catchorwaste.model.screens.NewPlayerModel;
import catchorwaste.view.screens.NewPlayerView;

public class NewPlayerController {

    NewPlayerModel newPlayerModel;
    NewPlayerView newPlayerView;

    public NewPlayerController(NewPlayerModel newPlayerModel){
        this.newPlayerModel = newPlayerModel;
        newPlayerView = new NewPlayerView(newPlayerModel);
    }
    public void initNewPlayerScreen(){
        newPlayerView.initNewPlayerScreenView();
    }


    public void updateChosenLabel(int i){
        newPlayerModel.setChosenLabel(i);
        newPlayerView.highlightChosenLabel();
    }

    public int getChosenLabel(){
        return newPlayerModel.getChosenLabel();
    }

}
