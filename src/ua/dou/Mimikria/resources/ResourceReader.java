package ua.dou.Mimikria.resources;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: David
 * Date: 30.03.13
 * Time: 16:24
 */
public class ResourceReader {
    private BufferedReader in;
    private StringBuilder data;
    private ResourceUpdateListener resourceUpdateListener;
    private String resourceUrl;

    public ResourceReader(String resourceUrl, ResourceUpdateListener resourceUpdateListener) {
        this.resourceUrl = resourceUrl;
        this.resourceUpdateListener = resourceUpdateListener;
    }

    public void startReading() {
        thread.start();
    }

    private Thread thread = new Thread() {
        public void run() {
            try {
                HttpClient client = new DefaultHttpClient();
                URI website = new URI(resourceUrl);
                HttpGet request = new HttpGet();
                request.setURI(website);
                HttpResponse response = client.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new IOException("Status code is not 200");
                }

                data = new StringBuilder("");

                String line;
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while (( line = in.readLine()) != null) {
                    data.append(line);
                    data.append('\n');
                }
                in.close();
            } catch (URISyntaxException urise) {
                Log.e("123", urise.getMessage());
            } catch (IOException ioe) {
                Log.e("123", ioe.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                        internetDataHandler.sendMessage(Message.obtain(internetDataHandler, 0, data.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private Handler internetDataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String parsedData = (String) msg.obj;
            if (resourceUpdateListener != null) {
                resourceUpdateListener.onResourceUpdated(parsedData);
            }
        }
    };
}
