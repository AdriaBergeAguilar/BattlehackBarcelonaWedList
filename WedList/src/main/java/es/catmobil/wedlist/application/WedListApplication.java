package es.catmobil.wedlist.application;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by mferran on 26/10/13.
 */
public class WedListApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
            Log.v("PARSE", "initializing ");
            Parse.initialize(this, "DH2cziL69zkg0x3QvDpPFkYvwsBtJXyUBWRRqAzT", "PwjyxjWlIwdNnvJylWG3KYtsAWJijqWrNzlvFsvI");
           /* ParseObject testObject = new ParseObject("TestObject");
            testObject.put("foo", "bar");
            testObject.saveInBackground();
            */
    }
}


