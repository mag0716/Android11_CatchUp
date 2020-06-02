## Behavior changes: Apps targeting Android 11

https://developer.android.com/preview/behavior-changes-11

### Privacy

https://developer.android.com/preview/privacy

### Compressed resource file

圧縮された `resources.arsc` や 4byteでアライメントされていないファイルを保持しているアプリがインストールできなくなる

### Connectivity

#### Performant VPNS

* 29 以上でIKEv2/IPsec が適用されるになる

#### Restricted read access to APN database

* `APN_READING_PERMISSION_CHANGE_ID`
* APN データベースへのアクセスに `Manifest.permission.WRITE_APN_SETTINGS` が必要になる
  * パーミッションがない場合は security exception になる

#### Per-process network access control

* プロセス毎にネットワークアクセスの許可設定を指定できるようになる

#### Allow multiple installed Passpoint configurations with the same FQDN

* `PasspointConfiguration.getUniqueId()` で `PasspointConfiguration` のユニークIDの取得が可能になり、同じ FQDN でも複数インストール###

### Security

#### MAC randomization

* Android 10 以下は SSID 毎に MACアドレスが決まる
* Android 11 以上では FQDN毎に MACアドレスが決まる

#### Netlink MAC restrictions

* 権限のないアプリでは MACアドレスのアクセスができなくなる
  * `getifaddrs()`, `NetworkInterface.getHardwareAddress()` に影響する
  * `ConnectivityManager` を利用する必要がある

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

### Firebase

#### Firebase JobDispatcher and GCMNetworkManager

* Android 6.0 以上の端末で `JobDispatcher`, `GcmNetworkManager` が無効化される

### Implicit media capture intents and location metadata

プリインストールされたカメラアプリのみが以下の `Intent` に対応するようになる

* android.media.action.VIDEO_CAPTURE
* android.media.action.IMAGE_CAPTURE
* android.media.action.IMAGE_CAPTURE_SECURE

プリインストールされたカメラアプリが複数ある場合は選択するためのダイアログが表示される。
もし、特定のカメラアプリを使いたい場合は明示的Intent で起動させる必要がある。

位置情報のメタデータを含む場合は、`ACCESS_MEDIA_LOCATION` の定義と `ACCESS_COARSE_LOCATION`か`ACCESS_FINE_LOCATION` のリクエストが必要。
