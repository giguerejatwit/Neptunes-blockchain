/* !!!DO NOT MODIFY CODE IN THIS FILE!!! */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchain {
	public List<Block> chain;
	public int difficulty;

	/**
	 * Constructor
	 */
	public Blockchain() {
		this.chain = new ArrayList<>();
		this.chain.add(Block.genesis());
		this.difficulty = 4;
	}

	/**
	 * Adds a new block to the blockchain
	 * 
	 * @param data Block data
	 */
	public void addBlock(Transaction data) {
		if (data == null)
			return;
		Block lastBlock = this.chain.get(this.chain.size() - 1);
		this.chain.add(Block.mineBlock(lastBlock, data, this.difficulty));
	}

	/**
	 * Prints out all blocks of a blockchain
	 */
	public void print() {
		System.out.printf("%nBlockchain%n");
		int i = -1;
		for (Block block : this.chain) {
			i++;
			System.out.printf("Block #%d%n", i);
			System.out.printf("  timestamp: %d%n", block.timestamp);
			System.out.printf("  nonce: %d%n", block.nonce);
			System.out.printf("  prevHash: %s%n", block.prevHash);
			System.out.printf("  hash: %s%n", block.hash);

			Transaction transaction = block.data;
			if (transaction == null)
				continue;
			System.out.printf("  Block transaction:%n");
			System.out.printf("    Transaction id: %s%n", transaction.id);
			System.out.printf("    From: %s%n", transaction.senderAddress);
			System.out.printf("    To: %s%n", transaction.recipientAddress);
			System.out.printf("    Amount: %s%n", transaction.amount);
		}
	}

	/**
	 * Checks whether a blockchain is valid Hints: - Genesis block must be valid -
	 * Hash of each block on the chain (except genesis block) must be valid - The
	 * current's prevHash must match the previous block's hash
	 * 
	 * @param blockchain The blockchain to check
	 * @return True if valid, false otherwise
	 */
	public static boolean isValidChain(Blockchain blockchain) {
		List<Block> chain = blockchain.chain;
		String hashTarget = String.join("", Collections.nCopies(blockchain.difficulty, "0"));

		for (int i = 1; i < chain.size(); i++) {
			Block current = chain.get(i);
			Block prev = chain.get(i - 1);

			if (!current.hash.equals(current.calculateHash())) {
				return false;
			}

			if (!prev.hash.equals(current.prevHash)) {
				return false;
			}

			if (!current.hash.substring(0, blockchain.difficulty).equals(hashTarget)) {
				return false;
			}
		}

		return true;
	}
}