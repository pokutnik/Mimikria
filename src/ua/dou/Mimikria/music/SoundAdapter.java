package ua.dou.Mimikria.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.w3c.dom.Text;
import ua.dou.Mimikria.R;
import ua.dou.Mimikria.widgets.RemoteImageView;

import java.util.List;

/**
 * User: David
 * Date: 30.03.13
 * Time: 18:42
 */
public class SoundAdapter extends BaseAdapter {
    private List<SoundItem> soundItems;
    private Context context;
    private LayoutInflater layoutInflater;

    public SoundAdapter(Context context, List<SoundItem> soundItems) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.soundItems = soundItems;
    }

    @Override
    public int getCount() {
        return soundItems.size();
    }

    @Override
    public SoundItem getItem(int i) {
        return soundItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View soundView = layoutInflater.inflate(R.layout.sound_item_layout, viewGroup, false);
        RemoteImageView remoteImageView = (RemoteImageView) soundView.findViewById(R.id.thumb);
        TextView name = (TextView) soundView.findViewById(R.id.name);

        remoteImageView.downloadFromUrl(getItem(i).getThumb());
        name.setText(getItem(i).getName());
        return soundView;
    }
}
