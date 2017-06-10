## Project Overview ## 
In this project, you will create an app with multiple flavors that uses multiple libraries and Google Cloud Endpoints. The finished app will consist of four modules:

1. A Java library that provides jokes
2. A Google Cloud Endpoints (GCE) project that serves those jokes
3. An Android Library containing an activity for displaying jokes
4. An Android app that fetches jokes from the GCE module and passes them to the Android Library for display

## Why this Project? ##
As Android projects grow in complexity, it becomes necessary to customize the behavior of the Gradle build tool, allowing automation of repetitive tasks. Particularly, factoring functionality into libraries and creating product flavors allow for much bigger projects with minimal added complexity.

## What I Learned? ##
You will learn the role of Gradle in building Android Apps and how to use Gradle to manage apps of increasing complexity. You'll learn to:

* Add free and paid flavors to an app, and set up your build to share code between them
* Factor reusable functionality into a Java library
* Factor reusable Android functionality into an Android library
* Configure a multi-project build to compile your libraries and app
* Use the Gradle App Engine plugin to deploy a backend
* Configure an integration test suite that runs against the local App Engine development server 

## How Do I Complete this Project? ##

### Step 0: Starting Point ###

* This is the starting point for the final project, which is provided to you in the course repository.

* It contains an activity with a banner ad and a button that purports to tell a joke, but actually just complains. 

### Step 1: Create a Java library ###

* My first task was to create a Java library that provides jokes. Created a new Gradle Java project either using the Android Studio wizard. Then introduced a project dependency between my app and the new Java Library.

* Made the button display a toast showing a joke retrieved from your Java joke telling library.

### Step 2: Create an Android Library ###

* Created an Android Library containing an Activity that will display a joke passed to it as an intent extra. Wired up project dependencies so that the button can now pass the joke from the Java Library to the Android Library.

### Step 3: Create GCE Module ###

* This next task was pretty tricky. Instead of pulling jokes directly from our Java library, i had set up a GCE development server, and pull our jokes from there.

* Introduce a project dependency between your Java library and your GCE module, and modify the GCE starter code to pull jokes from your Java library. Create an Async task to retrieve jokes. Make the button kick off a task to retrieve a joke, then launch the activity from your Android Library to display it.

### Step 4: Add Functional Tests ###

Added code to test that my Async task successfully retrieves a non-empty string.

### Step 5: Add a Paid Flavor ###

Added free and paid product flavors to my app. Removed the ad (and any dependencies) from the paid flavor.
