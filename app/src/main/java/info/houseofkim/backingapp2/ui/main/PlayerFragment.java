package info.houseofkim.backingapp2.ui.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Step;

public class PlayerFragment extends Fragment implements ExoPlayer.EventListener {
    private SimpleExoPlayer exoPlayer;
    private long currentExoPlayerPosition;
    private PlayerView mediaBinding;
    private DataSource.Factory dataSourceFactory;
    private TextView stepDescription;
    private TextView tvError;
    private BottomNavigationMenu stepNav;

    private Step mStepCurrent;


    private String videoUrl = "";
    private Activity activity;
    private String descr = "";

    public PlayerFragment() {
    }

    //    private Step mStepCurrent;
    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().hide();


            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentExoPlayerPosition = savedInstanceState.getLong(getString(R.string.player_currentt_pos));

        }
        View view = inflater.inflate(R.layout.player_fragment, container, false);
        mediaBinding = view.findViewById(R.id.exo_playerView);
        stepDescription = view.findViewById(R.id.player_step_description);
        tvError = view.findViewById(R.id.tv_error_no_url);
        tvError.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            MainViewModel playerModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            //  mStepCurrent = playerModel.getCurrentStep();
            playerModel.getSelectedStep().observe(this, new Observer<Step>() {
                @Override
                public void onChanged(@Nullable Step step) {
                    if (step != null) {
                        mStepCurrent = step;
                        videoUrl = step.getVideoURL();

                        if (!videoUrl.equals("")) {
                            tvError.setVisibility(View.INVISIBLE);

                            mediaBinding.setVisibility(View.VISIBLE);
                            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                    .createMediaSource(Uri.parse(videoUrl));
                            // Prepare the player with the source.
                            exoPlayer.prepare(videoSource);
                        }else {
                            mediaBinding.setVisibility(View.INVISIBLE);
                            tvError.setVisibility(View.VISIBLE);
                        }
                        descr = step.getDescription();
                        stepDescription.setText(descr);
                        Log.e("PlayerFragment", videoUrl);
                    }
                }
            });


//            View decorView = getActivity().getWindow().getDecorView();
//// Hide the status bar.
//            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
        }
        if (!videoUrl.equals("")) {
            tvError.setVisibility(View.INVISIBLE);

            initializePlayer();
            mediaBinding.setVisibility(View.VISIBLE);


        } else {
            mediaBinding.setVisibility(View.INVISIBLE);
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(getString(R.string.player_currentt_pos), exoPlayer.getCurrentPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void setmStepCurrent(Step mStepCurrent) {
        this.mStepCurrent = mStepCurrent;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    private void initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());

            // Produces DataSource instances through which media data is loaded.
            Context context = getContext();

            dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "BackingApp2"));
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl));
            // Prepare the player with the source.
            exoPlayer.prepare(videoSource);

            mediaBinding.setPlayer(exoPlayer);


            exoPlayer.addListener(this);

            if (currentExoPlayerPosition > 0)
                exoPlayer.seekTo(currentExoPlayerPosition);
            exoPlayer.setPlayWhenReady(true);

        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

}
