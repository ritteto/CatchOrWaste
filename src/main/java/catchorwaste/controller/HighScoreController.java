package catchorwaste.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import static catchorwaste.model.NameGeneratorModel.getPlayerName;
import static catchorwaste.model.PunktesystemModel.getPoints;

public class HighScoreController {
    public static void addHighScore(){
        var playername = getPlayerName();
        var score = getPoints();
        long milliseconds = System.currentTimeMillis();

        LocalDateTime dateTime = Instant.ofEpochMilli(milliseconds)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        var minutes = checkIfLowerTen(dateTime.getMinute());
        var hours = checkIfLowerTen(dateTime.getHour());
        var days = checkIfLowerTen(dateTime.getDayOfMonth());
        var month = checkIfLowerTen(dateTime.getMonthValue());
        var years = dateTime.getYear();


        var date = hours+":"+minutes+"/"+days+"-"+month+"-"+years;
        var entry = new String[]{date, String.valueOf(score), playername};
        var file = new File("src/main/resources/files/highscore/highscores.csv");

        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(file,true))){
            csvWriter.writeNext(entry);
        } catch (IOException e) {
            System.out.println(e);;
        }

    }

    private static String checkIfLowerTen(long value){
        if(value<10){
            return 0+""+value;
        }else {
            return ""+value;
        }
    }

    public static ArrayList<String[]> readHighScore(){
        var list = new ArrayList<String[]>();
        var file = new File("src/main/resources/files/highscore/highscores.csv");

        try(CSVReader csvReader = new CSVReader(new FileReader(file))){
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null){
               if(nextLine.length>1){
                   list.add(nextLine);
               }
            }
        } catch (IOException e) {
            System.out.println(e);;
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }


        list.sort(Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());



        if(list.size()<10){
            return list;
        }else{
            return list.stream().limit(10).collect(Collectors.toCollection(ArrayList::new));
        }
    }
}
