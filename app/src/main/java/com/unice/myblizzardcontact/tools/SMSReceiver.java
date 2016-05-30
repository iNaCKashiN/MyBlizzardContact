package com.unice.myblizzardcontact.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.unice.myblizzardcontact.activities.contacts.AddContactActivity;
import com.unice.myblizzardcontact.activities.contacts.ContactsActivity;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 28/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class listen all sms receive and launch the good activity.
 */
public class SMSReceiver extends BroadcastReceiver {
    //-----------------------------------------------
    //Fields

    private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";

    private final static Pattern pattern = Pattern.compile("^(\\{@MyBlizzardContact\\})");

    //-----------------------------------------------
    //Constructor

    //-----------------------------------------------
    //Getters and setters

    //-----------------------------------------------
    //Methods

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_RECEIVE_SMS)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    Matcher matcher = pattern.matcher(messages[0].getMessageBody());
                    if (matcher.find()) {
                        Intent addContactsActivity = new Intent(context, AddContactActivity.class);
                        addContactsActivity.putExtra("friend", messages[0].getMessageBody());
                        addContactsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(addContactsActivity);
                    }
                }
            }
        }
    }
}
