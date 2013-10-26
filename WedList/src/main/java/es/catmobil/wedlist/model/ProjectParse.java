package es.catmobil.wedlist.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by mferran on 26/10/13.
 */
@ParseClassName("Project")
public class ProjectParse extends ParseObject {
    public ProjectParse(){

    }
    public String getName() {
        return getString("name");
    }

    public void setName(String name){
    }

    public String getDescription() {
        return  getString("description");
    }

    public void setDescription(String description) {

    }





}
