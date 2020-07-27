# Add 5G capabilities to your app

## Build for 5G

* アプリにどういう体験を取り込むべきかを考慮する必要がある
  * 自動的に速度を高速化する
  * 4K動画や高解像度のゲームアセットなどユーザ体験をレベルアップさせる
  * データ使用量の増加についてユーザに確認した上で、Wi-Fi でしか提供していないコンテンツをダウンロードできるようにする
  * 高速・低遅延でしか利用できない5Gならではの体験を提供する

## 5G functionality

* Android 11 では以下の機能が変更、改善された
  * Meteredness
  * 5G detection
  * Bandwidth estimation

### Check meteredness

* `NET_CAPABILITY_TEMPORARILY_NOT_METERED`：キャリアから提供された情報に基づいて unmetered かどうかを取得する
  * `NET_CAPABILITY_NOT_METERED` と一緒に利用される
  * 2つのフラグの違いは、`NET_CAPABILITY_TEMPORARILY_NOT_METERED` がネットワークタイプ変更なしに変更される可能性があること
  * targetSdkVersion が 11 の場合は利用できるが、Android 9 以下の端末ではこのフラグの利用によって動作は変わらない

#### Register a network callback

* `ConnectivityManager.registerDefaultnetworkCallback()` を利用して、`NetworkCapabilities` の変更を検知する
  * `onCapabilitiesChanged()`

#### Check for meteredness

* `NetowkCapabilities.hasCapability()`
* Note：ネットワークが一時的に非従量制から従量制に切り替わった場合、ネットワークが切断されることはない

#### Additional considerations

* 以下に気を付ける必要がある
  * Android 11 以上でコンパイルする必要がある
  * 非従量制として返却されていても、`onCapabilitiesChanged()` コールバックでネットワークの変更を検知するする必要がある

### 5G detection

* Android 11 から 5G ネットワークに接続しているかどうかを検知することが可能
  * Note：5G ネットワークに接続しているかどうかを検知することはできるが、従量制、接続速度、帯域幅は推測することはできない
* この API を以下の用途も含まれている
  * 5G ブランディングを表示して、5G 体験を提供していることを強調する
  * 5G ネットワーク上でのみ利用可能な 5G 体験を提供する
  * アナリティクス
* 5G ネットワークがなくても、エミュレータでテストすることが可能


#### Detect 5G

* `TelephonyManager.listen()` に `LISTEN_DISPLAY_INFO_CHANGED` を渡して呼び出す
  * `onDisplayInfoChanged()` をオーバーライドしてネットワークタイプを検知する

### Bandwidth estimation

* `NetworkCapabilities` を利用して帯域幅を推定する
* より信頼性、正確性を求めるなら、`getLinkDownstreamBnadwidthKbps()`, `getLinkUpstreamBandwidthKbps()` を利用する
* Note：帯域幅の推定値だけでは、5G を使用しているかどうかは決定できない
