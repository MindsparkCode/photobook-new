# photobook-new 

By NetIDs:
ksa55
mw866

## Demo Video
https://www.youtube.com/watch?v=qa_WkP5lYQ8

## Reference

* Android Firebase: Uploading and Retrieving File Using Firebase Storage - Part2
 https://www.youtube.com/watch?v=akIrsTB2zIQ&t=923s
* Android Firebase: Uploading and Retrieving File Using Firebase Storage - Part1: https://www.youtube.com/watch?v=jj_pheDler0


### Android
* Android Studio User Guide: https://developer.android.com/studio/intro/index.html
* Android API Guides: https://developer.android.com/guide/index.html
    * Search: https://developer.android.com/guide/topics/search/index.html
    * Search Action View: https://developer.android.com/training/appbar/action-views.html
* Android Training: https://developer.android.com/training/index.html
    * Search: https://developer.android.com/training/search/setup.html
* Android API Reference: https://developer.android.com/reference/packages.html
* Buiilding simple UI: ttps://developer.android.com/training/basics/firstapp/building-ui.html
* Sign Your App: https://developer.android.com/studio/publish/app-signing.html

### Firebase
* Firebase Documentation for Android: https://firebase.google.com/docs/android/setup
* Firebase Authentication: https://firebase.google.com/docs/auth/
* Firebase Authentication Quick Start Code: https://github.com/firebase/quickstart-android/tree/master/auth/app/src/main/java/com/google/firebase/quickstart/auth

## Troubleshooting
### `Didn't find class "com.google.android.gms.dynamite.descriptors.com.google.firebase.auth.ModuleDescriptor"`
Solution: Just ignore it.
https://stackoverflow.com/questions/37370258/failed-to-load-module-descriptor-class-didnt-find-class-com-google-android-gm

### Google Sign-in Fails
Solution:
Add SHA-1 from Gradle>Tasks>signingReport in Android Studio to Firebase Console
