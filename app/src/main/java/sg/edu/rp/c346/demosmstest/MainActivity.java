package sg.edu.rp.c346.demosmstest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSendSMS;

    private BroadcastReceiver br; //register the receiver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        btnSendSMS = findViewById(R.id.buttonSendSMS);

        //register the receiver
        br = new Broadcast();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(br , filter);

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcast.sent");
                sendBroadcast(intent);
            }
        });

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("5556", null, "Hello 5556", null, null);

    }


    //unregister the receiver
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }



    private void checkPermission() {
        int permissionSendSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int permissionRecvSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);
        if (permissionSendSMS != PackageManager.PERMISSION_GRANTED &&
                permissionRecvSMS != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissionNeeded, 1);
        }
    }

}