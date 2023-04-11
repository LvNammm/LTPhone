package com.example.ltuddd.Utils;

import android.media.MediaPlayer;

public class Contain {
    public static final String title = "title";
    public static final String text  = "text";
    public static final String stop = "true";
    public static MediaPlayer mediaPlayer;
    public static String keySavePathRingStone = "keyRingStone";
    public static String valueStrDefault = "-1";
    public static String usernameDefault ="00000000001111111111112222222222222222";
    public static void stopMediaPlayer(){
        if(Contain.mediaPlayer != null && Contain.mediaPlayer.isPlaying()){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Contain.mediaPlayer.stop();
        }
    }
}
