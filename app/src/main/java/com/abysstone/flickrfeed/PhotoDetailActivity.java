package com.abysstone.flickrfeed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        activateToolbar(true);

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null){
            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
            photoTitle.setText("Title" + photo.getmTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            photoTags.setText("Title" + photo.getmTag());

            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText("Title" + photo.getmAuthor());

            ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
            Picasso.with(this).load(photo.getmLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }

    }

}
