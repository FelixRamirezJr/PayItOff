package com.example.felix.payitoff;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by felix on 10/9/17.
 */

public class AddDialog extends DialogFragment {

    View rootView;
    EditText name;
    EditText amount;
    Button create;
    Button cancel;
    DialogFragment dialog;

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        rootView =  inflater.inflate(R.layout.add_more_popup, container, false);
        dialog = this;

        // Get Listeners

        name = rootView.findViewById( R.id.item_to_add_name );
        amount = rootView.findViewById( R.id.item_to_add_amount );
        create = rootView.findViewById( R.id.submit_add );
        cancel = rootView.findViewById( R.id.cancel_add_item_popup );



        // Cancel Listener
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Create listener
        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( name.getText().length() == 0 || amount.getText().length() == 0 )
                {
                    Toast.makeText(getActivity(), "Name or Amount can't be blank!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    add_to_firebase( new Item( name.getText().toString(),
                                               Double.parseDouble( amount.getText().toString() ) ) );
                    dialog.dismiss();
                    Toast.makeText(getActivity(),"Success!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        return rootView;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    void add_to_firebase( Item item_to_add )
    {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference db = database.push();
        db.setValue( item_to_add );
    }

}
