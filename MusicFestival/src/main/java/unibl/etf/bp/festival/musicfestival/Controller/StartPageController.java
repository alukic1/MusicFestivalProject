package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Dobro došli " + LoginController.currentUsername + "!");
    }

    public void goToFestivals(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/festivals-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void goToStages(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/stages-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void goToPerformances(ActionEvent event){
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
}
