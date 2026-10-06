package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Country {
    private Integer countryId;
    private String countryName;
    public Country(){}
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public static Country getCountryId(int countryId) {
        Country country = new Country();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from drzava where IdDrzave=?");
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                country.setCountryId(rs.getInt("IdDrzave"));
                country.setCountryName(rs.getString("Naziv"));
                return country;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
