package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Festival {
    private Integer festivalId;
    private String festivalName;
    private String festivalDescription;
    private String festivalEmail;
    private Calendar festivalDate;

    public Festival(){}
    public Festival(String festivalName, String festivalDescription, String festivalEmail, Calendar festivalDate) {
        this.festivalName = festivalName;
        this.festivalDescription = festivalDescription;
        this.festivalEmail = festivalEmail;
        this.festivalDate = festivalDate;
    }

    public Festival(Integer id, String festivalName, String festivalDescription, String festivalEmail, Calendar festivalDate) {
        this.festivalId = id;
        this.festivalName = festivalName;
        this.festivalDescription = festivalDescription;
        this.festivalEmail = festivalEmail;
        this.festivalDate = festivalDate;
    }
    public Integer getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Integer festivalId) {
        this.festivalId = festivalId;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getFestivalDescription() {
        return festivalDescription;
    }

    public void setFestivalDescription(String festivalDescription) {
        this.festivalDescription = festivalDescription;
    }

    public String getFestivalEmail() {
        return festivalEmail;
    }

    public void setFestivalEmail(String festivalEmail) {
        this.festivalEmail = festivalEmail;
    }

    public Calendar getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(Calendar festivalDate) {
        this.festivalDate = festivalDate;
    }

    public static Festival getFestivalByName(String festivalName) {
        Festival festival = new Festival();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from festival where Naziv=?");
            ps.setString(1, festivalName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                festival.setFestivalId(rs.getInt("IdFestivala"));
                festival.setFestivalName(rs.getString("Naziv"));


                return festival;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Festival getFestivalById(int festivalId) {
        Festival festival = new Festival();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from festival where IdFestivala=?");
            ps.setInt(1, festivalId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                festival.setFestivalId(rs.getInt("IdFestivala"));
                festival.setFestivalName(rs.getString("Naziv"));

                return festival;


            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
