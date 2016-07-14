package com.demo.compat.value.animator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Interpolator;


public final class MainActivity extends AppCompatActivity {

	private boolean mShouldBackToClose = true;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CardView cardView = (CardView) findViewById(R.id.cv);
		cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mShouldBackToClose = false;
				ValueAnimatorCompat animatorCompat = AnimatorCompatHelper.emptyValueAnimator();
				animatorCompat.setDuration(1000);
				final Interpolator interpolator1 = new BakedBezierInterpolator();
				animatorCompat.addUpdateListener(new AnimatorUpdateListenerCompat() {
					private float old = getResources().getDimension(R.dimen.cardElevationNormal);
					private float end = getResources().getDimension(R.dimen.cardElevationSelected);


					@Override
					public void onAnimationUpdate(ValueAnimatorCompat animation) {
						float fraction = interpolator1.getInterpolation(animation.getAnimatedFraction());

						//Set frame on cardview.
						float cur = old + (fraction * (end - old));
						CardView cardView = (CardView) findViewById(R.id.cv);
						cardView.setCardElevation(cur);
						cardView.setMaxCardElevation(cur);
					}
				});

				animatorCompat.start();
			}
		});


	}

	@Override
	public void onBackPressed() {
		if (mShouldBackToClose) {
			super.onBackPressed();
		} else {
			mShouldBackToClose = true;

			ValueAnimatorCompat animatorCompat = AnimatorCompatHelper.emptyValueAnimator();
			animatorCompat.setDuration(1000);
			final Interpolator interpolator1 = new BakedBezierInterpolator();
			animatorCompat.addUpdateListener(new AnimatorUpdateListenerCompat() {
				private float old = getResources().getDimension(R.dimen.cardElevationSelected);
				private float end = getResources().getDimension(R.dimen.cardElevationNormal);


				@Override
				public void onAnimationUpdate(ValueAnimatorCompat animation) {
					float fraction = interpolator1.getInterpolation(animation.getAnimatedFraction());

					//Set frame on cardview.
					float cur = old + (fraction * (end - old));
					CardView cardView = (CardView) findViewById(R.id.cv);
					cardView.setCardElevation(cur);
					cardView.setMaxCardElevation(cur);
				}
			});

			animatorCompat.start();
		}
	}
}
