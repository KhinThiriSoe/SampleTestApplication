package com.khinthirisoe.sampletestapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khinthirisoe.sampletestapplication.R;
import com.khinthirisoe.sampletestapplication.data.model.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by khinthirisoe on 3/30/17.
 */

public class BookListsAdapter extends RecyclerView.Adapter<BookListsAdapter.ViewHolder> {

    Context mContext;
    List<Datum> lists;

    public BookListsAdapter(Context context, List<Datum> data) {
        super();
        this.lists = data;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Datum list = lists.get(position);
        Picasso.with(mContext).load(list.getIcon()).into(holder.imageIcon);

        holder.textName.setText(list.getName());
        holder.textEndDate.setText(list.getEndDate());

    }

    @Override
    public int getItemViewType(int position) {
        boolean isLast = false;
        if(position == lists.size() - 1){
            isLast = true;
        }

        return (isLast) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView textName;
        private TextView textEndDate;
        private ImageView imageOverflow;

        private ViewHolder(View itemView) {
            super(itemView);

            imageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textEndDate = (TextView) itemView.findViewById(R.id.text_end_date);
            imageOverflow = (ImageView) itemView.findViewById(R.id.overflow);

        }
    }
}