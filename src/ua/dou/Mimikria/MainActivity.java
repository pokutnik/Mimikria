package ua.dou.Mimikria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import ua.dou.Mimikria.music.SoundAdapter;
import ua.dou.Mimikria.music.SoundItem;
import ua.dou.Mimikria.resources.ApiResourceReader;
import ua.dou.Mimikria.resources.DefaultResourceReader;
import ua.dou.Mimikria.resources.ResourceReader;
import ua.dou.Mimikria.resources.ResourceUpdateListener;

import java.util.List;

public class MainActivity extends Activity implements ResourceUpdateListener {
    private EmailFinder emailFinder;
    private ResourceReader apiResourceReader;
    private ResourceReader voicesResourceReader;
    private JSONParser jsonParser;
    private ListView soundsView;
    private List<SoundItem> soundItemList;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        soundsView = (ListView) findViewById(R.id.sounds_list);
        soundsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailMusicActivity.class);
                intent.putExtra("SoundItem", soundItemList.get(i));
                startActivity(intent);
            }
        });

        jsonParser = new JSONParser();

        apiResourceReader = new ApiResourceReader(this, "http://172.27.40.20:3000/api/", this);
        apiResourceReader.startReading();
        voicesResourceReader = new DefaultResourceReader("http://172.27.40.20:3000/api/voices/", this);

        emailFinder = new EmailFinder(this);

        TextView serviceInfo = (TextView) findViewById(R.id.service_info);
        serviceInfo.setText(emailFinder.getEmail());
    }

    @Override
    public synchronized void onResourceUpdated(String updatedData) {
        Log.d("123", "Parsed " + updatedData);
        String resource = jsonParser.getResourceValue(updatedData);
        if (resource.equals("api")) {
            voicesResourceReader.startReading();
        } if (resource.equals("voices")) {
            soundItemList = jsonParser.getSoundItemList(updatedData);
            soundsView.setAdapter(new SoundAdapter(this, soundItemList));
        }
    }
}
