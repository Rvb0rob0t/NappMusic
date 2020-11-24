package um.tds.nappmusic.domain;

import java.util.ArrayList;

public class User {
    private String name;
    private boolean premium;
    private Discount discount;
    private ArrayList<Playlist> playlists;
    // DIFFERENCE We use Playlist for almost everything (that is a list of songs).
    // The idea is that the user can also replay the list of recently played songs.
    private Playlist recent;

    public User(
        String name,
        boolean premium,
        Discount discount,
        ArrayList<Playlist> playlists,
        Playlist recent
    ) {
        this.name = name;
        this.premium = premium;
        this.discount = discount;
        this.playlists = playlists;
        this.recent = recent;
    }

    //TODO consider if this is necessary
    // public void addPlaylist(Playlist playlist) {
    //     playlists.add(playlist);
    // }

    //TODO consider if this is necessary
    // public void updateRecent(Song reproduccion) {
    //     recent.add(reproduccion);
    // }

    public String getName() {
        return name;
    }

    public boolean isPremium() {
        return premium;
    }

    public Discount getDiscount() {
        return discount;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getRecent() {
        return recent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}

