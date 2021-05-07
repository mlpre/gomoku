package ml.minli.util;

import java.io.InputStream;
import java.net.URL;

public class ResourceUtil {

    public static URL getResource(String resource) {
        return ResourceUtil.class.getClassLoader().getResource(resource);
    }

    public static String getExternalForm(String resource) {
        return getResource(resource).toExternalForm();
    }

    public static InputStream getInputStream(String resource) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(resource);
    }

}
