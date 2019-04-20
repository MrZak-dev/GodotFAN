# Facebook Audience Network *GODOT MODULE*

## Using the module

`clone` the repo to your godot source code modules file 
`godot_source/modules`

*use `scons platform=android` to build android export templates*

### Initialize the module
in your `project.godot` add
```
[android]
modules="org/godotengine/godot/GodotFAN"
```
## API 
```
var facebookAds
func _ready()-> void:
	
	facebookAds = Engine.get_singleton("GodotFAN")
	facebookAds.FacebookAdsInit("YOUR_INTERSTITIAL_PLACEMENT_id","YOUR_REWARDED_VIDEO_PLACEMENT_id")

```

### Calling the interstitial and rewarded video ads

`facebookAds.showInterstitial()`
`facebookAds.showRewardedVideo()`

### Debugging

`adb -d logcat GodotFan:V FAN:V godot:V *:S`

*callbacks will be add soon*
*Tested with godot 3.1 stable*
