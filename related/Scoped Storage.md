## Scoped Storage

### [Modern User Storage on Android](https://medium.com/androiddevelopers/modern-user-storage-on-android-e9469e8624f9)

* Scoped Storage は Android 10 で導入されたが、Android 10 時点では開発者の対応時間の確保のため対応必須ではなかった
* 以下の原則に従っている
  * Better attribution:システムはどのファイルがどのアプリに所属しているかを管理している
  * Protecting app data:アプリが外部ストレージにファイルを書き込む場合、他のアプリからは見えなくする
  * Protecting user data:ユーザがメールの添付ファイルをダウンロードする場合、これらのファイルは大部分のアプリには見せるべきではない
* ストレージへのアクセス
  * 自身が作成した外部ストレージやメディアデータには権限なしでアクセス可能
  * メディアデータへのアクセスには MediaStore API を利用する
    * file descriptors に切り替えることが困難なアプリでは file path は引き続き利用可能(https://developer.android.com/preview/privacy/storage#media-files-raw-paths)
      * パフォーマンスはよくないので可能であれば MediaStore API を使うのが推奨されている
  * それ以外のデータについては Storage Access Framework を利用する
  * 広範囲のストレージへアクセスが必要なファイルマネージャーやバックアップアプリについては `MANAGE_EXTERNAL_STORAGE` が必要になる
