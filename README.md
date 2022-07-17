# How to run application

## Reset Databases

`cd fabric-samples/asset-transfer-basic/application-java`\
`./reset_db.sh`
This commands can start and deploy databases.

## Build and Run application

( `cd fabric-samples/asset-transfer-basic/application-java` )\
`./rebuild.sh`

OR

## Build application

`./gradlew installDist`

## Run application

`./build/install/application-java/bin/application-java`

## Change Organization

### Change to Org1

`./switch.sh 1`

### Change to Org2

`./switch.sh 2`

## How to use database

After running application, we can control databases by following commands.

### Add data

`AddLog id name time description`

example

`AddLog 5 Amane 100 coding`

### Add private data

`AddPrivateLog id name time description`

example

`AddPrivateLog 5 Amane 100 coding`

### Delete data

`DeleteLog id`

example

`DeleteLog 5`

### Change data

`ChangeLog id name time description`

example

`ChangeLog 5 Amane 1000 meeting`

### Show data

`ShowLog id`

example

`ShowLog 5`

### Show private data

`ShowPrivateLog id`

example

`ShowLog 5`

### Show all data

`ShowAllLog`

## File Locations

### App Location

`/asset-transfer-basic/application-java/src/main/java/application/java`

### Shell Script Location

`/asset-transfer-basic/application-java/reset_db.sh`

### Asset Class Location

`/asset-transfer-basic/chaincode-java/src/test/java/org/hyperledger/fabric/samples/assettransfer/`
