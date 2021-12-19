package com.DaffaJmartRK.jmart_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.DaffaJmartRK.jmart_android.model.Product;

/**
 * Adapter class untuk menampilkan list produk pada MainActivity
 * @author ASUS
 * @version Final
 */
public class RecyclerViewMain extends RecyclerView.Adapter<RecyclerViewMain.ViewHolder> {

    private List<Product> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Method melakukan passing data
    RecyclerViewMain(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // Method untuk menambah baris pada layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // Method untuk mengambil data untuk setiap cardnya
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product productName = mData.get(position);
        holder.myTextView.setText(productName.toString());
    }
    // Method untuk menghitung total data pada json
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Method untuk memperbaharui list ketika ada perubahan
    public void refresh(List<Product> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    int getClickedItemId(int id){ return mData.get(id).id;}
    // scrolled page
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_productName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
