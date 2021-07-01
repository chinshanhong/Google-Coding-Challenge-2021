package com.google;

import java.util.*;

public class VideoPlayer {

  private LinkedList<VideoPlaylist> playList = new LinkedList<>();
//  private final VideoLibrary videoLibrary;
  private VideoLibrary videoLibrary;
  private Video currentVideo;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> videos = videoLibrary.getVideos();
    sortVideo(videos);
    System.out.println("Here's a list of all available videos:");
    for (Video video: videos
           ) {
      if(video.isFlagged()){
        System.out.println(video.getTitle() + " (" + video.getVideoId() + ") "
                + video.getTags().toString().replaceAll(", ", " ")
                + " - FLAGGED (reason: " + video.getFlagReason() + ")");
      }
      else{
        System.out.println(video.getTitle() + " (" + video.getVideoId() + ") "
                + video.getTags().toString().replaceAll(", ", " "));
      }
      }
    }


  public void sortVideo(List<Video> videos){
    boolean needNextPass = true;
    for(int i = 1; i < videos.size() && needNextPass; i++){
      needNextPass = false;
      for(int j = 0; j < videos.size() - i; j++){
        if(videos.get(j).getTitle().compareTo(videos.get(j + 1).getTitle()) > 0){
          Video temp = videos.get(j);
          videos.set(j, videos.get(j + 1));
          videos.set(j + 1, temp);
          needNextPass = true;
        }
      }
    }
  }

  public void playVideo(String videoId) {
    if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if(currentVideo == null){
      currentVideo = videoLibrary.getVideo(videoId);
      if(!currentVideo.isFlagged()){
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
        currentVideo.setStopped(false);
      }
      else{
        System.out.println("Cannot play video: Video is currently flagged (reason: "
                + currentVideo.getFlagReason() + ")");
        currentVideo = null;
      }
    }
    else{
      if(!currentVideo.isStopped()){
        System.out.println("Stopping video: " + currentVideo.getTitle());
        currentVideo.setStopped(true);
        currentVideo.setPaused(false);

        currentVideo = videoLibrary.getVideo(videoId);
        if(!currentVideo.isFlagged()){
          System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
          currentVideo.setStopped(false);
          currentVideo.setPaused(false);
        }
        else{
          System.out.println("Cannot play video: Video is currently flagged (reason: "
                  + currentVideo.getFlagReason() + ")");
          currentVideo = null;
        }
      }
      else{
        currentVideo = videoLibrary.getVideo(videoId);
        if(!currentVideo.isFlagged()){
          System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
          currentVideo = videoLibrary.getVideo(videoId);
          currentVideo.setStopped(false);
          currentVideo.setPaused(false);
        }
        else{
          System.out.println("Cannot play video: Video is currently flagged (reason: "
                  + currentVideo.getFlagReason() + ")");
          currentVideo = null;
        }
      }
    }
  }

  public void stopVideo() {
    if(currentVideo == null || currentVideo.isStopped()){
      System.out.println("Cannot stop video: No video is currently playing");
      return;
    }
    if(!currentVideo.isStopped()){
      System.out.println("Stopping video: " + currentVideo.getTitle());
      currentVideo.setStopped(true);
      currentVideo.setPaused(false);
      currentVideo = null;
    }
  }

  public void playRandomVideo() {
    if(videoLibrary.getVideos().isEmpty() || getUnFlaggedVideo(videoLibrary.getVideos()).isEmpty()){
      System.out.println("No videos available");
      return;
    }
    Random rand = new Random();
    LinkedList<Video> unFlaggedVideo = (LinkedList<Video>) getUnFlaggedVideo(videoLibrary.getVideos());
    if(currentVideo == null){
      currentVideo = unFlaggedVideo.get(rand.nextInt(unFlaggedVideo.size()));
      System.out.println("Playing video: " + currentVideo.getTitle());
      currentVideo.setStopped(false);
      currentVideo.setPaused(false);
      return;
    }
    if(currentVideo.isPaused()){
      System.out.println("Stopping video: " + currentVideo.getTitle());
      currentVideo.setStopped(true);
      currentVideo.setPaused(false);

      currentVideo = unFlaggedVideo.get(rand.nextInt(unFlaggedVideo.size()));
      System.out.println("Playing video: " + currentVideo.getTitle());
      currentVideo.setStopped(false);
      currentVideo.setPaused(false);
      return;

    }
    if(!currentVideo.isStopped()){
      System.out.println("Stopping video: " + currentVideo.getTitle());
      currentVideo.setStopped(true);
      currentVideo.setPaused(false);

      currentVideo = unFlaggedVideo.get(rand.nextInt(unFlaggedVideo.size()));
      System.out.println("Playing video: " + currentVideo.getTitle());
      currentVideo.setStopped(false);
      currentVideo.setPaused(false);
      return;
    }
    else{
        currentVideo = unFlaggedVideo.get(rand.nextInt(unFlaggedVideo.size()));
        System.out.println("Playing video: " + currentVideo.getTitle());
        currentVideo.setStopped(false);
        currentVideo.setPaused(false);
        return;
      }
    }

  public List<Video> getUnFlaggedVideo(List<Video> videos){
    List<Video> results = new LinkedList<>();
    for(int i = 0; i < videos.size(); i++){
      if(!videos.get(i).isFlagged()){
        results.add(videos.get(i));
      }
    }
    return results;
  }

  public void pauseVideo() {
    if(currentVideo == null || currentVideo.isStopped()){
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }
    if(!currentVideo.isPaused()){
      System.out.println("Pausing video: " + currentVideo.getTitle());
      currentVideo.setPaused(true);
    }
    else{
      System.out.println("Video already paused: " + currentVideo.getTitle());
    }
  }

  public void continueVideo() {
    if(currentVideo == null || currentVideo.isStopped()){
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }
    if(!currentVideo.isPaused()){
      System.out.println("Cannot continue video: Video is not paused");
    }
    else{
      System.out.println("Continuing video: " + currentVideo.getTitle());
      currentVideo.setPaused(false);
    }
  }

  public void showPlaying() {
    if(currentVideo == null || currentVideo.isStopped()){
      System.out.println("No video is currently playing");
      return;
    }
    else{
      if(!currentVideo.isPaused()){
        System.out.println("Currently playing: " + currentVideo.getTitle()
        + " (" + currentVideo.getVideoId() + ") "
                + currentVideo.getTags().toString().replaceAll(", ", " "));
      }
      else{
        System.out.println("Currently playing: " + currentVideo.getTitle()
                + " (" + currentVideo.getVideoId() + ") "
                + currentVideo.getTags().toString().replaceAll(", ", " ") + " - PAUSED");
      }
    }
  }

  public void createPlaylist(String playlistName) {
    if(!containPlayList(playlistName)){
      System.out.println("Successfully created new playlist: " + playlistName);
      playList.add(new VideoPlaylist(playlistName, playlistName.toLowerCase()));
    }
    else{
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }

  public boolean containPlayList(String playListName){
    for(int i = 0; i < playList.size(); i++){
      if(playList.get(i).getLowerCastplayListName().equals(playListName.toLowerCase())){
        return true;
      }
    }
    return false;
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if(videoLibrary.getVideo(videoId) == null && !containPlayList(playlistName)){
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }
    if(containPlayList(playlistName)){
      if(videoLibrary.getVideo(videoId) == null){
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
      else if(videoLibrary.getVideo(videoId) != null
              && containVideo(playlistName, videoLibrary.getVideo(videoId).getVideoId())){
        if(videoLibrary.getVideo(videoId).isFlagged()){
          System.out.println("Cannot add video to " + playlistName
          + ": Video is currently flagged (reason: "
                  + videoLibrary.getVideo(videoId).getFlagReason() + ")");
        }
        else{
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        }
      }
      else if(videoLibrary.getVideo(videoId) != null
              && !containVideo(playlistName, videoLibrary.getVideo(videoId).getVideoId())){
        if(!videoLibrary.getVideo(videoId).isFlagged()){
          System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
          getVideoPlayList(playlistName).addVideoId(videoLibrary.getVideo(videoId).getVideoId());
        }
        else{
          System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason:"
          + " " + videoLibrary.getVideo(videoId).getFlagReason() + ") ");
        }
      }
    }
    else{
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }
  }

  public VideoPlaylist getVideoPlayList(String playListName){
    for(int i = 0; i < playList.size(); i++){
      if(playList.get(i).getLowerCastplayListName().equals(playListName.toLowerCase())){
        return playList.get(i);
      }
    }
    return null;
  }

  public boolean containVideo(String playListName, String videoID){
    for(int i = 0; i < playList.size(); i++){
      if(playList.get(i).getLowerCastplayListName().equals(playListName.toLowerCase())){
        for(int j = 0; j < playList.get(i).getVideoIDs().size(); j++){
          if(playList.get(i).getVideoIDs().contains(videoID)){
            return true;
          }
        }
      }
    }
    return false;
  }

  public void showAllPlaylists() {
    if(playList.isEmpty()) {
      System.out.println("No playlists exist yet");
    }else{
      System.out.println("Showing all playlists: ");
      sortPlaylist(playList);
      for(int i = 0; i < playList.size(); i++){
        System.out.println(playList.get(i).getOriginalPlayListName());
      }
    }
  }

  public void sortPlaylist(LinkedList<VideoPlaylist> playList){
    boolean needNextPass = true;
    for(int i = 1; i < playList.size() && needNextPass; i++){
      needNextPass = false;
      for(int j = 0; j < playList.size() - i; j++){
        if(playList.get(j).getOriginalPlayListName().
                compareTo(playList.get(j + 1).getOriginalPlayListName()) > 0){
          VideoPlaylist temp = playList.get(j);
          playList.set(j, playList.get(j + 1));
          playList.set(j + 1, temp);
          needNextPass = true;
        }
      }
    }
  }

  public void showPlaylist(String playlistName) {
    if(!containPlayList(playlistName)){
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }
    else if(getVideoPlayList(playlistName).getVideoIDs().isEmpty()){
      System.out.println("Showing playlist: " + playlistName);
      System.out.println("No videos here yet");
    }
    else{
      System.out.println("Showing playlist: " + playlistName);
      for(int i = 0; i <  getVideoPlayList(playlistName).getVideoIDs().size(); i++){
        String videoId = getVideoPlayList(playlistName).getVideoIDs().get(i);
        Video video = videoLibrary.getVideo(videoId);
        if(video.isFlagged()){
          System.out.println(video.getTitle() + " (" + video.getVideoId() + ") "
                  + video.getTags().toString().replaceAll(", ", " ")
                  + " - FLAGGED (reason: " + video.getFlagReason() + ")");
        }
        else{
          System.out.println(videoLibrary.getVideo(videoId).getTitle()
                  + " (" + videoLibrary.getVideo(videoId).getVideoId() + ") "
                  + videoLibrary.getVideo(videoId).getTags().toString().replaceAll(", ", " "));
        };
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if(containPlayList(playlistName)){
      if(videoLibrary.getVideo(videoId) != null
              && getVideoPlayList(playlistName).getVideoIDs().contains(videoId)){
        System.out.println("Removed video from " + playlistName
                + ": " + videoLibrary.getVideo(videoId).getTitle());
        getVideoPlayList(playlistName).getVideoIDs().remove(videoId);
      }
      else if(videoLibrary.getVideo(videoId) != null
              && !getVideoPlayList(playlistName).getVideoIDs().contains(videoId)){
        System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      }
      else if(videoLibrary.getVideo(videoId) == null){
        System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      }
    }
    else{
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if(containPlayList(playlistName)){
      getVideoPlayList(playlistName).getVideoIDs().clear();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
    else{
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName){
    if(containPlayList(playlistName)){
      playList.remove(getVideoPlayList(playlistName));
      System.out.println("Deleted playlist: " + playlistName);
    }
    else{
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void searchVideos(String searchTerm) {
    Scanner scan = new Scanner(System.in);
    LinkedList<Video> searchResult = new LinkedList<>();
    for(int i = 0; i < videoLibrary.getVideos().size(); i++){
      if(videoLibrary.getVideos().get(i).getTitle().toLowerCase().contains(searchTerm)){
        searchResult.add(videoLibrary.getVideos().get(i));
      }
    }
    searchResult = (LinkedList<Video>) getUnFlaggedVideo(searchResult);
    sortVideo(searchResult);
    if(!searchResult.isEmpty()){
      System.out.println("Here are the results for " + searchTerm + ":");
      for(int i = 0; i < searchResult.size(); i++){
        System.out.println((i + 1) + ") " + searchResult.get(i).getTitle()
                + " (" + searchResult.get(i).getVideoId() + ") "
                + searchResult.get(i).getTags().toString().replaceAll(", ", " "));
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      try{
        int videoNumber = scan.nextInt();
        if(videoNumber >= 1 && videoNumber <= searchResult.size()){
          playVideo(searchResult.get(videoNumber - 1).getVideoId());
        }

      }catch(InputMismatchException e){
      }
    }
    else{
      System.out.println("No search results for " + searchTerm);
    }
  }

  public void searchVideosWithTag(String videoTag) {
    Scanner scan = new Scanner(System.in);
    if(videoTag.contains("#")){
      LinkedList<Video> searchResult = new LinkedList<>();
      for(int i = 0; i < videoLibrary.getVideos().size(); i++){
        if(videoLibrary.getVideos().get(i).getTags().toString().toLowerCase().contains(videoTag)){
          searchResult.add(videoLibrary.getVideos().get(i));
        }
      }
      searchResult = (LinkedList<Video>) getUnFlaggedVideo(searchResult);
      sortVideo(searchResult);
      if(!searchResult.isEmpty()){
        System.out.println("Here are the results for " + videoTag + ":");
        for(int i = 0; i < searchResult.size(); i++){
          System.out.println((i + 1) + ") " + searchResult.get(i).getTitle()
                  + " (" + searchResult.get(i).getVideoId() + ") "
                  + searchResult.get(i).getTags().toString().replaceAll(", ", " "));
        }
        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");
        try{
          int videoNumber = scan.nextInt();
          if(videoNumber >= 1 && videoNumber <= searchResult.size()){
            playVideo(searchResult.get(videoNumber - 1).getVideoId());
          }
        }catch(InputMismatchException e){
        }
      }
      else{
        System.out.println("No search results for " + videoTag);
      }
    }
    else{
      System.out.println("No search results for " + videoTag);
    }
  }

  public void flagVideo(String videoId) {
    if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot flag video: Video does not exist");
    }
    else{
      if(!videoLibrary.getVideo(videoId).isFlagged()){
        if(currentVideo != videoLibrary.getVideo(videoId)){
          videoLibrary.getVideo(videoId).setFlagged(true);
          videoLibrary.getVideo(videoId).setFlagReason("Not supplied");
          System.out.println("Successfully flagged video: "
                  + videoLibrary.getVideo(videoId).getTitle()
                  + " (reason: " + videoLibrary.getVideo(videoId).getFlagReason() + ")");
        }
        else{
          if(!currentVideo.isStopped() || currentVideo.isPaused()){
            System.out.println("Stopping video: " + currentVideo.getTitle());
            currentVideo.setStopped(true);
            currentVideo.setPaused(false);

            currentVideo.setFlagged(true);
            currentVideo.setFlagReason("Not supplied");
            System.out.println("Successfully flagged video: " + currentVideo.getTitle()
                    + " (reason: " + currentVideo.getFlagReason() + ")");
            currentVideo = null;
          }
        }
      }
      else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
  }

  public void flagVideo(String videoId, String reason) {
    if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot flag video: Video does not exist");
    }
    else{
      if(!videoLibrary.getVideo(videoId).isFlagged()){
        if(currentVideo != videoLibrary.getVideo(videoId)){
          videoLibrary.getVideo(videoId).setFlagged(true);
          videoLibrary.getVideo(videoId).setFlagReason(reason);
          System.out.println("Successfully flagged video: "
                  + videoLibrary.getVideo(videoId).getTitle()
                  + " (reason: " + videoLibrary.getVideo(videoId).getFlagReason() + ")");
        }
        else{
          if(!currentVideo.isStopped() || currentVideo.isPaused()){
            System.out.println("Stopping video: " + currentVideo.getTitle());
            currentVideo.setStopped(true);
            currentVideo.setPaused(false);

            currentVideo.setFlagged(true);
            currentVideo.setFlagReason(reason);
            System.out.println("Successfully flagged video: " + currentVideo.getTitle()
                    + " (reason: " + currentVideo.getFlagReason() + ")");
            currentVideo = null;
          }
        }
      }
      else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
  }

  public void allowVideo(String videoId) {
    if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
    else if(!videoLibrary.getVideo(videoId).isFlagged()){
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }
    else{
      videoLibrary.getVideo(videoId).setFlagged(false);
      System.out.println("Successfully removed flag from video: "
              + videoLibrary.getVideo(videoId).getTitle());
    }
  }
}