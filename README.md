![Word Combiner](WordCombiner.png)

# Developed by Jeroen Van De Laer

## Run application using java

### Requirements
* java 17

### Run the app
When in root folder
```
java -jar ./target/word-combiner-0.0.1-SNAPSHOT.jar
```

## Run application using docker
### Requirements
* docker
### Build the Dockerfile
```
docker build -t word-combiner .
```
### Run the app
```
docker run -it -p 8080:8080 --rm --name word-combiner word-combiner
```
## Postman
[Import this collection in postman to interact with the application.](WordCombination.postman_collection.json)

The following [input file](input.txt) can be used for testing.

## Licence
This application may only be used if the job offer is guaranteed.
