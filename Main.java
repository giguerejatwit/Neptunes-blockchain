class Main {
	// Test program
	public static void main(String[] args) {
		// Create a blockchain
		Blockchain myBlockchain = new Blockchain();

		// Create wallets
		Wallet walletMe = new Wallet(myBlockchain, "Me", 0);
		Wallet walletBill = new Wallet(myBlockchain, "Bill", 1000);
		Wallet walletJeff = new Wallet(myBlockchain, "Jeff", 2000);
		Wallet walletElon = new Wallet(myBlockchain, "Elon", 500);

		// Make some transactions
		// Uncle Bill sent me 12 BTC
		Transaction transaction1 = walletBill.createTransaction(walletMe.address, 12);
		myBlockchain.addBlock(transaction1);
		printTransaction(transaction1, 1);
		// Print out blockchain after transaction 1
		printWallet(walletMe, "My");
		// myBlockchain.print();
		// printWallet(walletBill, "Billy");

		// Jeff sent me 5 BTC
		Transaction transaction2 = walletJeff.createTransaction(walletMe.address, 5);
		myBlockchain.addBlock(transaction2);
		printTransaction(transaction2, 2);

		// Check my current wallet balance and UTXOs
		printWallet(walletMe, "My");
		// Print out blockchain after transaction 2
		// myBlockchain.print();

		// Send Elon 10 BTC to buy a Tesla Model S
		Transaction transaction3 = walletMe.createTransaction(walletElon.address, 10);
		myBlockchain.addBlock(transaction3);
		printTransaction(transaction3, 3);

		// Check my current wallet balance and UTXOs again
		printWallet(walletMe, "My");
		// Check Elon's wallet balance and UTXOs
		printWallet(walletElon, "Elon");

		// Print out blockchain after transaction 3
		// myBlockchain.print();

		// Send Elon 20 BTC to buy a Roaster
		Transaction transaction4 = walletMe.createTransaction(walletElon.address, 20);
		myBlockchain.addBlock(transaction4);
		printTransaction(transaction4, 4);

		// Check my current wallet balance and UTXOs again
		printWallet(walletMe, "My");
		// Check Elon's wallet balance and UTXOs
		printWallet(walletElon, "Elon");

		// Print out blockchain after transaction 4
		// myBlockchain.print();

		System.out.printf("This blockchain is %s%n", Blockchain.isValidChain(myBlockchain) ? "valid" : "invalid");
	}

	private static void printTransaction(Transaction tx, int i) {
		System.out.printf("%n============Transaction %d============%n", i);
		System.out.printf("Transaction:%n");
		if (tx == null) {
			System.out.println("  Not enough fund! Transaction denied...");
			return;
		}
		System.out.printf("  Details: %s%n", tx);
		System.out.printf("  Inputs: %s%n", tx.inputs.toString());
		System.out.printf("  Outputs: %s%n", tx.outputs.toString());
	}

	private static void printWallet(Wallet wallet, String name) {
		System.out.printf("%n%s wallet:%n", name);
		System.out.printf("  Balance: %f%n", wallet.calculateBalance());
		System.out.printf("  UTXOs: %s%n", wallet.UTXOs.toString());
	}

}