package unibl.etf.bp.festival.musicfestival.Model;

public class Musician extends Performer{
    private String name ="";
    private String lastname ="";
    private String nickname ="";

    public Musician(String name, String lastname, String nickname) {
        super();
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
    }
    public Musician(Integer id, String name, String lastname, String nickname) {
        super(id);
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
    }

    public Musician() {
        super();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return name + " " + lastname + " " + nickname;
    }
}
