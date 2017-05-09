package cornelltech.cwkj.photobook_new;

/**
 * Created by kylin1989 on 5/8/17.
 */

public class ImageUpload {
    public String url;
    public String name;
    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ImageUpload(){}
}
