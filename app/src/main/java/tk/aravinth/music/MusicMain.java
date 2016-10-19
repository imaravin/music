package tk.aravinth.music;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.net.Uri;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MusicMain extends AppCompatActivity
{
    RecyclerView recyclerView;
    SongsAdapter adapter;
    static boolean play=true;
    static FloatingActionButton playButton;
    FloatingActionButton shufflebutton;
    public static int current=0;


    static List<String> songs=new ArrayList<>();
    public static MediaPlayer mediaPlayer=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        find();
        setContentView(R.layout.activity_music_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shufflebutton = (FloatingActionButton) findViewById(R.id.shuffle);
        playButton = (FloatingActionButton) findViewById(R.id.fab);

        // play
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songs.size()>0)
                {
                    if(play)
                    {
                        setSongDetails(current);
                        setIconAndPlay(getResources());
                        play=false;
                    }
                    else
                    {
                        setIconAndPlay(getResources());
                        play=true;
                    }
                }
            }
        });

        // Shuffle

        shufflebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Snackbar.make(v, R.string.shuffleString, Snackbar.LENGTH_SHORT).setAction(R.string.actionText, null).show();
                Collections.shuffle(songs);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
                current=0;
                play=true;
                setSongDetails(current);
                setIconAndPlay(getResources());
                play=false;

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.songslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SongsAdapter(songs);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void find()
    {
        ContentResolver  contentResolver = getContentResolver();
        Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = contentResolver.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    songs.add(data);
                }

            }
        }
        songs.add("ADD");
        songs.add("ADD");
        songs.add("ADD");
        songs.add("ADD");
        cur.close();

    }

    public static void setIconAndPlay(Resources resources)
    {
        if(play)
        {
                mediaPlayer.start();
                playButton.setImageDrawable(resources.getDrawable(R.mipmap.ic_media_pause));
        }
        else
        {
            mediaPlayer.pause();
            playButton.setImageDrawable(resources.getDrawable(R.mipmap.ic_media_play));
        }
    }

    public static void setSongDetails(final int postion)
    {

        if(songs.size()>0)
        {
            try
            {

                mediaPlayer.reset();
                mediaPlayer.setDataSource(songs.get(postion).toString());
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {

                    }
                });

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
