# AweSDK-Android

## Introduce

`AweSDK-Android` allows you to quickly create AI digital human


## Quickstart

### Importing `AweSDK.aar` 

1. Introducing libraries into the project
2. Modify the **build.gradle** configuration file under the app and add the following configuration to the dependencies node

```gradle
dependencies {
    implementation fileTree("libs")
}
```

### Asset files are not compressed

Modify the **build.gradle** configuration file under the app and add the following configuration to the android node

```gradle
android {
    aaptOptions {
        noCompress = ['.unity','.ress','.resource','.obb','.yml','.bin','.png','.xml','.glsl','.ttf','.pts','.jpg','.json','.txt','.params','.manifest','.map','.obj','.prefab','.skeleton','.weight','.proxy','.data','.mat','.target','avatarengine/data/bundle/android/corematerials','avatarengine/data/bundle/android/human']
    }
}
```

### Add support for armv7, arm64

Modify the **build.gradle** configuration file under the app and add the following configuration to the android node

```gradle
android {
    defaultConfig {
        ndk {
            abiFilters 'armeabi-v7a', 'arm64-v8a'
        }
    }

    packagingOptions {
        doNotStrip '*/armeabi-v7a/*.so'
        doNotStrip '*/arm64-v8a/*.so'
    }
}
```

### Environmental assembly

1. Import references

```java
import com.awesdk.EnvironmentManager;
import com.awesdk.Global;
import com.awesdk.UnityPlayerManager;
```

2. Embedded Renderer

Mount the rendered view to the current Activity and implement the lifecycle methods with the following code

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    UnityPlayerManager.getInstance().onCreate(this);
    UnityPlayerManager.getInstance().attachTo(view);
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
    UnityPlayerManager.getInstance().attachTo(findViewById(R.id.ll));
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
```

3. Assembling the environment and generating the context

Replace `{YourAppKey}` and `{YourAppSecret}` in the sample code with the requested AppKey and AppSecret values by themselves.

```java
Context context = EnvironmentManager.init({YourAppKey}, {YourAppSecret});
```

### Loading AI Digital Human

Call `loadHuman(context)`, the interface for generating ai digital human, with the following code

```java
private void loadHuman(Context context)
{
    // 使用性别、人脸贴图和人脸 target 等信息，快速构建一个数字人
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
    // 如果没有动画资源，也可以传入空字符串，会显示默认的处于 A-Pose 状态的数字人，如下
    // human.playAnimation("");

    // 将数字人添加到场景中
    Scene scene = SceneManager.getInstance(context).getCurrentScene();
    scene.addElement(human);
}
```

> Note: The creation of a digital person needs to be executed after the EnvironmentManager's callback `initEnd`

See [MainActivity.java](https://github.com/MetaMakerDeveloper/AweSDK-Android/blob/main/MainActivity.java) for the complete source code


## Download

* release from [github: ![Latest release](https://img.shields.io/badge/release-v1.1.3-blue.svg
)](https://github.com/MetaMakerDeveloper/AweSDK-Android/releases/tag/v1.1.3)


## Document

* [Quick Access](https://help.metamaker.cn/open/528b/9595/26af)
* [Resources Download](http://developer.metamaker.cn/)

## Version

### V1.1.3 (2023-06-29) 

* Solve the bug that the setVisible function of Digital People does not work

### V1.1.2 (2023-06-26) 

* Solve the bug that A-Pose will appear when playing animation
* Add the default display of digital people in the A-Pose state when the parameter in the play animation function is an empty string.

### V1.1.1 (2023-06-12) 

* Adjusted the ambient lighting to make the digital people more realistic

### V1.1.0 (2023-06-07) 

* Remove the splash of unity
* Add background transparency effect
* Improved rendering quality
* Remove shadows

### V1.0.0 (2023-02-01) 

* Provides a basic SDK for presenting AI digital humans on Android
* Documentation and resources are provided


## NOTICE

MetaMakerDeveloper 发布的代码或数字资产（数字人、服装、动作、表情等）以及试用数字人小镜、大黑都属于黑镜科技公司。如需商用，请添加以下二维码联系，谢谢！

For the codes, digital assets (including but not limited to digital human, clothing, animations, expressions, etc.) and trial digital human (e.g. xiaojing, dahei, etc.) released by MetaMakerDeveloper are all belong to Heijing Technology Company. For commercial use, please scan the following QR code to contact us, thank you!

![图 3](https://user-images.githubusercontent.com/110818144/186798509-1deb2c8a-27ce-4d41-9a89-ac2541fc1825.jpg)  
