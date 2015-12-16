package com.example.sebo.shoplocationmobile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.products.Product;

import java.util.List;

/**
 * Created by Sebo on 2015-11-11.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> products;

    public ProductListAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.title.setText(product.getName());
        holder.description.setText(product.getDesc());
        holder.price.setText(product.getPrice() + "$");

        if (product.getIcon() == null) {
            holder.icon.setImageResource(R.drawable.ic_corp_icon);
        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView description;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.product_icon);
            title = (TextView) itemView.findViewById(R.id.product_title);
            description = (TextView) itemView.findViewById(R.id.product_description);
            price = (TextView) itemView.findViewById(R.id.product_price);
        }
    }
}
