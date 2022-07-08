/*
 * Copyright IBM Corp. All Rights Reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

// Running TestApp:
// gradle runApp

package application.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class App {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	// helper function for getting connected to the gateway
	public static Gateway connect() throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations",
				"org1.example.com", "connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
		return builder.connect();
	}

	public static void main(String[] args) throws Exception {
		// enrolls the admin and registers the user
		try {
			EnrollAdmin.main(null);
			RegisterUser.main(null);
		} catch (Exception e) {
			System.err.println(e);
		}

		// connect to the network and invoke the smart contract
		try (Gateway gateway = connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("basic");

			byte[] result;

			System.out.println("Submit Transaction: InitLedger creates the initial set of assets on the ledger.");
			final String pid = System.getenv("CORE_PEER_LOCALMSPID");
			try {
				contract.submitTransaction("InitLedger");
			} catch (Exception e) {
				System.out.println("\n");
			}

			System.out.println("\n");
			System.out.println("Please enter process");
			while (true) {
				Scanner sc = new Scanner(System.in);
				String[] words = sc.nextLine().split(" ");
				if (Objects.equals(words[0], "q")) {
					break;
				} else if (Objects.equals(words[0], "ShowAllLog")) {
					result = contract.evaluateTransaction("ShowAllLog");
					System.out.println("result: " + new String(result));

				} else if (Objects.equals(words[0], "AddLog")) {
					contract.submitTransaction("AddLog", words[1], words[2], words[3], words[4]);
					System.out.println("Done!");
				} else if (Objects.equals(words[0], "ShowLog")) {
					result = contract.evaluateTransaction("ShowLog", words[1]);
					System.out.println("result: " + new String(result));
				} else if (Objects.equals(words[0], "ChangeLog")) {
					contract.submitTransaction("ChangeLog", words[1], words[2], words[3], words[4]);
					System.out.println("Done!");
				} else if (Objects.equals(words[0], "DeleteLog")) {
					contract.submitTransaction("DeleteLog", words[1]);
					System.out.println("Done!");
				} else if (Objects.equals(words[0], "AddPrivateLog")) {
					if (pid.equals("Org1MSP")) {
						System.out.println("Org1");
						contract.submitTransaction("AddPrivateLog", words[1], words[2], words[3], words[4]);
					} else {
						System.out.println("Org2");
						contract.submitTransaction("AddPrivateLog2", words[1], words[2], words[3], words[4]);
					}
					System.out.println("Done!");
				} else if (Objects.equals(words[0], "ShowPrivateLog")) {
					try {
						if (pid.equals("Org1MSP")) {
							System.out.println("Org1");
							result = contract.evaluateTransaction("ShowPrivateLog", words[1], pid);
							System.out.println("result: " + new String(result));
						} else {
							System.out.println("Org2");
							result = contract.evaluateTransaction("ShowPrivateLog2", words[1], pid);
							System.out.println("result: " + new String(result));
						}
					} catch (Exception e) {
						System.out.println("No data found");
					}

				}

			}
		} catch (

		Exception e) {
			System.err.println(e);
		}

	}
}
