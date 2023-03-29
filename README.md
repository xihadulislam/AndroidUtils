# Android Utils

<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/android_utils.png" alt="alt text" style="width:200;height:200">

[![](https://jitpack.io/v/xihadulislam/AndroidUtils.svg)](https://jitpack.io/#xihadulislam/AndroidUtils)

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
    implementation 'com.github.xihadulislam:AndroidUtils:2.0.4'
}
  
```

[![](https://jitpack.io/v/xihadulislam/AndroidUtils.svg)](https://jitpack.io/#xihadulislam/AndroidUtils)

## Usage


### Access shared preference easily

```kt
      val sharePrefSettings = AndroidUtils.getSharePrefSetting(this);

        sharePrefSettings.setBoolValue("key", false)
        val kBoolean = sharePrefSettings.getBoolValue("key")
        
        Log.d(TAG, "onCreate: $kBoolean")

        sharePrefSettings.setStringValue("key2", "xihad islam")
        val xd = sharePrefSettings.getStringValue("key2")
        
        Log.d(TAG, "onCreate: $xd")

```



### Make a click effect of all view 

```kt

   AndroidUtils.applyClickEffect(view)
   view.setOnClickListener{}

```


### Generate a random unique uuid in ine line

```kt
   val uId: String = AndroidUtils.uId() // example: 42N35260Y390345AM

```

### Check your Internet connection is Available or not

```kt
        
   AndroidUtils.isInternetAvailable(context) // it will return true or false

```

### Screenshot Utils

#### take Screenshot Of a View

```kt
        
  var image : Bitmap = AndroidUtils.takeScreenshotOfView(rootView,200,200) // height & width is optional 
         
  var image2 : Bitmap = AndroidUtils.takeScreenshotOfView(rootView) // height & width is optional 

```

#### protect To Take Screenshot of a Screen

```kt
   AndroidUtils.protectToScreenshot(this) // 

```

### Number Utils

```kt
   val numberUtils: NumberUtils = AndroidUtils.getNumberUtils()
   numberUtils.numberToWords(100)
   numberUtils.numberInBangla("100")
        
   // or you can use it with Quick Access
        
   val word = AndroidUtils.numberToWords(100)
        
   val banglaNumber = AndroidUtils.numberInBangla("12-10-2022")
        
   val banNumber = AndroidUtils.getDigitBanglaFromEnglish("1234")
        
   val enNumber = AndroidUtils.getDigitEnglishFromBangla(banNumber)

```

### Payment Utils

```kt
     
  val includingTax = AndroidUtils.getIncludingTax(100,20)
        
  val excludingTax = AndroidUtils.getExcludingTax(100,25)
        
  val num = AndroidUtils.twoDigitDouble(14.4444334343)
        
  val str:String = AndroidUtils.twoDigitString(14.364433)
        
  val num = AndroidUtils.stringToNumber("13")

```

### Encryption Utils

```kt
     
  val encrypt = AndroidUtils.encrypt("hello from xihad")
        
  val decrypt = AndroidUtils.decrypt(encrypt) // hello from xihad
  

```

### Application Utils

```kt
     
  AndroidUtils.setWebView(url, webView) // load webview
  
  val systemApplications : List<ApplicationInfo> = AndroidUtils.getSystemApplications(context)
  
  val installApplications : List<ApplicationInfo> = AndroidUtils.getInstallApplications(context)
  
  val allApplications : List<ApplicationInfo> = AndroidUtils.getAllApplications(context)
  
  val is = AndroidUtils.isAppOnForeground(context)
  
  val isPack= AndroidUtils.isSystemPackage(context)
  

```

[//]: # (<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/wp.jpeg" >)

## Here a Sample code snippet for SnackBar

```kt
  toast.setOnClickListener {
            AndroidUtils.toast(this, "show something")
        }

        showSnack.setOnClickListener {
            AndroidUtils.getSnackBar(this).snackBar("show something")
        }

        showSnackSuccess.setOnClickListener {
            AndroidUtils.getSnackBar(this).successSnack(root, "show something")
        }

        showSnackInfo.setOnClickListener {
            AndroidUtils.getSnackBar(this).infoSnack(root, "show something", Gravity.BOTTOM, fun() {
                AndroidUtils.toast(this, "click")
            })
        }

        showSnackWarning.setOnClickListener {
            AndroidUtils.getSnackBar(this).warningSnack(root, "show something")
        }

        showSnackError.setOnClickListener {
            AndroidUtils.getSnackBar(this).errorSnack(root, "show something")
        }


```

#### Intent several activities just need one line code

```kt
           startNextActivity.setOnClickListener {
            AndroidUtils.getIntent().startNextActivity(this, SecondActivity::class.java)
        }

        afterNextActivity.setOnClickListener {
            AndroidUtils.getIntent().afterNextActivity(this, 2000, SecondActivity::class.java)
        }

        startFacebookIntent.setOnClickListener {
            AndroidUtils.getIntent().startFacebookIntent(this, "url")
        }

      
```

## Sample project

Clone this repo and check out
the [app](https://github.com/xihadulislam/androidUtils/blob/master/app) module.

## Author

* **xihad islam**
    * **[Linkedin](https://www.linkedin.com/in/xihad-islam-315417185/)**
    * **[Github](https://github.com/xihadulislam)**
    * **[Twitter](https://twitter.com/islamxihad)**

#### This is my First built library, so if you face any issues or errors feel free to tell me. I will update it continuously.


# Share

> Like this project? Why not share to your friend :)
>
> <a href="https://twitter.com/intent/tweet?text=Look%20at%20this%20nice%20project,%20a%20of%20Android%20Utils%20app.%20Made%20by%20@xihadulislam%20Url%20https://github.com/xihadulislam/androidUtils" target="_blank" title="share to twitter" style="width:100%"><img src="http://i.imgur.com/GlSWEr7.png" title="share to twitter"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.facebook.com/sharer/sharer.php?u=https://github.com/xihadulislam/androidUtils" target="_blank" title="share to facebook" style="width:100%"><img src="http://i.imgur.com/0evE2QJ.png" title="share to facebook"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://plus.google.com/share?url=https://github.com/xihadulislam/androidUtils" target="_blank" title="share to google+" style="width:100%"><img src="http://i.imgur.com/zvDBPqj.png" title="share to google+"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://service.weibo.com/share/share.php?searchPic=false&title=Android Utils &url=https://github.com/xihadulislam/androidUtils&utm_content=share_button&utm_campaign=post_show&utm_medium=github&utm_source=weibo" target="_blank" title="share to sina weibo" style="width:100%"><img src="http://i.imgur.com/pH9q4qu.png" title="share to sina weibo"/></a>

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
