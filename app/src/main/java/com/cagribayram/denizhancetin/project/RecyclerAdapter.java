package com.cagribayram.denizhancetin.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<DataModel> mArrayList;
    // Turkish lira symbol
    StringBuilder sb = new StringBuilder().append('\u20BA');

    public RecyclerAdapter(Context mContext, ArrayList<DataModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    // Each object of the ViewHolder will be created here
    @NonNull
//    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = (View) mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(itemView);

        return mViewHolder;
    }

    // This method will be called to assign data to each row or cell
//    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        holder.icon.setImageResource(mArrayList.get(position).getImageId());
//        holder.title.setText(mArrayList.get(position).getTitle());
//        holder.description.setText(mArrayList.get(position).getDescription());
//        holder.price.setText(sb.toString() + String.valueOf(mArrayList.get(position).getPrice()) );
    }

    // How many items exist in the list
//    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    // method to delete an item from the ArrayList
    public void removeItem(int position) {
        mArrayList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mArrayList.size());
    }

    // method to perform undo operartion by adding item back to the  ArrayList
    public void restoreItem(DataModel mDatamodel, int position) {
        mArrayList.add(position, mDatamodel);
        // notify item added by position
//        notifyItemInserted(position);
    }

    // This class is responsible for each item on the list
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);

//            icon = (ImageView) itemView.findViewById(R.id.imv);
            title = (TextView) itemView.findViewById(R.id.title);
//            description = (TextView) itemView.findViewById(R.id.description);
//            price = (TextView) itemView.findViewById(R.id.price);
        }
    }

}
