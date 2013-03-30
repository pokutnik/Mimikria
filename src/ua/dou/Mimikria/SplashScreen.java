package ua.dou.Mimikria;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SplashScreen extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
        
    }
    
    private static class GraphicsView extends View {
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
