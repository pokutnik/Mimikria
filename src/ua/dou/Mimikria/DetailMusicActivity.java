package ua.dou.Mimikria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ua.dou.Mimikria.music.AudioRecorder;
import ua.dou.Mimikria.music.SoundItem;
import ua.dou.Mimikria.resources.ResourceReader;
import ua.dou.Mimikria.resources.ResourceUpdateListener;
import ua.dou.Mimikria.resources.ShoutResourceReader;
import ua.dou.Mimikria.widgets.DrawView;
import ua.dou.Mimikria.widgets.RemoteImageView;

import java.io.File;
import java.io.IOException;

/**
 * User: David
 * Date: 30.03.13
 * Time: 20:36
 */
public class DetailMusicActivity extends Activity implements ResourceUpdateListener {
    private SoundItem selectedSoundItem;
    private RemoteImageView imageView;
    private TextView name;
    private ToggleButton playPause;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private Button recButton;
    private CountDownTimer progressTimer;
    private DrawView drawView;

    private Visualizer visualizer;
    private CountDownTimer recordTimer;
    private AudioRecorder audioRecorder;
    private ResourceReader shoutResourceReader;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_music);

        Intent intent = getIntent();
        selectedSoundItem = intent.getParcelableExtra("SoundItem");

        name = (TextView) findViewById(R.id.name);
        name.setText(selectedSoundItem.getName());

        imageView = (RemoteImageView) findViewById(R.id.thumb);
        imageView.downloadFromUrl(selectedSoundItem.getThumb());

        mediaPlayer = MediaPlayer.create(this, Uri.parse(selectedSoundItem.getMp3()));
        progressBar = (ProgressBar) findViewById(R.id.play_progress);
        progressBar.setMax(mediaPlayer.getDuration());
        progressTimer = new CountDownTimer(mediaPlayer.getDuration(), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (mediaPlayer.getDuration() - millisUntilFinished));
            }

            @Override
            public void onFinish() {
                playPause.setChecked(false);
            }
        };

        playPause = (ToggleButton) findViewById(R.id.play_pause);
        playPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mediaPlayer = MediaPlayer.create(DetailMusicActivity.this, Uri.parse(selectedSoundItem.getMp3()));
                    mediaPlayer.start();
                    progressTimer.start();
                } else {
                    progressTimer.cancel();
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
                progressBar.setProgress(0);
            }
        });

        audioRecorder = new AudioRecorder();
        recButton = (Button) findViewById(R.id.rec_button);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AssetFileDescriptor afd = getAssets().openFd("start_recording.mp3");
                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(afd.getFileDescriptor());
                    player.prepare();
                    player.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            audioRecorder.startRecording();
                            recordTimer.start();
                            recButton.setEnabled(false);
                        }
                    });
                    player.start();
                } catch (IOException e) {
                    Log.e("123", e.getMessage());
                }

            }
        });

        recordTimer = new CountDownTimer(mediaPlayer.getDuration(), mediaPlayer.getDuration()) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                recButton.setEnabled(true);
                audioRecorder.stopRecording();
                MediaPlayer mediaPlayer = MediaPlayer.create(DetailMusicActivity.this, Uri.parse(audioRecorder.getFileName()));
                drawView = (DrawView) findViewById(R.id.equalizer);
                visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
                visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
                visualizer.setEnabled(true);
                visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                    @Override
                    public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {
                        drawView.setFft(bytes);
                    }

                    @Override
                    public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {
                        drawView.setFft(bytes);
                    }
                }, Visualizer.getMaxCaptureRate(), true, true);
                mediaPlayer.start();
                shoutResourceReader.startReading();
            }
        };
        shoutResourceReader = new ShoutResourceReader(this, "http://172.27.40.20:3000/api/shouts/", selectedSoundItem, new File(audioRecorder.getFileName()),
                this);


    }

    @Override
    public void onResourceUpdated(String updatedData) {
        File file = new File(audioRecorder.getFileName());
        file.delete();
    }
}
