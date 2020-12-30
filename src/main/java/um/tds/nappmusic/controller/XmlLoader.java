package um.tds.nappmusic.controller;

import java.util.ArrayList;
import java.util.List;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;
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

  @Override
  public void notifySongsLoaded(SongsLoadedEvent event) {
    SongCatalog catalog = SongCatalog.getSingleton();
    // Unfortunately, this exception cannot be thrown
    try {
      Dao<um.tds.nappmusic.domain.Song> songDao = DaoFactory.getSingleton().getSongDao();
      event.getSongs().stream()
          .map(s -> toDomainSong(s))
          .forEach(
              s -> {
                songDao.register(s);
                catalog.addSong(s);
              });
    } catch (DaoException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private static um.tds.nappmusic.domain.Song toDomainSong(um.tds.songloader.Song song) {
    List<String> uniqueStyle = new ArrayList();
    uniqueStyle.add(song.getStyle());
    return new um.tds.nappmusic.domain.Song(
        song.getTitle(), song.getAuthor(), uniqueStyle, song.getUrl(), 0);
  }
}
