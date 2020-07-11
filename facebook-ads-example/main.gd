extends Node

"""
Facebook  ads Plugin
https://github.com/MrZak-dev/GodotFAN
"""
var banner : bool = false

func _ready():
	pass

func _on_Button_pressed():
	FacebookAds.showFacebookInterstitial()


func _on_Button2_pressed():
	FacebookAds.showFacebookRewraded()


func _on_Button3_pressed():
	if not banner : 
		FacebookAds.showfacebookBanner()
		banner = true
		$VBoxContainer/CenterContainer3/Button.text = "Hide Banner"
	else:
		FacebookAds.hideFacebookBanner()
		banner = false
		$VBoxContainer/CenterContainer3/Button.text = "Show banner Banner"
