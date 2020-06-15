## Storage updates in Android 11

https://developer.android.com/preview/privacy/storage

* preview 1 では去年 Android Dev Summit で発表された内容が含まれている
* アプリでよくあるユースケースとベストプラクティスが記述されている https://developer.android.com/training/data-storage/use-cases

### Scoped storage enforcement

* targetSdkVersion がAndroid 10 以下は `requestLegacyExternalStorage` が利用可能
* targetSdkVersion を Android 11 にすると、`requestLegacyExternalStorage` は無視される

#### Maintain compatibility with Android 10

Android 10 の端末で起動されるアプリで Scoped Storage をオプトアウトする場合、`requestLegacyExternalStorage` の定義を残すことが推奨

#### Migrate data to directories that are visible when using scoped storage

targetSdkVersion が Android 10 以前で Scoped Storage に対応していない場合、Scoped Storage に対応するとデータを保存しているディレクトリにアクセスできなくなる可能性がある。
targetSdkVersion を Android 11 にする前に Scoped Storage に対応したディレクトリにマイグレートする必要がある。
たいていのケースではアプリ専用のディレクトリに移せばよい。

もしマイグレートが必要なデータがある場合、`preserveLegacyExternalStorage` を利用して、targetSdkVersion を Android 11 にした時に現状のストレージモデルを保持することができる。
これによってユーザはアプリが以前に保存していたディレクトリにアクセスすることが可能になる。

Note:大部分のアプリでは `preserveLegacyExternalStorage` は利用すべきではない。
このフラグは Scoped Storage と互換性のある場所にデータを移行し、アプリを更新してもユーザにデータへのアクセスを可能にするためのみに設計されている。
このフラグを利用すると引き続き Scoped Storage が有効にならないのでテストが困難になる。

もし `preserveLegacyExternalStorage` を使う場合はレガシーストレージモデルはアプリがアンインストールするまで継続されることを覚えておく必要がある。
`preserveLegacyExternalStorage` を設定していたも、Android 11 の端末にアプリを新たにインストールすると Scoped Storage をオプトアウトすることは不可能

#### Test scoped storage

以下のフラグを変更することで `targetSdkVersion` を変更しなくてもテストすることが可能

* `DEFAULT_SCOPED_STORAGE`
* `FORCE_ENABLE_SCOPED_STORAGE`

### Manage device storage

Android 11 では、Scoped Storage に対応した自身のアプリ専用のキャッシュファイルのみアクセスすることが可能。
もし、デバイスストレージを管理する必要がある場合は以下に従う。

* `ACTION_MANAGE_STORAGE` Intent を起動し、空き容量をチェックする
* 空き容量がない場合は、`ACTION_CLEAR_APP_CACHE` でキャッシュのクリアをユーザに促す

Caution：`ACTION_CLEAR_APP_CACHE` はバッテリー寿命に影響を与える可能性があり、また多数のファイルが削除される可能性がある

### App-specific directory on external storage

Android 11 では、アプリは外部ストレージ上にアプリ専用のディレクトリを作成することはできない。
システムが提供するアプリ専用のディレクトリにアクセスするためには `getExternalFilesDirs()` を利用する。

### Media file access

#### Perform batch operations

* Android 11 では `MediaStore` にいくつかの API が追加される
  * `createWriteRequest`
    * ユーザに指定した media ファイルのグループの書き込み権限をリクエストする
  * `createFavoriteRequest`
    * 指定した media ファイルにお気に入りをつける
    * 読み込み権限が必要
  * `createTrashRequest`
    * 指定した media ファイルをデバイスのゴミ箱への移動をリクエストする
      * デフォルト7日間で完全に削除される
  * `createDeleteRequest`
    * 指定した media ファイルの削除をリクエストする
      * ゴミ箱への移動はされない
* これらの API が呼ばれたあとは `PendingIntent` が生成され、`PendingIntent` を実行することでユーザが更新、削除を同意するためのダイアログが表示される

#### Access files using direct file paths and native libraries

* Android 11 から `READ_EXTERNAL_STORAGE` を保持しているアプリは端末の media ファイルに直接読み取ることが可能になり、サードパーティ製のライブラリからのアクセスをよりスムーズにする

もし、`READ_EXTERNAL_STORAGE` を保持していたら、全てのメディアファイルにアクセス可能になる。

もし、`File` API かネイティブライブラリを使ってメディアファイルにアクセスするなら、Scoped Storage をオプトアウトが推奨。

Caution：パフォーマンスは若干下がるので可能な限り `MediaStore` API を使った方がよい

### Access to other app's private directories

Android 11 では他のアプリの専用ディレクトリにアクセスすることはできない。

### Document access restrictions

テストのための時間を確保するため、以下の Storage Access Framework に関する変更は `targetSdkVersion` を変更した場合にのみ影響する。

#### Access to directories

* `ACTION_OPEN_DOCUMENT_TREE` で以下のディレクトリへのアクセスができなくなる
  * 内部ストレージのルートディレクトリ
  * SD card ボリュームのルートディレクトリ
  * `Downloads` ディレクトリ
  
#### Access to files

* `ACTION_OPEN_DOCUMENT_TREE` や `ACTION_OPEN_DOCUMENT` で以下のファイルへのアクセスができなくなる
  * `Android/data/` とそのサブディレクトリ
  * `Android/obb/` とそのサブディレクトリ
  
#### Test the change

* 動作の変更をテストするために以下を実行する

* `ACTION_OPEN_DOCUMENT` Intent を起動し、`Android/data/`, `Android/obb/` が表示されないことを確認する
* `RESTRICT_STORAGE_ACCESS_FRAMEWORK` を有効にするか、`targetSdkVersion` を Android 11 にする
* `ACTION_OPEN_DOCUMENT_TREE` Intent を起動し、`Download` ディレクトリがグレイアウトされていることを確認する

### Permissions

#### Target any version

* `targetSdkVersion` に関係なく Android 11 で以下の動作が変わる
  * Storage runtime permission が `Files & Media` にリネームされる
  * scoped storage をオプトアウトせずに `READ_EXTERNAL_STORAGE` をリクエストしたときのダイアログが Android 10 と異なる
    * 写真とメディアへのアクセスというテキストになる

Note：アプリの `targetSdkVersion` が Android 11 の場合、全てのファイルへのアクセスは読み取り専用であることに注意。
このアプリで共有ストレージ内のファイルを変更する場合は別の権限が必要になる。

#### Target Android 11

* `WRITE_EXTERNAL_STORAGE` と `WRITE_MEDIA_STORAGE` でアクセスできなくなる
* Android 10 以上の端末では `MediaStore.Downloads` などに権限なしでアクセスすることができる。

### All Files Access

* ファイル管理、バックアップなどのファイルアクセスが必要なケースでは以下のように全てのファイルへアクセスすることができるようになる
  * `MANAGE_EXTERNAL_STORAGE` を定義
  * ファイルのアクセス権限を許可してもらうため端末の設定画面へユーザを誘導する
* 権限が許可されたかどうかは `Environment.isExternalStorageManager()` で取得する
* Note：センシティブなデータを共有できてしまうのでガイドラインに従う必要がある
* この権限を許可することで以下が可能になる
  * shared storage 内のファイルへの読み込み、書き込み
  * USBドライブやSDKカードのルートディレクトリへのアクセス
  * 特定のディレクトリを除く内部ディレクトリへのアクセス
    * アプリ専用のディレクトリにはアクセスできない
* `MediaStore` API か raw file paths でアクセスできる
* Storage Access Framework を使っているアプリはこれらのファイルへのアクセスはできない
