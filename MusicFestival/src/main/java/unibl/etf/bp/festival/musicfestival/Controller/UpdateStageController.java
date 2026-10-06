package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Festival;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStageController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField stageName;

    @FXML
    private TextField stageLabel;
    @FXML
    private Label infoLabel;

    private unibl.etf.bp.festival.musicfestival.Model.Stage stg = new unibl.etf.bp.festival.musicfestival.Model.Stage();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stageName.setText(StageController.selectedStage.getStageName());
        stageLabel.setText(StageController.selectedStage.getStageLabel());

    }

    public void modifyStage(ActionEvent actionEvent) {

        if(stageName.getText().length() >0 && stageLabel.getText().length() >0 ) {
            stg.setStageId(StageController.selectedStage.getStageId());
            stg.setStageName(stageName.getText());
            stg.setStageLabel(stageLabel.getText());

            DBModifier.updateStage(stg);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/stages-view.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
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
