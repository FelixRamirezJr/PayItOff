package com.example.felix.payitoff;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    List <Item> itemList = new ArrayList<>();


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
        // Load Items

        // Get/Set View's
        rootView = inflater.inflate(R.layout.fragment_paying, container, false);
        listView = rootView.findViewById(R.id.paying_list);
        myContainer = container;
        add_item_button = rootView.findViewById( R.id.add_item );

        // Setting List Adapter
        listAdapter = new ItemAdapter( getActivity(), itemList );
        listView.setAdapter(listAdapter);
        Log.d("Create", "Notify change!");
        listAdapter.notifyDataSetChanged();

        // Set On Click Actions
        set_add_item_listener();

        // Load Items
        load_saved_items();

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


    // This will open a popup to add an item.
    void set_add_item_listener()
    {
        add_item_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final AddDialog popup = new AddDialog();
                popup.show( getActivity().getFragmentManager(), "hi" );
            }
        });
    }

    void load_saved_items(){
        mRootRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if(!dataSnapshot.getValue().toString().contains("null")) {
                    Log.d("Load saved items","Does not contain null");
                    Item toAdd  = dataSnapshot.getValue(Item.class);
                    if(toAdd.name != null) {
                        Log.d("Load Saved items","Item List add");
                        itemList.add(toAdd);
                    }
                    listAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
