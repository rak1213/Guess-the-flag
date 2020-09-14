package com.rak12.flagquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Region;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ChoiceFormat;
import java.util.Set;

import static android.icu.text.MessagePattern.ArgType.CHOICE;

public class MainActivity extends AppCompatActivity {
    public static final String c="pref_numberofChoices";
    public static final String r="pref_regionsToInclude";
    private boolean phoneDevice=true;
    private boolean prefrencesChanged=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PreferenceManager.setDefaultValues(this,R.xml.prefrences,false);
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(prefrencesChangedListener);


    }
    @Override
    protected void onStart(){
        super.onStart();
        if(prefrencesChanged)
        {
             FirstFragment quizFrament=(FirstFragment)
                    getSupportFragmentManager().findFragmentById(R.id.quizFragment);
            quizFrament.updateGuessRows(PreferenceManager.getDefaultSharedPreferences(this));
            quizFrament.updateRegions(PreferenceManager.getDefaultSharedPreferences(this));
quizFrament.resetQuiz();
prefrencesChanged=false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent prefrencesIntent=new Intent(this,SettingsActivity.class);
        startActivity(prefrencesIntent);
        return super.onOptionsItemSelected(item);


    }
    private SharedPreferences.OnSharedPreferenceChangeListener prefrencesChangedListener=
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    prefrencesChanged=true;
                    FirstFragment quizFragment = (FirstFragment)
                            getSupportFragmentManager().findFragmentById(R.id.quizFragment);
                     if (key.equals(c)
                     ){quizFragment.updateGuessRows(sharedPreferences);
                     quizFragment.resetQuiz();
                     }
                    else if ( key.equals(r))
                {
                         Set<String> regions=sharedPreferences.getStringSet
                                 (r,null);
                          if (regions !=null&& regions.size()>0)
                          {
                              quizFragment.updateGuessRows(sharedPreferences);
                              quizFragment.resetQuiz();
                          }
                          else{
                              SharedPreferences.Editor editor=sharedPreferences.edit();
                              regions.add(getString(R.string.default_region));
                              editor.putStringSet(r,regions);
                              editor.apply();
                              Toast.makeText(MainActivity.this,R.string.default_region_message,
                                      Toast.LENGTH_SHORT).show();
                          }

                     }
                    Toast.makeText(MainActivity.this,R.string.restarting_quiz,
                            Toast.LENGTH_SHORT).show();
                }
            };
}
