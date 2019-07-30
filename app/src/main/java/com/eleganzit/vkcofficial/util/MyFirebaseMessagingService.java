package com.eleganzit.vkcofficial.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(remoteMessage.getData().get("message")+"");
            showNotification1(""+jsonObject.getString("message"));
          //  sendNotification("VKC Official",""+jsonObject.getString("message"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("notificationdata",""+remoteMessage.getData());
    }

    private void showNotification1( String text)


    {

        Intent i = new Intent(this, HomeActivity.class);



        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)

                .setAutoCancel(true)

                .setDefaults(Notification.DEFAULT_SOUND)



                .setSmallIcon(getNotificationIcon())

                .setContentText(text)

        .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text))


                .setContentTitle("VKC Official")

                .setSound(uri)

                .setVibrate(new long[]{1000,500})

                .setContentIntent(pendingIntent)

                .setColor(getResources().getColor(R.color.colorPrimary));



        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            String id = "id_product";

            // The user-visible name of the channel.

            CharSequence name = "VKC Official";

            // The user-visible description of the channel.

            String description = text;

            int importance = NotificationManager.IMPORTANCE_HIGH;

            @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(id, name, importance);

            // Configure the notification channel.

            mChannel.setDescription(description);

            mChannel.enableLights(true);

            // Sets the notification light color for notifications posted to this

            // channel, if the device supports this feature.

            mChannel.setLightColor(Color.RED);

            assert manager != null;

            manager.createNotificationChannel(mChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"id_product")

                    .setSmallIcon(getNotificationIcon())//your app icon

                    .setBadgeIconType(getNotificationIcon()) //your app icon
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))

                    .setChannelId(id)

                    .setSound(uri)

                    .setVibrate(new long[]{1000,500})

                    .setContentTitle(name)

                    .setAutoCancel(true)

                    .setContentIntent(pendingIntent)

                    .setNumber(1)

                    .setColor(255)

                    .setContentText(text)

                    .setWhen(System.currentTimeMillis());

            manager.notify(m, notificationBuilder.build());

        }





        manager.notify(m, builder.build());





    }

    private void sendNotification(String title, String messageBody) {
      //  Logger.printLog(TAG, messageBody);

        String id = "id_product";
        Intent intent = new Intent(this, HomeActivity.class);

        if (!TextUtils.isEmpty(title)) {
            PendingIntent pendingIntent;
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(this, (int) System
                                .currentTimeMillis() /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
            } else {
                pendingIntent = PendingIntent.getActivity(this, (int) System
                                .currentTimeMillis() /* Request code */, new Intent(),
                        PendingIntent.FLAG_ONE_SHOT);
            }

            Bitmap notificationLargeIconBitmap = BitmapFactory.decodeResource(
                    getResources(), R.drawable.icon_official);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder;
            if (pendingIntent != null) {
                notificationBuilder = new NotificationCompat.Builder(this,
                        id)
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setLargeIcon(notificationLargeIconBitmap)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this,
                        id)
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setLargeIcon(notificationLargeIconBitmap)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				int smallIconViewId = getResources().getIdentifier("ic_push_notification", "id",
						android.R.class.getPackage().getName());

				if (smallIconViewId != 0) {
					if (notificationBuilder.build().contentIntent != null) {
						notificationBuilder.build().contentView.setViewVisibility(smallIconViewId,
								View.INVISIBLE);
					}

					if (notificationBuilder.build().headsUpContentView != null) {
						notificationBuilder.build().headsUpContentView.setViewVisibility
								(smallIconViewId, View.VISIBLE);
					}

					if (notificationBuilder.build().bigContentView != null) {
						notificationBuilder.build().bigContentView.setViewVisibility
								(smallIconViewId, View.VISIBLE);
					}
				}
			}*/
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

            if (notificationManager != null) {
                notificationManager.notify(m, notificationBuilder.build());
            }
        }
    }

    private int getNotificationIcon () {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.icon_official : R.mipmap.ic_launcher;
    }
}
