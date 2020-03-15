package com.example.safer;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.media.VolumeProviderCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class VoiceService extends Service implements RecognitionListener{

    private static final String KWS_SEARCH = "wakeup";
    private SpeechRecognizer recognizer;
    public SharedPreferences sharedPref;
    public String FileName="data";
    boolean voice_activation;
    int count1=0;
    MediaSessionCompat mediaSession;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        showNotification();

        mediaSession = new MediaSessionCompat(this, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0) //you simulate a player which plays something.
                .build());

        //this will only work on Lollipop and up, see https://code.google.com/p/android/issues/detail?id=224134
        VolumeProviderCompat myVolumeProvider =
                new VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/100, /*initial volume level*/50) {
                    @Override
                    public void onAdjustVolume(int direction) {
                        if (direction==1) {
                            count1++;
                        }
                        if (count1>=3){
                            Toast.makeText(VoiceService.this, "trigger", Toast.LENGTH_SHORT).show();

                            count1=0;
                            openactivity5();

                        }
                /*
                -1 -- volume down
                1 -- volume up
                0 -- volume button released
                 */
                    }
                };

        mediaSession.setPlaybackToRemote(myVolumeProvider);
        mediaSession.setActive(true);

        return START_STICKY;
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private void showNotification() {
        new SetupTask(this).execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "voice_command_activation_status";
            String channelName =  "Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            chan.setShowBadge(false);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification1 = notificationBuilder.setOngoing(true)
                    .setContentText("Background Service Running")
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setChannelId(NOTIFICATION_CHANNEL_ID)
                    .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                    .build();
            startForeground(464, notification1);
        }
        else{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"voice_command_activation_status")
                    .setContentText("Background Service Running")
                    .setOngoing(true);
            Notification notification = builder.build();
            startForeground(1989, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private static class SetupTask extends AsyncTask<Void, Void, Exception> {
        WeakReference<VoiceService> serviceReference;
        SetupTask(VoiceService service) {
            this.serviceReference = new WeakReference<>(service);
        }
        @Override
        protected Exception doInBackground(Void... params) {
            try {
                Assets assets = new Assets(serviceReference.get());
                File assetDir = assets.syncAssets();
                serviceReference.get().setupRecognizer(assetDir);

            } catch (IOException e) {
                return e;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Exception result) {
            if (result == null) {
                serviceReference.get().switchSearch(KWS_SEARCH);
            }
        }
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();
        sharedPref = getSharedPreferences(FileName, Context.MODE_PRIVATE);
        if (text.equals("help")){
            Intent intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(this, "sos triggered", Toast.LENGTH_SHORT).show();
        }
        switchSearch(KWS_SEARCH);
    }
    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
        if (recognizer != null) {
            if (recognizer.getSearchName() != null) {
                if (!recognizer.getSearchName().equals(KWS_SEARCH)) switchSearch(KWS_SEARCH);
            }
        }
    }

    public void switchSearch(String searchName) {
        recognizer.stop();
        recognizer.startListening(searchName,1000);
    }

    public void setupRecognizer(File assetsDir) throws IOException {
        SharedPreferences sharedPreferences0=getSharedPreferences("data",MODE_PRIVATE);
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .getRecognizer();
        recognizer.addListener(this);
        try {
            FileOutputStream fileout=openFileOutput("keyphrase.list", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("help/1e-10");
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file1 = new File(getApplicationContext().getFilesDir(), "keyphrase.list");
        recognizer.addKeywordSearch(KWS_SEARCH, file1);
    }

    @Override
    public void onError(Exception error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        voice_activation = sharedPref.getBoolean("voice_activation", true);

        if (voice_activation) {
            Intent service = new Intent(getApplicationContext(), VoiceService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(service);
            } else {
                startService(service);
            }
        }
        super.onTaskRemoved(rootIntent);
    }

    public void openactivity5(){
        Intent intent=new Intent(this,sosactivated.class);
        startActivity(intent);
    }

}
