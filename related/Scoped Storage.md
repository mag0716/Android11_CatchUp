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
  
### [Android storage use cases and best practices](https://developer.android.com/training/data-storage/use-cases#handle-non-media-files)

ユーザがファイルをより細かく制御、整理できるように Android 10 で Scoped Storage が導入された。
Scoped Storage は外部ストレージ上のファイルの保存とアクセス方法を変更する。

#### Handle media files

##### Show image or video files from multiple folders

* `ContentResolver#query` を利用する。
  * フィルタリングするためにはパラメータを指定する。

https://developer.android.com/training/data-storage/shared/media#query-collection

##### Show images or videos from a particular folder

* `READ_EXTERNAL_STORAGE` をリクエストする
* `MediaColumns.DATA` の値を使ってメディアファイルを取得する

疑問点：`MediaColumns.DATA` は deprecated

##### Access location information from photos

* `ACCESS_MEDIA_LOCATION` をリクエストする
* `MediaStore.setRequireOriginal` を利用して `Uri` を取得
* `ContentResolver#openInputStream` 経由で `ExifInterface` を取得する

https://developer.android.com/training/data-storage/shared/media#location-info-photos

Note: Scoped Storage をオプトアウトしていても `MediaStore` で位置情報にアクセスするためには `ACCESS_MEDIA_LOCATION` は必要

##### Modify or delete multiple media files in a single operation

###### Android 11

* `MediaStore.createWriteRequest()` か `MediaStore.createTrashRequest()` で Pending Intent を作成し、その Intent を使ってユーザにファイルの編集、削除の許可をリクエストする
  * 許可した場合は、編集、削除を実行する
  * 拒否されたら何故権限が必要なのかをユーザに説明する

  複数ファイルを同時に編集、削除するためには https://developer.android.com/preview/privacy/storage#media-batch-operations

###### Android 10

* Scoped Storage をオプトアウトして、Android 9 以下と同じ処理を利用する

###### Android 9以下

* `WRIET_EXTERNAL_STORAGE` をリクエストする
* `MediaStore` API を利用して編集、削除を実施する

##### Import a single image that already exists

###### Present your own user interface

* `READ_EXTERNAL_STORAGE` をリクエストする
* `ContentResolver#query` を利用する
* 結果をカスタムUIで表示する

###### Use the system picker

* `ACTION_GET_CONTENT` を利用する
  * 対象ファイルをフィルタリングしたい場合は `setType()` か `EXTRA_MIME_TYPES` を利用する

##### Capture a single image

* `ACTION_IMAGE_CAPTURE` を利用する
  * システムは撮影した画像を `MediaStore.Images` に保存する

##### Share media files with other apps

* `ContentResolver#insert` を利用する

https://developer.android.com/training/data-storage/shared/media#add-item

##### Share media files with a specific apps

* `FileProvider` を利用する

https://developer.android.com/training/secure-file-sharing/setup-sharing

##### Access files from code or libraries that use direct file paths

###### Android 11

* `READ_EXTERNAL_STORAGE` をリクエストする
* ファイルパスを使って直接ファイルにアクセスする

###### Android 10

* Scoped Storage をオプトアウトして、Android 9 以下と同じ処理を利用する

###### Android 9 以下

* `WRITE_EXTERNAL_STORAGE` をリクエストする
* ファイルパスを使って直接ファイルにアクセスする

#### Handle non-media files

##### Open a document file

* `ACTION_OPEN_DOCUMENT` を利用する
  * 対象ファイルをフィルタリングしたい場合は `setType()` か `EXTRA_MIME_TYPES` を利用する

##### Migrate existing files from a legacy storage location

アプリ固有のディレクトリ、共有ディレクトリでない限りレガシーとして扱われる。
レガシーなディレクトリでファイルの作成、利用を行っている場合は Scoped Storage に移行することが推奨されている

###### Maintain access to the legacy storage location for data migration

データ移行のためにレガシーなディレクトリへのアクセスを維持する必要がある

####### targetSdkVersion が Android 11

* `preserveLegacyExternalStorage` を利用することで移行が可能になる
* Android 10 端末のために Scoped Storage はオプトアウトしておく

Note: `preserveLegacyExternalStorage` を利用するとアプリがアンインストールされるまでは Android 11 端末でも維持される。アプリをアンインストールして再インストールしてしまうと、Scoped Storage はオプトアウトできない

####### targetSdkVersion が Android 10

* Scoped Storage をオプトアウトする

###### Migrate app data

* `/sdcard/` ディレクトリサブディレクトリにファイルがあるかどうかをチェックする
* プライベートなファイルは `getExternalFilesDir()` で返されるディレクトリに移す
* 共有ファイルは `Downloads/` ディレクトリのアプリ専用のサブディレクトリに移す
* `/sdcard/` ディレクトリからファイルを削除する

##### Share content with other apps

* `FileProvider` を利用する

https://developer.android.com/training/secure-file-sharing/setup-sharing

* アプリ間で共有する必要がある場合は、`ContentProvider` を利用して同期させる

https://developer.android.com/guide/topics/providers/content-provider-basics

##### Cache non-media files

* サイズが小さいファイル、もしくは、センシティブな情報を含む場合は `Context#getCacheDir()` に保存する
* サイズが大きいファイル、もしくは、センシティブな情報を含まない場合は `Context#getExternalCacheDir()` に保存する

#### Temporarily opt-out of scoped storage

一時的に Scoped Storage をオプトアウトするためには

* targetSdkVersion を 28 以下にする
* targetSdkVersion を 29 の場合は、`requestLegacyExternalStorage` に `true` を指定する

Caution: targetSdkVersion を Android 11 にすると、Android 11 端末で動作する際は `requestLegacyExternalStorage` は無視されるので Scoped Storage のサポートが必須になる

targetSDkVersion が 28 以下の場合で Scoped Storage をテストする場合は、 `requestLegacyExternalStorage` に `false` を指定する。
Android 11 端末でテストする場合は、`FORCE_ENABLE_SCOPED_STORAGE` も利用可能。

https://developer.android.com/preview/privacy/storage#test-scoped-storage
