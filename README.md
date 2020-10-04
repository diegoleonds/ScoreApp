# Score App
![](app_prints.png)

## About this project

A Android App for register basketball seasons and games.

## User functionalities

- Display information based on games of a season.

- Seasons
    - Register and delete seasons.

- Games 
    - Register, delete and edit games.

## Installers

If you want to test the app in the production mode, here is the [Android .apk installer](https://drive.google.com/file/d/1LKgdu1WDPo8eU2NVjoB92TPi4my8QP4D/view?usp=sharing).

## Getting Started

### Prerequisites

To run this project in the development mode, you'll need to have a basic environment to run a Android App, that can be found [here](https://developer.android.com/studio).
After that you can run the app in an Android Emulator, [here is a guide for configure it](https://developer.android.com/studio/run/emulator#install), or with a physical device with developer mode on, [here is a guide for it](https://developer.android.com/studio/debug/dev-options).

### Running

**Cloning the Repository**

```
$ git clone https://github.com/diegoleonds/ScoreApp.git
```

**Installing dependencies**

```
$ yarn
```

_or_

```
$ npm install
```

### Connecting the App with the Server

1 - Follow the instructions on the [mindcast-server](https://github.com/steniowagner/mindcast-server) to have the server up and running on your machine.

2 - With the server up and running, go to the [/.env.development](https://github.com/steniowagner/mindCast/blob/master/.env.development) file and edit the SERVER_URL value for the IP of your machine (you can have some issues with _localhost_ if you're running on an android physical device, but you can use localhost safely on iOS).

It should looks like this:

SERVER_URL=http://**_IP_OF_YOUR_MACHINE_**:3001/mind-cast/api/v1

*or*

SERVER_URL=http://localhost:3001/mind-cast/api/v1

### Running

With all dependencies installed and the environment properly configured, you can now run the app:

Android

```
$ react-native run-android
```

iOS

```
$ react-native run-ios
```

## Built With


