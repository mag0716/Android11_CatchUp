# Behavior changes: all apps

https://developer.android.com/preview/behavior-changes-all

### Privacy

https://developer.android.com/preview/privacy

### Security

#### Perform file-based encryption after an OTA restart without user credentials

OTAアップデートでの再起動後に File-Based Encryption が即座に実行され、PIN 入力などなしに File-Based Encryption に関する操作を実行することができる

Note: この動作はOTAアップデートでの再起動のみなので Direct Boot はサポートする必要がある

#### SSL sockets use Conscrypt SSL engine by default

`SSLSocket` の実装が `Conscrypt` ベースになる

#### Scudo Hardened Allocator

カバーしていない領域なのでスキップ

### Camera

#### Support for concurrent use of more than one camera

1度に同時に複数のカメラを使うための API が追加

`getConcurrentCameraIds`：同時に利用できるカメラのセット
`isConcurrentSessionConfigurationSupported`：カメラの同時利用をサポートしているかどうか

### Connectivity

#### Open Mobile API changes

カバーしていない領域なのでスキップ

### Performance and debugging

#### JobScheduler API call limits debugging

* パフォーマスの問題の特定のためにデバッグ機能が提供される
* `debuggable` が `true` なアプリで有効になる
* 制限を超えた場合は `RESULT_FAILURE` が返される

#### File descriptor sanitizer(fdsan)

* Android 10 で導入された fdsan のデフォルトモードが変更される
  * 警告されていたものがエラーによって中断されるようになる

### Accessibility

#### Declare accessibility button usage in metadata file

* `AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON` を利用しても無視されるようになった
* `flagRequestAccessibilityButton` に metadata file を指定する必要がある

### App compatibility

#### Non-SDK interface restrictions

* Non-SDK interface への制限が更新された
* Android 11 をターゲットにしていないのであればすぐには影響されないがグレーリストの API の利用はアプリが破壊されるリスクがある
* https://developer.android.com/preview/non-sdk-11