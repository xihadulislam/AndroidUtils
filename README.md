# Android Utils
<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/android_utils.png" alt="alt text" style="width:200;height:200">

# To get a Git project into your build

### Step 1. Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories:

``` 
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
     }
  
```

### Step 2. Add the dependency

``` 
dependencies {
    implementation 'com.github.xihadulislam:androidUtils:1.0.3'
}
  
```


## Usage

### First you have to initialize with Context

```kt
 AndroidUtils.init(this)
```

### Access shared preference easily 

```kt
 AndroidUtils.sharePrefSettings.setBoolValue("key", false)
        
 val kBoolean = AndroidUtils.sharePrefSettings.getBoolValue("key")
        
 AndroidUtils.sharePrefSettings.setStringValue("key2","xihad islam")
        
 val xd = AndroidUtils.sharePrefSettings.getStringValue("key2");
        
 AndroidUtils.toast(xd)
```




<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/wp.jpeg" align="left" width="30%">

## Here a Sample code snippet

```kt
  toast.setOnClickListener {
            AndroidUtils.toast("show something")
        }
        showSnack.setOnClickListener {
            AndroidUtils.snackBar("show something")
        }

        showSnackSuccess.setOnClickListener {
            AndroidUtils.successSnack(root, "show something")
        }

        showSnackInfo.setOnClickListener {
            AndroidUtils.infoSnack(root, "show something", Gravity.BOTTOM, fun() {
                AndroidUtils.toast("click")
            })
        }

        showSnackWarning.setOnClickListener {
            AndroidUtils.warningSnack(root, "show something")
        }

        showSnackError.setOnClickListener {
            AndroidUtils.errorSnack(root, "show something")
        }


        playTapSound.setOnClickListener {
            AndroidUtils.playClickSound()
        }

        showKeyboard.setOnClickListener {
          AndroidUtils.showKeyboard()
        }

```


#### Intent several activities just need one line code

```kt
     startNextActivity.setOnClickListener {
          AndroidUtils.startNextActivity(SecondActivity::class.java)
        }

        afterNextActivity.setOnClickListener {
          AndroidUtils.afterNextActivity(2000,SecondActivity::class.java)
        }

        startFacebookIntent.setOnClickListener {
          AndroidUtils.startFacebookIntent("url")
        }
```


#### Check Internet connection is available or not.

```kt
  if (AndroidUtils.isInternetAvailable()){
          AndroidUtils.toast("Available")
        }
```


#### You can Play a mediaPlayer just calling one method.

```kt
    startMediaPlayer.setOnClickListener {
          //  AndroidUtils.startMediaPlayer()
        }
        stopMediaPlayer.setOnClickListener {
          //  AndroidUtils.stopMediaPlayer()
        }

```




#### This is my First built library, so if you face any issues or errors feel free to tell me. I will update it continuously.


## Sample project
Clone this repo and check out the [app](https://github.com/xihadulislam/androidUtils/blob/master/app) module.

## Author

* **xihad islam**
    * **[Linkedin](https://www.linkedin.com/in/xihad-islam-315417185/)**
    * **[Github](https://github.com/xihadulislam)**
    * **[Twitter](https://twitter.com/islamxihad)**
    

## Licence
```
Copyright 2021 @xihad islam.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


