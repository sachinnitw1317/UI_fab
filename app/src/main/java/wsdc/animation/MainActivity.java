package wsdc.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;


public class MainActivity extends Activity {

    TextView textView,textView_num;
//    ImageView imageView;
    PlayGifView pGif;
    ImageButton imageButton, closeButton;
    RelativeLayout revealView;
    LinearLayout layoutButtons;
    Animation alphaAppear, alphaDisappear,slide_up,slide_down;
    WebView wb;
    int x, y, width, height, hypotenuse;
    float pixelDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.loading);

        pixelDensity = getResources().getDisplayMetrics().density;


        imageButton = (ImageButton) findViewById(R.id.launchTwitterAnimation);
        closeButton = (ImageButton) findViewById(R.id.closeButton);
        revealView = (RelativeLayout) findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        textView= (TextView) findViewById(R.id.textView);
        textView_num= (TextView) findViewById(R.id.textView_num);
        alphaAppear = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        alphaDisappear = AnimationUtils.loadAnimation(this, R.anim.alpha_disappear);
        slide_up=AnimationUtils.loadAnimation(this,R.anim.slide_up);
        slide_down=AnimationUtils.loadAnimation(this,R.anim.slide_down);
    }
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
    private void startCountAnimation(final TextView text) {
        ValueAnimator animator = new ValueAnimator();
//        Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();
        if (Float.valueOf(textView_num.getText().toString())==180f){
            animator.setFloatValues(180f, 92.3f);
        }else{
            animator.setFloatValues(92.3f, 180f);
        }

        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText("" + (float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void launchTwitter(View view) {


        Log.d("Err", "launchTwitter: ");
        /*
         MARGIN_RIGHT = 16dp
         FAB_BUTTON_RADIUS = 28dp
         */
        width = pGif.getWidth();
        height = pGif.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                revealView.getLayoutParams();
        parameters.height = pGif.getHeight();
        revealView.setLayoutParams(parameters);

        imageButton.animate()
            .translationX(-x)
            .translationY(-y)
            .setDuration(200)
            .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                textView.startAnimation(slide_down);
                startCountAnimation(textView_num);
                textView.setText("Sachin Patil");
                textView.startAnimation(slide_up);

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d("Err", "launchTwitter: 2");
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, 28 * pixelDensity, hypotenuse);
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.d("Err", "launchTwitter: 3");
                        layoutButtons.setVisibility(View.VISIBLE);
                        closeButton.setVisibility(View.VISIBLE);
                        layoutButtons.startAnimation(alphaAppear);
                        closeButton.startAnimation(alphaAppear);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                imageButton.setVisibility(View.GONE);
                revealView.setVisibility(View.VISIBLE);
                Log.d("Err", "launchTwitter: 4");
                anim.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }



    public void closeTwitter(View view) {

        Log.d("Err", "closeTwitter: ");
        alphaDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.startAnimation(slide_down);
                textView.setText("Sachin Patil_closed");
                textView.startAnimation(slide_up);
                startCountAnimation(textView_num);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, hypotenuse, 28 * pixelDensity);
                anim.setDuration(350);

                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        Log.d("Err", "closeTwitter: 3");
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.d("Err", "closeTwitter: 4");

                        revealView.setVisibility(View.GONE);
                        imageButton.setVisibility(View.VISIBLE);
                        imageButton.setTranslationX(0f);
                        imageButton.setTranslationY(0f);

                        /*imageButton.clearAnimation();
                        imageButton.animate()
                          .translationX(0f)
                          .translationY(0f)
                          .setDuration(200);*/
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        Log.d("Err", "closeTwitter: 5");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        Log.d("Err", "closeTwitter: 6");

                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("Err", "closeTwitter: 7");


            }
        });

        layoutButtons.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);
        layoutButtons.startAnimation(alphaDisappear);
        closeButton.startAnimation(alphaDisappear);
        Log.d("Err", "closeTwitter: 8");

    }
}