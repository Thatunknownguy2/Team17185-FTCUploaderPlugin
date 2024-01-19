# FTCUploader Plugin

## Love coding in Android Studio or IntelliJ? But hate the weight of adb?
This IntelliJ platform plugin aims to alleviate this problem by directly uploading your code (Java files) to OnBot Java!


## ⚠️ WARNING ⚠️
I do not have a lot of experience in making IntelliJ plugins. Per MIT license, I am not responsible for **ANY** loss of code. Though I have not encountered any problems (tested on WIFI Direct). **Use this plugin at your own discretion!**

## How to Install
1. Make sure you have the **latest** version of Android Studio or IntelliJ.


**Android Studio Hedgehog+ or IntelliJ (Any Edition) 2023+ Should Work**

![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin/assets/78375824/66d264f9-ae78-435d-93e1-b4957f14109c)
![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin/assets/78375824/9e293656-edda-47ac-93e6-9787f1f19693)



   
3. Download the latest version of the plugin from the [Releases Page](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin/releases).
4. In your IntelliJ or Android Studio, go to the Welcome screen and then click on Plugins.
5. Click on the cog and then select `Install Plugin from Disk...`.

![image](https://user-images.githubusercontent.com/78375824/217407231-2c9da62a-3a55-4b84-b2d6-e82f227f1d0b.png)

5. Locate the plugin and click `Ok`.

![image](https://user-images.githubusercontent.com/78375824/217407440-04661e2c-9b80-492e-adf7-5c47ebc1c1af.png)

6. Restart your IDE.

## How to Use

### Setup
1. Ensure you are connected to your Phone's or Control Hub's WIFI access point.
2. In your Android project structure, right click on any Java file and select `FTC Uploader`.

![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/9f6b3cc6-cabb-4718-ad32-519997f6f32a)

### Uploading Code
1. In the FTC Uploader menu, select `Upload` to upload the selected Java file.
> [!NOTE]
> All files are uploaded with paths that are relative to the `TeamCode/src/main/java` folder.

2. If uploaded successfully, the Java file should be visible and buildable on OnBot Java.

![image](https://user-images.githubusercontent.com/78375824/217409943-d9c2d8b2-98f9-4cae-83b5-e6e2dd70f3f8.png)

### Building Code
a. In the FTC Uploader menu, select `Build` to build all files in OnBot Java.

b. It is also possible to select the `Upload and Build` option which will upload the selected file and build all files.

> [!NOTE]
> Like OnBot Java, an output console will appear showing the status of the build.

![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/c74b402c-a304-4f49-acee-92a6500b3db9)
![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/9ac8a4d3-24fc-44e1-8a7d-5f2441d31091)

## Configuration

### Main Configuration
The configuration for this plugin can be found in the IDE's settings under `Tools`. Here you can set the ip address of the robot controller. You can also set the timeout of builds which are in milliseconds.
> [!IMPORTANT]
> If the plugin does not work, it may be because the ip address does not match in the configuration with the ip of the robot. Importantly, the ip address will vary depending on whether a Control Hub or phone is used.

> [!NOTE]
> Timeout does not affect the actual timeout of the build process in the robot controller. It only affects how long the plugin waits for a response from the robot controller before displaying a timeout message.

![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/eae62a0b-f600-49c4-8a0c-96e9b5189c6f)

### Action Configuration (Keybindings)
It is possible to bind actions to the keyboard or mouse. This may be useful for repetitive actions such as Building. Simply double click any action to add a shortcut.

![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/e48c276c-b689-4068-b936-316f80ceeaa7)
![image](https://github.com/Thatunknownguy2/Team17185-FTCUploaderPlugin-Develop/assets/78375824/ee8f294a-7d8a-47c7-85a4-df2b63c16701)
