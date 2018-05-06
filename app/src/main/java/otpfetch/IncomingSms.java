package otpfetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by Harsh on 3/29/2016.
 */
public class IncomingSms extends BroadcastReceiver {
    SmsMessage[] msgs1 = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("broading", "..");
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                msgs1 = new SmsMessage[pdusObj.length];
                Log.i("broadingss", "from broadcast " + Arrays.toString(pdusObj) + Arrays.toString(msgs1));
                for (int i = 0; i < msgs1.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    Log.i("broadingss", "from broadcast " + senderNum);
                    String message = currentMessage.getDisplayMessageBody();
                    try {

                        if ((senderNum.toUpperCase().endsWith("Cashya")||
                                senderNum.endsWith("CASHYA") )&&
                                message.contains("verification"))
                            {
                            OtpFetch otpf = new OtpFetch();
                            otpf.receivedSms(message);
                            intent = new Intent(context.getApplicationContext(), OtpFetch.class);
                            intent.putExtra("message", message);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.i("broading", "ERRRRRRRRRRRor " + e);

                    }
                }
            }
        } catch (Exception e) {
            Log.i("broading", "ERRRRRRRRRRRor " + e);
        }
    }
}