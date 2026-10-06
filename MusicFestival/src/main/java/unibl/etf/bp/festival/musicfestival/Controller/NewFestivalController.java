package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Calendar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewFestivalController implements Initializable {
    private Stage stage;
    private Scene scene;


    @FXML
    private Label infoLabel;
    @FXML
    private TextField festivalName;
    @FXML
    private TextArea festivalDescription;
    @FXML
    private TextField festivalEmail;
    @FXML
    private ChoiceBox<Integer> calendarIds;

    public static Integer selectedCalendarId = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(selectedCalendarId!=null) selectedCalendarId = null;

        List<Calendar> calendars = DBModifier.getAllCalendars();
        List<Integer> calendarIdentifiers = new ArrayList<Integer>();
        for (Calendar calendar : calendars) {
            calendarIdentifiers.add(calendar.getCalendarId());
        }

        calendarIds.getItems().setAll(calendarIdentifiers);
        calendarIds.setOnAction(this::getCalendar);
    }


    public void getCalendar(ActionEvent event) {
        selectedCalendarId = calendarIds.getValue();
    }

    public void addFestival(ActionEvent event) {
        if(selectedCalendarId != null && festivalName.getLength()>0 && festivalDescription.getLength()>0 && festivalEmail.getLength()>0) {
            DBModifier.addNewFestival(festivalName.getText(), festivalEmail.getText(), festivalDescription.getText(), selectedCalendarId);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/festivals-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else
            infoLabel.setText("Svi podaci moraju da budu uneseni!");
    }
}
