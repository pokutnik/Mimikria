package ua.dou.Mimikria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SplashScreen extends Activity {
    private ViewGroup rootView;
    private boolean animationProceeded;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));

        rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (animationProceeded) {
            finish();
        }
    }

    private class GraphicsView extends View {
        private static final String AnimatedTxt =
            "Mimikria";
 
        private Animation textAnim;
 
        public GraphicsView(Context context) {
            super(context);
        }
        private void createAnim(Canvas canvas) {
        	textAnim = new TranslateAnimation(
        			Animation.RELATIVE_TO_PARENT, -0.4f,
        			Animation.RELATIVE_TO_PARENT, 1.0f,
        			Animation.RELATIVE_TO_PARENT, 0.5f,
        			Animation.RELATIVE_TO_PARENT, 0.5f);
        	
            textAnim.setRepeatCount(0);
            textAnim.setDuration(8000L);
            startAnimation(textAnim);
            textAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    rootView.removeAllViews();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    SplashScreen.this.startActivity(intent);
                    animationProceeded = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
 
            // creates the animation the first time
            if (textAnim == null) {
                createAnim(canvas);
            }
 
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setTextSize(50);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.create(Typeface.SERIF,
                    Typeface.ITALIC));
            
            canvas.drawText(AnimatedTxt, 0, 50, paint);
            
        }
    }
}
