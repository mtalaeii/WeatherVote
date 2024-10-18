# WeatherVote

A plugin that voting between online players and change overworld weather based on vote!


## Installation
for clone and build library in your system type :
```bash
git clone https://github.com/mtalaeii/WeatherVote.git && cd WeatherVote && gradlew build

```
Then pick up .jar file from build/libs and put it in your server plugins directory!

### Supported versions : 1.18+
### Tested versions : 1.18.2 ,1.19.4 ,1.20.1 ,1.21.1

## Configuration
```yaml
wv:
  vote-time : 60

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
- [x] Add ability for voting by click on message (yes/no)
- [x] Add support for multiple minecraft server
- [ ] Add support for admin command to control/cancel in process votes
