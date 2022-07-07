/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import java.util.ArrayList;
import java.util.List;


import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

@Contract(
        name = "basic",
        info = @Info(
                title = "Asset Transfer",
                description = "The hyperlegendary asset transfer",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "a.transfer@example.com",
                        name = "Adrian Transfer",
                        url = "https://hyperledger.example.com")))
@Default
public final class AssetTransfer implements ContractInterface {

    private final Genson genson = new Genson();

    private enum AssetTransferErrors {
        ASSET_NOT_FOUND,
        ASSET_ALREADY_EXISTS
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void InitLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        AddLog(ctx, "1", "Ryo", 100, "coding");
        AddLog(ctx, "2", "Tsumugi", 10, "coding");
        AddLog(ctx, "3", "Amane", 200, "debug");
        AddLog(ctx, "4", "Amane", 120, "test");

    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record AddLog(final Context ctx, final String recordID, final String recordusername, final int time, final String description) {
        ChaincodeStub stub = ctx.getStub();

        if (RecordExists(ctx, recordID)) {
            String errorMessage = String.format("Record %s already exists", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Record record = new Record(recordID, recordusername, time, description);
        System.out.println(record.toString());

        String sortedJson = genson.serialize(record);
        stub.putStringState(recordID, sortedJson);

        return record;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record AddPrivateLog(final Context ctx, final String recordID, final String recordusername, final int time, final String description) {
        ChaincodeStub stub = ctx.getStub();

        if (RecordExists(ctx, recordID)) {
            String errorMessage = String.format("Record %s already exists", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Record record = new Record(recordID, recordusername, time, description);

        String sortedJson = genson.serialize(record);
        stub.putPrivateData("_implicit_org_Org1MSP", recordID, sortedJson);

        return record;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Record ShowLog(final Context ctx, final String recordID) {
        ChaincodeStub stub = ctx.getStub();
        String recordJSON = stub.getStringState(recordID);

        if (recordJSON == null || recordJSON.isEmpty()) {
            String errorMessage = String.format("Record %s does not exist", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Record record = genson.deserialize(recordJSON, Record.class);
        return record;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Record ShowPrivateLog(final Context ctx, final String recordID) {
        ChaincodeStub stub = ctx.getStub();
        String recordJSON = stub.getPrivateDataUTF8("_implicit_org_Org1MSP", recordID);

        if (recordJSON == null || recordJSON.isEmpty()) {
            String errorMessage = String.format("Record %s does not exist", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Record record = genson.deserialize(recordJSON, Record.class);
        return record;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record ChangeLog(final Context ctx, final String recordID, final String recordusername, final int time, final String description) {
        ChaincodeStub stub = ctx.getStub();

        if (!RecordExists(ctx, recordID)) {
            String errorMessage = String.format("Record %s does not exist", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Record newRecord = new Record(recordID, recordusername, time, description);
        String sortedJson = genson.serialize(newRecord);
        stub.putStringState(recordID, sortedJson);
        return newRecord;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void DeleteLog(final Context ctx, final String recordID) {
        ChaincodeStub stub = ctx.getStub();

        if (!RecordExists(ctx, recordID)) {
            String errorMessage = String.format("Record %s does not exist", recordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        stub.delState(recordID);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean RecordExists(final Context ctx, final String recordID) {
        ChaincodeStub stub = ctx.getStub();
        String recordJSON = stub.getStringState(recordID);

        return (recordJSON != null && !recordJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String ShowAllLog(final Context ctx, final boolean showprivate) {
        ChaincodeStub stub = ctx.getStub();

        List<Record> queryResults = new ArrayList<Record>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result: results) {
            Record record = genson.deserialize(result.getStringValue(), Record.class);
            System.out.println(record);
            queryResults.add(record);
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
}
