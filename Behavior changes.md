# Android11 Behavior changes

### all apps

https://developer.android.com/preview/behavior-changes-all

#### JobScheduler API call limits debugging

* パフォーマスの問題の特定のためにデバッグ機能が提供される
* `debuggable` が `true` なアプリで有効になる
* 制限を超えた場合は `RESULT_FAILURE` が返される

#### One-time permissions

* 位置情報、マイク、カメラに関して一時的に利用可能にするパーミッションが提供される
* https://developer.android.com/preview/privacy/permissions#one-time

#### User choice can restrict when a permission dialog appears

* パーミッションを2回拒否したら `don't ask again` を選択した時と同じように権限確認ダイアログが表示されなくなる
* https://developer.android.com/preview/privacy/permissions#dialog-visibility

#### Background location access

* バックグラウンドでの位置情報へのアクセスするためのリクエストが不可になる
* https://developer.android.com/preview/privacy/location#background-location

#### Storage UI

* Storage runtime permission
* https://developer.android.com/preview/privacy/storage#permissions-target-any

#### Change to ACTION_MANAGE_OVERLAY_PERMISSION intent behavior

* `ACTION_MAMAGE_OVERLAY_PERMISSION` で設定のトップ画面が表示されるようになる
  * ユーザはアプリを選択しないといけなくなる
* `package:` データが無視される

#### All Files Access

* 全てのファイルにアクセスできる `MANAGE_EXTERNAL_STORAGE` が定義される
* https://developer.android.com/preview/privacy/storage#all-files-access

#### Non-SDK interface restrictions

* Non-SDK interface への制限が更新された
* Android 11 をターゲットにしていないのであればすぐには影響されないがグレーリストの API の利用はアプリが破壊されるリスクがある
* https://developer.android.com/preview/non-sdk-11

#### File descriptor sanitizer(fdsan)

* Android 10 で導入された fdsan のデフォルトモードが変更される
  * 警告されていたものがエラーによって中断されるようになる
