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

### Apps targeting Android 11

https://developer.android.com/preview/behavior-changes-11

#### Scoped storage

* Android 10 で Scoped storage が導入されたが対応時間の確保のために `requestLegacyExternalStorage` が用意されている
* Android 11 では `requestLegacyExternalStorage` が利用不可になる
* https://developer.android.com/preview/privacy/storage

#### Directory access restrictions

* Storage Access Framework を利用する場合、`ACTION_OPEN_DOCUMENT`, `ACTION_OPEN_DOCUMENT_TREE` で特定のディレクトリにアクセスできなくなる
* https://developer.android.com/preview/privacy/storage#directory-access-restrictions

#### Storage permissions

* storage に関するパーミッションの動作が変更された
* https://developer.android.com/preview/privacy/storage?hl=ja#permissions

#### MAC randomization

* Android 10 以下は SSID 毎に MACアドレスが決まる
* Android 11 以上では FQDN毎に MACアドレスが決まる

#### Netlink MAC restrictions

* 権限のないアプリでは MACアドレスのアクセスができなくなる
  * `getifaddrs()`, `NetworkInterface.getHardwareAddress()` に影響する
  * `ConnectivityManager` を利用する必要がある
