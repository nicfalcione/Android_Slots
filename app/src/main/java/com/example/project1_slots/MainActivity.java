package com.example.project1_slots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int money = Constants.STARTUP_CASH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int randomizeImage(ImageView image) {
        final ImageView go = (ImageView) findViewById(R.id.goImage);
        int random = 0;
        int i = 0;
        Random r = new Random();
        random = r.nextInt(3) + 1;

        switch (random) {
            case 1:
                i = R.drawable.f1;
                break;
            case 2:
                i = R.drawable.f2;
                break;
            case 3:
                i = R.drawable.f3;
                break;
        }
        image.setImageResource(i);
        return random;
    }

    public void reset(View view) {
        final ImageView flower1 = (ImageView) findViewById(R.id.slot1);
        final ImageView flower2 = (ImageView) findViewById(R.id.slot2);
        final ImageView flower3 = (ImageView) findViewById(R.id.slot3);
        final TextView moneyText = (TextView) findViewById(R.id.moneyCounter);
        flower1.setImageResource(R.drawable.f1);
        flower2.setImageResource(R.drawable.f2);
        flower3.setImageResource(R.drawable.f3);
        view.setVisibility(View.INVISIBLE);
        money = Constants.STARTUP_CASH;
        moneyText.setText(Integer.toString(money));
    }

    private void calculateMoney(int one, int two, int three) {
        final ImageView flower1 = (ImageView) findViewById(R.id.slot1);
        final ImageView flower2 = (ImageView) findViewById(R.id.slot2);
        final ImageView flower3 = (ImageView) findViewById(R.id.slot3);

        if (one == two && two == three) {
            money += Constants.MATCH_3;
        }
        else if (one == two || one == three || two == three) {
            money += Constants.MATCH_2;
        }
        else {
            money--;
        }
    }

    private Animation rotateImage(ImageView image) {
        RotateAnimation rotate = new RotateAnimation(0, 719, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotate);
        return rotate;
    }

    public void runSlots(View view) {
        money -= Constants.COST_PER_ROLL;
        final ImageView resetButton = (ImageView) findViewById(R.id.resetImage);
        final ImageView flower1 = (ImageView) findViewById(R.id.slot1);
        final ImageView flower2 = (ImageView) findViewById(R.id.slot2);
        final ImageView flower3 = (ImageView) findViewById(R.id.slot3);
        final TextView moneyText = (TextView) findViewById(R.id.moneyCounter);
        final ImageView goButton = (ImageView) findViewById(R.id.goImage);

        flower1.setImageResource(R.drawable.tmp);
        flower2.setImageResource(R.drawable.tmp);
        flower3.setImageResource(R.drawable.tmp);

        Animation a = rotateImage(flower1);
        rotateImage(flower2);
        rotateImage(flower3);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                finish(goButton);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void finish(View view) {
        final ImageView resetButton = (ImageView) findViewById(R.id.resetImage);
        final ImageView flower1 = (ImageView) findViewById(R.id.slot1);
        final ImageView flower2 = (ImageView) findViewById(R.id.slot2);
        final ImageView flower3 = (ImageView) findViewById(R.id.slot3);
        final TextView moneyText = (TextView) findViewById(R.id.moneyCounter);

        int slot1 = randomizeImage(flower1);
        int slot2 = randomizeImage(flower2);
        int slot3 = randomizeImage(flower3);
        resetButton.setVisibility(View.VISIBLE);
        if (money <= Constants.YOUR_BROKE) {
            money = Constants.YOUR_BROKE;
            view.setVisibility(View.INVISIBLE);
        }
        else {
            calculateMoney(slot1, slot2, slot3);
        }
        moneyText.setText(Integer.toString(money));
    }
}