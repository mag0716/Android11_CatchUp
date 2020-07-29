# Use cases for package visibility in Android 11

https://developer.android.com/preview/privacy/package-visibility-use-cases

## Open URL

### Open URLs in a browser or other app

`ACTION_VIEW` を指定して、`startActivity()` を呼び出すと以下のいずれかが発生する

* ブラウザアプリでのWebコンテンツの表示
* デープリンクとしてアプリを起動
* URLを開くためのアプリ選択ダイアログが表示
* `ActivityNotFoundException` が発生

このケースでは暗黙的 Intent での URL の表示となるので `<queries>` の定義は不要

#### Check if a browser is available

URLを開く前に利用可能なブラウザが存在することや特定のブラウザが既定のブラウザとなっていることを確認したいケースでは `<queries>` を指定する。

それから、`queryIntentActivities()` を呼び出すと、利用可能なブラウザアプリの一覧が取得できる。

### Open URLs in Custom Tabs

Custom Tabs でのWebコンテンツの表示は `<queries>` の定義は不要。

ただし、デバイスが Custom Tabs にサポートされているかどうかを知りたいケースや `CustomTabClient.getPackageName()` を使って特定のブラウザを選択するケースでは `<queries>` の定義が必要になる。

Note: Check if a browser is available のスニペットが定義済みだったら対応不要。

### Let non-browser apps handle URLs

Custom Tabs でURLを開ける時でさえ可能であればブラウザでないアプリを選択可能にすることが推奨されている。
これを実装するためには、`FLAG_ACTIVITY_REQUIRE_NON_BROWSER` を利用する。

以下のケースでは `ActivityNotFoundException` が発生する。

* ブラウザアプリを直接起動する
* 選択ダイアログが表示されるが、選択肢がブラウザアプリのみになる

### Avoid a disambiguation dialog

URLを開く際に選択ダイアログを表示したくない場合は、`FLAG_ACTIVITY_REQUIRE_DEFAULT` を利用する。

選択ダイアログが表示されるケースでは `ActivityNotFoundException` が発生する。

`FLAG_ACTIVITY_REQUIRE_NON_BROWSER` と一緒に利用する場合、以下のケースでは `ActivityNotFoundException` が発生する。

* ブラウザアプリを直接起動する
* 選択ダイアログが表示される

## Open a file

ファイルや添付ファイルを扱う場合、`ACTION_VIEW` を利用するのが簡単。
ファイルを開けるアプリがない場合は `ActivityNotFoundException` が発生する。

特定のファイルが開けるアプリがあるかどうかを取得する場合は、`<queries>` を定義する。

## Create a custom sharesheet

可能な限り、システムが提供する sharesheet を使うのがよい。
`<queries>` を定義し、一部のアプリのみ表示することも可能。

`queryIntentActivities()` の実装方法には変更なし。

## Show custom text selection actions

ユーザがテキストを選択した時、選択したテキストに対する可能な操作が表示される。
他のアプリを表示する場合は、`<queries>` を定義する。
`android.intent.action.PROCESS_TEXT`

## Connect to a text-to-speech engine

text-to-speech(TTS) と対話する場合は、`<queries>` を定義する。
`android.intent.action.TTS_SERVICE`

## Connect to media browser services

クライアントメディアブラウザアプリでは、`<queries>` を定義する。
`android.media.browse.MediaBrowserService`

## Show custom data rows for a contact

アプリは Contacts Provider にカスタムデータを追加することができる。
コンタクトアプリがカスタムデータを表示する場合は、以下の対応が必要

* 他のアプリから `contacts.xml` を読み込む
* カスタム MIME タイプに対応するアイコンを読み込む

コンタクトアプリの場合は `<queries>` の定義が必要
`android.accounts.AccountAuthenticator`

## Declare package visibility needs in a library

Android ライブラリを開発している場合、AARマニフェストファイルに `<queries>` を定義することが可能。
アプリのマニフェストファイルで定義した場合と同様の動作となる。

ライブラリがライブラリを実装しているアプリとやりとりしている場合、`<package android:name=PACKAGE_NAME />` を定義する。
