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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Festival;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewStageController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField stageName;

    @FXML
    private TextField stageLabel;

    @FXML
    private ChoiceBox<String> stageFestival;

    @FXML
    private Label infoLabel;

    private List<String> festivalNames = new ArrayList<>();
    private String selectedFestivalName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Festival> festivals = DBModifier.getAllFestivals();
        for(Festival f : festivals){
            festivalNames.add(f.getFestivalName());
        }

        stageFestival.getItems().addAll(festivalNames);
        stageFestival.setOnAction(this::getFestival);
    }

    public void getFestival(ActionEvent event) {
        selectedFestivalName = stageFestival.getValue();
    }

    public void addStage(ActionEvent event) {
        if(stageName.getText().length() >0 && stageLabel.getText().length() >0 && selectedFestivalName!=null) {
            Festival f = Festival.getFestivalByName(selectedFestivalName);
            DBModifier.addNewStage(stageName.getText(), stageLabel.getText(), f.getFestivalId());

            try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/stages-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else {
            infoLabel.setText("Svi podaci moraju da budu uneseni!");
        }
    }
}
