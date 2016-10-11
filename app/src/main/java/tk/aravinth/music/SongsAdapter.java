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
    static MediaPlayer mediaPlayer=new MediaPlayer();
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
        File file=new File(song);
        holder.songname.setText(file.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                File file=new File(songs.get(position).toString());
                MusicMain.current=position;
                playSong(MusicMain.current);
                Snackbar.make(v,"Playing "+file.getName(),Snackbar.LENGTH_SHORT).show();

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
    public static void playSong(int postion)
    {
       if(songs.size()>0)
       {
           try
           {
               mediaPlayer.reset();
               mediaPlayer.setDataSource(songs.get(postion).toString());
               mediaPlayer.prepare();
               mediaPlayer.start();

           } catch (IOException e)
           {
               e.printStackTrace();
           }
       }
    }
}
