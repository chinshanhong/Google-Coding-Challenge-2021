package com.google;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String lowerCastplayListName;
    private String originalPlayListName;
    private LinkedList<String> videoIDs;

    public VideoPlaylist(String originalPlayListName, String lowerCastplayListName) {
        this.originalPlayListName = originalPlayListName;
        this.lowerCastplayListName = lowerCastplayListName;
        this.videoIDs = new LinkedList<>();
    }


    public void addVideoId(String videoId){
        videoIDs.add(videoId);
    }

    public String getLowerCastplayListName() {
        return lowerCastplayListName;
    }

    public String getOriginalPlayListName() {
        return originalPlayListName;
    }

    public LinkedList<String> getVideoIDs() {
        return videoIDs;
    }
}
