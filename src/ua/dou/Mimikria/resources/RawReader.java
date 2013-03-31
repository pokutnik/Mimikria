package ua.dou.Mimikria.resources;

import android.content.Context;
import ua.dou.Mimikria.DataReader;
import ua.dou.Mimikria.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: David
 * Date: 30.03.13
 * Time: 16:22
 */
public class RawReader implements ResourceReader {
    private Context context;
    private ResourceUpdateListener resourceUpdateListener;

    public RawReader(Context context, ResourceUpdateListener resourceUpdateListener) {
        this.resourceUpdateListener = resourceUpdateListener;
        this.context = context;
    }

    @Override
    public void startReading() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.first_input);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
        }
        if (resourceUpdateListener != null) {
            resourceUpdateListener.onResourceUpdated(text.toString());
        }
    }
}
