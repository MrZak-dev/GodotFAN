# Facebook Audience Network *GODOT Plugin compatible with godot >= 3.2.1 new plugin system*

## Using the plugin

`clone` the repo to your `your_godot_project/android` folder or download and rename The folder to GodotFAN , after installing [Android Build Template](https://docs.godotengine.org/en/stable/getting_started/workflow/export/android_custom_build.html)
*the new Godot plugin system doesn't require compiling the source code*


### Initialize the module
in your `project.godot` add
```
[android]
modules="org/godotengine/godot/GodotFAN"
```
Or from your project settings (Android modules section)

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
