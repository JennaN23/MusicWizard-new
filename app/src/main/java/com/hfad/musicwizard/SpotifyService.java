package com.hfad.musicwizard;

import java.util.Map;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface SpotifyService {

    // the maximum number of objects to return
    String LIMIT = "limit";

    // the index of the first playlist to return
    // default: 0
    // use with limit to get the next set of objects
    String OFFSET = "offset";



    //artists
    @GET("/artists/{id}")
    void getArtist(@Path("id") String artistId, Callback<Artist> callback);
    @GET("/artists/{id}")
    Artist getArtist(@Path("id") String artistId);
    @GET("/artists")
    void getArtists(@Query("ids") String artistIds, Callback<Artists> callback);
    @GET("/artists")
    Artists getArtists(@Query("ids") String artistIds);



    // tracks
    @GET("/tracks/{id}")
    void getTrack(@Path("id") String trackId, Callback<Track> callback);
    @GET("/tracks/{id}")
    Track getTrack(@Path("id") String trackId);
    @GET("/tracks/{id}")
    void getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options, Callback<Track> callback);
    @GET("/tracks/{id}")
    Track getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options);



    // library (your music)
    @GET("/me/tracks")
    void getMySavedTracks(Callback<Pager<SavedTrack>> callback);
    @GET("/me/tracks")
    Pager<SavedTrack> getMySavedTracks();
    @GET("/me/tracks")
    void getMySavedTracks(@QueryMap Map<String, Object> options, Callback<Pager<SavedTrack>> callback);
    @GET("/me/tracks")
    Pager<SavedTrack> getMySavedTracks(@QueryMap Map<String, Object> options);



    // check if one or more tracks is already saved in the current user's library
    @GET("/me/tracks/contains")
    void containsMySavedTracks(@Query("ids") String ids, Callback<boolean[]> callback);
    @GET("/me/tracks/contains")
    Boolean[] containsMySavedTracks(@Query("ids") String ids);



    // save tracks to the user's library
    @PUT("/me/tracks")
    void addToMySavedTracks(@Query("ids") String ids, Callback<Object> callback);
    @PUT("/me/tracks")
    Result addToMySavedTracks(@Query("ids") String ids);



    // delete tracks from the user's library
    @DELETE("/me/tracks")
    void removeFromMySavedTracks(@Query("ids") String ids, Callback<Object> callback);
    @DELETE("/me/tracks")
    Result removeFromMySavedTracks(@Query("ids") String ids);



    // search tracks

    @GET("/search?type=track")
    void searchTracks(@Query("q") String q, Callback<TracksPager> callback);
    @GET("/search?type=track")
    TracksPager searchTracks(@Query("q") String q);
    @GET("/search?type=track")
    void searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<TracksPager> callback);
    @GET("/search?type=track")
    TracksPager searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options);



    //search artists
    @GET("/search?type=artist")
    void searchArtists(@Query("q") String q, Callback<ArtistsPager> callback);
    @GET("/search?type=artist")
    ArtistsPager searchArtists(@Query("q") String q);
    @GET("/search?type=artist")
    void searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<ArtistsPager> callback);
    @GET("/search?type=artist")
    ArtistsPager searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options);

}
