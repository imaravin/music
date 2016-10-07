package tk.aravinth.music;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import android.net.Uri;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MusicMain extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<String> songs=new ArrayList<>();
    SongsAdapter adapter;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                byte[] bytes=new byte[10];
                new Random().nextBytes(bytes);
                songs.add(0,new String(bytes));
                adapter.notifyDataSetChanged();
            }
        });

        // Shuffle

        FloatingActionButton shufflebutton = (FloatingActionButton) findViewById(R.id.shuffle);
        shufflebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Collections.shuffle(songs);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.songslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SongsAdapter(songs);
        recyclerView.setAdapter(adapter);
//        findSongs(Environment.getRootDirectory());
        find();
        adapter.notifyDataSetChanged();

    }

    private void findSongs(File sdcard)
    {
        if(sdcard==null)
            return;
        File[] files=sdcard.listFiles();
        if(files == null)
            return;
        for(File file:files)
            if(!file.isDirectory())
                songs.add(file.getName());
        else
                findSongs(file);
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
        if (id == R.id.action_settings) {
            return true;
        }

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
