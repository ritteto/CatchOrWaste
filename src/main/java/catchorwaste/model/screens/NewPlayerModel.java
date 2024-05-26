package catchorwaste.model.screens;

public class NewPlayerModel {

    private int chosenLabel = 1;
    public NewPlayerModel(){
    }

    public int getChosenLabel() {
        return chosenLabel;
    }

    public void setChosenLabel(int chosenLabel) {
        if(chosenLabel <1){
            this.chosenLabel = 1;
        }else if (chosenLabel > 2){
            this.chosenLabel = 2;
        }else {
            this.chosenLabel = chosenLabel;
        }
    }
}
