package ua.dou.Mimikria;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import ua.dou.Mimikria.music.SoundItem;
import ua.dou.Mimikria.widgets.RemoteImageView;

import java.io.IOException;

/**
 * User: David
 * Date: 30.03.13
 * Time: 20:36
 */
public class DetailMusicActivity extends Activity {
    private SoundItem selectedSoundItem;
    private RemoteImageView imageView;
    private TextView name;
    private ToggleButton playPause;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private CountDownTimer progressTimer;


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
                    mediaPlayer.start();
                    progressTimer.start();
                } else {
                    progressTimer.cancel();
                    mediaPlayer.stop();
                }
                progressBar.setProgress(0);

            }
        });
    }
}
