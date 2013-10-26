package es.catmobil.wedlist.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by mferran on 26/10/13.
 */
@ParseClassName("Weding")
public class WedingParse extends ParseObject {
    public WedingParse (){

    }
    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return  getString("description");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getGroomm() {
        return  getString("groomm;
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
