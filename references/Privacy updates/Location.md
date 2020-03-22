## Location updates in Android 11

https://developer.android.com/preview/privacy/location

### Background location access

* ユーザが段階的に権限を許可することができるようにアプッリで Background location access の権限を許可できないようになった
* Background location access の権限をリクエストするための手順は `targetSdkVersion` によって異なる

#### Target Android 11

* 直接 background location の常時アクセス権限をリクエストできない
* その代わり、background での位置情報の取得がなぜ必要なのかをユーザに理解させるための UI を提供する
* `getBackgroundPermissionOptionLabel`

##### Limited requests for background location

* リクエストが制限された場合でもアプリの設定ページへ遷移させることができる
  * `Settigs.ACTION_APPLICATION_DETAILS_SETTINGS` を利用する
  * ただし、この方法はユーザに説明がないので避けるべき

#### Target Android 10

* アプリ利用中のみで許可している場合はダイアログで継続するか、設定画面へ遷移するかどうかを選択させることができる
  * ユーザが2回選択したらダイアログは表示されなくなる
