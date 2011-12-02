package HttpServer.Utility;

import java.io.File;
import java.io.IOException;

/**
 * Author: Myles Megyesi
 */
public class FileInformation {

    public boolean FileExists(String directory, String file) {
        File fileToServe = new File(directory, file);
        try {
            System.out.println(fileToServe.getCanonicalPath());
        } catch (IOException e) {
        }
        return fileToServe.exists() && fileToServe.isFile();
    }

}
