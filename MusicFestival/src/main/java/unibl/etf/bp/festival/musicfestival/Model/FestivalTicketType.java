package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FestivalTicketType {
    private Integer festivalId;
    private Integer ticketTypeId;
    private BigDecimal price;
    private Integer maxNumTickets;
    private String ticketTypeName;

    public FestivalTicketType() {}

    public FestivalTicketType(Integer festivalId,Integer ticketTypeId, BigDecimal price, Integer maxNumTickets, String ticketTypeName) {
        this.festivalId = festivalId;
        this.ticketTypeId = ticketTypeId;
        this.price = price;
        this.maxNumTickets = maxNumTickets;
        this.ticketTypeName = ticketTypeName;
    }

    public Integer getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Integer festivalId) {
        this.festivalId = festivalId;
    }

    public Integer getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Integer ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMaxNumTickets() {
        return maxNumTickets;
    }

    public void setMaxNumTickets(Integer maxNumTickets) {
        this.maxNumTickets = maxNumTickets;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public static FestivalTicketType getFestTicketTypeByIds(Integer festivalId, Integer ticketTypeId) {

        FestivalTicketType ticketType = new FestivalTicketType();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from festival_vrsta_ulaznice where VRSTA_ULAZNICE_IdVrsteUlaznice=? and FESTIVAL_IdFestivala=?");
            ps.setInt(1, ticketTypeId);
            ps.setInt(2, festivalId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ticketType.setPrice(rs.getBigDecimal("Cijena"));
                ticketType.setMaxNumTickets(rs.getInt("MaxBrojUlaznica"));
                ticketType.setTicketTypeId(ticketTypeId);
                ticketType.setFestivalId(festivalId);

                return ticketType;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
