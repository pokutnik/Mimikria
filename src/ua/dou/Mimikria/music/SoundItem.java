package ua.dou.Mimikria.music;

/**
 * User: David
 * Date: 30.03.13
 * Time: 18:28
 */
public class SoundItem {
    private String name = "";
    private String duration = "";
    private String thumb = "";
    private String mp3 = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
