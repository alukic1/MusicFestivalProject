package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class City {
    private Integer cityId;
    private String cityName;
    private Country country;
    private String postalCode;
    public City(){}
    public City(int cityId, String cityName, Country country, String postalCode) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public static City getCityById(int cityId) {
        City city = new City();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from grad where IdGrada=?");
            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                city.setCityId(rs.getInt("IdGrada"));
                city.setCityName(rs.getString("Naziv"));
                city.setPostalCode(rs.getString("PostanskiBroj"));

                return city;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
