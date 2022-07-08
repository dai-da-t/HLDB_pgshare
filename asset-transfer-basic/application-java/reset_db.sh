./switch.sh 1
rm -rf wallet/*.id

cd ../../test-network/
./network.sh down
./network.sh up createChannel -ca
./network.sh deployCC -ccn basic -ccp ../asset-transfer-basic/chaincode-java -ccl java

cd ../../fabric-samples/asset-transfer-basic/application-java