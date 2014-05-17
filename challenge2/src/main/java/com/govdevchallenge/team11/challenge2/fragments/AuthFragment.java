package com.govdevchallenge.team11.challenge2.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.govdevchallenge.team11.challenge2.R;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AuthFragment extends Fragment {

	public static AuthFragment getInstance() {
		AuthFragment fragment = new AuthFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_auth, container, false );
		return view;
	}
}
