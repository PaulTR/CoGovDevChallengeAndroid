package com.govdevchallenge.team11.challenge2.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.models.Item;

/**
 * Created by PaulTR on 5/17/14.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

	public ItemAdapter( Context context ) {
		this( context, 0 );
	}

	public ItemAdapter(Context context, int resource) {
		super( context, resource );
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.e("ItemAdapter", "getView");
		ViewHolder holder;
		if( convertView == null ) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate( R.layout.view_donated_item, parent, false );
			holder.itemName = (TextView) convertView.findViewById( R.id.item_name );
			holder.itemDescription = (TextView) convertView.findViewById( R.id.item_desc );
			holder.itemCategory = (TextView) convertView.findViewById( R.id.category_text );
			holder.upcNumber = (TextView) convertView.findViewById( R.id.upc_number );
			holder.itemQuantity = (TextView) convertView.findViewById( R.id.item_qty );
			convertView.setTag( holder );
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if( !TextUtils.isEmpty( getItem( position ).getName() ) ) {
			holder.itemName.setText(getItem(position).getName());
		}
		if( !TextUtils.isEmpty( getItem( position ).getDescription() ) ) {
			holder.itemDescription.setText( getItem( position ).getDescription() );
		}
		if( !TextUtils.isEmpty( getItem( position ).getUpc() ) ) {
			holder.upcNumber.setText( getItem( position ).getUpc() );
		}
		if( !TextUtils.isEmpty( getItem( position ).getCategory() ) ) {
			holder.itemCategory.setText( getItem( position ).getCategory() );
		}
		if( !TextUtils.isEmpty( getItem( position ).getQuantity() ) ) {
			holder.itemQuantity.setText( getItem( position ).getQuantity() );
		} else {
			holder.itemQuantity.setText( "1" );
		}

		return convertView;
	}

	public class ViewHolder {
		TextView itemName;
		TextView itemDescription;
		TextView itemCategory;
		TextView upcNumber;
		TextView itemQuantity;
	}
}
