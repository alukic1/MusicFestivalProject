package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketType {
    private Integer id;
    private String name;


    public TicketType() {}
    public TicketType(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static TicketType getTicketTypeById(Integer id) {

        TicketType ticketType = new TicketType();
            try{
                Connection c = ConnectionPool.getInstance();
                PreparedStatement ps = null;
                ps = c.prepareStatement("select * from vrsta_ulaznice where IdVrsteUlaznice=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    ticketType.setId(rs.getInt("IdVrsteUlaznice"));
                    ticketType.setName(rs.getString("Naziv"));


                    return ticketType;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            return null;

    }
    public static TicketType getTicketTypeByName(String name) {

        TicketType ticketType = new TicketType();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from vrsta_ulaznice where Naziv=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ticketType.setId(rs.getInt("IdVrsteUlaznice"));
                ticketType.setName(rs.getString("Naziv"));

                return ticketType;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }
}
