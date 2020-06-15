## Privacy in Android 11

https://developer.android.com/preview/privacy

### Top privacy changes

* Scoped storage enforcement
  * `targetSdkVersion` が Android 11、もしくは、`requestLegacyExternalStorage=true` がセットされていないアプリに影響する
* One-time permissions
  * 全てのアプリに影響する
* Permissions auto-reset
  * `targetSdkVersion` が Android 11 の端末に影響する
* Background location access
  * `targetSdkVersion` が Android 11で常に位置情報にアクセスが必要なアプリに影響する
* Package Visibility
  * `targetSdkVersion` が Android 11 で他のアプリと連携が必要なアプリに影響する
* Foreground service types
  * `targetSdkVersion` が Android 11で Foreground Service でカメラかマイクにアクセスするアプリに影響する

### Get started with privacy updates

1. Review the privacy features
1. Test your app on Android 11
1. Update your app
