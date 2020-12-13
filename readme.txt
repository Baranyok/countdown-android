Dependencies:
 - Gradle
 - Maven
 - Android SDK (Built & Tested on 30 with MIN_SDK 21)

Set ANDROID_SDK_ROOT to an appropriate value, for example:
export ANDROID_SDK_ROOT=/path/to/Android/Sdk

Make the gradle wrapper executable:
chmod +x gradlew

Building:
./gradlew build

Assembling to an .apk:
./gradlew assemble
