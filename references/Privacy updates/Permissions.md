## Permission updates in Android 11

https://developer.android.com/preview/privacy/permissions

### One-time permissions

Android 11 では、位置情報、マイク、カメラに関するパーミッションをリクエストすると「Only this time」が選択肢として表示される

* Activity が表示されている間だけ権限が付与される
* バックグラウンドにいっても一定期間はデータにアクセス可能
* Activity が表示されている間に起動されたフォアグラウンド Service が実行されている場合は Service が起動している間は権限が付与される
* 端末の設定アプリで権限が拒否された場合アプリのプロセスは中止され、データにはアクセスできなくなる

### Auto-reset permission from unused apps

`targetSdkVersion` が Andriod 11 のアプリを数ヶ月利用しないと、システムはユーザのデータを保護するために自動的に許可済みの権限をリセットする。
これはユーザが「Deny」選択するのと同じ意味を持つ。
アプリはベストプラクティスに従って実装している場合は、アプリの実装は変更する必要はない。

#### Request user to disable auto-reset

以下のようにバックグラウンドで動作するアプリの場合、システムがアプリの権限をリセットしないようにユーザにリクエストすることができる。

* 家族の利用状況などを把握するアプリ
* データの同期
* 外部デバイスとの連携

端末の設定アプリでアプリ専用の画面へ遷移させるには `Settings.ACTION_APPLICATION_DETAILS_SETTINGS` を利用する。

#### Determine whether auto-reset is disabled

`isAutoRevokeWhiltelisted()` で自動リセットを無効化しているかどうかを取得できる。

#### Test the auto-reset feature

以下の手順で自動リセットを検証できる

* テストごとに設定を元に戻すために自動リセットまでの合計時間のデフォルト値を取得する
  * `threshold=$(adb shell device_config get permissions auto_revoke_unused_threshold_millis2)`
* 自動リセットまでの合計時間を変更する
  * `adb shell device_config put permissions auto_revoke_unused_threshold_millis2 1000`
* 自動リセットのプロセスを手動で実行する
  * `adb shell cmd jobscheduler run -u 0 -f com.google.android.permissioncontroller 2`
* アプリでリセット後の動作を検証する
* 自動リセットまでの合計時間を元に戻す
  * `adb shell device_config put permissions auto_revoke_unused_threshold_millis2 $threshold`

### Permission dialog visibility

※ https://developer.android.com/privacy/best-practices#permissions に従っていたら対応は不要

* 権限に対して2度拒否すると「don't ask again」と同じ動作をする
* バックキーでダイアログを閉じた場合は拒否としてカウントしない
* `requestPermissions()` から端末の設定画面へ遷移し、バックキーをタップした場合は拒否としてカウントする

### Phone numbers

`targetSdkVersion` を Android 11 にすると、以下の API で電話番号の一覧を取得するためには、`READ_PHONE_STATE` の代わりに `READ_PHONE_NUMBERS` 権限をリクエスト
する必要がある。

* `TelephonyManager#getLine1Number()`, `TelecomManager#getLine1Number()`
* `TelephonyManager#getMsisdn()`

`READ_PHONE_STATE` は以前の端末用に定義したままにする必要がある。
