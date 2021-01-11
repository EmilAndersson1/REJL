package model.spotify;

/**
 * A bean representing an image.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#image-object
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
