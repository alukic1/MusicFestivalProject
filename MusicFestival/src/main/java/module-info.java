module unibl.etf.bp.festival.musicfestival {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;


    exports unibl.etf.bp.festival.musicfestival;
    opens unibl.etf.bp.festival.musicfestival.Controller;
    exports unibl.etf.bp.festival.musicfestival.Controller;
    opens unibl.etf.bp.festival.musicfestival.Model;
    opens unibl.etf.bp.festival.musicfestival;
    //  exports unibl.etf.bp.festival.musicfestival.Model;

}