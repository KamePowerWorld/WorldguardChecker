# WorldguardChecker

ワールドガードの保護を持っているかチェックし、持っていない場合はログイン時にメッセージを表示してテレポートさせるプラグイン

- このプラグインはWorldGuardが必要です
- テストバージョン: Minecraft 1.19.2, WorldGuard 7.1.0

## 設定

config.yml

```yaml
teleport_location: # ← テレポートさせる場所
  ==: org.bukkit.Location
  world: world
  x: 0.0
  y: 0.0
  z: 0.0
  pitch: 0.0
  yaw: 0.0
teleport_message: チュートリアルに飛ばされました。 # ← テレポート時に表示するメッセージ
```