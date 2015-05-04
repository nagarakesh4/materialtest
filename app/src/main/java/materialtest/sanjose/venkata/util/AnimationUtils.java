package materialtest.sanjose.venkata.util;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by buddhira on 5/3/2015.
 */
public class AnimationUtils {
    public static void animateItems(RecyclerView.ViewHolder holder) {
        //object animator
        //ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", -100, 0);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", -100, 0);
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.start();
    }

    public static void animateToolbar(View containerToolBar){

    }
}
