## Package visibility in Android 11

https://developer.android.com/preview/privacy/package-visibility

* 多くのケースでは対応が不要
* マニフェストファイルに `<queries>` の追加
  * package name
  * intent signature
* `PackageManager#queryIntentActivityes` などで他のアプリの情報を取得できるようになる
* `startService()` などのようなメソッドで他のアプリを操作できるようになる

### Most common interactions aren't affected

* 以下のケースでは対応が不要
  * 自身のアプリがターゲット
  * 暗黙的 Intent で他アプリと連携している
    * `resolveActivity()` を使うには `FLAG_ACTIVITY_REQUIRE_NOW_BROWSER`,`FLAG_ACTIVITY_REQUIRE_DEFAULT`の利用が必要になる
  * システムの設定や機能と連携している
  * ContentProvider を経由して他のアプリのデータと連携する

### Query and interact with specific packages

* 連携したいアプリの情報を知っている場合は `<queries>` 内に `<packages>` を指定

### Query and interact with apps given an intent filter

* 連携したいアプリの情報を知らない場合は `<queries>` 内に `<intent>` を指定

### Query and interact with all apps

* Google Play のような全てのアプリと連携する必要があるアプリについては `QUERY_ALL_PACKAGES` を利用する

### Test the change

* Android Studio 3.6.1 以上を利用
* `targetSdkVersion` に `R` を指定
* マニフェストファイルには `<queries>` は定義しない
* `getInstalledApplications(), getInstalledPackages()` を呼びだす
* 動作しないこと機能がないかを確認
* `<queries>`を定義する
