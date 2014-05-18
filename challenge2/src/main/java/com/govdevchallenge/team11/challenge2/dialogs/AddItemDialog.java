package com.govdevchallenge.team11.challenge2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.AddItemEvent;
import com.govdevchallenge.team11.challenge2.models.Item;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AddItemDialog extends DialogFragment {

	private EditText mUPCET;
	private EditText mItemNameET;
	private EditText mItemDescriptionET;
	private EditText mQuantityET;
	private Spinner mCategorySpinner;
	private ImageButton mCameraButton;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from( getActivity() );
		View view = inflater.inflate( R.layout.dialog_add_item, null );
		initViews( view );
		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setView( view );
		builder.setPositiveButton( "Add Item", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				addItem();
				getDialog().dismiss();
			}
		});
		builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				getDialog().dismiss();
			}
		});
		return builder.create();

	}

	public void addItem() {
		Log.e( "AddItemDialog", "addItem" );
		Item item = new Item();
		if( mUPCET != null && !TextUtils.isEmpty( mUPCET.getText().toString() ) ) {
			item.setUpc( mUPCET.getText().toString() );
		}

		if( mItemNameET != null && !TextUtils.isEmpty( mItemNameET.getText().toString() ) ) {
			item.setName(mItemNameET.getText().toString());
		}

		if( mItemDescriptionET != null && !TextUtils.isEmpty( mItemDescriptionET.getText().toString() ) ) {
			item.setDescription(mItemDescriptionET.getText().toString());
		}

		if( mQuantityET != null && !TextUtils.isEmpty( mQuantityET.getText().toString() ) ) {
			item.setQuantity(mQuantityET.getText().toString());
		}

		if( mCategorySpinner != null ) {
			item.setCategory( mCategorySpinner.getSelectedItem().toString() );
		}

		NavigationBus.getInstance().post( new AddItemEvent( item ) );
	}

	private void initViews( View view ) {
		mUPCET = (EditText) view.findViewById( R.id.upc_text );
		mItemNameET = (EditText) view.findViewById( R.id.item_name );
		mItemDescriptionET = (EditText) view.findViewById( R.id.item_desc );
		mQuantityET = (EditText) view.findViewById( R.id.quantity );
		mCategorySpinner = (Spinner) view.findViewById( R.id.category_spinner );
		mCameraButton = (ImageButton) view.findViewById( R.id.upc_camera_button );

		mCameraButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
					startActivityForResult(intent, 0);
				} catch( ActivityNotFoundException e ) {

				}
			}
		});

		List<String> items = new ArrayList<String>();
		items.add( "Blankets" );
		items.add( "Canned Food" );
		items.add( "Clothes" );
		items.add( "Shampoo" );
		items.add( "Soap" );
		items.add( "Water" );

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

		mCategorySpinner.setAdapter(adapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//change to process image for upc code
		if( resultCode == getActivity().RESULT_OK ) {
			Random random = new Random();
			String upc = Integer.toString( Math.abs( 100000 + random.nextInt( 899999 ) ) );
			mUPCET.setText(upc);
		}
	}
}
