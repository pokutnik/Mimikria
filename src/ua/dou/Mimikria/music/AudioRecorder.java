package ua.dou.Mimikria.music;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * User: David
 * Date: 30.03.13
 * Time: 22:32
 */
public class AudioRecorder {
    private String fileName = null;
    private MediaRecorder recorder = null;

    public AudioRecorder() {
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/mimikria.mp3";
    }

    public void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("123", "prepare() failed");
        }

        recorder.start();
    }

    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public String getFileName() {
        return fileName;
    }
}
