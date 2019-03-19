package com.nanodegree.bakingapp.features.steps;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Step;
import com.nanodegree.bakingapp.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.nanodegree.bakingapp.utils.Constants.STEP_PLAY_WHEN_READY;
import static com.nanodegree.bakingapp.utils.Constants.STEP_PLAY_WINDOW_INDEX;
import static com.nanodegree.bakingapp.utils.Constants.STEP_SINGLE;
import static com.nanodegree.bakingapp.utils.Constants.STEP_URI;
import static com.nanodegree.bakingapp.utils.Constants.STEP_VIDEO_POSITION;

public class VideoFragment extends Fragment {
    private PlayerView video;
    private ImageView imageView;
    private boolean mShouldPlayWhenReady = true;
    private long mPlayerPosition;
    private int mWindowIndex;
    private Uri videoUri;
    private Step step;
    private SimpleExoPlayer simpleExoPlayer;
    public VideoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment_layout, container, false);
        video = view.findViewById(R.id.video);
        imageView = view.findViewById(R.id.imageView);
        Bundle bundle = getArguments();
        if(bundle != null) {
            step = bundle.getParcelable(Constants.STEP_SINGLE);
        }
        if(savedInstanceState != null){
            step = savedInstanceState.getParcelable(STEP_SINGLE);
            mShouldPlayWhenReady = savedInstanceState.getBoolean(STEP_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(STEP_VIDEO_POSITION);
            mWindowIndex = savedInstanceState.getInt(STEP_PLAY_WINDOW_INDEX);
            videoUri = Uri.parse(savedInstanceState.getString(STEP_URI));
        }else {
            setVideo();
        }
        return view;
    }

    private void setVideo() {
        if(step.getVideoURL().equals("")){
            if(step.getThumbnailURL().equals("")){
                imageView.setVisibility(View.VISIBLE);
                video.setUseArtwork(true);
                video.setUseController(false);
            }else{

                imageView.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                String thumbnail = step.getThumbnailURL();
                Bitmap thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(thumbnail, MediaStore.Video.Thumbnails.MICRO_KIND);
                video.setUseArtwork(true);
                video.setDefaultArtwork(thumbnailBitmap);
            }
        }else{
            video.setVisibility(View.VISIBLE);
            videoUri = Uri.parse(step.getVideoURL());
        }
    }

    public void initializeVideoPlayer(Uri videoUri){

        if(simpleExoPlayer == null){

            simpleExoPlayer=  ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            video.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            if (mPlayerPosition != C.TIME_UNSET) {
                simpleExoPlayer.seekTo(mPlayerPosition);
            }
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            updateStartPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeVideoPlayer(videoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializeVideoPlayer(videoUri);
        }
        if(simpleExoPlayer != null){
            simpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
            simpleExoPlayer.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(simpleExoPlayer != null){
            updateStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(simpleExoPlayer != null){
            updateStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putString(STEP_URI, step.getVideoURL());
        outState.putParcelable(STEP_SINGLE, step);
        outState.putLong(STEP_VIDEO_POSITION, mPlayerPosition);
        outState.putBoolean(STEP_PLAY_WHEN_READY, mShouldPlayWhenReady);
    }

    private void updateStartPosition() {
        if (simpleExoPlayer != null) {
            mShouldPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            mWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
            mPlayerPosition = simpleExoPlayer.getCurrentPosition();
        }
    }

}
