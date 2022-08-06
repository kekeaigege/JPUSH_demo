package com.example.item_3;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 3; //推送通知最多显示条数
    private NotificationManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //标题
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            //内容
            String contentMes = bundle.getString(JPushInterface.EXTRA_ALERT);
            //附加参数
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            //注册 id  1a0018970a5964ece1f
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "regId:" + regId);
            //
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            //上下文对象,ID,内容,标题,信道名称,级别
            createNotificationChannel(context, "1a0018970a5964ece1f", bundle, title, contentMes, extra);
            Log.i(TAG, "标题:【" + title + "】，内容：【" + contentMes + "】，附加参数:【" + extra + "】");
        } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            Log.i(TAG, "接收到了消息");
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.i(TAG, "接收到的消息是:【" + message + "】");
        } else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            Log.i(TAG, "用户正在打开通知");
        }
    }

    //上下文对象,ID,内容,标题,信道名称,级别
    public void createNotificationChannel(Context context, String channelID, Bundle bundle, String title, String contentMes, String extra) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.i(TAG, "执行createNotificationChannel!!!!!!!!!");

            //创建通知渠道实例
            NotificationChannel channel  = new NotificationChannel(channelID, "supperman",NotificationManager.IMPORTANCE_HIGH);
            // 自定义声音\
            channel.setSound(Uri.parse("android.resource://"  + context.getPackageName() + "/" + R.raw.default_push_sound), Notification.AUDIO_ATTRIBUTES_DEFAULT);//设置通知声音
            //创建和管理通知渠道
            manager.createNotificationChannel(channel);

            //设置消息通知
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext(), channelID);
            Log.i(TAG, "执行notification!!!!!!!!!");
            notification.setAutoCancel(true)
                    .setContentText(title)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.default_push_sound));
//        调用notify()让通知显示出来（第一个参数是ID， 保证每个通知所指定的id都是不同的，第二个参数是notification对象）
//            playSound(context,R.raw.default_push_sound);
            Log.i(TAG, "执行notify!!!!!!!!!");
            manager.notify(1, notification.build());
        }
    }

    /**
     * 适合播放声音短，文件小
     * 可以同时播放多种音频
     * 消耗资源较小
     */
    public static void playSound(Context context, int rawId) {
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        soundPool.load(context, rawId, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
        });
        //第一个参数id，即传入池中的顺序，第二个和第三个参数为左右声道，第四个参数为优先级，第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
//        soundPool.play(1, 1, 1, 0, 0, 1);
        //回收Pool中的资源
        //soundPool.release();
    }
}