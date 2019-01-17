package com.jack.viewgroup.floating.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import com.jack.viewgroup.floating.spring.SimpleReboundListener;
import com.jack.viewgroup.floating.spring.SpringHelper;
import com.jack.viewgroup.floating.transition.FloatingTransition;
import com.jack.viewgroup.floating.transition.YumFloating;

public class TranslateFloatingTransition implements FloatingTransition {

    private float mTranslateY;
    private long mDuration;

    public TranslateFloatingTransition() {
        mTranslateY = -200f;
        mDuration = 1500;
    }


    public TranslateFloatingTransition(float translateY, long duration) {
        this.mTranslateY = translateY;
        this.mDuration = duration;
    }

    @Override
    public void applyFloating(final YumFloating yumFloating) {
        
        ValueAnimator translateAnimator = ObjectAnimator.ofFloat(0, mTranslateY);
        translateAnimator.setDuration(mDuration);
        translateAnimator.setStartDelay(50);
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                yumFloating.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                yumFloating.setTranslationY(0);
                yumFloating.setAlpha(0f);

            }
        });
        
        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0.0f);
        alphaAnimator.setDuration(mDuration);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                yumFloating.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        
        SpringHelper.createWithBouncinessAndSpeed(0.0f, 1.0f,10, 15)
                .reboundListener(new SimpleReboundListener(){
                    @Override
                    public void onReboundUpdate(double currentValue) {
                        yumFloating.setScaleX((float) currentValue);
                        yumFloating.setScaleY((float) currentValue);
                    }
                }).start(yumFloating);
        
        alphaAnimator.start();
        translateAnimator.start();
    }

}
