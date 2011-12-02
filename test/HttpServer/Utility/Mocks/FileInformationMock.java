package HttpServer.Utility.Mocks;

import HttpServer.Utility.FileInformation;

/**
 * Author: Myles Megyesi
 */
public class FileInformationMock extends FileInformation {

    @Override
    public boolean FileExists(String directory, String file) {
        return this.isFileExists();
    }

    public boolean isFileExists() {
        return this.fileExists;
    }

    public void setFileExists(boolean fileExists) {
        this.fileExists = fileExists;
    }

    private boolean fileExists = false;
}
