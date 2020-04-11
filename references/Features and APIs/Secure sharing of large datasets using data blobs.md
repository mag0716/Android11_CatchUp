## Secure sharing of large datasets using data blobs

ML やメディア再生など他のアプリのサイズが大きいデータを利用したいケースでネットワークとディスクの余分なデータを減らすために、Android 11 で共有のデータをキャッシュすることができる

システムが shared data blobs を維持し、アプリがアクセスをコントロールする
データを与えるアプリは以下のメソッドで他のアプリのアクセスを制限する

* `allowPackageAccess`：指定したパッケージのアプリにアクセスを許可する
* `allowSameSignatureAccess`：署名ファイルが同じアプリにアクセスを許可する
* `allowPublicAccess`：全てのアプリにアクセスを許可する

### Access shared data blobs

shared data blob には `BlobHandle` を通じてアクセスする

#### Dataset available

* `BlobHandle#createWithSha256`
* `BlobStoreManager#openBlob`

#### Dataset unavailable

* `BlobStoreManager#createSession`
* `BlobStoreManager#openSession`
* `Session#openWrite`
* `Session#commit`
