package com.aw.flance2.ui.catalogue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aw.flance2.R;
import com.aw.flance2.catalogue.Product;
import com.aw.flance2.ui.catalogue.ProductsFragment.OnListFragmentInteractionListener;

import java.util.List;


public class ProductDetailsViewAdapter extends RecyclerView.Adapter<ProductDetailsViewAdapter.ViewHolder> {


    private final Product mValues;
    private Context context;

    public ProductDetailsViewAdapter(Product item) {
        mValues = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       /* holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mImg.setImageResource(R.drawable.ic_menu_gallery);
*/

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImg;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImg = (ImageView) view.findViewById(R.id.photo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
