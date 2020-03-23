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
