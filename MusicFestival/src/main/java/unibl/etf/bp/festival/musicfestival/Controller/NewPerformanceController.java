package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Performer;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewPerformanceController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> performerName;
    @FXML
    private ChoiceBox<String> stageName;
    @FXML
    private DatePicker performanceDate;
    @FXML
    private TextField performanceTime;
    @FXML
    private Label infoLabel;

    public static String selectedPerformerName;
    public static String selectedStageName;
    public static String selectedPerformanceDateTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Performer> performerList = DBModifier.gettAllPerformers();
        List<String> performerNames = new ArrayList<>();
        for(Performer performer : performerList){
            performerNames.add(performer.toString());
        }
        performerName.getItems().addAll(performerNames);
        performerName.setOnAction(this::getPerformerName);

        List<unibl.etf.bp.festival.musicfestival.Model.Stage> stageList = DBModifier.getAllStages();
        List<String> stageNames = new ArrayList<>();
        for(unibl.etf.bp.festival.musicfestival.Model.Stage st:stageList)
            stageNames.add(st.getStageName());

        stageName.getItems().addAll(stageNames);
        stageName.setOnAction(this::getStageName);


    }
    public void getPerformerName(ActionEvent event) {
        selectedPerformerName = performerName.getValue();
    }
    public void getStageName(ActionEvent event) {
        selectedStageName = stageName.getValue();
    }
    public void addPerformance(ActionEvent event) {
        if(performerName.getValue().length()>0 && performanceDate.getValue()!=null && performanceTime.getText().length()==5 && stageName.getValue().length()>0){
            String datetime = performanceDate.getValue().toString() + " " + performanceTime.getText() + ":00";
           String[] timeValidityCheck = performanceTime.getText().split(":");
           Integer startHour = Integer.parseInt(timeValidityCheck[0]);
           Integer startMinute = Integer.parseInt(timeValidityCheck[1]);
           if(timeValidityCheck[0].length()==2 && timeValidityCheck[1].length()==2 &&
           startHour>=0 && startMinute>=0 && startHour<24 && startMinute<60){
                selectedPerformanceDateTime=datetime;
               DBModifier.addPerformance();

               try{
                   Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/performances-view.fxml"));
                   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   scene = new Scene(root);
                   stage.setScene(scene);
                   stage.show();}
               catch(Exception ex){
                   ex.printStackTrace();
               }}
           else
               infoLabel.setText("Svi podaci moraju da budu pravilno uneseni!");

        }
        else
            infoLabel.setText("Svi podaci moraju da budu pravilno uneseni!");
    }
}
