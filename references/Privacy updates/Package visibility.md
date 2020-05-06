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
    * `resolveActivity()` を使うには `FLAG_ACTIVITY_REQUIRE_NON_BROWSER`,`FLAG_ACTIVITY_REQUIRE_DEFAULT`の利用が必要になる
  * システムの設定や機能と連携している
  * ContentProvider を経由して他のアプリのデータと連携する

### Add restrictions to activity starts

* Android 11 ではフラグが追加されており、`ActivityNotFoundException` になるケースを指定できる
  * このケースでは `resolveActivity()`, `queryIntentActivities()` を呼び出すべきではない
  * 特に、Web に関する `Intent` で役立つ

#### Launch a web intent in a non-browser app

* `FLAG_ACTIVITY_REQUIRE_NON_BROWSER`
  * ブラウザでないアプリが直接 `Intent` を扱える場合
  * ユーザがダイアログでブラウザでないアプリを選択した場合

#### Require only one matching activity

* `FLAG_ACTIVITY_REQUIRE_DEFAULT`
  * デバイスに `Intent` を扱えるアプリが1つしか存在しない、もしくはデフォルトアプリに設定されている場合のみ処理される

### Query and interact with specific packages

* 連携したいアプリの情報を知っている場合は `<queries>` 内に `<packages>` を指定

### Query and interact with apps given an intent filter

* 連携したいアプリの情報を知らない場合は `<queries>` 内に `<intent>` を指定

### Query and interact with all apps

* Google Play のような全てのアプリと連携する必要があるアプリについては `QUERY_ALL_PACKAGES` を利用する

### Log messages for package filtering

* 以下のコマンドでログをフィルタリングすることが可能
  * `adb shell pm log-visibility --enable your-package-name`
  * `I/AppsFilter: interaction: PackageSetting{7654321 com.example.myapp/12345} -> PackageSetting{...} BLOCKED` のようなログが出力される
* Caution: アプリのパフォーマンスに影響するのでテスト中以外は無効化すること

### Test the change

* Android Studio 3.6.1 以上を利用
* `targetSdkVersion` に `R` を指定
* マニフェストファイルには `<queries>` は定義しない
* `getInstalledApplications(), getInstalledPackages()` を呼びだす
* 動作しないこと機能がないかを確認
* `<queries>`を定義する
