package com.sergeenko.alexey.titangym;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mygdx.game.IActivityRequestHandler;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.CompetitionOpponent;
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding;
import com.sergeenko.alexey.titangym.databinding.PlayerListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static android.content.ContentValues.TAG;

@SuppressLint("Registered")
public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	private RelativeLayout gameLayout;

	MainActivityBinding binding;
	private Map<String, Object> data;

	FastAdapter adapter;
	ItemAdapter itemAdapter;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		binding = MainActivityBinding.inflate(LayoutInflater.from(this));
		setContentView(binding.getRoot());
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		gameLayout = createGameLayout(config);
		launchGame();
	}

	private void launchGame() {
		binding.container.addView(gameLayout);
	}

	private RelativeLayout createGameLayout(AndroidApplicationConfiguration config) {
		RelativeLayout layout = new RelativeLayout(this);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView = initializeForView(new MyGdxGame(this), config);
		layout.addView(gameView);

		return layout;
	}


	@Override
	public void showAds(boolean show) { }

	@Override
	public Map<String, Object> loadData() {
		return data;
	}

	@Override
	public void showToast(final String s) {
		runOnUiThread(() -> Toast.makeText(AndroidLauncher.this.getApplicationContext(), s, Toast.LENGTH_SHORT).show());
	}

	@Override
	public void showPlayers(List<CompetitionOpponent> playerList) {
		PlayerListBinding listBinding = PlayerListBinding.inflate(LayoutInflater.from(this));


		binding.getRoot().post(()->{

			itemAdapter = new ItemAdapter();
			adapter = FastAdapter.with(itemAdapter);
			adapter.setHasStableIds(true);

			listBinding.rv.setLayoutManager(new LinearLayoutManager(this));
			listBinding.rv.setAdapter(this.adapter);



			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				ArrayList<PlayerItem> list = new ArrayList<>();
				for (CompetitionOpponent op: playerList){
					list.add(new PlayerItem(op));
				}

				itemAdapter.setNewList(list, false);
			}
			listBinding.getRoot().setOnClickListener((v -> {

			}));
			listBinding.cont.setOnClickListener((v -> {
				binding.viewContainer.removeAllViews();
				binding.viewContainer.setVisibility(View.GONE);
			}));
			binding.viewContainer.addView(listBinding.getRoot());
			binding.viewContainer.setVisibility(View.VISIBLE);
		});
	}



	@Override
	public boolean isAdShown() {
		return false;//adView.isShown();
	}

	@Override
	public void signOut() {

	}

	@Override
	public void saveData(Map value) {

	}

}