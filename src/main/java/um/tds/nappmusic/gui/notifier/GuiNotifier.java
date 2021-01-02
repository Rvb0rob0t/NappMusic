package um.tds.nappmusic.gui.notifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import um.tds.nappmusic.domain.Playlist;

public enum GuiNotifier {
  INSTANCE;

  private Map<String, Set<PlaylistListener>> playlistListeners;
  private Set<PlaylistListListener> playlistListListeners;

  private GuiNotifier() {
    playlistListeners = new HashMap<String, Set<PlaylistListener>>();
    playlistListListeners = new HashSet<PlaylistListListener>();
  }

  /** Notifies to all the listeners that a playlist has been modified. */
  public void notifyPlaylistListeners(Playlist playlist) {
    Set<PlaylistListener> listeners = playlistListeners.get(playlist.getName());
    if (listeners == null) {
      return;
    }
    for (PlaylistListener listener : listeners) {
      listener.playlistModified();
    }
  }

  /** Notifies to all the listeners that the list of playlist has been modified. */
  public void notifyPlaylistListListeners() {
    for (PlaylistListListener listener : playlistListListeners) {
      listener.playlistListModified();
    }
  }

  /**
   * Register a listener to be notified when a playlist changes.
   *
   * @param playlist The playlist that changes
   * @param listener The object to be notified
   * @return true if the listener were not already registered
   */
  public boolean addPlaylistListener(Playlist playlist, PlaylistListener listener) {
    Set<PlaylistListener> listeners =
        playlistListeners.computeIfAbsent(playlist.getName(), k -> new HashSet<>());
    return listeners.add(listener);
  }

  /**
   * Register a listener to be notified when the list of playlists changes.
   *
   * @param listener The object to be notified
   * @return true if the listener were not already registered
   */
  public boolean addPlaylistListListener(PlaylistListListener listener) {
    return playlistListListeners.add(listener);
  }

  /**
   * Unregister a listener.
   *
   * @param playlist The playlist that changes
   * @param listener The object not to be notified
   * @return true if the listener were registered
   */
  public boolean removePlaylistListener(Playlist playlist, PlaylistListener listener) {
    if (!playlistListeners.containsKey(playlist.getName())) {
      return false;
    }
    return playlistListeners.get(playlist.getName()).remove(listener);
  }

  /**
   * Unregister a listener.
   *
   * @param listener The object to be notified
   * @return true if the listener were registered
   */
  public boolean removePlaylistListListener(PlaylistListListener listener) {
    return playlistListListeners.remove(listener);
  }
}
