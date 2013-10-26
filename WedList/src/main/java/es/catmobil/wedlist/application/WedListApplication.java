package es.catmobil.wedlist.application;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.Parse;

import java.util.Date;

import es.catmobil.wedlist.model.Project;

/**
 * Created by mferran on 26/10/13.
 */
public class WedListApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Gson gson = new Gson();

        Project project = new Project();
        project.setName("Ferrari");
        project.setImage("http://www.diariomotor.com/imagenes/ferrari-430-scuderia-2.jpg");
        project.setDate(new Date(System.currentTimeMillis()));
        project.setEmail("user1-facilitator@gmail.com");
        project.setDescription("I want a ferrari!");
        project.setExtras("A");


        Log.i("WED-TAG", gson.toJson(project));

        project = new Project();
        project.setName("Torre eifel");
        project.setImage("http://machbel.com/fotos/2010/12/Torre-Eiffel-Par%C3%ADs-Vista-frontal-de-la-torre-Eiffel-desde-los-campos-de-Marte.-Paris-Francia.jpg");
        project.setDate(new Date(System.currentTimeMillis()));
        project.setEmail("user1-facilitator@gmail.com");
        project.setDescription("Repair this old tower");
        project.setExtras("B");

        Log.i("WED-TAG", gson.toJson(project));


        Log.v("PARSE", "initializing ");
        Parse.initialize(this, "DH2cziL69zkg0x3QvDpPFkYvwsBtJXyUBWRRqAzT", "PwjyxjWlIwdNnvJylWG3KYtsAWJijqWrNzlvFsvI");
           /* ParseObject testObject = new ParseObject("TestObject");
            testObject.put("foo", "bar");
            testObject.saveInBackground();
            */
    }
}


