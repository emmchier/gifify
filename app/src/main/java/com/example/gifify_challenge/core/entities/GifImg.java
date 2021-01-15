package com.example.gifify_challenge.core.entities;
import com.example.gifify_challenge.core.entities.sizes.Downsized;
import com.example.gifify_challenge.core.entities.sizes.DownsizedLarge;
import com.example.gifify_challenge.core.entities.sizes.DownsizedMedium;
import com.example.gifify_challenge.core.entities.sizes.DownsizedSmall;
import com.example.gifify_challenge.core.entities.sizes.DownsizedStill;
import com.example.gifify_challenge.core.entities.sizes.Looping;
import com.example.gifify_challenge.core.entities.sizes.PreviewGif;

public class GifImg {
    private Downsized downsized;
    private DownsizedSmall downsized_small;
    private DownsizedStill downsized_still;
    private DownsizedLarge downsized_large;
    private DownsizedMedium downsized_medium;
    private Looping looping;
    private PreviewGif preview_gif;

    public GifImg(Downsized downsized, DownsizedSmall downsized_small, DownsizedStill downsized_still, DownsizedLarge downsized_large,
                  DownsizedMedium downsized_medium, Looping looping, PreviewGif preview_gif) {
        this.downsized = downsized;
        this.downsized_small = downsized_small;
        this.downsized_still = downsized_still;
        this.downsized_large = downsized_large;
        this.downsized_medium = downsized_medium;
        this.looping = looping;
        this.preview_gif = preview_gif;
    }

    public Downsized getDownsized() {
        return downsized;
    }

    public void setDownsized(Downsized downsized) {
        this.downsized = downsized;
    }

    public DownsizedSmall getDownsized_small() {
        return downsized_small;
    }

    public void setDownsized_small(DownsizedSmall downsized_small) {
        this.downsized_small = downsized_small;
    }

    public DownsizedStill getDownsized_still() {
        return downsized_still;
    }

    public void setDownsized_still(DownsizedStill downsized_still) {
        this.downsized_still = downsized_still;
    }

    public DownsizedLarge getDownsized_large() {
        return downsized_large;
    }

    public void setDownsized_large(DownsizedLarge downsized_large) {
        this.downsized_large = downsized_large;
    }

    public DownsizedMedium getDownsized_medium() {
        return downsized_medium;
    }

    public void setDownsized_medium(DownsizedMedium downsized_medium) {
        this.downsized_medium = downsized_medium;
    }

    public Looping getLooping() {
        return looping;
    }

    public void setLooping(Looping looping) {
        this.looping = looping;
    }

    public PreviewGif getPreview_gif() {
        return preview_gif;
    }

    public void setPreview_gif(PreviewGif preview_gif) {
        this.preview_gif = preview_gif;
    }
}
