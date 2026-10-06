package unibl.etf.bp.festival.musicfestival.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController{
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label infoLabel;

    public static String currentUsername;

    public void login(ActionEvent event){
        if(DBModifier.login(username.getText(), password.getText())){
            currentUsername = username.getText();
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
        else{
            infoLabel.setText("Unijeli ste pogrešne podatke! Pokušajte ponovo.");
        }
    }
}
