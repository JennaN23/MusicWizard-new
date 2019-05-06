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

    /**
     * The maximum number of objects to return.
     */
    String LIMIT = "limit";

    /**
     * The index of the first playlist to return. Default: 0 (the first object).
     * Use with limit to get the next set of objects (albums, playlists, etc).
     */
    String OFFSET = "offset";

    /**
     * A comma-separated list of keywords that will be used to filter the response.
     * Valid values are: {@code album}, {@code single}, {@code appears_on}, {@code compilation}
     */
    String ALBUM_TYPE = "album_type";

    /**
     * The country: an ISO 3166-1 alpha-2 country code.
     * Limit the response to one particular geographical market.
     * Synonym to {@link #COUNTRY}
     */
    String MARKET = "market";

    /**
     * Same as {@link #MARKET}
     */
    String COUNTRY = "country";

    /**
     * The desired language, consisting of a lowercase ISO 639 language code
     * and an uppercase ISO 3166-1 alpha-2 country code, joined by an underscore.
     * For example: es_MX, meaning "Spanish (Mexico)".
     */
    String LOCALE = "locale";

    /**
     * Filters for the query: a comma-separated list of the fields to return.
     * If omitted, all fields are returned.
     */
    String FIELDS = "fields";

    /**
     * A timestamp in ISO 8601 format: yyyy-MM-ddTHH:mm:ss. Use this parameter to
     * specify the user's local time to get results tailored for that specific date
     * and time in the day. If not provided, the response defaults to the current UTC time
     */
    String TIMESTAMP = "timestamp";

    String TIME_RANGE = "time_range";

    /***********
     * Artists *
     ***********/

    @GET("/artists/{id}")
    void getArtist(@Path("id") String artistId, Callback<Artist> callback);

    @GET("/artists/{id}")
    Artist getArtist(@Path("id") String artistId);

    @GET("/artists")
    void getArtists(@Query("ids") String artistIds, Callback<Artists> callback);

    @GET("/artists")
    Artists getArtists(@Query("ids") String artistIds);

    /**********
     * Tracks *
     **********/

    @GET("/tracks/{id}")
    void getTrack(@Path("id") String trackId, Callback<Track> callback);

    @GET("/tracks/{id}")
    Track getTrack(@Path("id") String trackId);

    @GET("/tracks/{id}")
    void getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options, Callback<Track> callback);

    @GET("/tracks/{id}")
    Track getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options);

    /************************
     * Library / Your Music *
     ************************/

    @GET("/me/tracks")
    void getMySavedTracks(Callback<Pager<SavedTrack>> callback);

    @GET("/me/tracks")
    Pager<SavedTrack> getMySavedTracks();

    @GET("/me/tracks")
    void getMySavedTracks(@QueryMap Map<String, Object> options, Callback<Pager<SavedTrack>> callback);

    @GET("/me/tracks")
    Pager<SavedTrack> getMySavedTracks(@QueryMap Map<String, Object> options);

    /**
     * Check if one or more tracks is already saved in the current SpotifyAPI user’s “Your Music” library.
     *
     * @param ids      A comma-separated list of the SpotifyAPI IDs for the tracks
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/check-users-saved-tracks/">Check User’s Saved Tracks</a>
     */
    @GET("/me/tracks/contains")
    void containsMySavedTracks(@Query("ids") String ids, Callback<boolean[]> callback);

    /**
     * Check if one or more tracks is already saved in the current SpotifyAPI user’s “Your Music” library.
     *
     * @param ids A comma-separated list of the SpotifyAPI IDs for the tracks
     * @return An array with boolean values that indicate whether the tracks are in the current SpotifyAPI user’s “Your Music” library.
     * @see <a href="https://developer.spotify.com/web-api/check-users-saved-tracks/">Check User’s Saved Tracks</a>
     */
    @GET("/me/tracks/contains")
    Boolean[] containsMySavedTracks(@Query("ids") String ids);

    /**
     * Save one or more tracks to the current user’s “Your Music” library.
     *
     * @param ids      A comma-separated list of the SpotifyAPI IDs for the tracks
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/save-tracks-user/">Save Tracks for User</a>
     */
    @PUT("/me/tracks")
    void addToMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Save one or more tracks to the current user’s “Your Music” library.
     *
     * @param ids A comma-separated list of the SpotifyAPI IDs for the tracks
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/save-tracks-user/">Save Tracks for User</a>
     */
    @PUT("/me/tracks")
    Result addToMySavedTracks(@Query("ids") String ids);

    @DELETE("/me/tracks")
    void removeFromMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    @DELETE("/me/tracks")
    Result removeFromMySavedTracks(@Query("ids") String ids);


    /**********
     * Search *
     **********/

    @GET("/search?type=track")
    void searchTracks(@Query("q") String q, Callback<TracksPager> callback);

    @GET("/search?type=track")
    TracksPager searchTracks(@Query("q") String q);

    @GET("/search?type=track")
    void searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<TracksPager> callback);

    @GET("/search?type=track")
    TracksPager searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options);

    @GET("/search?type=artist")
    void searchArtists(@Query("q") String q, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    ArtistsPager searchArtists(@Query("q") String q);

    @GET("/search?type=artist")
    void searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    ArtistsPager searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options);

}
