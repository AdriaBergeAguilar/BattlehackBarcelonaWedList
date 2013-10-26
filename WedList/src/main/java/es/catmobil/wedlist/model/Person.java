package es.catmobil.wedlist.model;

/**
 * Created by Bernat on 26/10/13.
 */
public class Person extends  Persistant{
    private String name;
    private String email;
    private String image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
