package catchorwaste.model.screens;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class HighScoreModel {
    ArrayList<String[]> topTen;
    ArrayList<String[]> fullList;
    String[] lastEntry;
    public ArrayList<String[]> getTopTen(){
        return this.topTen;
    }

    public void setTopTen(ArrayList<String[]> list){
        if(!(list.size() <10)){
            list = list.stream().limit(10).collect(Collectors.toCollection(ArrayList::new));
        }
        this.topTen = list;
    }

    public ArrayList<String[]> getFullList() {
        return fullList;
    }

    public void setFullList(ArrayList<String[]> fullList) {
        this.fullList = fullList;
    }

    public String[] getLastEntry() {
        return lastEntry;
    }

    public void setLastEntry(String[] lastEntry) {
        this.lastEntry = lastEntry;
    }
}

