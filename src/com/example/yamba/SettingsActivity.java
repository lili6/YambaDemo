package com.example.yamba;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lili on 14/12/16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager().beginTransaction().add(
                    android.R.id.content,fragment,
                    fragment.getClass().getSimpleName()

            ).commit();
        }
    }
}
