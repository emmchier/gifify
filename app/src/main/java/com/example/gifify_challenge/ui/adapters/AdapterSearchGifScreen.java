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
import java.util.List;

public class AdapterSearchGifScreen extends RecyclerView.Adapter {

    private List<GifEntity> searchList;
    private SearchListener searchListener;

    public AdapterSearchGifScreen(SearchListener searchListener) {
        this.searchList = new ArrayList<>();
        this.searchListener = searchListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        CellGifBinding searchCell = CellGifBinding.bind(inflater.inflate(R.layout.cell_gif, parent, false));
        SearchViewholder searchViewholder = new SearchViewholder(searchCell);

        return searchViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GifEntity gif = searchList.get(position);
        SearchViewholder searchViewholder = (SearchViewholder) holder;
        searchViewholder.setCellData(gif);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void loadSearchGifs(List<GifEntity> searchGifs) {
        if (searchGifs != null) {
            this.searchList.clear();
            this.searchList.addAll(searchGifs);
            notifyDataSetChanged();
        }
    }

    public void clearList() {
        this.searchList.clear();
        notifyDataSetChanged();
    }

    public class SearchViewholder extends RecyclerView.ViewHolder {

        private CellGifBinding binding;

        public SearchViewholder(CellGifBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    searchListener.addToFavourite(searchList.get(getAdapterPosition()));
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchListener.shareGif(searchList.get(getAdapterPosition()));
                }
            });
        }

        @SuppressLint("CheckResult")
        public void setCellData(GifEntity gif) {
            Glide.with(itemView)
                    .load(gif.getImages().getDownsized().getUrl())
                    .placeholder(R.drawable.ic_baseline_videocam_24)
                    .error(R.drawable.ic_baseline_videocam_24)
                    .into(binding.imageViewGif);
        }
    }

    public interface SearchListener {
        void addToFavourite(GifEntity gif);
        void shareGif(GifEntity gif);
    }
}
