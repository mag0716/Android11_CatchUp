## New Media Controls

メディアコントロールの表示方法が更新され、`MediaSession`, `MediaRouter2` を利用してレンダリングされる。

Android 11 ではメディアコントロールは Quick Setting 付近に表示され、メディアコントロールを表示するアプリが複数ある場合は以下の順で表示されスワイプで切り替えられる。

* 端末上で再生しているアプリ
* 外部端末やキャストを利用して再生しているアプリ
* 前回再生していたアプリ

ユーザはアプリ起動することなくメディアコントロールから操作することが可能になる。

### Supporting playback resumption

この機能を利用するためには開発者オプションから `Media resumption` を有効にする必要がある。

Quick Setting のエリアに表示するためには、`MediaSession` のトークンを使って `MediaStyle` の通知を生成する必要がある。

アイコンを表示するためには `NotificationBuilder.setSmallIcon()` を利用する。

playback resumption をサポートするためには、`MediaBrowserService` と `MediaSession` を実装する必要がある。

### MediaBrowserService implementation

デバイス起動後、システムは最近利用したメディアアプリを5つ探し、再生の再開ができるようにメディアコントロールを提供する。

システムは `MediaBrowserService` への接続を試みるので、アプリがサポートしていない場合は再生の再開はできない。

システム UI からの接続かどうかはパッケージ名と署名を使って特定できる。
[サンプル](https://github.com/android/uamp/blob/f60b902643407ba234a316abe91410da7c08a4af/common/src/main/java/com/example/android/uamp/media/PackageValidator.kt#L118)

再生の再開をサポートするためには、`MediaBrowserService` で以下を実装する必要がある。

* `onGetRoot()`, `onLoadChildren()`
* `EXTRA_RECENT` を受け取った時に最近再生したメディアを返却するべき
* タイトルとサブタイトル、アイコンがセットされた `MediaDescription` を提供する必要がある

### MediaSession implementation

システムは `MediaSession` の `MediaMetadata` から以下の情報を取得する

* `METADATA_KEY_ALUBUM_ART_URI`
* `METADATA_KEY_TITLE`
* `METADATA_KEY_ARTIST`
* `METADATA_KEY_DURATION`

play resumption をサポートするためには、`MediaSession#onPlay()` を実装する必要がある。

再生中のメディアの経過時間が `MediaSession.PlaybackState` にマップされているシークバーと一緒に表示される。

シークでの動作をサポートするためには、`PlaybackState.Builder#setActions` と `ACTION_SEEK_TO` を実装する必要がある。

プレイヤーコントロールを設定するには `Notification.Builder#setCustomActions` を使用する。
Quick Setting のエリアに表示されるメディアコントロールには `Notification.MediaStyle#setShowsActionsInCompactView` で設定したコントロールのみが表示される
