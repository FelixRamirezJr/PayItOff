package com.example.felix.payitoff;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felix.payitoff.dummy.DummyContent;
import com.example.felix.payitoff.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PayingFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    ItemAdapter listAdapter;
    View rootView;
    ListView listView;
    ViewGroup myContainer;
    FloatingActionButton add_item_button;
    PopupWindow pw;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PayingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PayingFragment newInstance(int columnCount) {
        PayingFragment fragment = new PayingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Get/Set View's
        rootView = inflater.inflate(R.layout.fragment_paying, container, false);
        listView = rootView.findViewById(R.id.paying_list);
        myContainer = container;
        add_item_button = rootView.findViewById( R.id.add_item );


        // Setting List Adapter
        listAdapter = new ItemAdapter(getActivity(), test_items() );
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        // Set On Click Actions
        set_add_item_listener( );

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    List test_items()
    {
        List<Item> items = new ArrayList<>();
        for( int i = 0; i < 10; i++ ){
            items.add( new Item( "item_" + Integer.toString(i), (i + 1 * 100 ) ) );
        }
        return items;
    }

    void set_add_item_listener()
    {
        add_item_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "Hi", Toast.LENGTH_LONG).show();
                add_more_popup(v);
            }
        });
    }

    void add_more_popup(View v)
    {
        try
        {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.add_more_popup, myContainer, false);
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1000, 1000, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            //TextView mResultText = (TextView) layout.findViewById(R.id.server_status_text);
            Button cancelButton = (Button) layout.findViewById(R.id.cancel_add_item_popup);
            cancelButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Toast.makeText(getContext(), "trying to cancel!", Toast.LENGTH_SHORT).show();
                    pw.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
