## Permission updates in Android 11

https://developer.android.com/preview/privacy/permissions

### One-time permissions

Android 11 では、位置情報、マイク、カメラに関するパーミッションをリクエストすると「Only this time」が選択肢として表示される

* Activity が表示されている間だけ権限が付与される
* バックグラウンドにいっても一定期間はデータにアクセス可能
* フォアグラウンド Service が実行されている場合は Service が起動している間は権限が付与される
* 端末の設定アプリで権限が拒否された場合アプリのプロセスは中止され、データにはアクセスできなくなる

#### Dialog shown when requesting permission again

* One-time permission を再度リクエストする場合のダイアログ
  * Android 10 以上 `ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION`：「Only this time」が選択肢があるダイアログ
  * Android 10 `ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION, followed by ACCESS_BACKGROUND_LOCATION`：`ACCESS_BACKGROUND_LOCATION` が別のダイアログでリクエストされる
  * Android 以下 All available location permissions simultaneously：「Allow in settings」リンクが含まれたダイアログ

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
