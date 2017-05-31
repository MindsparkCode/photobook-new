# Photobook: A photo-sharing Android app using Firebase 

By NetIDs: ksa55 mw866

Photobook helps users to store and share photos. There are two types of photos: Public (available to everyone) and Private (available to the user). Each of the photo has a short description. The app has four views:
1. Photos — This part allows a user to look through photos that were uploaded by anyone and have a tag Public. If the user is authenticated then there should be additionally Private photos that were uploaded by this user. You don’t need to implement preview of photos, just make it a small rectangle with description (simple is better)
2. Upload — A user can upload photos, provide description and tag them either Public or Private. You should use Firebase Database. Additionally, Security Rules should be used to manage access for photos on the Firebase level.
3. Search — The user should look up the image by description. If the user is authenticated, then search is enabled for Private images, otherwise only Public images are available.
4. Auth — User authenticates with the app using Email or Google Sign-in (OAuth). The authentication doesn’t happen at the start of the app and is voluntarily.

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
