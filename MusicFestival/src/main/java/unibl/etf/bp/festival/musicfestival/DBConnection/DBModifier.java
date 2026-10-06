package unibl.etf.bp.festival.musicfestival.DBConnection;

import unibl.etf.bp.festival.musicfestival.Controller.FestivalController;
import unibl.etf.bp.festival.musicfestival.Controller.NewPerformanceController;
import unibl.etf.bp.festival.musicfestival.Controller.PerformanceController;
import unibl.etf.bp.festival.musicfestival.Controller.UpdatePerformanceController;
import unibl.etf.bp.festival.musicfestival.Model.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class DBModifier {
    private static Connection conn = null;
    public DBModifier() {
        try{
            conn = ConnectionPool.getInstance();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Festival> getAllFestivals() {
        List<Festival> festivals = new ArrayList<Festival>();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from festival where festival.exists=1");


            while(rs.next()){
                Festival f = new Festival();
                f.setFestivalId(rs.getInt("IdFestivala"));
                f.setFestivalName(rs.getString("Naziv"));
                f.setFestivalEmail(rs.getString("Email"));
                f.setFestivalDescription(rs.getString("Opis"));
                Calendar calendar =  Calendar.getCalendarById(rs.getInt("IdKalendara"));
                f.setFestivalDate(calendar);

                festivals.add(f);

            }
            return festivals;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    return null;
    }

    public static List<Stage> getAllStages(){
        List<Stage> stages = new ArrayList<Stage>();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from bina where bina.exists=1");


            while(rs.next()){
                Stage s = new Stage();
                s.setStageId(rs.getInt("IdBine"));
                s.setStageLabel(rs.getString("Oznaka"));
                s.setStageName(rs.getString("Naziv"));

                Festival f = Festival.getFestivalById(rs.getInt("IdFestivala"));
                s.setFestival(f);
                stages.add(s);


            }
            return stages;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static void addNewStage(String stageName, String stageLabel, Integer festivalId){

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "insert into bina (Naziv, Oznaka, IdFestivala)VALUES(?,?,?)";
        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, stageName);
            statement.setString(2, stageLabel);

            statement.setInt(3, festivalId);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static void updateStage(Stage stage){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "update bina set Oznaka = ?, Naziv = ? where IdBine = ?";
        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, stage.getStageLabel());
            statement.setString(2, stage.getStageName());

            statement.setInt(3, stage.getStageId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static List<TicketType> getAllTicketTypesByFestivalId(Integer festivalId){

        List<TicketType> types = new ArrayList<TicketType>();
        String query="call festivaldb.getTicketTypesByFestival(?)";
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, festivalId);
            rs = stmt.executeQuery();


            while(rs.next()){
                TicketType ticketType=new TicketType();
                ticketType.setId(rs.getInt("IdVrsteUlaznice"));
                ticketType.setName(rs.getString("Naziv"));

                types.add(ticketType);

            }
            return types;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static List<FestivalTicketType> getAllFestivalTicketTypesByFestivalId(Integer festivalId){

        List<FestivalTicketType> types = new ArrayList<FestivalTicketType>();
        String query="select * from festival_vrsta_ulaznice where FESTIVAL_IdFestivala=?";
        List<TicketType> ticketTypes = getAllTicketTypesByFestivalId(festivalId);
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, festivalId);
            rs = stmt.executeQuery();

            while(rs.next()){
                FestivalTicketType ticketType=new FestivalTicketType();
                ticketType.setTicketTypeId(rs.getInt("VRSTA_ULAZNICE_IdVrsteUlaznice"));
                ticketType.setFestivalId(festivalId);
                ticketType.setPrice(rs.getBigDecimal("Cijena"));
                ticketType.setMaxNumTickets(rs.getInt("MaxBrojUlaznica"));

                for(TicketType tt : ticketTypes){
                    if(Objects.equals(tt.getId(), ticketType.getTicketTypeId()))
                        ticketType.setTicketTypeName(tt.getName());
                }
                types.add(ticketType);

            }


            return types;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static List<Ticket> getAllTicketsByFestivalId(Integer festivalId) {
        List<Ticket> tickets = new ArrayList<Ticket>();

        String query = "select * from ulaznica where ulaznica.IdFestivala=?";
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, festivalId);
            rs = stmt.executeQuery();


            while(rs.next()){
                Ticket ticket=new Ticket();
                ticket.setId(rs.getInt("IdUlaznice"));

                TicketType ticketType= TicketType.getTicketTypeById(rs.getInt("IdVrsteUlaznice"));
                ticket.setType(ticketType);

                tickets.add(ticket);

            }
            return tickets;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static void deleteStageById(Integer stageId){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "update bina set bina.Exists=0 where bina.IdBine = ?";
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, stageId);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
    public static List<Calendar> getAllCalendars(){
        List<Calendar> calendars = new ArrayList<Calendar>();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from kalendar");


            while(rs.next()){
                Calendar calendar = new Calendar();
                calendar.setCalendarId(rs.getInt("IdKalendara"));
                calendar.setStartDate(rs.getDate("DatumOd"));
                calendar.setEndDate(rs.getDate("DatumDo"));
                calendar.setCity(City.getCityById(rs.getInt("IdGrada")));

                calendars.add(calendar);

            }
            return calendars;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }
    public static void addNewFestival(String name, String email, String description, Integer calendarId){

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "insert into festival (Naziv, Opis, IdKalendara, Email)VALUES(?,?,?,?)";
        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, description);

            statement.setInt(3, calendarId);
            statement.setString(4, email);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static void updateFestival(Festival festival, Integer calendarId){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "call updateFestival(?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, festival.getFestivalName());
            statement.setString(2, festival.getFestivalDescription());
            statement.setString(3, festival.getFestivalEmail());
            statement.setInt(4, calendarId);
            statement.setInt(5, festival.getFestivalId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static void deleteFestivalById(Integer festivalId){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "update festival set festival.Exists=0 where festival.IdFestivala = ?";
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, festivalId);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
    public static List<Performance> getAllPerformances(Stage stage){

        List<Performance> performances = new ArrayList<Performance>();
        String query="";

        if(stage==null){
            Statement statement = null;
            ResultSet rs=null;
            query = "select * from nastup where nastup.Exists=1";
            try{
                statement = conn.createStatement();
                rs = statement.executeQuery(query);


                while(rs.next()){
                    Performance performance = new Performance();
                    performance.setPerformer(DBModifier.getPerformerById(rs.getInt("IdIzvodjaca")));
                    performance.setStage(Stage.getStageById(rs.getInt("IdBine")));
                    performance.setStartTime(rs.getTimestamp("VrijemeNastupa").toLocalDateTime());

                    performances.add(performance);

                }
                return performances;
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                if (rs != null)
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                if (statement != null)
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
            }
            return null;

        }
        else{
            query = "select * from nastup where nastup.IdBine = ? and nastup.Exists=1";
            PreparedStatement stmt=null;
            ResultSet rs=null;

            try{
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, stage.getStageId());
                rs = stmt.executeQuery();


                while(rs.next()){
                    Performance performance = new Performance();
                    performance.setPerformer(DBModifier.getPerformerById(rs.getInt("IdIzvodjaca")));
                    performance.setStage(Stage.getStageById(rs.getInt("IdBine")));
                    performance.setStartTime(rs.getTimestamp("VrijemeNastupa").toLocalDateTime());

                    performances.add(performance);

                }
                return performances;
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                if (rs != null)
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                if (stmt != null)
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
            }
            return null;
        }

    }

    public static List<Musician> getAllMusicianPerformers(){

        List<Musician> musicians = new ArrayList<Musician>();
        String query = "call festivaldb.pronadjiMuzicareIzvodjace();";
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                Musician musician = new Musician();
                musician.setPerformerId(rs.getInt("IdIzvodjaca"));
                musician.setName(rs.getString("Ime"));
                musician.setLastname(rs.getString("Prezime"));
                musician.setNickname(rs.getString("Nadimak"));

                musicians.add(musician);

            }
            return musicians;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static List<Band> getAllBandPerformers(){
        List<Band> bands = new ArrayList<Band>();
        String query = "select * from bend where bend.IdIzvodjaca in "+
                "(select IdIzvodjaca from izvodjac)";
        Statement stmt = null;
        ResultSet rs=null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                Band band = new Band();
                band.setPerformerId(rs.getInt("IdIzvodjaca"));
                band.setName(rs.getString("Naziv"));


                bands.add(band);

            }
            return bands;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
        return null;
    }

    public static List<Performer> gettAllPerformers(){

        List<Performer> performers = new ArrayList<Performer>();
        List<Musician> musicians = DBModifier.getAllMusicianPerformers();
        List<Band> bands = DBModifier.getAllBandPerformers();

        for(Musician musician : musicians){
            performers.add(musician);
        }
        for(Band band : bands){
            performers.add(band);
        }
        return performers;
    }

    public static Performer getPerformerById(Integer id){
        List<Performer> performers = gettAllPerformers();
        for(Performer performer : performers){
            if(performer.getPerformerId() == id){
                return performer;
            }
        }
        return null;
    }

    public static Performer getPerformerByName(String name){
        List<Performer> performers = gettAllPerformers();
        for(Performer performer:performers){
            if(name.equals(performer.toString()))
                return performer;
        }
        return null;
    }

    public static void deletePerformance(Performance performance){

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "update nastup set nastup.Exists=0 where nastup.IdIzvodjaca = ? and nastup.IdBine = ?";
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, performance.getPerformer().getPerformerId());
            statement.setInt(2, performance.getStage().getStageId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
    }
    }

    public static void addPerformance(){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "insert into nastup (IdIzvodjaca, IdBine, VrijemeNastupa)VALUES(?,?,?)";
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1,DBModifier.getPerformerByName(NewPerformanceController.selectedPerformerName).getPerformerId());
            statement.setInt(2, Stage.getStageByName(NewPerformanceController.selectedStageName).getStageId());
            statement.setString(3, NewPerformanceController.selectedPerformanceDateTime);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }


    }
    public static void modifyPerformance(){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query="call festivaldb.updateNastup(?, ?, ?)";
        try {
            statement = conn.prepareStatement(query);

            statement.setString(1,UpdatePerformanceController.selectedPerformanceDateTime);
            statement.setInt(2, PerformanceController.selectedPerformance.getPerformer().getPerformerId());
            statement.setInt(3, PerformanceController.selectedPerformance.getStage().getStageId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static boolean login(String username, String password){

        Statement statement=null;
        ResultSet resultSet = null;
        String query = "select * from festivaldb.administrator";
        try{
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Administrator admin = new Administrator();
                admin.setPassword(resultSet.getString("Lozinka"));
                admin.setUsername(resultSet.getString("KorisnickoIme"));

                if(admin.getUsername().equals(username) && admin.getPassword().equals(password))
                    return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }

        return false;
    }

    public static boolean sellTicket(String ticketType){
        Festival festival = FestivalController.selectedFestival;
        TicketType type = TicketType.getTicketTypeByName(ticketType);
        CallableStatement callableStatement=null;
        int count=0;

        try{
            callableStatement = conn.prepareCall("{call brojProdatihUlaznicaTipaPoFestivalu(?,?,?)}");
            callableStatement.setInt(1,type.getId());
            callableStatement.setInt(2, festival.getFestivalId());
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            callableStatement.execute();
            count = callableStatement.getInt(3);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if(callableStatement!=null){

                        try {
                            callableStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
            }
        }

        FestivalTicketType festivalTicketType = FestivalTicketType.getFestTicketTypeByIds(festival.getFestivalId(),type.getId());
        int maxTickets = festivalTicketType.getMaxNumTickets();
        if(count<maxTickets){

            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = "insert into ulaznica (IdVrsteUlaznice, IdFestivala)VALUES(?,?)";
            try {
                statement = conn.prepareStatement(query);
                statement.setInt(1, type.getId());
                statement.setInt(2, festival.getFestivalId());
                statement.executeUpdate();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                if (statement != null)
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
            }

        }
        return false;
    }
}
