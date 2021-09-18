package com.gmail.virustotalop.bukkitbot;

import java.io.File;

public class ServerRunner {



    private final File downloadFolder;
    private final File serverFile;

    public ServerRunner(File downloadFolder) {
        this.downloadFolder = downloadFolder;
        this.serverFile = new File(this.downloadFolder, "paperclip.jar");
    }

    public boolean download() {
        if(this.serverFile.exists()) { //Skip if downloaded

        }
        return false;
    }
}