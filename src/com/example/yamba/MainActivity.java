package com.example.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by lili on 14/12/16.
 */
public class MainActivity extends Activity {

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;
            case R.id.action_tweet:
//                startActivity(new Intent("com.example.yamba.action.tweet"));
                startActivity(new Intent(this,StatusActivity.class));
                return true;
            case R.id.itemServiceStart:
                startService(new Intent(this,RefreshService.class));
            default:
                return false;
        }

    }
}
