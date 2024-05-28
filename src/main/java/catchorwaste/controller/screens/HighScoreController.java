package catchorwaste.controller.screens;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import catchorwaste.model.screens.HighScoreModel;
import catchorwaste.view.screens.HighScoreView;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import static catchorwaste.model.variables.globalVariables.playerName;
import static catchorwaste.model.variables.globalVariables.score;

public class HighScoreController {

    private HighScoreView highScoreView;
    private HighScoreModel highScoreModel;

    public HighScoreController(HighScoreModel highScoreModel){
        this.highScoreView = new HighScoreView(highScoreModel);
        this.highScoreModel = highScoreModel;
        readHighScore();
    }

    public void initHighScoreView(){
        highScoreView.initHighScoreView();
    }

    public void addHighScore(){
        var playername = playerName;
        long milliseconds = System.currentTimeMillis();

        LocalDateTime dateTime = Instant.ofEpochMilli(milliseconds)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        var seconds = checkIfLowerTen(dateTime.getSecond());
        var minutes = checkIfLowerTen(dateTime.getMinute());
        var hours = checkIfLowerTen(dateTime.getHour());
        var days = checkIfLowerTen(dateTime.getDayOfMonth());
        var month = checkIfLowerTen(dateTime.getMonthValue());
        var years = dateTime.getYear();


        var date = hours+":"+minutes+":"+seconds+"/"+days+"-"+month+"-"+years;
        var entry = new String[]{date, String.valueOf(score), playername};

        File file;
        if(System.getProperty("os.name").contains("Windows")){
            file = new File("src/main/resources/files/highscore/highscores.csv");
        }else{
            file = new File("/home/pi4j/deploy/highscores.csv");
        }

        var list = readHighScore();
        if(list.size()>=999){
            list.remove(0);
            list.add(entry);

            try(CSVWriter csvWriter = new CSVWriter(new FileWriter(file,false))){
                for(int i=0; i<list.size(); i++){
                    csvWriter.writeNext(list.get(i));
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }else{
            try(CSVWriter csvWriter = new CSVWriter(new FileWriter(file,true))){
                csvWriter.writeNext(entry);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    private String checkIfLowerTen(long value){
        if(value<10){
            return 0+""+value;
        }else {
            return ""+value;
        }
    }

    public ArrayList<String[]> readHighScore(){
        var list = new ArrayList<String[]>();
        File file;
        if(System.getProperty("os.name").contains("Windows")){
            file = new File("src/main/resources/files/highscore/highscores.csv");
        }else{
            file = new File("/home/pi4j/deploy/highscores.csv");
        }

        try(CSVReader csvReader = new CSVReader(new FileReader(file))){
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null){
               if(nextLine.length >1 && !nextLine[2].isEmpty()){
                   list.add(nextLine);
               }
            }
        } catch (IOException e) {
            System.out.println(e);;
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        if(!list.isEmpty()){
            highScoreModel.setLastEntry(list.get(list.size()-1));
        }
        highScoreModel.setFullList(list);

        var topTen = new ArrayList<>(list);
        topTen.sort(Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());
        topTen = topTen.stream().limit(10).collect(Collectors.toCollection(ArrayList::new));

        highScoreModel.setTopTen(topTen);
        return list;
    }


}
