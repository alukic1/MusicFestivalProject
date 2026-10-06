package unibl.etf.bp.festival.musicfestival.Model;

public class Performer {
    private Integer PerformerId;

    public Performer(){}
    public Performer(Integer PerformerId) {
        this.PerformerId = PerformerId;
    }
    public Integer getPerformerId() {
        return PerformerId;
    }
    public void setPerformerId(Integer performerId) {
        PerformerId = performerId;
    }

    @Override
    public String toString() {
        return "Performer{" + "PerformerId=" + PerformerId + '}';
    }
}
