# Ethos Sample Java CLI

This is a sample java commandline app, with deployment instructions.

## Get Code
First thing we need to do is to grab the source.

1. Open cmd / powershell/ terminal
2. Change directories to where you want the code downloaded to.
3. Clone the repository with git [(download git)](https://git-scm.com/downloads):
`git clone https://github.com/asaladino/ethos-sample-java-cli.git`

## Build Jar
To build the jar file for deployment you will need to have gradle installed
 [(download gradle)](https://gradle.org/install/).

1. Open cmd / powershell/ terminal
2. Change directories to where you checked out the code: `./ethos-sample-java-cli`
3. The run: `gradle clean build jar`

Your jar file has been generated and is located at `./build/libs/ethos-sample-java-cli-1.0.jar`

## Run Jar
This jar file can be deployed anywhere java is installed and run from the command:

```
java -jar ./build/libs/ethos-sample-java-cli-1.0.jar ./path-to-config/sample.json
```

