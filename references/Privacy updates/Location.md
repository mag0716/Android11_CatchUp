## Location updates in Android 11

https://developer.android.com/preview/privacy/location
### Background location access

Android 11 では one-time permission が追加され、`ACCESS_BACKGROUND_LOCATION` の許可がアプリでできなくなる。

* `targetSdkVersion` が Android 11 の場合、`ACCESS_BACKGROUND_LOCATION` が必要な理由を説明するためのカスタム UI を作成することができる
* `targetSdkVersion` が 29 以下の場合、システムが提供する UI を利用する必要がある

### Create a custom UI

#### Explain your app's background location requirement

`ACCESS_BACKGROUND_LOCATION` をリクエストするためには、`ACCESS_COARSE_LOCATION` か `ACCESS_FINE_LOCATION` のどちらかを許可してもらう必要がある。
`ACCESS_BACKGROUND_LOCATION` を他の権限と一緒にリクエストするとシステムは例外をスローする。

`ACCESS_COARSE_LOCATION` か `ACCESS_FINE_LOCATION` のいずれかを取得したら、`ACCESS_BACKGROUND_LOCATION` が必要な理由をユーザに示し、ユーザに以下の選択をさせる

* Grant the permission：ユーザが許可したら、`ACCESS_BACKGROUND_LOCATION` をリクエストする。システムは直接端末の設定画面へ遷移させる。
                        ユーザがどの選択肢を選べばいいのか分かりやすくするために、`getBackgroundPermissionOptionLabel()` を使うことも可能。
* Don't grant the permission：許可しなくても `ACCESS_BACKGROUND_LOCATION` が必要な機能から離れ、アプリを継続で利用できるようにするべき。

`ACCESS_BACKGROUND_LOCATION` を許可されたあとでも、ユーザは設定を変更できることを考慮しておく必要がある。

#### Direct users to system settings if necessary

ユーザが `ACCESS_BACKGROUND_LOCATION` の許可に同意した場合、端末の設定画面へ遷移させるべき。
端末の設定アプリで「Allow all the time」を選択すると、バックグラウンドでの位置情報の取得が可能になる。

##### Limited redirects to system settings

Android 11 ではユーザを端末の設定画面へ遷移させられるのは最大2回まで。
そのあとは、バックグラウンドでの位置情報アクセスが何故必要なのかを明確に説明する必要がある。

アプリがこの制限に達した時、`Settings.ACTION_APPLICATION_DETAILS_SETTINGS` の Intent を発行することで端末の設定画面へ遷移させることが可能。

Caution：`ACTION_APPLICATION_DETAILS_SETTINGS` はアプリが権限をリクエストしていることをユーザに説明しないので使用しないこと推奨されている。

### Use the system-provided UI

`targetSdkVersion` が 29 以下の場合、`ACCESS_BACKGROUND_LOCATION` をリクエストする時に、システムが提供する UI を表示する必要がある。

* `Allow all the time` を選択するための方法の説明
* フォアグラウンドでの位置情報へのアクセスを許可するか拒否するためのボタン

Note:`ACCESS_BACKGROUND_LOCATION` を2回拒否した場合、このダイアログは表示されなくなる。これは `don't ask again` の動作と同じ
