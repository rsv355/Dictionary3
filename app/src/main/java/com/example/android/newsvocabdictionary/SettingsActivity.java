package com.example.android.newsvocabdictionary;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.newsvocab.dictionary.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    ToggleButton toggle;
    Spinner spWordLimit,spLanguage;
    Button btn,btn2;
    private PendingIntent pendingIntent;
    int Langpos;
TextView txttime;
    static final int TIME_DIALOG_ID = 1111;
    private int hour;
    private int minute;

    int hour2,min,ampm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setTitle("Settings");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    //    toggle = (ToggleButton)findViewById(R.id.toggle);
        spWordLimit= (Spinner)findViewById(R.id.spWordLimit);
        spLanguage= (Spinner)findViewById(R.id.spLanguage);
     //   txttime = (TextView)findViewById(R.id.txttime);

       /* Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        txt1.clearAnimation();
        txt1.startAnimation(anim);*/

      /*  txttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });*/








//        ******** display current time on screen Start *******
//
//        final Calendar c = Calendar.getInstance();
//        // Current Hour
//        hour = c.get(Calendar.HOUR_OF_DAY);
//        // Current Minute
//        minute = c.get(Calendar.MINUTE);
//
//        // set current time into output textview
//        updateTime(hour, minute);
//
//        ******** display current time on screen End *******


     /*   int hour = Prefs.getInt("hours", -1);
        int min = Prefs.getInt("minutes", -1);
        int ampm = Prefs.getInt("ampm", -1);

String AMPM;
        String aTime;
        if(ampm == 0){
            AMPM="AM";
        }
        else{
            AMPM="PM";
        }

        if(hour==-1 && min==-1 && ampm ==-1){
             aTime = new StringBuilder().append(10).append(':')
                    .append("30").append(" ").append("AM").toString();

        }
        else {
             aTime = new StringBuilder().append(hour).append(':')
                    .append(min).append(" ").append(AMPM).toString();



        }


        txttime.setText(aTime);
*/

    }




    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour,minute);

        }

    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        txttime.setText(aTime);

        Prefs.putInt("hours", hours);
        Prefs.putInt("minutes", Integer.valueOf(minutes));

        if(timeSet.equals("AM")){
            Prefs.putInt("ampm", 0);
        }
        else{
            Prefs.putInt("ampm",  0);
        }


        startalarm();

    }



    void startalarm(){

        Intent myIntent = new Intent(SettingsActivity.this, MyNotification.class);
        pendingIntent = PendingIntent.getService(SettingsActivity.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Date dat  = new Date();//initializes to now
        Calendar calendar = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        //  calendar.setTimeInMillis(System.currentTimeMillis());

      /*  editor.putInt("hours", hours);
        editor.putInt("minutes", Integer.valueOf(minutes));
        editor.putInt("ampm",  Integer.valueOf(timeSet));*/


        int hour = Prefs.getInt("hours", -1);
        int min = Prefs.getInt("minutes", -1);
        int ampm = Prefs.getInt("ampm", -1);

        //calendar.add(Calendar.SECOND, 15);
//AM = 0  and PM=1


      //  Toast.makeText(SettingsActivity.this,""+hour+"/"+min+"/"+ampm,Toast.LENGTH_LONG).show();

        if(hour==-1 && min==-1 && ampm ==-1){

         //   Toast.makeText(SettingsActivity.this,"inside if",Toast.LENGTH_SHORT).show();

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




void setToggle(){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    Boolean isActive = preferences.getBoolean("notification", true);
    if(isActive)
    {
       toggle.setChecked(true);
    }
    else{
        toggle.setChecked(false);
    }

}

void setWordlimitSpinner(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int pos = preferences.getInt("wordlimit", 2);
        spWordLimit.setSelection(pos);

 }

void setLanguageSpinner(){
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Langpos = preferences.getInt("language", 0);*/

        Langpos = Prefs.getInt("language", 0);
        spLanguage.setSelection(Langpos);


    }
    @Override
    protected void onResume() {
        super.onResume();
      //  toggle.setChecked(true);

        spLanguage.clearFocus();
     //   setToggle();
        setWordlimitSpinner();
        setLanguageSpinner();


spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

        if(position!=Langpos) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
            alertDialog.setTitle("Settings");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure want to change language ?");

            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                    Prefs.putInt("language", position);

                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();




                    /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("language", position);
                    editor.apply();
                    editor.commit();*/
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


spWordLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("wordlimit", position);
                editor.apply();
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle.isChecked()){

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("notification",true);
                    editor.apply();
                    editor.commit();
                    startalarm();
                }
                else{
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("notification",false);
                    editor.apply();
                    editor.commit();
                    cancelalarm();
                }

            }
        });*/
    }

/*void cancelalarm(){
    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    alarmManager.cancel(pendingIntent);
    alarmManager.cancel(SplashActivity.pendingIntent);
}*/


//end of main class
}
