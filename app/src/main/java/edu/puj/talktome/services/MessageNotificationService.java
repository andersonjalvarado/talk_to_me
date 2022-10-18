package edu.puj.talktome.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import edu.puj.talktome.activities.BasicActivity;
import edu.puj.talktome.utils.PermissionHelper;
import lombok.Getter;

public class MessageNotificationService {
    /*public static final String TAG = MessageNotificationService.class.getName();
    public static final int JOB_ID = 10;
    public static final String CHANNEL_ID = "20";

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = mDatabase.getReference(DatabaseRoutes.MESSAGES_PATH);
    AtomicInteger counter = new AtomicInteger(1);

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, MessageNotificationService.class, JOB_ID, intent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Messages Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notification for users different than me");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message lastMessage = snapshot.getValue(Message.class);
                String email = intent.getStringExtra("email");
                if (!lastMessage.getFrom().equals(email)) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_baseline_email_24);
                    builder.setContentTitle(String.format("%s says:", lastMessage.getFrom()));
                    builder.setContentText(lastMessage.getMessage());
                    Intent destIntent = new Intent(MessageNotificationService.this, MainActivity.class);
                    builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, destIntent, PendingIntent.FLAG_IMMUTABLE));
                    builder.setAutoCancel(true);
                    NotificationManagerCompat manager =  NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(counter.getAndIncrement(), builder.build());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}
