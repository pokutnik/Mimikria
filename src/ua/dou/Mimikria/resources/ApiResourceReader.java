package ua.dou.Mimikria.resources;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import ua.dou.Mimikria.EmailFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: David
 * Date: 30.03.13
 * Time: 19:14
 */
public class ApiResourceReader implements ResourceReader {
    private ResourceUpdateListener resourceUpdateListener;
    private Context context;
    private String resourceUrl;
    private StringBuilder data;
    private BufferedReader in;

    public ApiResourceReader(Context context, String resourceUrl, ResourceUpdateListener resourceUpdateListener) {
        this.context = context;
        this.resourceUrl = resourceUrl;
        this.resourceUpdateListener = resourceUpdateListener;
    }

    @Override
    public void startReading() {
        Thread thread = new Thread(load);
        thread.start();
    }

    private Runnable load = new Runnable() {
        public void run() {
            try {
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(AppCookies.getInstance().getCookieStore());
                URI website = new URI(resourceUrl);
                HttpPost request = new HttpPost();
                request.setURI(website);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", new EmailFinder(context).getEmail()));
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200 && statusCode != 201) {
                    throw new IOException("Status code is not 200");
                }

                AppCookies.getInstance().setCookieStore(client.getCookieStore());

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
