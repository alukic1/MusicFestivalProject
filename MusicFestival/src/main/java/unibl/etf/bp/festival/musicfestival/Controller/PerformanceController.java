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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Calendar;
import unibl.etf.bp.festival.musicfestival.Model.Festival;
import unibl.etf.bp.festival.musicfestival.Model.Performance;
import unibl.etf.bp.festival.musicfestival.Model.Performer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PerformanceController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Performance> performancesTable;
    @FXML
    private TableColumn performanceStage;
    @FXML
    private TableColumn performer;
    @FXML
    private TableColumn performanceDate;
    @FXML
    private TableColumn performanceTime;
    @FXML
    private Label infoLabel;
    @FXML
    private ChoiceBox<String> stageChoice;

    private List<Performance> performances = null;
    public static Performance selectedPerformance = null;
    private static String selectedStageName = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if(selectedPerformance!=null) selectedPerformance=null;

        unibl.etf.bp.festival.musicfestival.Model.Stage st = unibl.etf.bp.festival.musicfestival.Model.Stage.getStageByName(selectedStageName);
        performances = DBModifier.getAllPerformances(st);

        this.performer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Performance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Performance, String> cellData) {
                Performance p = cellData.getValue();
                Performer performer = p.getPerformer();
                if(performer != null)
                return new SimpleStringProperty(performer.toString());

                return new SimpleStringProperty("");
            }
        });

        this.performanceStage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Performance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Performance, String> cellData) {
                Performance p = cellData.getValue();
                unibl.etf.bp.festival.musicfestival.Model.Stage st = p.getStage();

                return new SimpleStringProperty(st.getStageName());
            }
        });


        this.performanceDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Performance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Performance, String> cellData) {
                Performance p = cellData.getValue();
                LocalDateTime dateTime=p.getStartTime();
                String[] date = dateTime.toString().split("T");

                return new SimpleStringProperty(date[0]);
            }
        });

        this.performanceTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Performance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Performance, String> cellData) {
                Performance p = cellData.getValue();
                LocalDateTime dateTime=p.getStartTime();
                String[] date = dateTime.toString().split("T");

                return new SimpleStringProperty(date[1]);
            }
        });
        performancesTable.getItems().setAll(performances);

        List<unibl.etf.bp.festival.musicfestival.Model.Stage> allStages = DBModifier.getAllStages();
        List<String> stageNames = new ArrayList<>();
        for(unibl.etf.bp.festival.musicfestival.Model.Stage stg : allStages)
            stageNames.add(stg.getStageName());
        stageNames.add("Sve bine");
        stageChoice.getItems().setAll(stageNames);
        stageChoice.setOnAction(this::getStage);



        performancesTable.setRowFactory(tv -> {
            TableRow<Performance> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Performance per = row.getItem();
                    selectedPerformance = per;

                }
            });
            return row;
        });


    }


    public void getStage(ActionEvent event) {
        selectedStageName = stageChoice.getValue();
        if("Sve bine".equals(selectedStageName))
            selectedStageName=null;
    }

    public void addPerformance(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/newPerformance-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void modifyPerformance(ActionEvent event){
        if(selectedPerformance!=null){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/updatePerformance-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else
            infoLabel.setText("Potrebno je izabrati zeljeni nastup.");
    }
    public void deletePerformance(ActionEvent event){
        if(selectedPerformance!=null){

                DBModifier.deletePerformance(selectedPerformance);
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/performances-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();}
                catch(Exception ex){
                    ex.printStackTrace();
                }


        }
        else
            infoLabel.setText("Potrebno je izabrati zeljeni nastup.");
    }
    public void showFilteredPerformances(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/performances-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception e){
            e.printStackTrace();
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
