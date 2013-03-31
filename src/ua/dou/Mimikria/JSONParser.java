package ua.dou.Mimikria;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.dou.Mimikria.music.ProcessingResult;
import ua.dou.Mimikria.music.SoundItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: David
 * Date: 30.03.13
 * Time: 16:05
 */
public class JSONParser {
    public String getResourceValue(String loadedInput) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(loadedInput);
            value = jsonObject.getString("resource");
        } catch (Exception e) {
            Log.e("123", "Get resource: " + e.getMessage());
        }
        return value;
    }

    public List<SoundItem> getSoundItemList(String loadedInput) {
        List<SoundItem> soundItemList = new ArrayList<SoundItem>();
        try {
            JSONObject massiveObject = new JSONObject(loadedInput);
            JSONArray jsonArray = massiveObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject soundItemJson = jsonArray.getJSONObject(i);
                SoundItem soundItem = new SoundItem();
                soundItem.setId(soundItemJson.getString("id"));
                soundItem.setName(soundItemJson.getString("name"));
                soundItem.setMp3(soundItemJson.getString("mp3"));
                soundItem.setThumb(soundItemJson.getString("thumb"));
                soundItemList.add(soundItem);
            }
        } catch (JSONException e) {
            Log.e("123", "Get sound item list: " + e.getMessage());
        }
        return soundItemList;
    }

    public ProcessingResult getProcessingResult(String input) {
        ProcessingResult processingResult = new ProcessingResult();
        JSONObject processingObject = new JSONObject();
        try {
            processingResult.setLink(processingObject.getString("link"));
            processingResult.setProcessed(processingObject.getBoolean("processed"));
        } catch (JSONException e) {
        }
        return processingResult;
    }
}
