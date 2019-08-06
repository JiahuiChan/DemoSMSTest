package sg.edu.rp.c346.demosmstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast my_toast = Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT);
        my_toast.show();

        // SMS messages are retrieved from the intent's extra using the key "pdus"
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0], format);
                } else {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                }
                // Obtain the originating phone number (sender's number)
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                // Obtain the message body
                String message = currentMessage.getDisplayMessageBody();
                // Display in Toast
                Toast.makeText(context, "Sender Number: " + senderNum +
                        ", Message: " + message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Error: " + e);
        }

    }
}