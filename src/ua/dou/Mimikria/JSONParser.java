package ua.dou.Mimikria;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: David
 * Date: 30.03.13
 * Time: 16:05
 */
public class JSONParser {
    private Context context;

    public JSONParser(Context context) {
        this.context = context;
    }

    public String getResourceValue(String loadedInput) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(loadedInput);
            value = jsonObject.getString("resource");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
