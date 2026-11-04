package com.example.poster1019;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The one place that owns the app's movie data: all posters, the selection, and the favorites.
 */
public class PosterRepository {

    // Notified whenever favorites are added or removed (Favorite List, badge).
    public interface FavoritesListener{
        void onFavoritesChanged();
    }

    private static PosterRepository instance;

    private final List<Posters> allPosters;
    private final FavoritesStore favoritesStore;
    private final List<FavoritesListener> favoritesListeners = new ArrayList<>();

    // Creates the single instance on first use. It keeps the application context,
    // never an Activity: holding an Activity in a singleton would leak it.
    public static synchronized PosterRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosterRepository(context.getApplicationContext());
        }
        return instance;
    }

    private PosterRepository(Context appContext) {
        allPosters = Collections.unmodifiableList(createPosters());
        favoritesStore = new FavoritesStore(appContext);

        // Forget saved favorites whose poster no longer exists in the app.
        List<String> validIds = new ArrayList<>();
        for (Posters poster : allPosters) {
            validIds.add(poster.id);
        }
        favoritesStore.retainOnly(validIds);
    }

    public List<Posters> getAllPosters(){
        return allPosters;
    }

    public Posters findById(String id){
        for(Posters posters:allPosters){
            if(posters.id.equals(id)){
                return posters;
            }
        }
        return null;
    }

    // deselects every poster
    public void clearSelection(){
        for(Posters poster : allPosters){
            poster.isSelected = false;
        }
    }

    //get favorite posters, in the order they were added
    public List<Posters> getFavorites(){
        List<Posters> favorites = new ArrayList<>();
        for (String id : favoritesStore.getIds()) {
            Posters poster = findById(id);
            if (poster != null) {
                favorites.add(poster);
            }
        }
        return favorites;
    }

    public int getFavoriteCount(){
        return favoritesStore.getIds().size();
    }

    // Adds posters to favorites, skipping ones already there.
    // Returns how many were new, so the page can show the right message.
    public int addFavorites(List<Posters> posters) {
        List<String> ids = new ArrayList<>();
        for (Posters poster : posters) {
            ids.add(poster.id);
        }
        int added = favoritesStore.addAll(ids);
        if (added > 0) {
            notifyFavoritesChanged();
        }
        return added;
    }

    // Removes one favorite. Returns true if it was a favorite.
    public boolean removeFavorite(String id) {
        boolean removed = favoritesStore.remove(id);
        if (removed) {
            notifyFavoritesChanged();
        }
        return removed;
    }

    // The listener (observer) pattern: pages register to hear about changes.
    // Whoever adds a listener must remove it again (onDestroyView / onDestroy).
    public void addFavoritesListener(FavoritesListener listener) {
        if (!favoritesListeners.contains(listener)) {
            favoritesListeners.add(listener);
        }
    }

    public void removeFavoritesListener(FavoritesListener listener) {
        favoritesListeners.remove(listener);
    }

    private void notifyFavoritesChanged() {
        for(FavoritesListener listener : new ArrayList<>(favoritesListeners)){
            listener.onFavoritesChanged();
        }
    }

    /**
     * Creates all the posters
     *
     * @return List of posters created
     */
    private static List<Posters> createPosters() {
        List<Posters> posters = new ArrayList<>();

        Posters theAvengers = new Posters();
        theAvengers.id = "theAvengers";
        theAvengers.image = R.drawable.the_avengers;
        theAvengers.name = "The Avengers";
        theAvengers.rating = 5f;
        theAvengers.createdBy = "Marvel";
        theAvengers.story = "This is a story about superheroes fighting to save earth";
        posters.add(theAvengers);

        Posters dune = new Posters();
        dune.id = "Dune";
        dune.image = R.drawable.dune;
        dune.name = "Dune";
        dune.rating = 4.5f;
        dune.createdBy = "Frank Herbert";
        dune.story = "The original Space-Opera";
        posters.add(dune);

        Posters avatar = new Posters();
        avatar.id = "Avatar";
        avatar.image = R.drawable.avatar;
        avatar.name = "Avatar";
        avatar.rating = 5f;
        avatar.createdBy = "James Cameron";
        avatar.story = "This is the story of humans invading an alien planet";
        posters.add(avatar);

        Posters jaws = new Posters();
        jaws.id = "Jaws";
        jaws.image = R.drawable.jaws;
        jaws.name = "Jaws";
        jaws.rating = 4f;
        jaws.createdBy = "Peter Benchley";
        jaws.story = "This is the story of a shark";
        posters.add(jaws);

        Posters joker = new Posters();
        joker.id = "Joker";
        joker.image = R.drawable.joker;
        joker.name = "Joker";
        joker.rating = 5f;
        joker.createdBy = "Todd Phillips";
        joker.story = "This is a movie about the Joker from Batman";
        posters.add(joker);

        return posters;

    }


}
