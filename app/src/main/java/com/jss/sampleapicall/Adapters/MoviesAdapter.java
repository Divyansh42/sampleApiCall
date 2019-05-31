/*
package com.example.shivamvk.machinemindful;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Product product = productList.get(i);
        viewHolder.tvProductName.setText(product.getProductName());
        viewHolder.tvProductPrice.setText(product.getInStatePrice());



        viewHolder.rlProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", product.getProductId());
                intent.putExtra("name", product.getProductName());
                intent.putExtra("slug", product.getSlug());
                intent.putExtra("image", product.getProductImage());
                intent.putExtra("instate_price", product.getInStatePrice());
                intent.putExtra("outstate_price", product.getOutStatePrice());
                intent.putExtra("price_type", product.getPriceType());
                intent.putExtra("status", product.getProductStatus());
               intent.putExtra("timestamp", product.getTimeStamp());
               intent.putExtra("category", product.getProductCategory());
               intent.putExtra("created_by", product.getCreatedBy());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProductName;
        TextView tvProductPrice;
        RelativeLayout rlProductLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //llCustomerLayout = itemView.findViewById(R.id.ll_cutomer_item);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            rlProductLayout = itemView.findViewById(R.id.rl_product_layout);
        }
    }
}
*/

package com.jss.sampleapicall.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jss.sampleapicall.Models.Movie;
import com.jss.sampleapicall.R;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{
    private Context context;
    private List<Movie> movieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, year;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_movie_name);
            year = view.findViewById(R.id.tv_movie_year);
            thumbnail = view.findViewById(R.id.movie_poster);

        }
    }


    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movie =  movieList.get(position);
        holder.name.setText(movie.getMovie_name());
        holder.year.setText(movie.getMovie_year());

        Glide.with(context)
                .load(movie.getMovie_img_url())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

