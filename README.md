#Facebook Audience network *GODOT MODULE*

##Using the module

`clone` the repo to your godot source code modules file 
`godot_source/modules`

*use `scons platform=android` to build android export templates*

###Initialize the module
in your `project.godot` add
```
[android]
modules="org/godotengine/godot/FacebookAudienceNetwork"
```
##API 
```
var facebookInterstitialAd
func _ready()-> void:
	
	facebookInterstitialAd=Engine.get_singleton("FacebookAudienceNetwork")
```

###To show the interstitial
`facebookInterstitialAd.InterstitialLoader("your placement ID")`

*more functions and callbacks will be add soon*
*Tested with godot 3.1 stable*
