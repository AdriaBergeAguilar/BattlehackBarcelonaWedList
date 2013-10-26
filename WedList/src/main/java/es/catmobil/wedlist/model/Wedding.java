package es.catmobil.wedlist.model;

import android.location.Location;

import java.util.Date;
import java.util.List;

/**
 * Created by Bernat on 26/10/13.
 */
public class Wedding extends  Persistant{
    private String name;
    private String description;
    private Person groomm;
    private Person bride;
    private String email;
    private String place;
    private Date date;
    private Location location;
    private List<Gift> gifts;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getGroomm() {
        return groomm;
    }

    public void setGroomm(Person groomm) {
        this.groomm = groomm;
    }

    public Person getBride() {
        return bride;
    }

    public void setBride(Person bride) {
        this.bride = bride;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
}
