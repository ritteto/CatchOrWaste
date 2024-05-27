package catchorwaste.controller.screens;

import catchorwaste.view.screens.EndScreenView;


public class EndScreenController {

    private EndScreenView endScreenView;

    public EndScreenController(){
        endScreenView = new EndScreenView();
    }
    public void initEndscreen(){
        endScreenView.initEndscreenView();
    }
}
