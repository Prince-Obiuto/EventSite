package com.cit306.EventSite.httpserver.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WebRootHandler {

    private final File webRoot;

    public WebRootHandler(String webRootPath) throws WebRootNotFoundException {
        webRoot = new File(webRootPath);
        if (!webRoot.exists() || !webRoot.isDirectory()) {
            throw new WebRootNotFoundException("Webroot provided does not exist or is not a folder");
        }
    }

    private boolean CheckIfEndsWithSlash(String relativePath){
        return relativePath.endsWith("/");
    }

    /**
     * This method checks to see if the relative path provided exists inside WebRoot
     *
     * @Param relativePath
     * @return true if the path exists inside WebRoot, false if not.
     */

    private boolean checkIfProvidedRelativePathExists(String relativePath) {
        File file = new File(webRoot, relativePath);

        if (!file.exists())
            return false;

        try {
            if (file.getCanonicalPath().startsWith(webRoot.getCanonicalPath())) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public String getFileMimeType(String relativePath) throws FileNotFoundException {
        if (checkIfProvidedRelativePathExists(relativePath)) {
            relativePath = "index.html"; // By default, serve the index.html if it exists.
        }

        if (!checkIfProvidedRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File not found" + relativePath)
        }
    }
}
