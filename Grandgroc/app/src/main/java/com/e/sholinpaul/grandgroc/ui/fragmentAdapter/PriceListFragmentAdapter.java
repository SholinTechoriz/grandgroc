package com.e.sholinpaul.grandgroc.ui.fragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.databinding.SinglepriceDetailslayoutBinding;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PriceListFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductModel> ProductList;
    private Context MContext;


    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_FOOTER = 0;

    public PriceListFragmentAdapter(List<ProductModel> productList, Context MContext) {
        ProductList = productList;
        this.MContext = MContext;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleprice_detailslayout, parent, false);
            SinglepriceDetailslayoutBinding singlepriceDetailslayoutBinding = SinglepriceDetailslayoutBinding.bind(view);
            return new MyViewHolder(singlepriceDetailslayoutBinding);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_section, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            final ProductModel productModel = ProductList.get(position);
            ((MyViewHolder) holder).binding.tvAmount.setText(productModel.getAmount());


        }
    }

    @Override
    public int getItemViewType(int position) {
        return ProductList.get(position) == null ? VIEW_TYPE_FOOTER : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        SinglepriceDetailslayoutBinding binding;

        MyViewHolder(@NonNull SinglepriceDetailslayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

        }
    }
}
