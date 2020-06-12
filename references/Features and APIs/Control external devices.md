## Control external devices

https://developer.android.com/preview/features/device-control

電源メニューからライト、サーモスタット、カメラなどの外部危機を素早く表示、操作することが可能になる。
Google Home などの複数の機器を管理する機器やサードパーティのアプリがここに危機を表示することができる。

`ControlsProviderService` を作成、定義し、機器を操作するための処理を実装する必要がある。

### User interface

以下テンプレートに沿った Widget を表示する

* Toggle
* Toggle with slider
* Range(cannot be toggled on or off)
* Stateless toggle
* Temperature panel

Widget を長押しすると詳細な操作のためにアプリに遷移する。
Widget ごとにアイコン、色はカスタマイズできるが、機器にデフォルトで用意されたものがマッチしない場合を除き、デフォルトのアイコン、色を利用するべき。

### Create the service

 Publisher：アプリ
 Subscriber：システムUI
 Subscription：PublisherがSubscriberに更新を送信できる時間

#### Declare the service

AndroidManifest.xml に `BIND_CONTROLS` 権限を含んだ状態で `Service` を定義する必要がある。
その `Service` は `intent-filter` に `ControlsProviderService` を含む必要がある。

#### Select the correct control type

一般的には以下に従って操作したい機器とどのように操作するかを決定する必要がある。

* `DeviceTypes` から操作対象の機器の種別を選択する
* ユーザに見せる名前、デバイスの位置などテキスト要素のテキストを決定する
* ユーザ操作に最適なテンプレートを選択する

上記の情報を使って `Control` を生成する

* `Control.StatelessBuilder`：`Control` の状態が不明の時
* `Control.StatefulBuilder`：`Control` の状態が明確な時

#### Create publishers for the controls

Publisher はシステムUIに `Control` が存在することを伝える。
`ControlsProviderService` を継承したクラスを作成し、以下を override する必要がある。

* `createPublisherForAllAvailable()`：アプリが操作可能な全ての `Control` の `Publisher` を生成する。`Control` の生成には `Control.StatelessBuilder()` を利用する。
* `createPublisherFor()`：渡された ID を元に `Publisher` を生成する。`Control` の生成には `Control.StatefulBuilder` を利用する。

##### Create the publisher

アプリが初めてシステムUIに `Control` を公開する時、アプリは `Control` ごとの状態を知らず、機器ごとの状態を知るためには時間がかかる可能性がある。
そこで、利用可能な `Control` がシステムUIに対してアドバタイズするために `createPublisherForAllAvailable()` を利用する。
`Control` の状態が分からないので、生成には `Control.StatelessBuilder` を利用する。

ユーザが `Control` を選択したら、選択された機器の `Control` を `createPublisherFor()` で `Control.StatefulBuilder` を利用し生成する。

#### Handle actions

`performControlAction()` はユーザが操作した際に呼び出される。
操作は送信された `ControlAction` の型で決定する。
機器に対して適切な処理を行い、UI の更新のために状態を更新する。
