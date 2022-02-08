package com.example.proccoli2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

/**
 * Class where users can view a collection of avatars to choose and update their profile from.
 * Colors of the avatar are randomly generated in this class and the image number and color
 * are sent back to profile page activity to be updated
 */
public class avatarView extends AppCompatActivity {

    ImageView avatar0, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, avatar9;
    ImageView avatar10, avatar11, avatar12, avatar13, avatar14, avatar15, avatar16, avatar17;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatarcollection_view);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar = findViewById(R.id.toolbarAvatar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        avatar0 = findViewById(R.id.avatar0);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        avatar9 = findViewById(R.id.avatar9);
        avatar10 = findViewById(R.id.avatar10);
        avatar11 = findViewById(R.id.avatar11);
        avatar12 = findViewById(R.id.avatar12);
        avatar13 = findViewById(R.id.avatar13);
        avatar14 = findViewById(R.id.avatar14);
        avatar15 = findViewById(R.id.avatar15);
        avatar16 = findViewById(R.id.avatar16);
        avatar17 = findViewById(R.id.avatar17);

        avatar0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar0.toString());
                setAvatar(0);
            }
        });

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar1.toString());
                setAvatar(1);
            }
        });
        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar2.toString());
                setAvatar(2);
            }
        });
        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar3.toString());
                setAvatar(3);
            }
        });
        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar4.toString());
                setAvatar(4);
            }
        });
        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar5.toString());
                setAvatar(5);
            }
        });
        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar6.toString());
                setAvatar(6);
            }
        });
        avatar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar7.toString());
                setAvatar(7);
            }
        });
        avatar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar8.toString());
                setAvatar(8);
            }
        });
        avatar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar9.toString());
                setAvatar(9);
            }
        });
        avatar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar10.toString());
                setAvatar(10);
            }
        });
        avatar11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar11.toString());
                setAvatar(11);
            }
        });
        avatar12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar12.toString());
                setAvatar(12);
            }
        });
        avatar13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar13.toString());
                setAvatar(13);
            }
        });
        avatar14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar14.toString());
                setAvatar(14);
            }
        });
        avatar15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar15.toString());
                setAvatar(15);
            }
        });
        avatar16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar16.toString());
                setAvatar(16);
            }
        });
        avatar17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Avatar", "onClick: You clicked: " + avatar17.toString());
                setAvatar(17);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAvatar(int i){
        Intent myIntent = new Intent(this, profileView.class);
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);

        String colorCode = String.format("#%06x", nextInt);
        myIntent.putExtra("avatarImage", Integer.toString(i));
        myIntent.putExtra("colorCode",colorCode);
        Log.d("IMAGEIDFromCollection", "setAvatar: " + Integer.toString(i));
        Log.d("ColorCodeFromCollection", "setAvatar: " + colorCode);
        setResult(RESULT_OK,myIntent);
        finish();

    }
}
