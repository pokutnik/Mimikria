package ua.dou.Mimikria.resources;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import ua.dou.Mimikria.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: David
 * Date: 31.03.13
 * Time: 11:53
 */
public class ProcessReader implements ResourceReader {
    private String resourceUrl;
    private BufferedReader in;
    private StringBuilder data;
    private final ResourceUpdateListener resourceUpdateListener;

    public ProcessReader(String link, ResourceUpdateListener resourceUpdateListener) {
        this.resourceUrl = link;
        this.resourceUpdateListener = resourceUpdateListener;
    }

    @Override
    public void startReading() {
        Thread thread = new Thread(load);
        thread.start();
    }

    private Runnable load = new Runnable() {
        @Override
        public void run() {
            try {
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(AppCookies.getInstance().getCookieStore());
                URI website = new URI(resourceUrl);
                HttpGet request = new HttpGet();
                request.setURI(website);
                HttpResponse response = client.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200 && statusCode != 201) {
                    throw new IOException("Status code is not 200");
                }

                AppCookies.getInstance().setCookieStore(client.getCookieStore());
                data = new StringBuilder("");

                String line;
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while ((line = in.readLine()) != null) {
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
