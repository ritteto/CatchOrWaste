package catchorwaste.controller;

import static catchorwaste.model.StartScreenModel.getOption;
import static catchorwaste.model.StartScreenModel.setOption;
import static catchorwaste.view.StartScreenView.choseOption;
import static catchorwaste.view.StartScreenView.initStartScreenView;

public class StartScreenController {
    public static void initStartScreen(){
        initStartScreenView();
    }

    public static void changeSelectedOption(int position){
        if(position<1){
            setOption(1);
        }else if(position>2){
            setOption(2);
        }else{
            setOption(position);
        }
        choseOption(getOption());
    }
}
