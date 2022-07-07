/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Record {

    @Property()
    private final String recordID;

    @Property
    private final String userNAME;

    @Property()
    private final int time;

    @Property()
    private final String description;

    public String getRecordID() {
        return recordID;
    }

    public String getuserNAME() {
        return userNAME;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Record(@JsonProperty("recordID") final String recordID, @JsonProperty("userNAME") final String userNAME,
            @JsonProperty("time") final int time, @JsonProperty("description") final String description) {
        this.recordID = recordID;
        this.userNAME = userNAME;
        this.time = time;
        this.description = description;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Record other = (Record) obj;

        return Objects.deepEquals(
                new String[] {getRecordID(), getuserNAME(), getDescription()},
                new String[] {other.getRecordID(), other.getuserNAME(), other.getDescription()})
                &&
                Objects.deepEquals(
                new int[] {getTime()},
                new int[] {other.getTime()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecordID(), getuserNAME(), getTime(), getDescription());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [recordID=" + recordID + ", userNAME="
                + userNAME + ", time=" + time + ", description=" + description + "]";
    }
}
