## Conversations

https://developer.android.com/preview/features/conversations

`Conversations` に関していくつか改善されている
`Conversations` とは

* リアルタイム
* 2者間以上の双方向
* 2人以上の人間

`conversation` の Notification を長押しするといくつかの選択肢が提供される

* `bubble` をサポートしていたら `bubble` が提供される
*  App Shortcut を生成しホームスクリーンに追加
* Notification のサイレント、スヌーズ
* 重要な `conversation` として扱う

Notification にはアバターアイコンが強調された `MessagingStyle` が利用される

特別な conversation を扱うために、`ShortcutManager` の `setDynamicShortcuts`, `addDynamicShortcuts` でショートカットを作成する。
このショートカットは長時間生存し、人間の情報が付加されている。
ショートカットのために `LocusId` をセットすることを推奨されている。

conversation が長時間なくなった場合、`ShortcutManager#removeLongLivedShortcuts` で削除することが可能。
関連している全ての会話が削除される。

conversation に関連する Notification を通知する場合は、`MessagingStyle` を利用し、ショートカットのリンクを含める必要がある。

ユーザの利用状況をベースにシステムが特に重要な会話として扱ったり、キャッシュしたり、顕著に表示したりする。
これらの優先度の扱いについてはアプリから操作することはできないが、`ShortcutManager.getShortcuts` に `FLAG_MATCH_CACHED` を渡すことで現在のキャッシュを取得することができる。
