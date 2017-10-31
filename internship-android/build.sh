mvn_tools.sh build
adb install -r  target/internship-android.apk
adb logcat -s internship_log:I
