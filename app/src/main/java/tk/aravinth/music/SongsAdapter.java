package tk.aravinth.music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aravinth-4022 on 4/10/16.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder>
{
    List songs;

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
    public void onBindViewHolder(SongViewHolder holder, int position)
    {
        String song= (String) songs.get(position);
        holder.songname.setText(song);
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