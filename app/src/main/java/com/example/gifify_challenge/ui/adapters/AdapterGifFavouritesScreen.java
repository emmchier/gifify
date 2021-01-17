package com.example.gifify_challenge.ui.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.CellGifBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterGifFavouritesScreen extends RecyclerView.Adapter {

    private List<GifEntity> favouriteList;
    private FavouriteListener favouriteListener;

    public AdapterGifFavouritesScreen(FavouriteListener favouriteListener) {
        this.favouriteList = new ArrayList<>();
        this.favouriteListener = favouriteListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        CellGifBinding favouriteCell = CellGifBinding.bind(inflater.inflate(R.layout.cell_gif, parent, false));
        FavouriteListViewholder viewholder = new FavouriteListViewholder(favouriteCell);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GifEntity favourite = favouriteList.get(position);
        FavouriteListViewholder favouriteViewholder = (FavouriteListViewholder) holder;
        favouriteViewholder.setFavouriteCellData(favourite);
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public void loadFavourites(List<GifEntity> favs) {
        this.favouriteList.clear();
        this.favouriteList.addAll(favs);
        Collections.reverse(favouriteList);
        notifyDataSetChanged();
    }

    public class FavouriteListViewholder extends RecyclerView.ViewHolder {

        private CellGifBinding binding;

        public FavouriteListViewholder(CellGifBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favouriteListener.shareGif(favouriteList.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    favouriteListener.deleteFromList(favouriteList.get(getAdapterPosition()));
                    favouriteList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    return true;
                }
            });
        }

        @SuppressLint("CheckResult")
        public void setFavouriteCellData(GifEntity gif) {
            Glide.with(itemView)
                    .load(gif.getImages().getDownsized().getUrl())
                    .placeholder(R.drawable.ic_baseline_videocam_24)
                    .error(R.drawable.ic_baseline_videocam_24)
                    .into(binding.imageViewGif);
        }
    }

    public interface FavouriteListener {
        void shareGif(GifEntity gif);
        void deleteFromList(GifEntity gif);
    }
}
