# WeatherVote

A plugin that voting between online players and change overworld weather based on vote!


## Installation
for clone and build library in your system type :
```bash
git clone https://github.com/mtalaeii/WeatherVote.git && cd WeatherVote && gradlew build

```
Then pick up .jar file from build/libs and put it in your server plugins directory!

## Configuration
```yaml
wv:
  vote-time   : 20

```
This is default config you can modify time of votes

## Usage

```
/voting [clear|storm|rain|day|night|yes|no]
```

## TODO

- [x] Limit server online players more than 1 player for starting votes
- [x] Change Tab completer to only show yes/no if vote in process
- [x] Automate build and release using CI/CD
- [ ] Add support for admin command to control/cancel in process votes
- [ ] Add support for multiple minecraft server