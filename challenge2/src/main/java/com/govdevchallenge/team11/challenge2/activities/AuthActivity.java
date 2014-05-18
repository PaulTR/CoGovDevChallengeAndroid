package com.govdevchallenge.team11.challenge2.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;
import com.govdevchallenge.team11.challenge2.R;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AuthActivity extends Activity {

	SignInButton mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_auth );

		mSignInButton = (SignInButton) findViewById( R.id.sign_in_button );

		mSignInButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent( getApplicationContext(), MainActivity.class );
				startActivity( intent );
				finish();
			}
		});
	}
}
