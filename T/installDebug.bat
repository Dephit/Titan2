call ./gradlew assembleDebug  
call ./gradlew installDebug  
call adb shell am start -n com.mygdx.game/com.mygdx.game.AndroidLauncher
