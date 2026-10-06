package unibl.etf.bp.festival.musicfestival.Controller;

import com.mysql.cj.xdevapi.Table;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.Calendar;
import unibl.etf.bp.festival.musicfestival.Model.Festival;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FestivalController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Festival> festivalsTable;
    @FXML
    private TableColumn festivalId;
    @FXML
    private TableColumn festivalName;
    @FXML
    private TableColumn festivalCity;
    @FXML
    private TableColumn festivalStartDate;
    @FXML
    private TableColumn festivalEndDate;
    @FXML
    private TableColumn festivalEmail;
    @FXML
    private Label infoLabel;

    private List<Festival> festivals = null;
    public static Festival selectedFestival = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(selectedFestival != null) {
            selectedFestival=null;
        }
        festivals = DBModifier.getAllFestivals();
        festivalId.setCellValueFactory(new PropertyValueFactory<Festival, String>("festivalId"));
        festivalName.setCellValueFactory(new PropertyValueFactory<Festival, String>("festivalName"));
        festivalEmail.setCellValueFactory(new PropertyValueFactory<Festival, String>("festivalEmail"));


        this.festivalCity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Festival, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Festival, String> cellData) {
                Festival f = cellData.getValue();
                Calendar calendar = f.getFestivalDate();
                String cityName = calendar.getCity().getCityName();
                return new SimpleStringProperty(cityName);
            }
        });

        this.festivalStartDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Festival, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Festival, String> cellData) {
                Festival f = cellData.getValue();
                Calendar calendar = f.getFestivalDate();
                String startDate = calendar.getStartDate().toString();
                return new SimpleStringProperty(startDate);
            }
        });

        this.festivalEndDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Festival, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Festival, String> cellData) {
                Festival f = cellData.getValue();
                Calendar calendar = f.getFestivalDate();
                String endDate = calendar.getEndDate().toString();
                return new SimpleStringProperty(endDate);
            }
        });

        festivalsTable.getItems().setAll(festivals);

        festivalsTable.setRowFactory(tv -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Festival st = row.getItem();
                    selectedFestival = st;

                }
            });
            return row;
        });
    }

    public void addFestival(ActionEvent e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/newFestival-view.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void modifyFestival(ActionEvent e){
        if(selectedFestival!=null){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/updateFestival-view.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else
            infoLabel.setText("Potrebno je izabrati zeljeni festival.");
    }
    public void deleteFestival(ActionEvent e){
        if(selectedFestival != null) {
            DBModifier.deleteFestivalById(selectedFestival.getFestivalId());
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/festivals-view.fxml"));
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
    public void sellTicket(ActionEvent e){
        if(selectedFestival != null) {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/tickets-view.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();}
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else
            infoLabel.setText("Potrebno je izabrati festival za koji se prodaje ulaznica.");
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
