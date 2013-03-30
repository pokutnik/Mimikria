package ua.dou.Mimikria.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.net.URL;
import java.net.URLConnection;

/**
 * User: David
 * Date: 30.03.13
 * Time: 18:55
 */
public class RemoteImageView extends ImageView {
    public RemoteImageView(Context context) {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void downloadFromUrl(String url) {
        ImgDownload imgDownload = new ImgDownload(url);
        imgDownload.execute();
    }

    private class ImgDownload extends AsyncTask {
        private String requestUrl;
        private ImageView view;
        private Bitmap pic;

        private ImgDownload(String requestUrl) {
            this.requestUrl = requestUrl;
            this.view = RemoteImageView.this;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {
                URL url = new URL(requestUrl);
                URLConnection conn = url.openConnection();
                pic = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (Exception ex) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            view.setImageBitmap(pic);
        }
    }
}
