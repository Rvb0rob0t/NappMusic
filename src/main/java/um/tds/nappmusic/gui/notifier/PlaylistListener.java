package um.tds.nappmusic.gui.notifier;

import java.util.EventListener;

public interface PlaylistListener extends EventListener {
  public void playlistModified();
}
