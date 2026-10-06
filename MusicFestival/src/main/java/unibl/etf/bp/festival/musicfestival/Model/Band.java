package unibl.etf.bp.festival.musicfestival.Model;

import java.util.ArrayList;

public class Band extends Performer{

    private String name;

    public Band(){ super();}
    public Band(String name){
        super();
        this.name = name;
    }
    public Band(Integer id, String name){
        super(id);
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
