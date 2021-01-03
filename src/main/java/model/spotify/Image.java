package model.spotify;

/**
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#image-object
 */
public class Image {

    public Image() {
    }

    public int height;
    public int width;
    public String url = "";

    @Override
    public String toString() {
        return "Image{" +
                "height=" + height +
                ", width=" + width +
                ", url='" + url + '\'' +
                '}';
    }
}
