package ua.dou.Mimikria.resources;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import ua.dou.Mimikria.EmailFinder;
import ua.dou.Mimikria.music.SoundItem;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: David
 * Date: 30.03.13
 * Time: 22:52
 */
public class ShoutResourceReader implements ResourceReader {
    private ResourceUpdateListener resourceUpdateListener;
    private Context context;
    private String resourceUrl;
    private StringBuilder data;
    private BufferedReader in;
    private File shout;
    private SoundItem selectedItem;

    public ShoutResourceReader(Context context, String resourceUrl, SoundItem selectedItem, File shout,
                               ResourceUpdateListener resourceUpdateListener) {
        this.resourceUpdateListener = resourceUpdateListener;
        this.context = context;
        this.resourceUrl = resourceUrl;
        this.shout = shout;
        this.selectedItem = selectedItem;
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

                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                FileBody fileBody = new FileBody(shout, "binary/octet-stream");
                StringBody stringBody = new StringBody(selectedItem.getId());
                reqEntity.addPart("id", stringBody);
                reqEntity.addPart("filedata", fileBody);
                request.setEntity(reqEntity);

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
