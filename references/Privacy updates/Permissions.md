## Permission updates in Android 11

https://developer.android.com/preview/privacy/permissions

### One-time permissions

* Android 11 では、位置情報、マイク、カメラに関するパーミッションをリクエストすると「Only this time」が選択肢として表示される
* Activity が表示されている間だけ権限が付与される
* フォアグラウンド Service が実行されている場合は Service が起動している間は権限が付与される

#### Dialog shown when requesting permission again

* One-time permission を再度リクエストする場合のダイアログ
  * Android 10 以上 `ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION`：「Only this time」が選択肢があるダイアログ
  * Android 10 `ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION, followed by ACCESS_BACKGROUND_LOCATION`：`ACCESS_BACKGROUND_LOCATION` が別のダイアログでリクエストされる
  * Android 以下 All available location permissions simultaneously：「Allow in settings」リンクが含まれたダイアログ


#### WebView location access

* WebView が位置情報にアクセスする場合は、One-time permissions のみに制限される

### Permission dialog visibility

※ https://developer.android.com/privacy/best-practices#permissions に従っていたら対応は不要

* 権限に対して2度拒否すると「don't ask again」と同じ動作をする
* バックキーでダイアログを閉じた場合は拒否としてカウントしない
* `requestPermissions()` から端末の設定画面へ遷移し、バックキーをタップした場合は拒否としてカウントする

### Data access auditing

※名称は変わるかも

* private データへのアクセスの方法と依存について透明性を提供する
  * 予期しないデータアクセスの特定と修正が可能になる
* `AppOpsManager.AppOptCollector`
  * アプリからのアクセス、ライブライ、SDKからのアクセスについて検知できる

#### Log access of data

* Activity#onCreate などで登録する
  * `onAsyncNoted()`
    * アプリの API 呼び出し中にデータアクセスが発生しない場合に呼び出される
      * 大抵はリスナーを登録し、データアクセスがリスナーのコールバックで発生するケース
    * `getMessage()` でデータアクセスの詳細情報を取得できる
  * `onSelfNoted()`
    * `noteOp()` 内で UID を渡した時に発生するレアケース

#### Audit data access by feature

* 写真を撮影し、共有するために連絡先へのアクセスを許可が必要なケースが存在する
* もし複数の機能がある場合はプライベートデータへアクセスする feature contexts を定義することができる
  * `featureId` は `onNoted()` で取得でき、呼び出し元の追跡に役立つ

##### Create feature contexts

* `createFeatureContext` で機能名を定義することができる

#### Include feature contexts in access logs

* `AppOpsManager.AppOpsCollector` コールバックを更新すれば定義した機能名がログに出力される
  * `null` の場合はデフォルト機能を意味する
