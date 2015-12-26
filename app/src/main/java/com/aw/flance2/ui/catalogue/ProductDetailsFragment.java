package com.aw.flance2.ui.catalogue;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aw.flance2.R;
import com.aw.flance2.catalogue.CatalogueFactory;
import com.aw.flance2.catalogue.Product;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link ProductsFragment.OnListFragmentInteractionListener}
 * interface.
 */
public class ProductDetailsFragment extends Fragment{

    private Product prod;
    private ProductsFragment.OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            prod = (Product) getArguments().getSerializable("prod");
        }
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance
        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        ((TextView)view.findViewById(R.id.name)).setText(prod.getName());
        ((TextView)view.findViewById(R.id.price)).setText(prod.getPrice()+" DT");
        ((TextView)view.findViewById(R.id.desc)).setText(prod.getDescription());
        ((ImageView)view.findViewById(R.id.img)).setImageResource(R.drawable.prod);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductsFragment.OnListFragmentInteractionListener) {
            mListener = (ProductsFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        getActivity().onBackPressed();
        return true;

    }


}
