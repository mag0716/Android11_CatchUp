## Storage updates in Android 11

https://developer.android.com/preview/privacy/storage

* preview 1 では去年 Android Dev Summit で発表された内容が含まれている

### Scoped storage enforcement

* targetSdkVersion がAndroid 10 以下は `requestLegacyExternalStorage` が利用可能

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

#### Access files using raw paths

* Android 11 から `READ_EXTERNAL_STORAGE` を保持しているアプリは端末の media ファイルに直接読み取ることが可能になり、サードパーティ製のライブラリからのアクセスをよりスムーズにする

Caution：パフォーマンスは若干下がるが可能な限り `MediaStore` API を使った方がよい

#### Test raw file path access

* テストするためには System -> Developer options > Feature flags の `setting_fuse` を `true` に変更し端末の再起動が必要

### File and directory access restrictions

* 動作確認の時間を与えるために、Storage Access Framework への動作の変更は targetSdkVersion を Android 11 にしたアプリのみに影響する

#### Access to directories

* `ACTION_OPEN_DOCUMENT_TREE` で以下のディレクトリへのアクセスができなくなる
  * `Downloads` のルートディレクトリ
  * SD card ボリュームのルートディレクトリ

#### Access to files

* `ACTION_OPEN_DOCUMENT_TREE` や `ACTION_OPEN_DOCUMENT` で以下のファイルへのアクセスができなくなる
  * `Android/data/` とそのサブディレクトリ
  * `Android/obb/` とそのサブディレクトリ

#### Test the change

* `requestLegacyExternalStorage` を `false` にすることで以下の動作が変わる
  * `ACTION_OPEN_DOCUMENT_TREE` で `Downloads` ディレクトリがグレーアウトされる
  * `ACTION_OPEN_DOCUMENT` で `Android/data/`, `Android/obb/` が表示されなくなる

### Permissions

#### Target any version

* targetSdkVersion に関係なく Android 11 で以下の動作が変わる
  * Storage runtime permission が `Files & Media` にリネームされる
  * scoped storage をオプトアウトせずに `READ_EXTERNAL_STORAGE` をリクエストしたときのダイアログが Android 10 と異なる

#### Target Android 11

* `WRITE_EXTERNAL_STORAGE` と `WRITE_MEDIA_STORAGE` でアクセスできなくなる

### All Files Access

* ファイル管理、バックアップなどのファイルアクセスが必要なケースでは以下のように全てのファイルへアクセスすることができるようになる
  * `MANAGE_EXTERNAL_STORAGE` を定義
  * ファイルのアクセス権限を許可してもらうため端末の設定画面へユーザを誘導する
* この権限を許可することで以下が可能になる
  * shared storage 内のファイルへの読み込み、書き込み
  * `MediaStore.Files` のコンテンツへのアクセス
* `MediaStore` API か raw file paths でアクセスできる
* Storage Access Framework を使っているアプリはこれらのファイルへのアクセスはできない
* センシティブなデータを共有できてしまうのでガイドラインに従う必要がある
