package unibl.etf.bp.festival.musicfestival;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/welcome-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        new DBModifier();
        launch();
        try{
        ConnectionPool.close();}
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}