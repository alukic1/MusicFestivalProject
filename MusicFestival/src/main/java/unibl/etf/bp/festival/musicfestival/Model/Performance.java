package unibl.etf.bp.festival.musicfestival.Model;

import java.time.LocalDateTime;

public class Performance {
    private Performer performer;
    private Stage stage;
    private LocalDateTime startTime;

    public Performance(){}
    public Performance(Performer performer, Stage stage, LocalDateTime startTime){
        this.performer = performer;
        this.stage = stage;
        this.startTime = startTime;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


}
