package com.example.yamba;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.marakana.android.yamba.clientlib.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.clientlib.YambaClientException;

import java.util.List;

/**
 * Created by lili on 14/12/16.
 */
public class RefreshService extends IntentService {

    private static final  String TAG ="RefreshService";
    public RefreshService() {
        super(TAG);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }
    /*
    EXCUTES ON A WORKER THREAD
     */
    @Override
    protected  void onHandleIntent(Intent intent) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String username=prefs.getString("username","");
        final String password = prefs.getString("password","");
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please update your username and password"
            , Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG,"onStarted");
        YambaClient cloud = new YambaClient(username,password);

        Log.d(TAG,"username=" +username +"||password="+password );
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            List<YambaClient.Status> timeline = cloud.getTimeline(20);
            for(YambaClient.Status status:timeline) {
                Log.d(TAG,String.format("%s:%s",status.getUser(),
                        status.getMessage()));
//                Log.d(TAG,String.format(status.getMessage()));
                values.clear();
                values.put(StatusContract.Column.ID,status.getId());
                values.put(StatusContract.Column.USER,status.getUser());
                values.put(StatusContract.Column.MESSAGE,status.getMessage());
                values.put(StatusContract.Column.CREATED_AT,status.getCreatedAt().getTime());
                db.insertWithOnConflict(StatusContract.TABLE,null,values,
                        SQLiteDatabase.CONFLICT_IGNORE);

                db.execSQL("select count(*) from " +StatusContract.TABLE);
            }
        } catch (YambaClientException e) {
            Log.e(TAG,"Failed to fetch the timeline",e);
            Log.d(TAG,"username=" +username +"||password="+password );
            e.printStackTrace();
        }
        return;
    }

//    @Override
//    public int onStartCommand(Intent intent,int flags,int startId) {
//        Log.d(TAG,"onStarted");
//        return START_STICKY;
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroyed");
    }
}
