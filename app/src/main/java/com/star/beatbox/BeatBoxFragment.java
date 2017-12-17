package com.star.beatbox;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.star.beatbox.databinding.FragmentBeatBoxBinding;
import com.star.beatbox.databinding.ListItemSoundBinding;

import java.util.List;

public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBeatBoxBinding fragmentBeatBoxBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_beat_box, container, false);

        fragmentBeatBoxBinding.fragmentBeatBoxRecyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(), 3)
        );
        fragmentBeatBoxBinding.fragmentBeatBoxRecyclerView.setAdapter(
                new SoundAdapter(mBeatBox.getSounds())
        );

        return fragmentBeatBoxBinding.getRoot();
    }

    private class SoundHolder extends RecyclerView.ViewHolder {
        private ListItemSoundBinding mListItemSoundBinding;

        public SoundHolder(ListItemSoundBinding listItemSoundBinding) {
            super(listItemSoundBinding.getRoot());

            mListItemSoundBinding = listItemSoundBinding;
            mListItemSoundBinding.setSoundViewModel(new SoundViewModel(mBeatBox));
        }

        public void bindSound(Sound sound) {
            mListItemSoundBinding.getSoundViewModel().setSound(sound);
            mListItemSoundBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding listItemSoundBinding = DataBindingUtil
                    .inflate(inflater, R.layout.list_item_sound, parent, false);

            return new SoundHolder(listItemSoundBinding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
