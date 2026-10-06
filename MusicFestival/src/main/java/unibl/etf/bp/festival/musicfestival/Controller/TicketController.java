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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import unibl.etf.bp.festival.musicfestival.DBConnection.DBModifier;
import unibl.etf.bp.festival.musicfestival.Model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn ticketId;
    @FXML
    private TableColumn ticketPrice;
    @FXML
    private TableColumn ticketType;
    @FXML
    private Label pickedFestival;
    @FXML
    private Label infoLabel;

    @FXML
    private ChoiceBox<String> ticketTypes;

    @FXML
    private TableView<FestivalTicketType> typesTable;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn price;

    private String selectedTicketType=null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Festival f=Festival.getFestivalByName(FestivalController.selectedFestival.getFestivalName());

        pickedFestival.setText(f.getFestivalName());

        List<FestivalTicketType> types = DBModifier.getAllFestivalTicketTypesByFestivalId(f.getFestivalId());

        type.setCellValueFactory(new PropertyValueFactory<TicketType, String>("ticketTypeName"));
        price.setCellValueFactory(new PropertyValueFactory<TicketType, String>("price"));

        typesTable.getItems().setAll(types);

        List<String> typesNames = new ArrayList<String>();
        for(FestivalTicketType type : types){
            typesNames.add(type.getTicketTypeName());
        }

        ticketTypes.getItems().setAll(typesNames);
        ticketTypes.setOnAction(this::getTicketType);


        List<Ticket> soldTickets = DBModifier.getAllTicketsByFestivalId(f.getFestivalId());

        ticketId.setCellValueFactory(new PropertyValueFactory<Ticket, String>("id"));

        this.ticketPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> cellData) {
                Ticket t = cellData.getValue();
                TicketType tt=t.getType();
                String price="";
                for(FestivalTicketType ft : types)
                    if(Objects.equals(ft.getTicketTypeId(), tt.getId()))
                        price = ft.getPrice().toString();

                return new SimpleStringProperty(price);
            }
        });

        this.ticketType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> cellData) {
                Ticket t = cellData.getValue();
                TicketType tt=t.getType();
                String type = tt.getName();
                return new SimpleStringProperty(type);
            }
        });

        ticketsTable.getItems().setAll(soldTickets);
     }

    public void getTicketType(ActionEvent event) {
        selectedTicketType = ticketTypes.getValue();
    }

    public void sellTicket(ActionEvent event){
        if(selectedTicketType!=null){
            if(DBModifier.sellTicket(selectedTicketType)){
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/unibl/etf/bp/festival/musicfestival/tickets-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();}
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else
                infoLabel.setText("Rasprodate ulaznice.");
        }
        else
            infoLabel.setText("Potrebno je izabrati vrstu ulaznice koja se prodaje.");
    }

    public void goBack(ActionEvent event){
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
}
