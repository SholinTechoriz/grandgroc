package com.e.sholinpaul.grandgroc.ui.fragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.databinding.SingleneworderlayoutBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.ui.activity.OrderDetailsActivity;
import com.e.sholinpaul.grandgroc.ui.fragment.NewOrderFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewOrderFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OrderModel> NewOrderList;
    private Context mcontext;
    NewOrderFragment fragment;

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_FOOTER = 0;

    public NewOrderFragmentAdapter(ArrayList<OrderModel> newOrderList, Context mcontext, NewOrderFragment fragment) {
        NewOrderList = newOrderList;
        this.mcontext = mcontext;
        this.fragment = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleneworderlayout, parent, false);
            SingleneworderlayoutBinding singleallOrderslistlayoutBinding = SingleneworderlayoutBinding.bind(view);
            return new MyViewHolder(singleallOrderslistlayoutBinding);

        } else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_section, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MyViewHolder) {
            final OrderModel orderModel = NewOrderList.get(position);

            ((MyViewHolder) holder).binding.tvProductId.setText("#" + orderModel.getOrder_id());
            ((MyViewHolder) holder).binding.tvLocation.setText(orderModel.getLocation());
            ((MyViewHolder) holder).binding.tvDeliveryType.setText(orderModel.getType());


            if (orderModel.getDelivarytype().equals("Normal")) {
                ((MyViewHolder) holder).binding.tvBcgrnd.setBackgroundResource(R.drawable.ic_regular_bg);
                ((MyViewHolder) holder).binding.tvOrderType.setText("Normal");
            } else {
                ((MyViewHolder) holder).binding.tvBcgrnd.setBackgroundResource(R.drawable.ic_rapid_bg);
                ((MyViewHolder) holder).binding.tvOrderType.setText("Express");
            }

            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoOrderDetails(holder);

                }
            });


        }

    }


    private void gotoOrderDetails(RecyclerView.ViewHolder holder) {
        final OrderModel orderModel = NewOrderList.get(holder.getAdapterPosition());
        Intent intent = new Intent(mcontext, OrderDetailsActivity.class);
        intent.putExtra("order", orderModel);
        intent.putExtra("OrderLIST", "OrderListActivity");
        ((Activity) mcontext).startActivityForResult(intent, 301);

    }


    @Override
    public int getItemViewType(int position) {
        return NewOrderList.get(position) == null ? VIEW_TYPE_FOOTER : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return NewOrderList.size();
    }


    public void addAll(ArrayList<OrderModel> moveResults) {
        NewOrderList.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void removeModel(ArrayList<OrderModel> moveResults) {
        if (NewOrderList != null) NewOrderList.remove(moveResults);
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        SingleneworderlayoutBinding binding;

        MyViewHolder(@NonNull SingleneworderlayoutBinding itemView) {
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
