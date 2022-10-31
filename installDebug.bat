call ./gradlew assembleDebug  
call ./gradlew installDebug  
call adb shell am start -n com.mygdx.game/com.sergeenko.alexey.titangym.activities.AndroidLauncherActivity
