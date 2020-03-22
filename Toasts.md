## Updates to toasts in Android 11

https://developer.android.com/preview/privacy/toasts

### Custom toasts from the background are blocked

* `targetSdkVersion` が Android 11 にするとカスタム View を含む Toast をシステムがブロックするようになる
  * セキュリティと UX の維持が目的
* カスタム View を含む Toast を表示しようとすると、Toast とログが出力される

### Toast callbacks

* `Toast` の表示、非表示のイベントを `addCallack` で取得できるようになる

### Text toast API changes

* `getView()` が `null` を返すようになる
* 以下のメソッドが返す値は実際の値ではなくなる
  * `getHorizontalMargin`
  * `getVerticalMargin`
  * `getGravity`
  * `getXOffxet`
  * `getYOffset`
* 以下のメソッドは動作しなくなる
  * `setMargin`
  * `setGravity`
