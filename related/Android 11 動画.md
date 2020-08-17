* [#Android11 ベータ版：各種ツールとフレームワーク](https://www.youtube.com/watch?v=bAdRKQNbLmQ&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=2&t=0s)
  * Database Inspector
  * Auto-snapshot on failures
  * Jetpack Compose
    * プレビューを確認するためだけに専用の Activity を自動で生成し、実機やエミュレータで起動できる
    * `@sample` アノテーションを付与すると IDE でドキュメントを確認する際にプレビューも表示される
    * alpha は今年の夏
    * 1.0 は来年
* [Androidの新機能](https://www.youtube.com/watch?v=fnkFOhA7FC4&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=4&t=0s)
* [What's new in Android Development Tools](https://www.youtube.com/watch?v=NMFGuy6TRqk&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=5&t=0s)
  * Jetpack Compose
    * プレビュー上でボタン操作などで機能の一部を試すことができる
  * Android Studio 4.1
    * IDE にエミュレータが組み込まれている
      * スナップショットを保存することができる
    * Database Inspector
      * `@Dao` を定義したクラスを使って、IDE 上から SQL を実行することができる
* [What's new in Android Jetpack](https://www.youtube.com/watch?v=R3caBPj-6Sg&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=6&t=0s)
  * WorkManager のデバッグ
    * `androidx.work.dignostics.REQUEST_DIGNOSTICS`
  * Navigation
    * Returning a Result
* [All things privacy in Android 11](https://www.youtube.com/watch?v=5w5h_UdIiqs&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=7&t=0s)
* [Storage access with Android 11](https://www.youtube.com/watch?v=RjyYCUW-9tY&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=8&t=0s)
* [What's new in Google Play](https://www.youtube.com/watch?v=cMr-b660Esw&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=9&t=0s)
  * App bundle explorer
  * Day-1 auto install
  * New Google Play Console
* [Play Commerce最新情報](https://www.youtube.com/watch?v=gnrNckXeSjQ&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=10&t=0s)
  * 定期購入しているアプリをアンインストールした場合はユーザに通知される
  * コンビニでの現金を使った定期購入が可能
  * アカウントの保持と復元が必須になる
  * 値下げが可能になる
  * Billing Library v1, 2 は 2021年に廃止される
    * 更新前のアプリの取引は継続される
* [Jetpack Compose](https://www.youtube.com/watch?v=U5BwfqBpiWU&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=11&t=0s)
  * New Generation UI Toolkit
  * `@Composable`
  * `state`
    * 自動的に適用され、UI も更新される
  * `Modifiers`
    * shape
    * border
  * `List`
    * `LiveData` を `observeAsState` で `state` に変換できる
    * Adapter を実装する必要もない
  * `ConstraintLayout`
    * 制約は `ConstraintSet` に定義する
  * `Animations`
    * `animate`
  * View inside Compose
    * Kotlin に影響を受けている
    * `AndroidView`
  * Testing
    * ID での View の取得はできない
    * `TestTag`
    * Espresso などは必要ない
* [デザインツールの最新情報](https://www.youtube.com/watch?v=ns67AAuDs4s&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=12&t=0s)
  * Android Studio 4.0
    * MotionLayout Editor
    * Layout Validation
    * Layout Inspector
  * Android Studio 4.1
    * Custom View Preview
    * ConstraintLayout Helper が Layout Editor で設定できる要因ある
    * Keyboard Shortcuts
      * 作成が簡単に
    * Drawable Gutter Picker
    * Vector Asset
      * 最新のアイコンが反映された
    * Accessibility  
      * IDE 上でチェックが可能
    * Navigation Editor
      * Deep Link サポート
    * MotionLayout Editor
      * Keyframes
      * Transform
  * Compose Tooling
    * Preview
    * Interactive Preview
* [人に注目したNortification：新しい機能と最適な使用方法（人＆コントロール）](https://www.youtube.com/watch?v=oLLUDOQxJS8&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=13&t=0s)
  * Conversations
  * Device Controls
  * MediaStyle
* [AndroidでのKotlinの現状](https://www.youtube.com/watch?v=AgPj1Q6D--c&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=14&t=0s)
  * Kotlin 1.4
    * `fun interface`
    * list での末尾の , が OK になる
  * KSP
    * kapt の変わり
    * コンパイル速度が早くなる
  * Android 11
    * 非同期処理には Coroutines が推奨
      * Jetpack も Coroutines がサポートされる
* [折りたたみ画面やその他の画面のための開発とデザイン](https://www.youtube.com/watch?v=llHMxCz2Jig&list=PLWz5rJ2EKKc9hqKx4qZWolQxy59Bt20t_&index=15&t=0s)
