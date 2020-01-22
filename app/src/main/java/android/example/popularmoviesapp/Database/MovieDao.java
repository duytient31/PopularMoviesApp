package android.example.popularmoviesapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY updated_at")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie WHERE is_popular = 1")
    LiveData<List<Movie>> getPopularMovies();

    @Query("SELECT * FROM movie WHERE is_highly_ranked = 1")
    LiveData<List<Movie>> getHighlyRankedMovies();

    @Query("SELECT * FROM movie WHERE is_liked = 1")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM movie WHERE data_string = :data")
    Movie getMovieByDataString(String data);

    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);
}
