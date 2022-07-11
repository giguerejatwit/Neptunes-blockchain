import java.util.*;
import java.util.Map.Entry;

public class Transaction {
    public String id;
    public String senderAddress;
    public String recipientAddress;
    public double amount;
    public Map<String, Pair<String, Double>> inputs;
    public Map<String, Double> outputs;

    /**
     * Constructor
     * 
     * @param senderWallet     The sender wallet instance
     * @param recipientAddress The address of the recipient
     * @param amoudnt          The amount to be transferred
     */
    public Transaction(Wallet senderWallet, String recipientAddress, double amount) {
        this.id = UUID.randomUUID().toString();
        this.senderAddress = senderWallet.address;
        this.recipientAddress = recipientAddress;
        this.amount = amount;
        this.inputs = createInputs(senderWallet);
        this.outputs = createOutputs(senderWallet, recipientAddress, amount);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("id: %s, From: %s, To: %s, Amount: %f",
                this.id,
                this.senderAddress,
                this.recipientAddress,
                this.amount);
    }

    /**
     * Creates transaction inputs
     * 
     * 
     * @param senderWallet The sender wallet instance
     * @return A mapping of transaction id and a pair of a recipient and amount
     *         transferred
     * 
     *         Hints:
     *         First, need to look into sender wallet UTXOs and add some UTXOs so
     *         that their total amount
     *         can cover the amount to be transferred
     *         Then, make sure to remove those UTXOs that are added to the
     *         transaction inputs from the sender wallet UTXOs
     */
    private Map<String, Pair<String, Double>> createInputs(Wallet senderWallet) {

        Map<String, Pair<String, Double>> mp = new HashMap<>();
        try {
            double sum = 0.00;
            // traverse UTXOs
            for (Map.Entry<String, Pair<String, Double>> entry : senderWallet.UTXOs.entrySet()) {
                // add UTXO until we have enough for the transaction
                if (sum < this.amount) {
                    // put the entry
                    mp.put(entry.getKey(), new Pair<String, Double>(entry.getValue().key, entry.getValue().value));
                    sum += entry.getValue().value;

                }
            }

        } catch (NoSuchElementException e) {
            System.out.print(e);
        }
        return mp;
    }

    /**
     * Creates transaction outputs
     * 
     * @param senderWallet     The sender wallet instance
     * @param recipientAddress The recipient address
     * @param amount           The amount to be transferred
     * @return A map of recipients and their corresponding amount to be transferred
     * 
     *         Hints:
     *         Since there are no transaction fees, outputs should be the
     *         followings:
     *         1. Whatever amount sender transfers to recipient
     *         2. The leftover from transaction inputs amount that goes back to the
     *         sender
     */
    private Map<String, Double> createOutputs(Wallet senderWallet, String recipientAddress, double amount) {
        // Write your code her
        double inputAmount = getInputsAmount(this.inputs);

        Map<String, Double> mp = new HashMap<String, Double>();
        if (inputAmount > amount) {
            inputAmount -= amount;

            mp.put(recipientAddress, amount);
        }

        mp.put(this.senderAddress, inputAmount);

        return mp;
    }

    /**
     * Gets the total inputs amount
     * 
     * @param inputs The transaction inputs
     * @return The total amount
     * 
     *         Hints:
     *         Need to calculate the total amount of all transaction inputs
     * 
     */
    private double getInputsAmount(Map<String, Pair<String, Double>> inputs) {

        Double total = 0.00;
        Iterator<Entry<String, Pair<String, Double>>> it = inputs.entrySet().iterator();
        while (it.hasNext() && total < this.amount) {
            Pair<String, Double> pair = it.next().getValue();

            if (total < this.amount) {
                total += pair.value;
            }
        }
        return total;
    }
}