import java.util.*;

public class Wallet {
    public String address;
    public Blockchain blockchain;
    public Map<String, Pair<String, Double>> UTXOs;

    /**
     * Constructor
     * 
     * @param blockchain      Blockchain instance
     * @param name            The address of the wallet. Use person name for easy
     *                        debugging purpose. In practice, this should be derived
     *                        from public key
     * @param startingBalance The starting balance of the wallet
     */
    public Wallet(Blockchain blockchain, String name, double startingBalance) {
        this.address = name;
        this.blockchain = blockchain;
        this.UTXOs = new HashMap<>();
        if (startingBalance != 0) {
            this.UTXOs.put(UUID.randomUUID().toString(), new Pair<>(this.address, startingBalance));
        }
    }

    /**
     * Calculates the wallet balance
     * 
     * @return The wallet balance
     * 
     *         Hints:
     *         Need to look at each block transaction to find all UXTOs associated
     *         with this wallet address
     */
    public double calculateBalance() {
        // Write your code here

        return 0;
    }

    /**
     * Creates a transaction to send fund from this wallet to recipient wallet
     * 
     * @param recipientAddress The address of the recipient of the fund
     * @param amount           The amount to be transferred
     * @return A transaction object if the current balance > amount; null otherwise
     * 
     *         Hints:
     *         Need to check the current balance of the wallet to make sure that
     *         the wallet has enough fund to cover the amount to be transferred
     */
    public Transaction createTransaction(String recipientAddress, double amount) {
        // Write your code here

        return null;
    }
}