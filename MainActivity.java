package com.meta.android_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.awesdk.EnvironmentManager;
import com.awesdk.FileTools;
import com.awesdk.Global;
import com.awesdk.UnityPlayerManager;

import java.io.IOException;

import com.awesdk.Authorization.LicenseManager;
import com.awesdk.Scene.Human;
import com.awesdk.Scene.Scene;
import com.awesdk.Scene.SceneManager;
import com.awesdk.Scene.Transaction;
import com.awesdkcore.Context;
import com.awesdkcore.Values.Color;
import com.awesdkcore.Values.Vector3;

public class MainActivity extends AppCompatActivity {

    private ViewGroup view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (ViewGroup) this.getWindow().getDecorView();

        Context context = EnvironmentManager.init({YourAppKey}, {YourAppSecret});
        EnvironmentManager.initEnd = () -> {
            loadHuman(context);
        };

        UnityPlayerManager.getInstance().onCreate(this);
        UnityPlayerManager.getInstance().attachTo(view);
    }

    private void loadHuman(Context context) {
        Human.Gender gender = Human.Gender.Female;
        String faceTarget = "xiaojing/face.target";
        String faceMapping = "xiaojing/face.jpg";
        Human.BaseInfo baseInfo = new Human.BaseInfo(gender, faceTarget, faceMapping);
        Human human = new Human(context, baseInfo);

        // 给数字人设置体型参数
        human.setTarget("20003", 1f);
        human.setTarget("23002", 0.5f);
        human.setTarget("20101", 0.4769f);
        human.setTarget("20102", -0.3075f);
        human.setTarget("20502", -0.3522f);
        human.setTarget("23202", 0.4769f);
        human.setTarget("23503", -0.8489f);

        // 给数字人穿戴发型、服饰、鞋子等
        human.wearHair("602");
        human.wearOutfits("11670", "11667");
        human.wearShoes("1501");

        // 给数字人播放一个动画
        human.playAnimation("34025");

        // 将数字人添加到场景中
        Scene scene = SceneManager.getInstance(context).getCurrentScene();
        scene.addElement(human);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        UnityPlayerManager.getInstance().onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        UnityPlayerManager.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        UnityPlayerManager.getInstance().onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        if (UnityPlayerManager.getInstance().getCurrentActivity() == this) {
            UnityPlayerManager.getInstance().onPause();
        }
        super.onPause();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        UnityPlayerManager.getInstance().onCreate(this);
        UnityPlayerManager.getInstance().attachTo(view);
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        UnityPlayerManager.getInstance().onResume();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        UnityPlayerManager.getInstance().onLowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        UnityPlayerManager.getInstance().onTrimMemory(level);
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(android.content.res.Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        UnityPlayerManager.getInstance().onConfigurationChanged(newConfig );
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        UnityPlayerManager.getInstance().onWindowFocusChanged(hasFocus);
    }
}