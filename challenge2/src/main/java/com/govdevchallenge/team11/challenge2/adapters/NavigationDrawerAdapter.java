package com.govdevchallenge.team11.challenge2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by PaulTR on 5/17/14.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

	public NavigationDrawerAdapter( Context context ) {
		this( context, 0 );
	}

	public NavigationDrawerAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if( convertView == null ) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from( getContext() ).inflate( android.R.layout.simple_list_item_1, parent, false );
			holder.navigation_title = (TextView) convertView.findViewById( android.R.id.text1 );
			convertView.setTag( holder );
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.navigation_title.setText( getItem( position ) );

		return convertView;
	}

	public class ViewHolder {
		public TextView navigation_title;
	}
}
