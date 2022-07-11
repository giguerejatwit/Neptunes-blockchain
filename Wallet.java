import java.util.*;
import java.util.Map.Entry;

public class Wallet {
    public String address;
    public Blockchain blockchain;
    public Map<String, Pair<String, Double>> UTXOs;

    /**
     * Constructor
     *
     * @param blockchain Blockchain instance
     * @param name       The address of the wallet. Use person name for easy
     *                   debugging purpose. In practice, this should be derived
     *                   from public key
     * @param star       tingBalance The starting balance of the wallet
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

        for (int i = 1; i < this.blockchain.chain.size(); i++) {
            Block block = this.blockchain.chain.get(i);
            Transaction tx = block.data;

            if (tx.outputs.containsKey(this.address)) {
                this.UTXOs.put(tx.id,
                        new Pair<String, Double>(this.address, tx.outputs.get(this.address)));
            }
            if (tx.senderAddress.equals(this.address)) {
                for (String s : tx.inputs.keySet()) {
                    if (tx.inputs.containsKey(s)) {
                        this.UTXOs.remove(s);
                    }
                }
            }
        }
        Double total = 0.00;

        for (Pair<String, Double> pair : this.UTXOs.values()) {
            total += pair.value;
        }

        return total;
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
        // check balance of this wallet
        double currentBalance = calculateBalance();
        if (currentBalance >= amount) {
            Transaction tx = new Transaction(this, recipientAddress, amount);
            return tx;
        }
        return null;
    }
}
