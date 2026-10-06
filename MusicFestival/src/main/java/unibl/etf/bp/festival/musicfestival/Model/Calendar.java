package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Calendar {
    private Integer calendarId;
    private City city;
    private Date startDate;
    private Date endDate;

    public Calendar(){}
    public Calendar(int calendarId, City city, Date startDate, Date endDate) {
        this.calendarId = calendarId;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static Calendar getCalendarById(int calendarId) {
        Calendar calendar = new Calendar();
        try{
        Connection c = ConnectionPool.getInstance();
        PreparedStatement ps = null;
        ps = c.prepareStatement("select * from kalendar where IdKalendara=?");
        ps.setInt(1, calendarId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            calendar.setCalendarId(rs.getInt("IdKalendara"));
            calendar.setStartDate(rs.getDate("DatumOd"));
            calendar.setEndDate(rs.getDate("DatumDo"));
            calendar.setCity(City.getCityById(rs.getInt("IdGrada")));
            return calendar;
        }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
