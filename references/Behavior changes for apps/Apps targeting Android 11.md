## Behavior changes: Apps targeting Android 11

https://developer.android.com/preview/behavior-changes-11

### Privacy

* Scoped storage enforcement：外部ストレージへのアクセスが制限される
* Permissions auto-reset：ユーザが数ヶ月アプリを利用しなかったらアプリのパーミッションが自動的にリセットされる
* Background location access：バックグラウンドでの位置情報を取得するためには端末の設定画面で許可する必要がある
* Package visibility：インストール済みのアプリ一覧を取得するとフィルタされた状態で取得される

https://developer.android.com/preview/privacy

### Compressed resource file

* `RESOURCES_ARSC_COMPRESSED`
* 圧縮された `resources.arsc` や 4byteでアライメントされていないファイルを保持しているアプリがインストールできなくなる

### Connectivity

#### Restricted read access to APN database

* `APN_READING_PERMISSION_CHANGE_ID`
* APN データベースへのアクセスに `Manifest.permission.WRITE_APN_SETTINGS` が必要になる
  * パーミッションがない場合は security exception になる

### Security

#### Heap pointer tagging

* `NATIVE_HEAP_POINTER_TAGGING`
* https://source.android.com/devices/tech/debug/tagged-pointers
* 無効化するために `allowNativeHeapPointerTagging`

#### Custom toast views are blocked

* `CHANGE_BACKGROUND_CUSTOM_TOAST_BLOCK`
* Toast のカスタマイズが deprecated になり表示されなくなる
* `SnackBar` への置き換えが推奨

#### APK Signature Scheme v2 now required

v2 の署名スキームでの署名も必須になり、v1 の署名スキームのみだと Android 11 の端末にインストール、更新ができなくなる

v2 以上の署名スキームで署名されているかどうかは Android Studio か `apksigner` で検証できる

Caution：古いバージョンの端末をサポートするためには v1 の署名スキームでの署名も引き続き行う必要がある

メモ：`apksigner verify -v xxx.apk` でどのバージョンで署名されているかがわかる

### Accessibility

#### Declare accessibility button usage in metadata file

* `REQUEST_ACCESSIBILITY_BUTTON_CHANGE`
* アクセシビリティサービスは実行時にシステムのボタンと関連付けを定義できなくなる
  * `AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON` を追加しても、フレームワークはアクセシビリティサービスにコールバックイベントを渡さない
* `res/raw/accesibilityservice.xml` に `flagRequestAccesibilityButton` を定義する

### Camera

#### Media intent actions require system default camera

* プリインストールされたカメラアプリのみが以下に反応できるようになる
  * `android.media.action.VIDEO_CAPTURE`
  * `android.media.action.IMAGE_CAPTURE`
  * `android.media.action.IMAGE_CAPTURE_SECURE`
* プリインストールされたカメラアプリが複数ある場合は選択ダイアログが表示される
* 特定のサードパーティアプリを利用したい場合は明示的 Intent で起動する必要がある

### Firebase

#### Firebase JobDispatcher and GCMNetworkManager

* Android 6.0 以上の端末で `JobDispatcher`, `GcmNetworkManager` が無効化される

### Device-to-device file transfer

* `allowBackup` を使ってもデバイス間のデータ移行を無効にすることができない
* `allowBackup` はクラウドベースのバックアップ、リストアのみに影響する
