import java.util.*;

public class Blockchain {
	// Add blockchain properties here
	private LinkedList<Block> chain = new LinkedList<>();
	static int difficulty = 2;

	/**
	 * Constructor
	 */
	public Blockchain() {
		// Write your code here
		Block genesis = Block.genesis();

		this.chain.add(genesis);
	}

	/**
	 * Adds a new block to the blockchain
	 * 
	 * @param transaction1 Block data
	 */
	public void addBlock(Transaction transaction) {

		Block block = Block.mineBlock(chain.getLast(), transaction, difficulty);

		this.chain.add(block);
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
		// Write your code here

		if (blockchain.chain.isEmpty())
			return false;

		int i = 0;
		int j = 1;

		while (j < blockchain.chain.size()) {
			Block anchor = blockchain.chain.get(i);
			Block runner = blockchain.chain.get(j);

			if (!(runner.prevHash == anchor.hash)) {
				return false;
			}

			i++;
			j++;

		}

		return true;
	}
}