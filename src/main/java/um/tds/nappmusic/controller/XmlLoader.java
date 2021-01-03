package um.tds.nappmusic.controller;

import java.util.ArrayList;
import java.util.List;
import um.tds.songloader.SongLoader;
import um.tds.songloader.SongLoaderListener;
import um.tds.songloader.TdsSongLoader;
import um.tds.songloader.events.SongsLoadedEvent;

public class XmlLoader implements SongLoaderListener {
  private SongLoader loaderComponent;

  public XmlLoader() {
    loaderComponent = new TdsSongLoader();
    loaderComponent.addSongLoaderListener(this);
  }

  public void loadSongs(String xmlPath) {
    loaderComponent.loadSongs(xmlPath);
  }

  @Override
  public void notifySongsLoaded(SongsLoadedEvent event) {
    event.getSongs().getSongs().stream()
        .forEach(
            s -> {
              List<String> uniqueStyle = new ArrayList<>();
              uniqueStyle.add(s.getStyle());
              Controller.getSingleton()
                  .registerSong(s.getTitle(), s.getAuthor(), uniqueStyle, s.getUrl());
            });
  }
}
