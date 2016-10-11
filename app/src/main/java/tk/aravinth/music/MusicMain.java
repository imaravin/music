package tk.aravinth.music;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.net.Uri;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MusicMain extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<String> songs=new ArrayList<>();
    SongsAdapter adapter;
    boolean play=false;
    public static int current=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songs.size()>0)
                {
                    if(!play)
                    {
                        Snackbar.make(view, R.string.playString, Snackbar.LENGTH_SHORT)
                                .setAction(R.string.actionText, null).show();

                        SongsAdapter.playSong(current);
                        play=true;
                    }
                    else
                    {
                        Snackbar.make(view, R.string.pauseString, Snackbar.LENGTH_SHORT)
                                .setAction(R.string.actionText, null).show();
                        SongsAdapter.mediaPlayer.pause();
                        play=false;
                    }
                }
            }
        });

        // Shuffle

        FloatingActionButton shufflebutton = (FloatingActionButton) findViewById(R.id.shuffle);
        shufflebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Snackbar.make(v, R.string.shuffleString, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.actionText, null).show();
                Collections.shuffle(songs);
                adapter.notifyDataSetChanged();
                current=0;
                play=true;
                SongsAdapter.playSong(current);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.songslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SongsAdapter(songs);
        recyclerView.setAdapter(adapter);
        find();
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

        cur.close();

    }

}
