## Features and APIs Overview

https://developer.android.com/preview/features

### New experiences

#### Screens

##### Better support for waterfall display

waterfall display：画面が側面に回り込んだディスプレイ

* waterfall display 用の API が提供される
* `DisplayCutout.getSafeInset` では waterfall エリアをのぞいた領域が返される
* waterfall エリアにコンテンツを描画するためには以下の API を利用する
  * `DisplayCutout.getWaterfallInsets` で waterfall エリアのサイズが取得できる
  * `layoutInDisplayCutoutMode` に `LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALLWAYS` をセットすることで waterfall エリアをカットアウトとして拡張することができる
* Note
  * `LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS` をセットしない場合、ノッチや waterfall エリアをのぞいがディスプレイになる

##### Hinge angle sensor and foldables

**TODO**

#### Conversations

##### Conversation improvements

* 2者間以上のリアルタイムコミュニケーションに関する改善点がある
  * [詳細](https://developer.android.com/preview/features/conversations)

##### Chat Bubbles

* 開発者オプションの設定なしに `Bubbles` が利用可能になった
* Note：preview 後以降はパーミッションが必要になる
* Android 11 で以下が改善された
  * `BubbleMetadata.Builder.createShortcutBubble` でショートカットIDから `BubbleMetadata` が生成される
  * `Icon.createWithContentUri` か `createWithAdaptiveBitmapContentUri` でアイコンが生成される
  * `BubbleMetadata.getIntent` が deprecated になり、`BubbleMetadata.getBubbleIntent` に変わる
  * `BubbleMetadata.getIcon` が deprecated になり、`BubbleMetadata.getBubbleIcon` に変わる
  * `BubbleMetadata.Builder.setIntent` と `setIcon` が deprecated になり、`BubbleMetadata.Builder.createIntentBubble` に変わる

#### Rich media in quick replies

* quick replies で画像などのコンテンツを追加できるようになる
* 対応するためには MIME types を指定する必要がある
  * `RemoteInput.Builder.setAllowDataType()`
* `RemoteInput.getDataResultsFromIntent()` でコンテンツが含まれていないかをチェックする必要がある

#### Data sharing with content capture service

**TODO**

#### 5G visual indicators

* `android.Manifest.permission.READ_PHONE_STATE` を `PhoneStateListener.onDisplayInfoChanged` を通じてリクエストできるようになる
* 5G用のアイコンが提供される

### Privacy

https://developer.android.com/preview/privacy

### Security

#### Biometric authentication updates

##### Authentication types

* 認証強度を定義する `BiometricManager.Authenticators` が追加
  * `BIOMETRIC_STRONG`：強強度を満たすハードウェア要素が利用される
  * `BIOMETRIC_WEAK`：弱強度を満たすハードウェア要素が利用される
  * `DEVICE_CREDENTIAL`：PIN, pattern password などのスクリーンロック認証が利用される
* `setAllowedAuthenticators` でセットする
* 利用可能かどうかは `canAuthenticate` でチェックする
* ユーザに登録を促す必要があれば、`ACTION_BIOMETRIC_ENROLL` を利用する
* ユーザが認証で利用した種別(device credential or biometric) は `getAuthenticationType` で取得できる

##### Additional support for auth-per-use keys

* `setUserAuthenticationParameters` で利用毎に認証させることが可能
  * 第1引数に0を渡す
  * 第2引数に認証強度を設定可能

##### Deprecated method

* 以下が deprecated になった
  * `setDeviceCredentialAllowed`
  * `setUserAuthenticationValidityDurationSeconds`
  * 引数なしの `canAuthenticate`

#### Secure sharing of large datasets

* ML やメディア再生など他のアプリとサイズが大きいデータセットを共有したい場合
  * Android 10 以下では別々にダウンロードする必要があった
  * Android 11 からは shared data blobs で共有できるようになる

https://developer.android.com/preview/features/shared-datasets

### Performance and quality

#### Wireless debugging

**TODO**

#### ADB Incremental APK installation

* バックグラウンドでストリーミングしながらアプリを起動する分だけのデータをインストールすることが可能
* `adb install --incremental`
* v4 signature file での署名が必須

#### Error detection using the native memory allocation

**TODO**

#### Neural Networks API 1.3

##### New operations

* 未経験の分野なのでスキップ

##### New ML controls

* 未経験の分野なのでスキップ

##### NDK Thermal API

* 未経験の分野なのでスキップ

#### Text and input

##### Improved IME transitions

**TODO**

##### Controlling the IME animation

**TODO**

##### Updates to the ICU libraries

* ICU(International Components for Unicode) が更新
  * いくつかのフォーマット API が `FormattedValue` を返すようになった
  * `LocaleManager` API が builder パターンにエンハンスされた
  * Unicode 13 をサポートした

#### Media

##### Allocating MediaCodec buffers

**TODO**
* New classes:
* New methos:

##### Low-latency decoding in MediaCodec

* ゲームなどのリアルタイム性が必要なアプリ用に low-latency decoding が `MediaCodec` にサポートされた
  * `MediaCodecInfo.CodecCapabilities.isFeatureSupported()` に `FEATURE_LowLatency` を渡すことでサポートしているかをチェックできる
* 以下のいずれかで low-latency decoding の設定を切り替えられる
  * `MediaCodec.configure()` で `KEY_LOW_LATENCY` を設定
  * `MediaCodec.setParameters()` で `PARAMETER_KEY_LOW_LATENCY` を設定

##### OpenSL ES is deprecated

* OPENSL ES が deprecated になり、Oboe を代わりに利用する必要がある
* プラットフォームは既存アプリでサポートは続けるが、ビルド時に警告が表示されるようになる

##### New AAudio function AAudioStream_release()

* `AAudioStream_close()` はオーディオストリームのリリースとクローズを同時に行う
  * 別プロセスでアクセスされるとプロセスがクラッシュする危険性がある
* オーディオストリームのリリースだけ行う `AAudioStream_release()` が追加される

##### MediaParser API

* `MediaExtractor` よりもフレキシブルな `MediaParser` が追加

#### Connectivity

##### Wi-Fi Passpoint enhancements

###### Expiration date enforcement and notification

* 利用期限が追加され期限切れの証明書で自動接続されないようになる
* 利用期限になったら Notification が表示される

###### FQDN Matching

* [詳細不明] ANQP の FQDN とは別に名前付き AAA ドメインの構成が許可される

###### Self-signed private CAs

* [詳細不明] Passpoint R1 プロファイル用に自己署名CAs を受け入れるようになる

###### Allow multiple profiles with identical FQDN

**TODO**

###### Allow to install profiles without a Root CA certificate

**TODO**

###### Improved Home and Roaming provider matching

**TODO**

##### Wi-Fi suggestion API is expanded

* Wi-Fi 管理用のアプリは切断リクエストによって自身のネットワークを管理でいるようになる
* Passpoint ネットワークを Suggestion API でユーザに提案できるようになる
* Analytics APIs がネットワーク品質に関する情報の取得を有効にする

##### CallScreeningService updates

**TODO**

##### GNSS antenna support

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

##### Privacy considerations

* GNSS antenna はデバイスモデルだけ認識できる
* `GnssAntennaInfo` は `ACCESS_FINE_LOCATION` が必要

#### Graphics

##### NDK image decoder

* NDK に 画像をデコードするための `ImageDecoder` API が提供された

https://developer.android.com/ndk/guides/image-decoder

##### Frame rate API

* 意図した Frame rate をシステムに通知する API が提供される
  * 主な目的は全てのディスプレイのリフレッシュレートをサポートによる利点の享受

##### Requesting and checking for low latency support

* HDMI 2.1 をサポートするディスプレイでは post-processing を OFF にすることで遅延を最小化することができる
* `Window.setPreferMinimalPostProcessing()` or `preferMinimalPostProcessing=true`
  * post-processing の ON/OFF を切り替える
* `Display.isMinimalPostProcessingSupported()`
  * minimal post-processing をサポートしているかどうかをチェックする

##### Performant graphics debug layer injection

* デバッグ用としてGLES や Vulkan などのグラフィックレイヤーをアプリにロードできるようになる
  * パフォーマンスのオーバーヘッドはない
* 以下で有効にする

```
<application ... >
    <meta-data android:name="com.android.graphics.injectLayers.enable"
                  android:value="true" />
</application>
```

##### ANGLE for OpenGL ES

* 未経験の分野なのでスキップ

#### Image and camera

##### Mute notification sounds and vibrations during active capture

* カメラを利用中はバイブレーションや音をミュートできるようになる
  * `setCameraAudioRestriction`

##### Expanded camera support in Android emulator

* エミューレータで以下の機能が追加された
  * RAW capture
  * YUV reprocessing
  * Level 3 devices
  * Logical camera support

##### Better support for HEIF images with multipe frames

HEIF：高画質のまま軽量化した写真の保存形式

* `ImageDecoder.decodeDrawable` に連続した画像を渡すと `AnimatedImageDrawable` が返される
* `MediaMetadataRetriver.getImageAtIndex` で個別フレームを取得することが可能

#### Accessibility

##### Updates for accessibility service developers

* HTML と画像にテキストを追加できるようになる
* `contentDescription` より UI 要素の状態を説明を処理するために `getStateDescription` を利用する
* タッチイベントやジェスチャーがバイパスするようにするために `setTouchExplorationPassthroughRegion`, `setGestureDetectionPassthroughRegion` を利用する
* `FLAG_SECURE` が無効な画面のスクショに enter や next などの IME action をリクエストできるようになる

### Additional features

#### App process exit reasons

* `getHistoricalProcessExitReasons()` で最近のプロセス終了の理由を取得することができる
  * `ApplicationExitInfo` が返却されアプリ終了に関係する情報が含まれる

#### Resource loaders

* 動的に拡張したリソースの検索や読み込みのために `ResourcesLoader`, `ResourcesProvider` が追加された
* `ResourcesLoader`
  * アプリの `Resources` に `ResourcesProvider` を供給するためのオブジェクト
* `ResourcesProvider`
  * APKs やリソーステーブルからリソースを読み込むためのメソッドを提供するオブジェクト
* `DirectoryAssetsProvider`
  * `ResourcesProvider` と一緒にリソースとアセットの検索ができる
  * `AssetsManager#open()` を介してアセットにアクセスすることができる

#### APK signature scheme v4

* `apk-name.apk.idsig` が生成される
* incremental APK installation をサポートする

#### Dynamic intent filters

* Android 10 以下では intent filter を動的に変化させる方法はなかった
* Android 11 では `android:mimeGroup` が追加され、動的に intent filter を修正することが可能になる
* Note
  * パッケージ毎に `android:mimeGroup` 文字列が定義される
  * 複数の intent filter で同じ mimeGroup 文字列を定義できる
  * 異なるパッケージでは MIME group を共有できないが、同じ文字列は利用可能

#### Autofill enhancements

**TODO**

#### Hint identifiers in AssistStructure.ViewNode

**TODO**

#### Datasets shown events

**TODO**
