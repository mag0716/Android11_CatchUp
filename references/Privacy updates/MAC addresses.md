# MAC address updates in Android 11

https://developer.android.com/preview/privacy/mac-address

## MAC randomization

targetSdkVersion が Android 10 以下：SSIDごとにMACアドレスがランダム
targetSdkVersion が Android 11 以上：FQDNごとにMACアドレスがランダム

## Netlink MAC restrictions

targetSdkVersion が Android 11 以上では、特権でないアプリはデバイスのMACアドレスにアクセスできなくなる。
`getifaddrs()`, `NewworkInterface.getHardwareAddress()` に影響する。

アプリが影響するのは以下

* `NetworkInterface.getHardwareAddress` が null を返す
* `NETLINK_ROUTE` に `bind` できない
* `ip` コマンドが情報を返さない

https://developer.android.com/training/articles/user-data-ids#mac-addresses

大多数の開発者は `ConnectivityManager` を利用すべき。
