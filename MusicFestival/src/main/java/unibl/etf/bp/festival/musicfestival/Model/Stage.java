package unibl.etf.bp.festival.musicfestival.Model;

import unibl.etf.bp.festival.musicfestival.DBConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stage {
    private Integer stageId;
    private String stageName;
    private String stageLabel;
    private Festival festival;

    public Stage(Integer stageId, String stageName, String stageLabel, Festival festival) {
        this.stageId = stageId;
        this.stageName = stageName;
        this.stageLabel = stageLabel;
        this.festival = festival;
    }
    public Stage(){}

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageLabel() {
        return stageLabel;
    }

    public void setStageLabel(String stageLabel) {
        this.stageLabel = stageLabel;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public static Stage getStageById(Integer id){
        Stage stage = new Stage();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from bina where IdBine=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                stage.setStageId(rs.getInt("IdBine"));
                stage.setStageName(rs.getString("Naziv"));
                stage.setStageLabel(rs.getString("Oznaka"));
                stage.setFestival(Festival.getFestivalById(rs.getInt("IdFestivala")));


                return stage;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Stage getStageByName(String name){
        Stage stage = new Stage();
        try{
            Connection c = ConnectionPool.getInstance();
            PreparedStatement ps = null;
            ps = c.prepareStatement("select * from bina where Naziv=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                stage.setStageId(rs.getInt("IdBine"));
                stage.setStageName(rs.getString("Naziv"));
                stage.setStageLabel(rs.getString("Oznaka"));
                stage.setFestival(Festival.getFestivalById(rs.getInt("IdFestivala")));


                return stage;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
    //CRUD

