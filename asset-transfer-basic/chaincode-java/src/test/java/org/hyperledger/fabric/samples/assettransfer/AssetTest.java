/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class AssetTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Record record = new Record("1", "A", 20, "coding");

            assertThat(record).isEqualTo(record);
        }

    //     @Test
    //     public void isSymmetric() {
    //         Asset assetA = new Asset("asset1", "Blue", 20, "Guy", 100);
    //         Asset assetB = new Asset("asset1", "Blue", 20, "Guy", 100);

    //         assertThat(assetA).isEqualTo(assetB);
    //         assertThat(assetB).isEqualTo(assetA);
    //     }

    //     @Test
    //     public void isTransitive() {
    //         Asset assetA = new Asset("asset1", "Blue", 20, "Guy", 100);
    //         Asset assetB = new Asset("asset1", "Blue", 20, "Guy", 100);
    //         Asset assetC = new Asset("asset1", "Blue", 20, "Guy", 100);

    //         assertThat(assetA).isEqualTo(assetB);
    //         assertThat(assetB).isEqualTo(assetC);
    //         assertThat(assetA).isEqualTo(assetC);
    //     }

    //     @Test
    //     public void handlesInequality() {
    //         Asset assetA = new Asset("asset1", "Blue", 20, "Guy", 100);
    //         Asset assetB = new Asset("asset2", "Red", 40, "Lady", 200);

    //         assertThat(assetA).isNotEqualTo(assetB);
    //     }

    //     @Test
    //     public void handlesOtherObjects() {
    //         Asset assetA = new Asset("asset1", "Blue", 20, "Guy", 100);
    //         String assetB = "not a asset";

    //         assertThat(assetA).isNotEqualTo(assetB);
    //     }

    //     @Test
    //     public void handlesNull() {
    //         Asset asset = new Asset("asset1", "Blue", 20, "Guy", 100);

    //         assertThat(asset).isNotEqualTo(null);
    //     }
    }
}
