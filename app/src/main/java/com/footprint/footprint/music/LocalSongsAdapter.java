package com.footprint.footprint.music;

import android.content.Context;

import com.footprint.footprint.R;

import java.util.List;


public class LocalSongsAdapter extends SimpleBaseAdapter<LocalSongs> {

    private Context context;

    public LocalSongsAdapter(Context context, int layoutId, List<LocalSongs> data){
        super(context,layoutId,data);
        this.context = context;
    }

    @Override
    public void getItemView(ViewHolder holder, LocalSongs localSongs) {
        holder.setText(R.id.song_title,localSongs.getTitle())
                .setText(R.id.song_artist,localSongs.getArtist())
                .setText(R.id.song_duration, MediaUtils.formatime(localSongs.getDuration()));
    }


}
