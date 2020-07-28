# People and conversations

## Conversation space

* Notification の上部に会話用のエリアが表示される
  * アバターが表示され強調されたデザインとなる
  * Android 10 では、Notification タップでアプリが表示され、キャレットタップでメッセージ全文が表示される
  * Conversation 特有のアクションが提供される
    * プライオリティを設定
    * Bubble の提供
    * conversation のサイレンス設定
    * conversation の音、バイブ設定

## Conversations in Bubbles

* Android 11 では、Notification から Bubbles を開始することが可能
  * ショートカットと関連づけられた Conversation のみ Bubbles にすることができる

## Conversation Shortcuts

## Shortcuts for Conversations

* アプリはシステムに長期間生存するショートカットを提供する必要がある
  * dynamic shortcuts でも可能だが、sharing shortcuts の利用が強く推奨されている
* `ShortcutManager` の `setDynamicShortcuts()`, `addDynamicShortcuts()`, `pushDynamicShortcuts()` を利用する
  * `LocusId` をセットすることが推奨されている
    * システムが求める利用状況に応じた重要度をより正確にする
* ショートカットの削除は `removeLongLivedShortcuts()`
  * ショートカットは削除可能だが、アプリはキャッシュされたショートカットを削除すべきではない
* Note:キャッシュされたショートカットを削除することで人物固有の Conversation の Notification チャンネルが削除されると、設定画面のカテゴリ数がインクリメントされる

## Conversation Notifications

* Conversation の Notification となるには以下のケースに該当している必要がある
  * `MessagingStyle` を利用
  * (targetSdkVersion が Android 11 の場合のみ)長時間生存するショートカットを関連づける
  * Notificationチャンネル設定を通じて Conversation セクションから降格していない

## In-app conversations and LocusId

* デバイスは Conversation がユーザにとって重要かどうかを決定する
  * 決定するためには `LocusId` が必要
  * `setShortcutInfo()` を利用している場合は自動的に設定される

## Conversion space requirements for apps that do not target Android 11

* targetSdkVersion が Android 11 でない場合でも Conversation セクションに表示されるがアプリは特定の条件を満たす必要がある
* Note: targetSdkVersion が Android 11 以上の場合は、https://developer.android.com/preview/features/conversations#api-guidelines に従う必要がある

* Notification に `MessagingStyle` を利用している、長時間生存するショートカットを関連づけている場合は以下の動作となる
  * conversation style で Notification が表示される
  * Bubble が実装されていたらボタンが提供される
  * Conversation 特有の機能が提供される

## Fallback: If MessagingStyle is used but no shortcut is provided

* conversation style として Notification が表示される
* Bubble のボタンは提供されない
* Conversation 特有の機能は提供されない

## Fallback: If MessagingStyle is not used, but the app is a recognized messaging app

* Android 1 以前のスタイルで Notification が表示される
* Bubble のボタンは提供されない
* Conversation 特有の機能は提供されない

## Guidance, usage, and testing

## When should I use conversations?

* Conversations Notification と関連するショートカットはリアルタイムの会話のユーザエクスペリエンスを向上させる
  * SMS、テキストチャット、電話など

## Providing great shortcuts

* ショートカットのアイコンやアバターのために `AdaptiveIconDrawable` を利用する
* ショートカットが正しくランク付けされるように、https://developer.android.com/training/sharing/receive#get-best-ranking を参考にする

## Testing Conversation Notifications and shortcuts

* Conversation スペースに表示されるかどうか
* Notification の長押しで Conversation 特有の機能が表示されるか
* ショートカットタップで正しい画面に遷移するかどうか
* 共有ショートカットがシステムのシェアシートに表示されること
