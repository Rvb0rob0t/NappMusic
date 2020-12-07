package um.tds.nappmusic.controller;

import java.util.ArrayList;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.songloader.SongLoader;
import um.tds.songloader.SongLoaderListener;
import um.tds.songloader.events.SongsLoadedEvent;

public class XmlLoader implements SongLoaderListener {
  private SongLoader loaderComponent;

  public XmlLoader() {
    loaderComponent = new SongLoader();
    loaderComponent.addSongLoaderListener(this);
  }

  public void loadSongs(String xmlPath) {
    loaderComponent.loadSongs(xmlPath);
  }

  public void notifySongsLoaded(SongsLoadedEvent event) {
    SongCatalog catalog = SongCatalog.getSingleton();
    event.getSongs().stream().map(s -> toDomainSong(s)).forEach(s -> catalog.addSong(s));
  }

  private static um.tds.nappmusic.domain.Song toDomainSong(um.tds.songloader.Song song) {
    ArrayList<String> uniqueStyle = new ArrayList();
    uniqueStyle.add(song.getStyle());
    return new um.tds.nappmusic.domain.Song(
        song.getTitle(), song.getAuthor(), uniqueStyle, song.getUrl(), 0);
  }
}
