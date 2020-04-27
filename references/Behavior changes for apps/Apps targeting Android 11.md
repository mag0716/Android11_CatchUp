## Behavior changes: Apps targeting Android 11

https://developer.android.com/preview/behavior-changes-11

### Privacy

https://developer.android.com/preview/privacy

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

### Firebase

#### Firebase JobDispatcher and GCMNetworkManager

* Android 6.0 以上の端末で `JobDispatcher`, `GcmNetworkManager` が無効化される
