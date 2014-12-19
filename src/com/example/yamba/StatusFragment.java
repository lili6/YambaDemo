package com.example.yamba;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.marakana.android.yamba.clientlib.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.clientlib.YambaClientException;

/**
 * Created by lili on 14/12/15.
 */
public class StatusFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "StatusFragment";
    SharedPreferences prefs;

    private EditText editStatus;

    private Button buttonTweet;
    private TextView textCount;
    private  int defautTextColor;
    private ImageButton bttonPicture;
    private ImageButton bttonCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_status, container, false);

        editStatus =(EditText) view.findViewById(R.id.editStatus);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        textCount=(TextView)view.findViewById(R.id.textCount);
        buttonTweet.setOnClickListener(this);

        defautTextColor = textCount.getTextColors().getDefaultColor();
        editStatus.addTextChangedListener(new TextWatcher() {
                      @Override
                      public void afterTextChanged(Editable s) {
                          int count = 140 - editStatus.length();
                          textCount.setText(Integer.toString(count));
                          textCount.setTextColor(Color.GREEN);
                          if (count < 10)
                              textCount.setTextColor(Color.RED);
                          else
                              textCount.setTextColor(defautTextColor);


                  }

        @Override
        public void beforeTextChanged(CharSequence s,
        int start, int count,
        int after) {

        }
        @Override
                public void onTextChanged(CharSequence s,
        int start,int before,int count) {

        }
    });
        return view;
    }


    @Override
    public void onClick(View v) {
        String status= editStatus.getText().toString();
       /* Toast toast =  Toast.makeText(this, R.string.toast_status, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/
        Log.d(TAG,"onClicked with status:" + status);
        new PostTask().execute(status);
    }

    private final class PostTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String username = prefs.getString("username","");
            String password = prefs.getString("password","");

            YambaClient yambaClient = new YambaClient("student","password");
            if ((TextUtils.isEmpty(username)) ||
            (TextUtils.isEmpty(password))) {
                getActivity().startActivity(
                        new Intent(getActivity(),SettingsActivity.class)
                );
                return "Please update your username and password!!";
            }
            Log.d(TAG,"username=" +username +"||password="+password );

            Log.d(TAG, "status=" + params[0]);
            try {
                //此demo中发送消息必须为英文，yamba网站暂时不支持中文
                yambaClient.postStatus(params[0]);
                return "Successfully posted";
            } catch (YambaClientException e) {
                e.printStackTrace();
                return "Failed to post to yamba service";
            }




        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(StatusFragment.this.getActivity(), result,
                    Toast.LENGTH_LONG).show();
        }
    }
}
