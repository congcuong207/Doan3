package com.example.doan3.Activity;
import android.content.Context;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Meeting {
    //đặt id cho room
    public static void videoCall(String idRoom, Context context) {
        try {
            URL server = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(server)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(idRoom)
                .setWelcomePageEnabled(false).build();
        JitsiMeetActivity.launch(context, options);
    }
}

