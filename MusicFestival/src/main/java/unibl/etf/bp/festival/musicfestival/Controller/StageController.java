package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Calendar;
import unibl.etf.bp.festival.musicfestival.Model.Festival;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class StageController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<unibl.etf.bp.festival.musicfestival.Model.Stage> stagesTable;
    @FXML
    private TableColumn stageId;
    @FXML
    private TableColumn stageName;
    @FXML
    private TableColumn stageLabel;

    @FXML
    private TableColumn festivalName;

    @FXML
    private Label infoLabel;

    public static unibl.etf.bp.festival.musicfestival.Model.Stage selectedStage=null;

    private List<unibl.etf.bp.festival.musicfestival.Model.Stage> stages = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(selectedStage != null) { selectedStage=null;}
        stages = DBModifier.getAllStages();

        stageId.setCellValueFactory(new PropertyValueFactory<unibl.etf.bp.festival.musicfestival.Model.Stage, String>("stageId"));
        stageName.setCellValueFactory(new PropertyValueFactory<unibl.etf.bp.festival.musicfestival.Model.Stage, String>("stageName"));
        stageLabel.setCellValueFactory(new PropertyValueFactory<unibl.etf.bp.festival.musicfestival.Model.Stage, String>("stageLabel"));

        this.festivalName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<unibl.etf.bp.festival.musicfestival.Model.Stage, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<unibl.etf.bp.festival.musicfestival.Model.Stage, String> cellData) {
                unibl.etf.bp.festival.musicfestival.Model.Stage s = cellData.getValue();

                return new SimpleStringProperty(s.getFestival().getFestivalName());
            }
        });
        stagesTable.getItems().setAll(stages);

        stagesTable.setRowFactory(tv -> {
            TableRow<unibl.etf.bp.festival.musicfestival.Model.Stage> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    unibl.etf.bp.festival.musicfestival.Model.Stage st = row.getItem();
                    selectedStage = st;
                   // changeDriverStatus(driver);
                }
            });
            return row;
        });
    }

    public void addStage(ActionEvent e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/newStage-view.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void modifyStage(ActionEvent e){
        if(selectedStage != null) {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/updateStage-view.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            infoLabel.setText("Potrebno je izabrati zeljenu binu.");
        }
    }
    public void deleteStage(ActionEvent e){
        if(selectedStage != null) {
            DBModifier.deleteStageById(selectedStage.getStageId());
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/stages-view.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            infoLabel.setText("Potrebno je izabrati zeljenu binu.");
        }
    }

    public void goBack(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/startPage-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
