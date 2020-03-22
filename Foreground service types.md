## Foreground service types in Android 11

https://developer.android.com/preview/privacy/foreground-service-types

* Foreground Service でカメラやマイクにアクセスする場合は、`foregroundServiceType` の指定が必要になる

### Example using location and camera

```
<manifest>
    ...
    <service ... android:foregroundServiceType="location|camera" />
</manifest>
```

### Example using location, camera, and microphone

```
<manifest>
    ...
    <service ...
        android:foregroundServiceType="location|camera|microphone" />
</manifest>
```
