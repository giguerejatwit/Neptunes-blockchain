/* !!!DO NOT MODIFY CODE IN THIS FILE!!! */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

public class Block {
    public String hash;
    public String prevHash;
    public long timestamp;
    public Transaction data;
    public long nonce;

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
    public Block(String hash, String prevHash, Transaction data, long timestamp, long nonce) {
        this.hash = hash;
        this.prevHash = prevHash;
        this.data = data;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }

    /**
     * Calculates hash of the current block
     * 
     * @return Hash string
     */
    public String calculateHash() {
        return calculateHash(this.prevHash, this.data.toString(), this.timestamp, this.nonce);
    }

    /**
     * Creates genesis block with the following values
     * - hash: "0"
     * - prevHash: "0"
     * - timestamp: 0
     * - data: null (no transaction)
     * - nonce: 0
     * 
     * @return Genesis block
     */
    public static Block genesis() {
        return new Block("0", "0", null, 0, 0);
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
    public static Block mineBlock(Block lastBlock, Transaction data, int difficulty) {
        String prevHash = lastBlock.hash;
        long nonce = 0;
        long timestamp;
        String hash;
        String hashTarget = String.join("", Collections.nCopies(difficulty, "0"));

        do {
            nonce++;
            timestamp = System.currentTimeMillis();
            hash = calculateHash(prevHash, data.toString(), timestamp, nonce);
        } while (!hash.substring(0, difficulty).equals(hashTarget));

        return new Block(hash, prevHash, data, timestamp, nonce);
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

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String hex = Integer.toHexString(0xff & digest[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
