package com.mygdx.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.xml.datatype.Duration;

import static android.content.ContentValues.TAG;

@SuppressLint("Registered")
public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	private static final int RC_SIGN_IN = 9001;
	String ad_id;
	protected AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	private FirebaseAuth mAuth;
	private FirebaseUser user;

	private RelativeLayout gameLayout;

	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
				switch(msg.what) {
				case SHOW_ADS:
					{
						adView.setVisibility(View.VISIBLE);
						break;
					}
					case HIDE_ADS:
						{
							adView.setVisibility(View.GONE);
							break;
						}
			}
		}
	};
	private GoogleSignInClient mGoogleSignInClient;
	GoogleSignInAccount account;
	private Intent signInIntent;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAuth = FirebaseAuth.getInstance();

		ad_id = getResources().getString(R.string.banner_id);

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();

		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
		signInIntent = mGoogleSignInClient.getSignInIntent();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		gameLayout = createGameLayout(config);

		if(mAuth.getCurrentUser() != null){
			account = GoogleSignIn.getLastSignedInAccount(this);
			user = mAuth.getCurrentUser();
			setContentView(gameLayout);

		} else
			setContentView(R.layout.login_activity);
	}

	private RelativeLayout createGameLayout(AndroidApplicationConfiguration config) {
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new MyGdxGame(this), config);

		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		 // Put in your secret key here

		adView.setAdUnitId(ad_id);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("E3D73C2FC3A2655912B6F0A352A37908")
				.addTestDevice("C6A692B5E4EE845C753A1B5F718AD1CE")
				.build();

		adView.loadAd(adRequest);

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		layout.addView(adView, adParams);

		return layout;
	}

	public void logIn(View view) {
		if(mAuth.getCurrentUser() != null){
			user = mAuth.getCurrentUser();
		} else {
			EditText edit = findViewById(R.id.emailText);
			String email = edit.getText().toString();
			EditText edit2 = findViewById(R.id.password);
			String password = edit2.getText().toString();
			signIn(email,password);
		}
		// Do something in response to button click

	}

	private void signIn(final String email, final String password) {
		if(email != null && !email.equals(""))
			if(password != null && !password.equals("")) {

				mAuth.signInWithEmailAndPassword(email, password)
						.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if (task.isSuccessful()) {
									// Sign in success, update UI with the signed-in user's information
									Log.d(TAG, "signInWithEmail:success");
									user = mAuth.getCurrentUser();
									setContentView(gameLayout);
									Toast.makeText(getContext(), user.getEmail() , Toast.LENGTH_LONG).show();
								} else {
									// If sign in fails, display a message to the user.
									Log.w(TAG, "signInWithEmail:failure", task.getException());
									signUP(email, password);
								}

								// ...
							}
						});
			}

	}

	private void signUP(String email, String password) {
		if(email != null && !email.toString().equals(""))
			if(password != null && !password.toString().equals(""))
				mAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if (task.isSuccessful()) {
									// Sign in success, update UI with the signed-in user's information
									Log.d(TAG, "createUserWithEmail:success");
									user = mAuth.getCurrentUser();

									setContentView(gameLayout);
									Toast.makeText(getContext(), user.getEmail() , Toast.LENGTH_LONG).show();
								} else {
									// If sign in fails, display a message to the user.
									Log.w(TAG, "createUserWithEmail:failure", task.getException());

								}

								// ...
							}
						});
	}

	@Override
	public void showAds(boolean show) {
		//TODO Make add not hide but to create/destroy
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	public void signOut(){
		mAuth.signOut();
		mGoogleSignInClient.signOut();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.login_activity);
			}
		});
	}

	public void sighUpAnonymously(View view){
		mAuth.signInAnonymously();
		user = mAuth.getCurrentUser();
		setContentView(gameLayout);
		Toast.makeText(getContext(),"Anon", Toast.LENGTH_LONG).show();
	}

	public void googleSignIn(View view){
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// Google Sign In was successful, authenticate with Firebase
				account = task.getResult(ApiException.class);
				assert account != null;
				handleSignInResult(task);
				firebaseAuthWithGoogle(account);
			} catch (ApiException e) {
				// Google Sign In failed, update UI appropriately
				Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getContext(),"Google sign in failed", Toast.LENGTH_LONG).show();
				// ...
			}
		}
	}

	private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
		try {
			account = completedTask.getResult(ApiException.class);
			assert account != null;
			Toast.makeText(this, account.getEmail(), Toast.LENGTH_LONG).show();
			// Signed in successfully, show authenticated UI.
		} catch (ApiException e) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getContext(),"signInResult:failed code=" + e.getStatusCode() , Toast.LENGTH_LONG).show();
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithCredential:success");

							user = mAuth.getCurrentUser();
							setContentView(gameLayout);
							Toast.makeText(getContext(), user.getEmail() , Toast.LENGTH_LONG).show();

						} else {
							// If sign in fails, display a message to the user.
							Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(),"signInWithCredential:failure" , Toast.LENGTH_LONG).show();
						}

						// ...
					}
				});
	}

	@Override
	public boolean isAdShown() {
		return adView.isShown();
	}
}