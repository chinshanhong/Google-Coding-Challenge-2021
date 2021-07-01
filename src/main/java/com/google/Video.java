package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private String flagReason;
  private boolean isPaused;
  private boolean isStopped;
  private boolean isFlagged;


  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  public boolean isStopped() {
    return isStopped;
  }

  public void setStopped(boolean stopped) {
    isStopped = stopped;
  }

  public boolean isFlagged() {
    return isFlagged;
  }

  public void setFlagged(boolean flagged) {
    isFlagged = flagged;
  }

  public String getFlagReason() {
    return flagReason;
  }

  public void setFlagReason(String flagReason) {
    this.flagReason = flagReason;
  }
}
