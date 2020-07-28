## Package visibility in Android 11

https://developer.android.com/preview/privacy/package-visibility

* マニフェストファイルに `<queries>` の追加
  * package name
  * intent signature
  
Note:多くのケースでは対応が不要

* `PackageManager#queryIntentActivityes` などでは `<queries>` で定義した他のアプリの情報を取得できるようになる
* `startService()` などのようなメソッドで `<queries>` で定義した他のアプリを操作できるようになる

### Set up your environment

* Android Studio 3.3 以上
* Android Gradle Plugin は最新リリースを利用する

Note: `<queries>` に `<provider>` が含まれると warning が表示されるかもしれないが、最新の Android Gradle Plugin を利用している限りビルドには影響はない

### Query and interact with specific packages

* 連携したいアプリの情報を知っている場合は `<queries>` 内に `<packages>` を指定

### Query and interact with apps given an intent filter

* 連携したいアプリの情報を知らない場合は `<queries>` 内に `<intent>` を指定

`<intent>` にはいくつかの制約がある

* 1つの `<action>` を含める必要がある
* `<data>` に `path`, `pathPrefix`, `pathPattern`, `port` は利用不可能。システムはワイルドカードを設定したかのように動作する
* `<data>` に `mimeGroup` は利用不可能
* `<data>` は以下の各属性を最大1度利用することができる
  * `mimeType`
  * `scheme`
  * `host`

`<intent>` のいくつかの attribute ではワイルドカード指定が可能

* `<action>` の `name`
* `<data>` の `mimeType`
* `<data>` の `scheme`
* `<data>` の `host`

`prefix*` のようにテキストとワイルドカード文字を組み合わせることはできない。

Note:マニフェストファイルで `<package>` を宣言するとそのパッケージ名に関連づけられたアプリが `PackageManager` のクエリの結果に表示される

### Query and interact with app given a provider authority

ContentProvider に問い合わせる必要があるがパッケージ名を知らないケースでは、`<provider>` を指定

* 単一の `<provider>` にセミコロン区切りで指定する
* 同一の `<queries>` に複数の `<provider>` を含める

### Query and interact with all apps

* Google Play のような全てのアプリと連携する必要があるアプリについては `QUERY_ALL_PACKAGES` を利用する

### Use cases that aren't affected by the changes

* 以下のケースでは対応が不要
  * 自身のアプリがターゲット
  * 暗黙的 Intent で他アプリと連携している
  * システムの設定や機能と連携している
  * [具体的なユースケースが不明] `getIntiatingPackageName()` と `getInstallingPackageName()` で返されたアプリの可視性を保持している
  * `startActivityForResult()` を使用して別アプリから起動される
  * 別アプリがサービスを開始、バインドする
  * 別アプリが ContentProvider へリクエストする
  * アプリが IME

### Log messages for package filtering

* 以下のコマンドでログをフィルタリングすることが可能
  * `adb shell pm log-visibility --enable your-package-name`
  * `I/AppsFilter: interaction: PackageSetting{7654321 com.example.myapp/12345} -> PackageSetting{...} BLOCKED` のようなログが出力される
* Caution: アプリのパフォーマンスに影響するのでテスト中以外は無効化すること

### Test the change

* Android Studio 3.6.1 以上を利用
* `targetSdkVersion` に `30` を指定
* マニフェストファイルには `<queries>` は定義しない
* `getInstalledApplications(), getInstalledPackages()` を呼びだす
* 動作しないこと機能がないかを確認
* `<queries>`を定義する
