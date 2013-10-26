package es.catmobil.wedlist.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Bernat on 26/10/13.
 */
public class Project extends  Persistant{

    private String serverId;
    private String name;
    private String description;
    private Person user;
    private String email;
    private String extras;
    private Date date;
    private String image;

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


    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Project{" +
                "serverId='" + serverId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", email='" + email + '\'' +
                ", extras='" + extras + '\'' +
                ", date=" + date +
                ", image='" + image + '\'' +
                ", gifts=" + gifts +
                '}';
    }
}
