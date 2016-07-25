package com.tyas.test.assignyas.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyas.test.assignyas.R;
import com.tyas.test.assignyas.repository.entity.Data;

import java.util.List;

/**
 * Created by tyasrus on 13/07/16.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private OnItemDataClickListener onItemDataClickListener;
    private List<Data> Datas;
    private Context context;

    public DataAdapter(List<Data> Datas) {
        this.Datas = Datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(Datas.get(position).getTitle());
        holder.priceView.setText(String.valueOf(Datas.get(position).getBody()));
    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    public void setOnItemDataClickListener(OnItemDataClickListener onItemDataClickListener) {
        this.onItemDataClickListener = onItemDataClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleView;
        private TextView priceView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.title_item_home);
            priceView = (TextView) itemView.findViewById(R.id.price_item_home);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemDataClickListener != null) {
                onItemDataClickListener.onItemDataClick(view, getAdapterPosition());
            }
        }
    }

    public interface OnItemDataClickListener {
        void onItemDataClick(View view, int position);
    }
}
