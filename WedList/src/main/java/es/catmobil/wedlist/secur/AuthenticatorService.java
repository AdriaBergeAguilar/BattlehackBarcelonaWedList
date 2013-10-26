package es.catmobil.wedlist.secur;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Bernat on 26/10/13.
 */
public class AuthenticatorService extends Service {

    private MyAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();

        if(mAuthenticator == null){
            mAuthenticator = new MyAuthenticator(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
