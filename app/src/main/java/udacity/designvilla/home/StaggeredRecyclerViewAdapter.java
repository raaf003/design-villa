package udacity.designvilla.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.splashscreenjava.R;

import java.util.ArrayList;

/**
 * Created by Rafaqat Rasool on 28-04-2018.
 */

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "StaggeredRecyclerViewAd";
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private Context mContext;

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> imageUrls) {
        this.titles = titles;
        this.imageUrls = imageUrls;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(imageUrls.get(position))
                .apply(requestOptions)
                .into(holder.imageView);

        holder.titleTxtView.setText(titles.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on:  "+titles.get(position));
                Toast.makeText(mContext, titles.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.feed_item_image);
            titleTxtView = itemView.findViewById(R.id.feed_item_title);
        }
    }
}
