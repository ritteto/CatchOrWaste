package catchorwaste.controller.screens;

import catchorwaste.view.screens.TutorialView;

public class TutorialController {
    TutorialView tutorialView;

    public TutorialController(){
        this.tutorialView = new TutorialView();
    }
    public void initTutorial(){
        tutorialView.initTutorialView();
    }
}
