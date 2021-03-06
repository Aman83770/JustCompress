package com.example.justcompress;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton imgButton,videoButton,musicButton,textButton;
    ImageView imageview;
    private final static int SELECT_PHOTO = 12345;
    private int PICK_IMAGE_REQUEST=1;
    private int PICK_MUSIC_REQUEST=2;
    private int PICK_VIDEO_REQUEST=3;
    private int PICK_TEXT_REQUEST=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imgButton = (ImageButton) findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //i.setAction("image/*");
                startActivityForResult(i, PICK_IMAGE_REQUEST);
                /*intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//at: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample#sthash.zxy9jNTq.dpuf
                //return false;*/
            }
        });
        musicButton =(ImageButton)findViewById(R.id.imageButton2);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent,PICK_MUSIC_REQUEST);
                //return false;
            }
        });

        videoButton =(ImageButton)findViewById(R.id.imageButton3);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                Log.e("welcome","svs");
                startActivityForResult(photoPickerIntent,PICK_VIDEO_REQUEST);
                //return false;
            }
        });

        textButton =(ImageButton)findViewById(R.id.imageButton4);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("*/*");
                startActivityForResult(photoPickerIntent, PICK_TEXT_REQUEST);
                //return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

           Uri selectedImage = data.getData();
            String [] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            Intent intent = new Intent(MainActivity.this,Image_activity.class);
            intent.putExtra("image", filePath);
            intent.setData(selectedImage);
            startActivity(intent);
        }
        else if(requestCode==PICK_MUSIC_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            Uri selectedmusic = data.getData();
            String [] filePathColumn = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedmusic, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            Log.e("Music",filePath);
            Bitmap selectedphoto = BitmapFactory.decodeFile(filePath);
            cursor.close();
            Intent intent = new Intent(MainActivity.this,Music_activity.class);
            intent.putExtra("video", filePath);
            intent.setData(selectedmusic);
            startActivity(intent);
        }
        else if(requestCode==PICK_VIDEO_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            Log.e("entry","svs");
            Uri selectedVideo = data.getData();
            Log.e("uri",selectedVideo+"0");
            String [] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            Log.e("Video",filePath);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            cursor.close();
            Intent intent = new Intent(MainActivity.this,Video_activity.class);
            intent.putExtra("video", filePath);
            intent.setData(selectedVideo);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.compress) {
            // Handle the first screen
        } else if (id == R.id.decompress) {

        } else if (id == R.id.history) {

        } else if (id == R.id.about) {

        } else if (id == R.id.share) {

        } else if (id == R.id.rate_me) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
