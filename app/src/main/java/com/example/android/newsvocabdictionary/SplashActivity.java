package com.example.android.newsvocabdictionary;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.newsvocab.dictionary.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pixplicity.easyprefs.library.Prefs;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import base.DBAdapter;
import base.MyDrawerActivity;


public class SplashActivity extends ActionBarActivity {
String DATABASE_VERSION;
String APP_VERSION;
boolean isNewDatabaseVersion;
boolean isNewAPPVersion;
    public static PendingIntent pendingIntent;

    private String TABLE_NAME;


    private ArrayList<String> TABLELANG_NAME_LIST ;

int hour,min,ampm;


    ProgressDialog dialog;
    DBAdapter db;

    @Override
    protected void onResume() {
        super.onResume();

    }


    void startalarm(){

        Intent myIntent = new Intent(SplashActivity.this, MyNotification.class);
        pendingIntent = PendingIntent.getService(SplashActivity.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Date dat  = new Date();//initializes to now
        Calendar calendar = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
      //  calendar.setTimeInMillis(System.currentTimeMillis());

      /*  editor.putInt("hours", hours);
        editor.putInt("minutes", Integer.valueOf(minutes));
        editor.putInt("ampm",  Integer.valueOf(timeSet));*/


         hour = Prefs.getInt("hours", -1);
         min = Prefs.getInt("minutes", -1);
         ampm = Prefs.getInt("ampm", -1);

        //calendar.add(Calendar.SECOND, 15);
//AM = 0  and PM=1


    //    Toast.makeText(SplashActivity.this,""+hour+"/"+min+"/"+ampm,Toast.LENGTH_LONG).show();

        if(hour==-1 && min==-1 && ampm ==-1){

        //    Toast.makeText(SplashActivity.this,"inside if",Toast.LENGTH_SHORT).show();

            calendar.set(Calendar.HOUR, 10);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 2);
            calendar.set(Calendar.AM_PM,Calendar.AM);
        }
        else {
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 2);
            calendar.set(Calendar.AM_PM, ampm);
        }

        if(calendar.before(cal_now)){//if its in the past increment
            calendar.add(Calendar.DATE,1);
        }
        /*calendar.add(Calendar.HOUR, 7);
        calendar.add(Calendar.MINUTE, 58);
*/


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        TABLELANG_NAME_LIST = new ArrayList<String>();

        TABLELANG_NAME_LIST.add(0,"D_Word_ENG_HIN");
        TABLELANG_NAME_LIST.add(1,"D_Word_ENG_TAM");
        TABLELANG_NAME_LIST.add(2,"D_Word_ENG_MAL");
        TABLELANG_NAME_LIST.add(3,"D_Word_ENG_GUJ");
        TABLELANG_NAME_LIST.add(4,"D_Word_ENG_TEL");
        TABLELANG_NAME_LIST.add(5,"D_Word_ENG_MAR");
        TABLELANG_NAME_LIST.add(6,"D_Word_ENG_BEN");
        TABLELANG_NAME_LIST.add(7,"D_Word_ENG_KAN");

        db= new DBAdapter(SplashActivity.this);

        //checking the first time installed app
        DATABASE_VERSION=Prefs.getString("DATABASE_VERSION","NULL");
        APP_VERSION=Prefs.getString("APP_VERSION", "NULL");


      //  Toast.makeText(getApplicationContext(),"value is: "+DATABASE_VERSION,Toast.LENGTH_LONG).show();

        if(DATABASE_VERSION.trim().equals("NULL")){
           //  Toast.makeText(getApplicationContext(),"No Database is Installed",Toast.LENGTH_LONG).show();
            TABLE_NAME=TABLELANG_NAME_LIST.get(0);
            Prefs.putString("language_name", TABLE_NAME);


          //  startalarm();




            firstProcess();

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            Prefs.putString("date1",formattedDate);

        }
        else{
            //process for vevery time openscreen



            int languagepos = Prefs.getInt("language", 0);
            String shared_language_name = TABLELANG_NAME_LIST.get(languagepos);
            String temp = Prefs.getString("language_name", TABLE_NAME);

            if(shared_language_name.equalsIgnoreCase(temp)){
                secondProcess();
            }
            else{
                TABLE_NAME=TABLELANG_NAME_LIST.get(languagepos);
                Prefs.putString("language_name", TABLE_NAME);
                firstProcess();
            }


        }


        // end of on create method
    }

public void firstProcess(){

        if(checkInternet()){
            processDownloadDatabase();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Connect your Internet",Toast.LENGTH_LONG).show();
        }


    }


public void secondProcess(){

    if(checkInternet()){
        processCheckNewDatabase();
    }
    else{
      //  Toast.makeText(SplashActivity.this,"Offline Mode is Activated",Toast.LENGTH_LONG).show();

        CountDownTimer countDownTimer;
        countDownTimer = new MyCountDownTimer(1000, 1000); // 1000 = 1s
        countDownTimer.start();
        }


}


private void processCheckNewDatabase(){

    ParseQuery<ParseObject> query = ParseQuery.getQuery("D_Version");
   // query.whereEqualTo("DATABASE_VERSION", "ALL3");

    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> parseObjects, ParseException e) {
            if(e==null){
                // check for database

                if(parseObjects.get(0).get("DATABASE_VERSION").toString().trim().equalsIgnoreCase(DATABASE_VERSION)){
                    isNewDatabaseVersion=false;
                   // Toast.makeText(getApplicationContext(),"db"+DATABASE_VERSION,Toast.LENGTH_LONG).show();
                   // Toast.makeText(SplashActivity.this,"You have Latest Version of Database",Toast.LENGTH_LONG).show();

                    CountDownTimer countDownTimer;
                    countDownTimer = new MyCountDownTimer(1000, 1000); // 1000 = 1s
                    countDownTimer.start();

                }
                else{
                    isNewDatabaseVersion=true;
                    showDialogDownloadDatabase();
                }



               /* // check for app
                if(parseObjects.get(0).get("APP_VERSION").toString().trim().equalsIgnoreCase(APP_VERSION)){
                    isNewAPPVersion=false;
                }
                else{
                    isNewAPPVersion=true;
                }*/
            }
            else{
                Toast.makeText(SplashActivity.this,"Error to fetch details !!!",Toast.LENGTH_SHORT).show();
                isNewDatabaseVersion=false;
                isNewAPPVersion=false;
            }

        }
    });
}


private void processDownloadDatabase(){

    dialog= ProgressDialog.show(this,"Please Wait","downloading dictionary from server...",true);
    dialog.setCancelable(false);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("D_Version");
        // query.whereEqualTo("DATABASE_VERSION", "ALL3");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e==null){
                    // check for database
                   // Prefs.putString("DATABASE_VERSION","V2");
                    processDowloadandSQLinsert(parseObjects.get(0).get("DATABASE_VERSION").toString());



                }
                else{
                    Toast.makeText(getApplicationContext(),"Error to fetch details !!!",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


 /*private void processDowloadandSQLinsertGROPUTABLE(  ) {

     dialog= ProgressDialog.show(this,"Please Wait","downloading dictionary from server...",true);
     dialog.setCancelable(false);

     ParseQuery<ParseObject> query = ParseQuery.getQuery("Word_of_day");
     // query.whereEqualTo("DATABASE_VERSION", "ALL3");
     query.findInBackground(new FindCallback<ParseObject>() {
         @Override
         public void done(List<ParseObject> parseObject, ParseException e) {
             if (e == null) {

                 db.open();
                 db.deleteRecordGROUPTABLE();
                 for (int i = 0; i < parseObject.size(); i++) {
                     db.insertRecordGROUPTABLE(parseObject.get(i).getString("GROUP_NAME"));
                 }

                 db.close();

                 CountDownTimer countDownTimer;
                 countDownTimer = new MyCountDownTimer(10000, 1000); // 1000 = 1s
                 countDownTimer.start();


             } else {
                 Toast.makeText(getApplicationContext(), "Error to fetch details !!!", Toast.LENGTH_SHORT).show();

             }

         }
     });
    }*/

private void processDowloadandSQLinsert(final String dataVersion){

    dialog= ProgressDialog.show(this,"Please Wait","downloading dictionary from server...",true);
    dialog.setCancelable(false);

    ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
    // query.whereEqualTo("DATABASE_VERSION", "ALL3");
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> parseObject, ParseException e) {
            if (e == null) {
                // check for database
                // check for database
                db.open();
                db.deleteRecord();
                for (int i = 0; i < parseObject.size(); i++) {

                    db.insertRecord(parseObject.get(i).getString("CATG_ID"),parseObject.get(i).getString("GROUP_NAME"), parseObject.get(i).getString("WORD"), parseObject.get(i).getString("PRON_ENG")
                            , parseObject.get(i).getString("MEANING_HIN"), parseObject.get(i).getString("MEANING2"), parseObject.get(i).getString("MEANING3")
                            , parseObject.get(i).getString("MEANING4"), parseObject.get(i).getString("MEANING5"), parseObject.get(i).getString("EX1")
                            , parseObject.get(i).getString("EX2"), parseObject.get(i).getString("EX3"), parseObject.get(i).getString("EX4")
                            , parseObject.get(i).getString("EX5"), parseObject.get(i).getString("MATCH1"), parseObject.get(i).getString("MATCH2")
                            , parseObject.get(i).getString("MATCH3"), parseObject.get(i).getString("MATCH4"), parseObject.get(i).getString("MATCH5")
                            , parseObject.get(i).getString("HIN_EX1"), parseObject.get(i).getString("HIN_EX2"), parseObject.get(i).getString("HIN_EX3")
                            , parseObject.get(i).getString("HIN_EX4"), parseObject.get(i).getString("HIN_EX5")
                    );

                }

                db.close();

                Prefs.putString("DATABASE_VERSION", dataVersion);
                dialog.dismiss();

                CountDownTimer countDownTimer;
                countDownTimer = new MyCountDownTimer(10000, 1000); // 1000 = 1s
                countDownTimer.start();

            //    processDowloadandSQLinsertGROPUTABLE();


            } else {
                Toast.makeText(getApplicationContext(), "Error to fetch details !!!", Toast.LENGTH_SHORT).show();

            }

        }
    });
}



private boolean checkInternet(){
  /*ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(SplashActivity.this.CONNECTIVITY_SERVICE);
    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
        //we are connected to a network
        // Toast.makeText(SplashActivity.this,"Internet is connected",Toast.LENGTH_SHORT).show();
        return true;
    } else {
        return false;
    }*/
  return true;

}

private void showDialogDownloadDatabase(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
    alertDialog.setTitle("Notification");

    // Setting Dialog Message
    alertDialog.setMessage("New Version of Database is availabel. \n Would you like to download?");

    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            processDownloadDatabase();
        }
    });

    // Setting Negative "NO" Button
    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
            CountDownTimer countDownTimer;
            countDownTimer = new MyCountDownTimer(1000, 1000); // 1000 = 1s
            countDownTimer.start();
        }
    });

    // Showing Alert Message
    alertDialog.show();
}

private void showDialogDownloadApplication(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setTitle("Notification");

        // Setting Dialog Message
        alertDialog.setMessage("New Version of Application is availabel. \n Would you like to download?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {

            Intent i = new Intent(SplashActivity.this, MyDrawerActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }
//end of main class
}
