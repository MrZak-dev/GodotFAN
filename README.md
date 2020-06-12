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
or From Project Setting >> Compression > Android

## API 
```gdscript
var facebookAds
func _ready()-> void:
	facebookAds = Engine.get_singleton("GodotFAN")
	facebookAds.FacebookAdsInit(get_instance_id(),"YOUR_INTERSTITIAL_PLACEMENT_id","YOUR_REWARDED_VIDEO_PLACEMENT_id")
```

### Calling the interstitial and rewarded video ads

```gdscript

facebookAds.showInterstitial() # Calls an interstitial ad

facebookAds.showRewardedVideo()# Calls a rewarded video ad

```

## CallBacks

```gdscript

##Interstitial Ad CallBacs

func onInterstitialReady -> void:
	#Called When a interstitial Ad is loaded and Ready
pass


func onInterstitialClosed -> void:
	#Called When a interstitial Ad is closed
pass

##Rewarded Video Ad CallBacs

func onRewardedReady -> void:
	#Called When a Rewarded video Ad is loaded and ready
pass

func onRewardedClosed -> void:
	#Called When a Rewarded video Ad is closed (completed or not , this only detects the close action)
pass

func onRewardedCompleted -> void:
	#Called When An Rewarded video Ad is completed

	#Call Reward Function here
pass


```

### Debugging

`adb -d logcat GodotFan:V FAN:V godot:V *:S`

*Tested with godot 3.2.2*
