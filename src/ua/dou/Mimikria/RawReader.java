package ua.dou.Mimikria;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: David
 * Date: 30.03.13
 * Time: 16:22
 */
public class RawReader implements DataReader {
    private Context context;

    public RawReader(Context context) {
        this.context = context;
    }

    @Override
    public String readData() {
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
            return null;
        }
        return text.toString();
    }
}
