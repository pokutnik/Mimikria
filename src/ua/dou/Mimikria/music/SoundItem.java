package ua.dou.Mimikria.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: David
 * Date: 30.03.13
 * Time: 18:28
 */
public class SoundItem implements Parcelable {
    private String id = "";
    private String name = "";
    private String thumb = "";
    private String mp3 = "";

    public SoundItem() {}

    public SoundItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        thumb = in.readString();
        mp3 = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = "http://172.27.40.20:3000" + thumb;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = "http://172.27.40.20:3000" + mp3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(thumb);
        parcel.writeString(mp3);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SoundItem createFromParcel(Parcel in) {
            return new SoundItem(in);
        }

        public SoundItem[] newArray(int size) {
            return new SoundItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
