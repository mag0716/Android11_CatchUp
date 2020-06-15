## Location updates in Android 11

https://developer.android.com/preview/privacy/location

### One-time access

Android 11 では位置情報へのアクセスすると、「Only this time」と呼ばれる選択肢が権限ダイアログに表示される。

### Background location access

Android 11 ではバックグラウンドでの位置情報へのアクセスの許可の仕方が変わる。

Note：バックグラウンドでの位置情報へアクセスしている場合、本当に必要かどうかを検証するべき。

#### Request background location separately

https://developer.android.com/training/location/permissions#request-location-access-runtime で記載されている通り、位置情報のリクエストは別々に行う必要がある。。
`targetSdkVersion` を Android 11 にしたアプリでは、フォアグラウンドでの位置情報アクセスとバックグラウンドでの位置情報アクセスの権限を同時にリクエストすると、Exception がスローされる。

#### Permission dialog changes

Android 11 の端末でバックグラウンドでの位置情報アクセスをリクエストすると、権限ダイアログにはバックグラウンドでの位置情報アクセスを許可するための選択肢は存在しない。
有効にするためには、端末の設定画面へ遷移し、「Allow all the time」を選択する必要がある。

[ベストプラクティス](https://developer.android.com/training/permissions/requesting)に沿って実装すること。

##### App targets Android 11

`shouldShowPermissionRationale()` が `true` を返却したらユーザに以下に従って UI を表示する。

* なぜアプリの機能がバックグラウンドでの位置情報へアクセスする必要があるのかの明確な説明
* ユーザがどの選択肢を選べばいいのか分かりやすくするために、`getBackgroundPermissionOptionLabel()` を使うことも可能
* ユーザが権限を拒否した場合でもアプリを継続利用できるようにしておく

##### App targets Android 10 or lower

バックグラウンドでの位置情報アクセスをリクエストするとシステムダイアログが表示される。
ダイアログには端末の設定画面へ遷移させる導線が含まれている。

すでにベストプラクティスに沿って実装されていたら、アプリの実装を変更する必要はない。
