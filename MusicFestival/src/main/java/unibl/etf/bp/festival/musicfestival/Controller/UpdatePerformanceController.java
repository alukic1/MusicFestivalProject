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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdatePerformanceController implements Initializable {

    private Stage stage;
    private Scene scene;

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

        String[] currentDateTime = PerformanceController.selectedPerformance.getStartTime().toString().split("T");
        String time = currentDateTime[1];
        performanceDate.setValue(PerformanceController.selectedPerformance.getStartTime().toLocalDate());
        performanceTime.setText(time);

    }

    public void modifyPerformance(ActionEvent event) {
        if(performanceDate.getValue()!=null && performanceTime.getText().length()==5){
            String datetime = performanceDate.getValue().toString() + " " + performanceTime.getText() + ":00";
            String[] timeValidityCheck = performanceTime.getText().split(":");
            Integer startHour = Integer.parseInt(timeValidityCheck[0]);
            Integer startMinute = Integer.parseInt(timeValidityCheck[1]);
            if(timeValidityCheck[0].length()==2 && timeValidityCheck[1].length()==2 &&
                    startHour>=0 && startMinute>=0 && startHour<24 && startMinute<60){
                selectedPerformanceDateTime=datetime;
                DBModifier.modifyPerformance();

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

