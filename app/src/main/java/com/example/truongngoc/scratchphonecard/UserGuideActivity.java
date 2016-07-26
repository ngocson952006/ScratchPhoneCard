package com.example.truongngoc.scratchphonecard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by Truong Ngoc Son on 6/25/2016.
 * Introduction screen will appear when user first launch the application
 */
public class UserGuideActivity extends Activity {

    private static final String TAG = UserGuideActivity.class.getSimpleName(); // tag
    private static final int MAX_GUIDE_SCREEN = 3;

    private TextSwitcher switcher;
    private ImageSwitcher imageSwitcherIntroduction;
    private int currentGuideIndex = 0; // it's value will be 1 , 2, 3 corresponding to index of the guided screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.introduction_screen);
        // set up animations for view component
        //  Animation fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        //Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        this.imageSwitcherIntroduction = (ImageSwitcher) this.findViewById(R.id.image_view_user_guide);
        this.imageSwitcherIntroduction.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(UserGuideActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT));
                return imageView;
            }

        });
        this.imageSwitcherIntroduction.setInAnimation(this, android.R.anim.fade_in); // fade in animation
        this.imageSwitcherIntroduction.setOutAnimation(this, android.R.anim.fade_out); // fade out animation
        // We use TextSwitcher to add animation for TextView
        this.switcher = (TextSwitcher) this.findViewById(R.id.text_view_tip);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // create new TextView object to set up content
                TextView textView = new TextView(UserGuideActivity.this);
                // fit center
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                return textView;
            }
        });
        switcher.setInAnimation(this, android.R.anim.fade_in); // fade in animation
        switcher.setOutAnimation(this, android.R.anim.fade_out); // fade out animation
        // first screen
        this.loadFirstScreenValue();
    }


    private void loadFirstScreenValue() {
        this.switcher.setText(this.getString(R.string.introduction_1));
        // image view
        this.imageSwitcherIntroduction.setImageResource(R.drawable.logo);
    }


    /**
     * Shows next guide when user click the next button
     *
     * @param view
     */
    public void showNextGuide(View view) {
        switch (this.currentGuideIndex) {
            case 0:
                loadFirstScreenValue();
                break;
            case 1:
                this.switcher.setText(this.getString(R.string.introduction_2));
                this.imageSwitcherIntroduction.setImageResource(R.drawable.forward_icon);
                break;
            case 2:
                this.switcher.setText(this.getString(R.string.introduction_3));
                this.imageSwitcherIntroduction.setImageResource(R.drawable.promotion_icon);
                break;
            default:
                loadFirstScreenValue();
                break;
        }
        this.currentGuideIndex = (this.currentGuideIndex + 1) % MAX_GUIDE_SCREEN;
    }


}
