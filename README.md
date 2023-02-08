# FTCUploader Plugin

## Love coding in Android Studio or IntelliJ? But hate the weight of adb?
This IntelliJ platform plugin aims to alleviate this problem by directly uploading your code (Java files) to OnBot Java!


## ⚠️ WARNING ⚠️
I do not have a lot of experience in making IntelliJ plugins. Though I haven't encountered any problems (Tested on WIFI Direct), I am not responsible for loss of code. **Use this plugin at your own discretion!**

## How to Install
1. Download the latest version of the plugin from the [Releases Page](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin/releases).
2. In your IntelliJ or Android Studio, go to the Welcome screen and then click on Plugins.
3. Click on the cog and then select `Install Plugin from Disk...`.

![image](https://user-images.githubusercontent.com/78375824/217407231-2c9da62a-3a55-4b84-b2d6-e82f227f1d0b.png)

4. Locate the plugin and click `Ok`.

![image](https://user-images.githubusercontent.com/78375824/217407440-04661e2c-9b80-492e-adf7-5c47ebc1c1af.png)

5. Restart your IDE.

## How to Use

1. Ensure you are connected to your Phone's or Control Hub's WIFI access point.
2. In your Android project structure, right click on any Java file and select `FTC Uploader`.

![image](https://user-images.githubusercontent.com/78375824/217408100-fc9a82d3-b071-41fb-a639-0bf9b94f9f1e.png)

3A. Select either `OnBot Java`, which opens your browser to the OnBot Java editor, or select `Upload` to upload the selected Java file. 

3B. If uploaded successfully, the Java file should be visible on OnBot Java. 

![image](https://user-images.githubusercontent.com/78375824/217409943-d9c2d8b2-98f9-4cae-83b5-e6e2dd70f3f8.png)

4. The code can now be built by clicking on the `Build Everything` button in OnBot Java. For best results, do not open the Java file in the OnBot Java editor when uploading from your IDE as your changes might not upload. You may constantly reupload the file and immediately build as necessary.
