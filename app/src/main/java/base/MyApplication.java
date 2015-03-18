package base;

import android.app.Application;

import com.parse.Parse;
import com.pixplicity.easyprefs.library.Prefs;

import net.qiujuer.genius.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by krishn on 2/1/2015.
 */
public class MyApplication extends Application{
    static final String TAG = "MyApp";
    String DATABASE_VERSION="NULL";



    @Override
    public void onCreate() {
        super.onCreate();
        // app key --  client key
        Parse.initialize(this, "JVXvePdHYQbDCaSyVNGOSESbwvSRZfXSYvvy5JGk", "g554oAsPt05q2Cgv4OnBMrUuBHCZWIzEBLIeUt34");


        // intializing the Shared prefernces
        Prefs.initPrefs(this);



//end of oncreate
    }




 //end of main clasa
}
