## Data access auditing

https://developer.android.com/preview/privacy/data-access-auditing

アプリとアプリの依存先がユーザのプライベートデータにアクセスする方法をより明確にするために data access auditing が追加される。
この機能を利用することで潜在的に予期しないデータアクセスを調査、修正することが可能になる
以下のイベント発生時に発火する `AppOpsManager.OnOpNotedCallback` を登録することが可能

* アプリのコードがプライベートデータにアクセスする
* アプリが依存するライブラリやSDKがプライベートデータにアクセスする

### Log access of data

* `Activity` の `onCreate` などで `AppOpsManager.OnOpNotedCallback` を登録する
* Note: foreground service やバックグラウンドタスクなどデータアクセスが複数存在する場合は、`Application` のサブクラスを用意して、`onCreate` で登録する
* `onAsyncNoted`：プライベートデータへのアクセスがアプリの API 実行中に発生しなかった場合
* `AsyncNotedOp`：`getMessage` でシステムを特定するハッシュ値などの詳細情報を取得することができる
* `onSelfNoted`：`noteOp()` に自身の UID を渡した時に発生するレアケース

### Audit data access by attribution tag

* もしアプリがいくつかの主要機能を持っていた場合は attribution tag を適用することで特定が容易になる
  * `onNoted()` で指定した `attributionTag` が取得できる

#### Create attribution tags

* `Activity#onCreate` で `createAttributionContext()` を呼び出し、取得した `Context` を利用してシステムサービスを取得するようにする

#### Include attribution tags in access logs

* `AppOpsManager.OnOpNotedCallback` のログ出力で attribution tag も出力する
* Note: attribution tag が null の場合は、通常の `Context` が利用されたということ
