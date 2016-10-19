package tk.aravinth.music;

import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by aravinth-4022 on 4/10/16.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder>
{
    static List songs;
    public SongsAdapter(List list)
    {
        songs=list;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.song,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, final int position)
    {
        String song= (String) songs.get(position);
        int index=song.lastIndexOf('/');
        if(index<0)
        {
            holder.songname.setText("\n"+song.substring(0));
        }
        else
        {
            holder.songname.setText("\n"+song.substring(index+1));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                MusicMain.setSongDetails(position);
                MusicMain.play=true;
                MusicMain.setIconAndPlay(v.getResources());
                MusicMain.play=false;


            }
        });
    }

    @Override
    public int getItemCount()
    {
        return songs.size();
    }


    // Inner view Holder

    public class SongViewHolder extends  RecyclerView.ViewHolder
    {
        TextView songname;

        public SongViewHolder(View itemView)
        {
            super(itemView);
            songname= (TextView) itemView.findViewById(R.id.songname);

        }
    }

}
