package core.dto;

import java.io.File;

public class Path {
    public static final String pathResource = System.getProperty("user.dir")
            + File.separator + "resources" + File.separator;
}
