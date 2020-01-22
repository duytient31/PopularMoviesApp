package android.example.popularmoviesapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.popularmoviesapp.R;
import android.example.popularmoviesapp.Repository.DataExchanger;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieUserRating;
    private TextView movieReleaseYear;
    private ImageButton favoriteButton;
    private TextView movieDesc;
    private TextView movieComments;
    private ImageButton trailerButton1;
    private ImageButton trailerButton2;
    private TextView textViewTrailer1;
    private TextView textViewTrailer2;
    private static DataExchanger dataExchanger;

    private static boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra("my_extra_data");

        dataExchanger = DataExchanger.getInstance();

        String[] data = message.split("  ");
        String posterUrl = data[0];
        String title = data[1];
        String userRating = data[2];
        String releaseYear = data[3];
        String description = data[4];
        String comment = data[5];
        final String url1 = data[6];
        String url2 = data[7];

        movieTitle = (TextView) findViewById(R.id.detail_tv_original_title);
        movieTitle.setText(title);

        movieUserRating = (TextView) findViewById(R.id.detail_tv_user_rating);
        movieUserRating.setText("User Rating: " + userRating + " / 10");

        movieReleaseYear = (TextView) findViewById(R.id.detail_tv_release_year);
        movieReleaseYear.setText("Release year: "+ releaseYear);

        movieDesc = (TextView) findViewById(R.id.detail_tv_synopsis);
        movieDesc.setText(description);

        moviePoster = (ImageView) findViewById(R.id.detail_iv_poster);
        Picasso.get().
                load(posterUrl).networkPolicy(NetworkPolicy.OFFLINE)
                .centerCrop().resize(375, 600).placeholder(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                into(moviePoster);

        favoriteButton = (ImageButton) findViewById(R.id.favorite_btn);
        favoriteButton.setOnClickListener(v -> {
            if (!isSelected){
                dataExchanger.markMovieAsFavorite(message);
                favoriteButton.setBackgroundResource(R.drawable.ic_favorite_36px);
                isSelected = true;
            } else{
                dataExchanger.cancelMovieAsFavorite(message);
                favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_36px);
                isSelected = false;
            }
        });

        movieComments = (TextView) findViewById(R.id.detail_tv_comments);
        movieComments.setText(comment);

        textViewTrailer1 = (TextView) findViewById(R.id.detail_tv_trailer1);
        trailerButton1 = (ImageButton) findViewById(R.id.detail_btn_trailer1);
        if (url1.substring(0,4).equals("Fail")){
            textViewTrailer1.setVisibility(View.INVISIBLE);
            trailerButton1.setVisibility(View.INVISIBLE);
        } else{
            textViewTrailer1.setOnClickListener(v -> openUrl(url1));
            trailerButton1.setOnClickListener(v -> openUrl(url1));
        }

        textViewTrailer2 = (TextView) findViewById(R.id.detail_tv_trailer2);
        trailerButton2 = (ImageButton) findViewById(R.id.detail_btn_trailer2);
        if (url2.substring(0,4).equals("Fail")){
            textViewTrailer2.setVisibility(View.INVISIBLE);
            trailerButton2.setVisibility(View.INVISIBLE);
        } else{
            textViewTrailer2.setOnClickListener(v -> openUrl(url2));
            trailerButton2.setOnClickListener(v -> openUrl(url2));
        }

    }

    public void openUrl(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
