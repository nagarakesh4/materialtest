package materialtest.sanjose.venkata.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import it.neokree.materialtabs.MaterialTabHost;

/**
 * Created by buddhira on 5/3/2015.
 */
public class AnimationUtils {
    public static void animateItems(RecyclerView.ViewHolder holder, boolean scrollDown) {

        //object animator
        //ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", -100, 0);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", scrollDown == true ? 300 : -300, 0);
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.start();
    }

    public static void animateComplex(RecyclerView.ViewHolder holder, boolean scrollDown) {

        //To group multiple animators
        AnimatorSet animatorSet = new AnimatorSet();

        //object animator
        //ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", -100, 0);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", scrollDown == true ? 300 : -300, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, -50, -30, 30, -20, 20, -5, 5, 0);

        animatorTranslateY.setDuration(1000);
        animatorTranslateX.setDuration(1000);
        animatorSet.playTogether(animatorTranslateX, animatorTranslateY);
        animatorSet.start();
        //animatorTranslateY.start();
    }

    public static void animateInterpolator(RecyclerView.ViewHolder holder, boolean scrollDown) {

        //To group multiple animators
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F);
        //object animator
        //ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", -100, 0);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", scrollDown == true ? 500 : -500, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, -50, -30, 30, -20, 20, -5, 5, 0);

        animatorTranslateY.setDuration(1000);
        animatorTranslateX.setDuration(1000);
        //animatorSet.playTogether(animatorTranslateX, animatorTranslateY, animatorScaleX, animatorScaleY);
        //animatorSet.playTogether(animatorTranslateX, animatorTranslateY, animatorScaleX, animatorScaleY);
        //animatorSet.playTogether(animatorTranslateY, animatorScaleX);
       /* //will aniticipate
        animatorSet.setInterpolator(new AnticipateInterpolator());
        //will overshoots
        animatorSet.setInterpolator(new OvershootInterpolator());*/
        animatorTranslateY.setDuration(1000);
        //animatorSet.start();
        animatorTranslateY.start();
    }

    public static void animateLibraryDown(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.BounceInUp).duration(1000).playOn(holder.itemView);
    }

    public static void animateLibraryUp(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.BounceInDown).duration(1000).playOn(holder.itemView);
    }


    public static void animateToolbar(View containerToolBar) {

        containerToolBar.setRotationX(-90);
        containerToolBar.setAlpha(0.2F);
        containerToolBar.setPivotX(0.0F);
        containerToolBar.setPivotY(0.0F);

        Animator alpha = ObjectAnimator.ofFloat(containerToolBar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolBar, "rotationX", -90, 60, -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(4000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }
}
