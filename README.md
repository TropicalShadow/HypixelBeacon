## HypixelBeacon

> HypixelBeacon is a paper plugin which adds beacons as currency, mined from specially placed beacons which respawn after a randomly set time.

---

## Features

- [x] Mine beacons for currency
- [x] Place beacons to mine them
- [x] Random respawn time
- [x] Configurable respawn time
- [x] PlaceholderAPI support
- [x] BossShopPro support

## Commands

- `/hypixelbeacon balance`
- `/hypixelbeacon reload`
- `/hypixelbeacon adminitem`

## API


```java
class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        HypixelBeaconAPI api = HypixelBeaconAPI.getInstance();
        IBeaconManager beaconManager = api.getBeaconManager();
        IEconomy economy = api.getEconomy();
    }
}
```

`depends: [HypixelBeacon]` or `softdepend: [HypixelBeacon]` in your `plugin.yml`

> Contact me on Discord: `RealName_123#2570`