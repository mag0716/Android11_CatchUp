# Behavior changes: all apps

https://developer.android.com/preview/behavior-changes-all

### Privacy

* One-time permissions：位置情報、マイク、カメラに一時的にアクセスを許可が可能になる
* Permission dialog visibility：繰り返し権限を拒否すると、「don't ask again」と同じ意味になる
* Data access auditing：プライベートなデータにアクセスしている箇所を特定できるようになる
* System alert window permissions：特定のアプリは自動的に `SYSTEM_ALERT_WINDOW` 権限が付与される。`ACTION_MANAGE_OVERLAY_PERMISSION` は常に端末の設定画面に遷移する

https://developer.android.com/preview/privacy

### Security

#### Perform file-based encryption after an OTA restart without user credentials

OTAアップデートでの再起動後に File-Based Encryption が即座に実行され、PIN 入力などなしに File-Based Encryption に関する操作を実行することができる

Note: この動作はOTAアップデートでの再起動のみなので Direct Boot はサポートする必要がある

#### SSL sockets use Conscrypt SSL engine by default

`SSLSocket` の実装が `Conscrypt` ベースになる

#### Scudo Hardened Allocator

カバーしていない領域なのでスキップ

#### App usage stats

* アプリごとの利用統計を暗号化されたストレージに保存される
  * 以下の状況で `isUserUnlocked()` が `true` を返さない限り、システムもアプリもデータにアクセスできない
    * ユーザがシステム起動後にアンロックする
    * アカウント切り替えを実施
  * `UsagesStatsManager` を利用している場合はアンロック後にアクセスするよう対応が必要

#### Emulator support for 5G

* Android 11 で追加された 5G API を使った機能をテストを可能とするために、エミュレータに 5G をエミュレートする機能が追加された

### Performance and debugging

#### JobScheduler API call limits debugging

* パフォーマスの問題の特定のためにデバッグ機能が提供される
* `debuggable` が `true` なアプリで有効になる
* 制限を超えた場合は `RESULT_FAILURE` が返される

#### File descriptor sanitizer(fdsan)

* Android 10 で導入された fdsan のデフォルトモードが変更される
  * 警告されていたものがエラーによって中断されるようになる

### Accessibility

#### Screen readers require definitions of click-based accessibility actions

以前のバージョンではフレームワークはクリックでのアクセシビリティアクションを適切に処理しなかったウィジェットにタッチイベントをディスパッチしていた。

アクセシビリティアクションを正しく定義したアプリの動作に一貫性を持たせるために Android 11 ではタッチイベントをディスパッチしなくなる。
代わりにシステムは `ACTION_CLICK`, `ACTION_LONG_CLICK` のクリックベースのアクセシビリティアクションに頼るようになる。
これはスクリーンリーダーの動作に影響する。

`OnTouchListener` に頼っているアプリはアクセシシビリティアクションを定義するために `replaceAccessibilityAction()` を実行する必要がある。

Note:以前のバージョンでもカスタムウィジェットでアクセシビリティアクションを定義する必要がある。アクセシビリティアクションの定義を簡単にするために https://developer.android.com/guide/topics/ui/accessibility/principles#system-widgets に従うことが推奨されている。

### App compatibility

#### Non-SDK interface restrictions

* Non-SDK interface への制限が更新された
* Android 11 をターゲットにしていないのであればすぐには影響されないがグレーリストの API の利用はアプリが破壊されるリスクがある
* https://developer.android.com/preview/non-sdk-11

#### Maps v1 shared library removed

* Android 10 の時点で deprecated & 機能停止となっていた v1 の Maps が完全に削除される
* Important：SDK 移行時にマニフェストファイルから `<uses-library>` から Maps v1 への参照を削除すること
