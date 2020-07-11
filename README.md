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
```python
var facebookAds
func _ready()-> void:
	facebookAds = Engine.get_singleton("GodotFAN")
	facebookAds.FacebookAdsInit(get_instance_id(),"YOUR_INTERSTITIAL_PLACEMENT_id","YOUR_REWARDED_VIDEO_PLACEMENT_id" , "YOUR_REWARDED_VIDEO_PLACEMENT_id")
```

### Calling the interstitial and rewarded video ads

```python

facebookAds.showInterstitial() #Calls an interstitial ad

facebookAds.loadRewardedVideo() # Loads a rewardedVideo ad

facebookAds.showRewardedVideo() #Calls a rewarded video ad (remmember to call loadRewardedVideo() before showing a rewarded video)

facebookAds.loadBanner(isTop : bool) #Loads a banner AdView , it takes a bool parameter , true for banner in the TOP , false for a banner in the BOTTOM

facebook.showBanner() #show a banner ad if it is hidden , the banner ad is by visible by default

facebook.hideBanner() #hide a banner ad  , the banner ad is by visible by default 

```

## CallBacks

```python

#Interstitial Ad CallBacs

func onInterstitialReady() -> void:
	#Called When a interstitial Ad is loaded and Ready
	pass


func onInterstitialClosed() -> void:
	#Called When a interstitial Ad is closed
	pass

##Rewarded Video Ad CallBacs

func onRewardedReady() -> void:
	#Called When a Rewarded video Ad is loaded and ready
	pass

func onRewardedClosed() -> void:
	#Called When a Rewarded video Ad is closed (completed or not , this only detects the close action)
	pass

func onRewardedCompleted() -> void:
	#Called When An Rewarded video Ad is completed

	#Call Reward Function here
	pass


```

### Debugging

`adb -d logcat GodotFan:V FAN:V godot:V *:S`

*Tested with godot 3.2.2*
