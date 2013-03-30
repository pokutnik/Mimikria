package ua.dou.Mimikria;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.dou.Mimikria.resources.ResourceReader;
import ua.dou.Mimikria.resources.ResourceUpdateListener;

public class MainActivity extends Activity implements ResourceUpdateListener {
    private EmailFinder emailFinder;
    private ResourceReader apiResourceReader;
    private ResourceReader voicesResourceReader;
    private JSONParser jsonParser;

    private ViewGroup rootView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rootView = (ViewGroup) findViewById(R.id.root);

        jsonParser = new JSONParser(this);

        apiResourceReader = new ResourceReader("http://172.27.40.20:3000/api/", this);
        apiResourceReader.startReading();
        voicesResourceReader = new ResourceReader("http://172.27.40.20:3000/api/voices/", this);
        voicesResourceReader.startReading();

        emailFinder = new EmailFinder(this);

        TextView serviceInfo = (TextView) findViewById(R.id.service_info);
        serviceInfo.setText(emailFinder.getEmail());
    }

    @Override
    public synchronized void onResourceUpdated(String updatedData) {
        Log.d("123", "Parsed " + updatedData);
        String resource = jsonParser.getResourceValue(updatedData);
        if (resource.equals("voices")) {

        }
    }
}
