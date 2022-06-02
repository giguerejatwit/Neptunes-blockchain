import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Block {
	public String hash;
	public String prevHash;
	private long timestamp;
	private String data;
	private long nonce;

	/**
	 * Block constructor
	 * 
	 * @param hash      Current block hash
	 * @param prevHash  Previous block hash
	 * @param timestamp Time block is created, which is number of milliseconds since
	 *                  1/1/1970
	 * @param data      Block data
	 * @param nonce     Nonce
	 */
	public Block(String hash, String prevHash, String data, long timestamp, long nonce) {
		// Write your code her
		this.hash = hash;
		this.prevHash = prevHash;
		this.timestamp = timestamp;
		this.data = data;
		this.nonce = nonce;
	}

	/**
	 * Creates genesis block with the following values - hash: "0" - prevHash: "0" -
	 * timestamp: 0 - data: "genesis block" - nonce: 0
	 * 
	 * @return Genesis block
	 */
	public static Block genesis() {
		Block genesis = new Block("0", "0", "genesis block", 0, 0);

		return genesis;
	}

	/**
	 * Mines a block
	 * 
	 * @param lastBlock  The last block on the current blockchain
	 * @param data       The data of the new block
	 * @param difficulty Mining difficulty that determines the number of leading Os
	 *                   of the hash
	 * @return A mined block
	 */
	public static Block mineBlock(Block lastBlock, String data, int difficulty) {

		long nonce = 0;

		long timeStamp = System.currentTimeMillis();
		String prefix = new String(new char[difficulty]).replace('\0', '0');

		String hash = Block.calculateHash(lastBlock.hash, data, timeStamp, nonce);

		while (!hash.substring(0, difficulty).equals(prefix)) {
			//brute force to regen. hashes and recompare.
			nonce++;
			hash = Block.calculateHash(lastBlock.hash, data, timeStamp, nonce);
		}
		
		//create new block with mined hash.
		Block block = new Block(hash, lastBlock.hash, data, timeStamp, nonce);

		return block;
	}

	/**
	 * Calculates hash of the current block
	 * 
	 * @return Hash string
	 */
	public String calculateHash() {
		
		String calculatedHash = Block.calculateHash(prevHash, data, timestamp, nonce);

		return calculatedHash;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// DO NOT MODIFY THIS METHOD
		Map<String, String> map = new HashMap<>();
		map.put("hash", this.hash);
		map.put("prevHash", this.prevHash);
		map.put("timestamp", Long.toString(this.timestamp));
		map.put("data", this.data);
		map.put("nonce", Long.toString(this.nonce));

		return String.format("%s", map.toString());
	}

	/**
	 * Calculates hash based on SHA256 algorithm
	 * 
	 * @param prevHash  Hash of previous block
	 * @param data      Block data
	 * @param timestamp Time the block is created
	 * @param nonce     The nonce
	 * @return Hash string
	 */
	private static String calculateHash(String prevHash, String data, long timestamp, long nonce) {
		// DO NOT MODIFY THIS METHOD
		String blockStr = String.join(" ", Long.toString(timestamp), data, prevHash, Long.toString(nonce));
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(blockStr.getBytes(StandardCharsets.UTF_8));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(0xff & digest[i]);
				if (hex.length() == 1)
					hash.append('0');
				hash.append(hex);
			}
			return hash.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	
}