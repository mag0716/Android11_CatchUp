## Features and APIs Overview

https://developer.android.com/preview/features

### Data access auditing

* privacy data へのアクセスについてより透明性を提供するための機能
* 予期しないデータへのアクセスを適切に識別、修正できるようになる

https://developer.android.com/preview/privacy/permissions#data-usage-callbacks

### Performant graphics debug layer injection

* デバッグ用としてGLES や Vulkan などのグラフィックレイヤーをアプリにロードできるようになる
  * パフォーマンスのオーバーヘッドはない
* 以下で有効にする

```
<application ... >
    <meta-data android:name="com.android.graphics.injectLayers.enable"
                  android:value="true" />
</application>
```

### Batch operations for media files

* デバイス間の一貫性とユーザに利便性のために、`MediaStore` にいくつかの API が追加された

https://developer.android.com/preview/privacy/storage#media-batch-operations

### Rich media in quick replies

* quick replies で画像などのコンテンツを追加できるようになる
* 対応するためには MIME types を指定する必要がある
  * `RemoteInput.Builder.setAllowDataType()`
* `RemoteInput.getDataResultsFromIntent()` でコンテンツが含まれていないかをチェックする必要がある

### Access to media files using raw file paths

* `READ_EXTERNAL_STORAGE` 権限を持つアプリは直接パス指定でファイルにアクセスできるようになる

https://developer.android.com/preview/privacy/storage#media-files-raw-paths

### Secure sharing of large datasets

* ML やメディア再生など他のアプリとサイズが大きいデータセットを共有したい場合
  * Android 10 以下では別々にダウンロードする必要があった
  * Android 11 からは shared data blobs で共有できるようになる

https://developer.android.com/preview/features/shared-datasets

### App process exit reasons

* `getHistoricalProcessExitReasons()` で最近のプロセス終了の理由を取得することができる
  * `ApplicationExitInfo` が返却されアプリ終了に関係する情報が含まれる

### Requesting and checking for low latency support

* HDMI 2.1 をサポートするディスプレイでは post-processing を OFF にすることで遅延を最小化することができる
* `Window.setPreferMinimalPostProcessing()` or `preferMinimalPostProcessing=true`
  * post-processing の ON/OFF を切り替える
* `Display.isMinimalPostProcessingSupported()`
  * minimal post-processing をサポートしているかどうかをチェックする

### Low-latency decoding in MediaCodec

* ゲームなどのリアルタイム性が必要なアプリ用に low-latency decoding が `MediaCodec` にサポートされた
  * `MediaCodecInfo.CodecCapabilities.isFeatureSupported()` に `FEATURE_LowLatency` を渡すことでサポートしているかをチェックできる
* 以下のいずれかで low-latency decoding の設定を切り替えられる
  * `MediaCodec.configure()` で `KEY_LOW_LATENCY` を設定
  * `MediaCodec.setParameters()` で `PARAMETER_KEY_LOW_LATENCY` を設定

### NDK image decoder

* NDK に 画像をデコードするための `ImageDecoder` API が提供された

https://developer.android.com/ndk/guides/image-decoder

### Resource loaders

* 動的に拡張したリソースの検索や読み込みのために `ResourcesLoader`, `ResourcesProvider` が追加された
* `ResourcesLoader`
  * アプリの `Resources` に `ResourcesProvider` を供給するためのオブジェクト
* `ResourcesProvider`
  * APKs やリソーステーブルからリソースを読み込むためのメソッドを提供するオブジェクト
* `DirectoryAssetsProvider`
  * `ResourcesProvider` と一緒にリソースとアセットの検索ができる
  * `AssetsManager#open()` を介してアセットにアクセスすることができる

### Updates to the ICU libraries

* ICU(International Components for Unicode) が更新
  * いくつかのフォーマット API が `FormattedValue` を返すようになった
  * `LocaleManager` API が builder パターンにエンハンスされた
  * Unicode 13 をサポートした

### Neural Networks API 1.3

#### New operations

* 未経験の分野なのでスキップ

#### New ML controls

* 未経験の分野なのでスキップ

### Biometric authentication updates

#### Authentication strength

* 認証強度を定義する `BiometricManager.Authenticators` が追加
  * `BIOMETRIC_STRONG`：強強度を満たすハードウェア要素が利用される
  * `BIOMETRIC_WEAK`：弱強度を満たすハードウェア要素が利用される
  * `DEVICE_CREDENTIAL`：PIN, pattern password などのスクリーンロック認証が利用される
* `setAllowedAuthenticators` でセットする
* 利用可能かどうかは `canAuthenticate` でチェックする
* ユーザに登録を促す必要があれば、`ACTION_BIOMETRIC_ENROLL` を利用する
* ユーザが認証で利用した種別(device credential or biometric) は `getAuthenticationType` で取得できる

#### Additional support for auth-per-use keys

* `setUserAuthenticationParameters` で利用毎に認証させることが可能
  * 第1引数に0を渡す
  * 第2引数に認証強度を設定可能

#### Deprecated methods

* 以下が deprecated になった
  * `setDeviceCredentialAllowed`
  * `setUserAuthenticationValidityDurationSeconds`
  * 引数なしの `canAuthenticate`

### CallScreeningService updates

* `android.Manifest.permission.READ_PHONE_STATE` を `PhoneStateListener.onDisplayInfoChanged` を通じてリクエストできるようになる
* 5G用のアイコンが提供される

### Expanded camera support in Android emulator

* エミューレータで以下の機能が追加された
  * RAW capture
  * YUV reprocessing
  * Level 3 devices
  * Logical camera support

### Mute notification sounds and vibrations during active capture

* カメラを利用中はバイブレーションや音をミュートできるようになる
  * `setCameraAudioRestriction`

### Wi-Fi Passpoint enhancements

#### Expiration date enforcement and notification

* 利用期限が追加され期限切れの証明書で自動接続されないようになる
* 利用期限になったら Notification が表示される

#### FQDN Matching

* [詳細不明] ANQP の FQDN とは別に名前付き AAA ドメインの構成が許可される

#### Self-signed private CAs

* [詳細不明] Passpoint R1 プロファイル用に自己署名CAs を受け入れるようになる

### Wi-Fi suggestion API is expanded

* Wi-Fi 管理用のアプリは切断リクエストによって自身のネットワークを管理でいるようになる
* Passpoint ネットワークを Suggestion API でユーザに提案できるようになる
* Analytics APIs がネットワーク品質に関する情報の取得を有効にする

### GNSS antenna support

```
GNSS
人工衛星(測位衛星)を利用した全世界測位システム
```

* `GnssAntennaInfo`
  * cm 単位制度の位置情報の利用が可能になる
  * `ACCESS_FINE_LOCATION` が許可された後、以下にアクセスできるようになる
    * PCO coordinates
    * PCV corrections
    * Signal gain corrections
  * 端末が利用可能かどうかは `hasGnssAntennaInfo` で調べられる

#### Privacy considerations

* GNSS antenna はデバイスモデルだけ認識できる
* `GnssAntennaInfo` は `ACCESS_FINE_LOCATION` が必要

### Chat Bubbles
