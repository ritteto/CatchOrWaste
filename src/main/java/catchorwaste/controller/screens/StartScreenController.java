package catchorwaste.controller.screens;

import catchorwaste.model.screens.StartScreenModel;
import catchorwaste.view.screens.StartScreenView;


public class StartScreenController {
    StartScreenModel startScreenModel;
    StartScreenView startScreenView;
    public StartScreenController(StartScreenModel startScreenModel){
        this.startScreenModel = startScreenModel;
        startScreenView = new StartScreenView(startScreenModel);
    }
    public void initStartScreen(){
        startScreenView.initStartScreenView();
        startScreenModel.setOption(1);
    }

    public void changeSelectedOption(int position){
        if(position<1){
            startScreenModel.setOption(1);
        }else if(position>2){
            startScreenModel.setOption(2);
        }else{
            startScreenModel.setOption(position);
        }
        startScreenView.choseOption();
    }

    public int getOption(){
        return this.startScreenModel.getOption();
    }

}
