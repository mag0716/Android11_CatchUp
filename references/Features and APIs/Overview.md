## Features and APIs Overview

https://developer.android.com/preview/features

### New experiences

#### Device controls

* 接続済みの外部危機を操作を可能にする `ControlsProviderService` API が追加される
* 電源メニューに表示される

https://developer.android.com/preview/features/device-control

#### Media Controls

* メディアコントローラーの表示の仕方が変わる
  * Quick Settings の近くに表示される
  * 複数のアプリの場合はスワイプでセッションを切り替えられる

https://developer.android.com/preview/features/device-control

#### Screens

##### Better support for waterfall display

waterfall display：画面が側面に回り込んだディスプレイ

* waterfall display 用の API が提供される
* `DisplayCutout.getSafeInset` では waterfall エリアをのぞいた領域が返される
* waterfall エリアにコンテンツを描画するためには以下の API を利用する
  * `DisplayCutout.getWaterfallInsets` で waterfall エリアのサイズが取得できる
  * `layoutInDisplayCutoutMode` に `LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS` をセットすることで waterfall エリアをカットアウトとして拡張することができる
* Note
  * `LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS` をセットしない場合、ノッチや waterfall エリアをのぞいがディスプレイになる

##### Hinge angle sensor and foldables

* `TYPE_HINGE_ANGLE` を利用することで foldable デバイスの角度を取得することが可能
* foldable デバイスの折りたたみの状態は以下で取得することが可能
  * `DeviceState.getPosture()`
  * `registerDeviceStateChangeCallback()`

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

#### Data sharing with content capture service

* アプリは端末のコンテンツキャプチャサービスとデータを共有することが可能になる
  * 現在再生されている曲の名前を表示する
  * 駅や空港の近くにいるときに関連する旅行情報を表示する
* `ContentCaptureManager#shareData()`
  * システムがデータ共有のリクエストを許可すると、アプリは書き込みの可能な file descriptor を受け取る

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

* adb 経由でアプリのデプロイとデバッグがワイヤレスで可能になる
* 利用するためにはペアリングコードを使ってペアリングする必要がある
  * `adp pair ipaddr:port`
    * ペアリングコード、IPアドレス、ポートは開発者オプションで有効にすると表示される

#### ADB Incremental APK installation

* バックグラウンドでストリーミングしながらアプリを起動する分だけのデータをインストールすることが可能
* `adb install --incremental`
* v4 signature file での署名が必須

#### Error detection using the native memory allocation

* メモリ解放後の利用やヒープのオーバフローのバグを検知可能になる

https://developer.android.com/ndk/guides/gwp-asan

#### Neural Networks API 1.3

##### New operations

* 未経験の分野なのでスキップ

##### New ML controls

* 未経験の分野なのでスキップ

##### NDK Thermal API

* 未経験の分野なのでスキップ

#### Text and input

##### Improved IME transitions

* IME の表示、非表示時のアニメーションを改善する API が追加される
  * 表示：`view.getInsetsController().show(Type.ime())`
  * 非表示：`view.getInsetsController().hide(Type.ime())`
  * 表示状態の確認：`view.getRootWindowInsets().isVisibile(Type.ime())`
* アプリの View と IME の表示・非表示を同期させたい場合は、`View.setWindowInsetsAnimationCallback()`
 に `WindowInsetsAnimation.Callback` を提供する

サンプル：https://github.com/android/user-interface-samples/tree/master/WindowInsetsAnimation

###### Controlling the IME animation

* IME のアニメーションについてもコントロールが可能になる
  * `setOnApplyWindowInsetsListener()`
  * `controlWindowInsetsAnimation()`

##### Updates to the ICU libraries

* ICU(International Components for Unicode) が更新
  * いくつかのフォーマット API が `FormattedValue` を返すようになった
  * `LocaleManager` API が builder パターンにエンハンスされた
  * Unicode 13 をサポートした

#### Media

##### Allocating MediaCodec buffers

* インプットとアウプットバッファの確保時によりコントロールを可能にするために `MediaCodec` API が追加される
  * より効率的にメモリを扱うことが可能になる
* `MediaCodec.Callback` の以下のメソッドの動作が変わる
  * `onInputBufferAvailable()`
    * `MediaCodec.getInputBuffer()` と `MediaCodec.queueInputBuffer()` の代わりに `MediaCodec.getQueueRequest` を利用する必要がある
  * `onOutputBufferAvailable()`
    * `MediaCodec.getOutputBuffer` の代わりに `MediaCodec.getOutputFrame()` を利用する必要がある

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

##### Output switcher

* キャストと MediaRouter API を利用したアプリのために新しい動作が実装された
  * キャスト先の選択がシステムのメディアプレイヤーから選択可能になる
  * これによってユーザはよりシームレスにキャスト先に切り替えが可能になる
* アプリは `MediaRouter2` API で表示される選択肢のカスタマイズすることができる

#### Connectivity

##### Wi-Fi Passpoint enhancements

Passpoint はセキュアな Wi-Fi スポットへの認証と接続を自動で有効にする

###### Expiration date enforcement and notification

* 利用期限が追加され期限切れの証明書で自動接続されないようになる
* 利用期限になったら Notification が表示される

###### FQDN Matching

* [詳細不明] ANQP の FQDN とは別に名前付き AAA ドメインの構成が許可される

###### Self-signed private CAs

* [詳細不明] Passpoint R1 プロファイル用に自己署名CAs を受け入れるようになる

###### Allow multiple profiles with identical FQDN

* [詳細不明] FQDN が同じ Passpoint のプロファイルのインストールが許可される

###### Allow to install profiles without a Root CA certificate

* [詳細不明] Root CA の認証がないプロファイルが許可される

###### Improved Home and Roaming provider matching

* アドバタイズされた認証方法に関係なく `Home` と `Roaming` ネットワークをマッチする
  * [疑問点] `Home` とは？

##### Wi-Fi suggestion API is expanded

* Wi-Fi 管理用のアプリは切断リクエストによって自身のネットワークを管理でいるようになる
* Passpoint ネットワークを Suggestion API でユーザに提案できるようになる
* Analytics APIs がネットワーク品質に関する情報の取得を有効にする

##### CallScreeningService updates

```
STIR/SHAKEN
発信者電話番号の偽装を防ぐためのプロトコル
電話番号の偽装の有無を判別できる
```

* `CallScreeningService` が着信時に STIR/SHAKEN についての情報のリクエストが可能になる
  * `Call.Details` の一部として情報が含まれる
* `CallScreeningService` が `READ_CONTACTS` 権限を保持していたら、アプリは着信時、発信時に通知される

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

* 動画撮影中はバイブレーションや音をミュートできるようになる
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
* `setProcessStateSummary` で解析のための情報を保存しておくこともできる

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

* Autofill Service が改善された

##### Hint identifiers in AssistStructure.ViewNode

* 署名ハッシュの計算のために View のプロパティをベースにし、hint が適したプロパティだが端末のロケールによっては hint が異なる場合があり問題になっていた
  * その問題を解決するために `AssistStructure.ViewNode` が拡張され `getHintIdEntry()` が追加される

##### Datasets shown events

* Autofill Service の改善のために、ユーザが選択肢なかったケースを `TYPE_DATASETS_SHOWN` で保存される
  * `TYPE_DATASET_SELECTED` と組み合わせて使うことで何度も表示されないように制御することが可能
