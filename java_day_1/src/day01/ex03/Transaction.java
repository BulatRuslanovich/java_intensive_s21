package day01.ex03;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private final UUID transactionId;
    private final User recipient;
    private final User sender;
    private final TransferCategory transferCategory;
    private BigDecimal amount;

    public Transaction(User recipient, User sender, TransferCategory transferCategory, BigDecimal amount) {
        this.transferCategory = transferCategory;
        this.recipient = recipient;
        this.sender = sender;
        checkAmount(amount);
        this.transactionId = UUID.randomUUID();
    }

    private void checkAmount(BigDecimal amount) {
        if (transferCategory == TransferCategory.CREDIT && (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(sender.getBalance()) > 0)) {
            this.amount = BigDecimal.ZERO;
        } else if (transferCategory == TransferCategory.DEBIT && (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(recipient.getBalance()) > 0)) {
            this.amount = BigDecimal.ZERO;
        } else {
            this.amount = amount;
            processTransaction();
        }
    }

    private void processTransaction() {
        if (transferCategory == TransferCategory.CREDIT) {
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));
        } else if (transferCategory == TransferCategory.DEBIT) {
            sender.setBalance(sender.getBalance().add(amount));
            recipient.setBalance(recipient.getBalance().subtract(amount));
        }
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(recipient, that.recipient) && Objects.equals(sender, that.sender) && transferCategory == that.transferCategory && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, recipient, sender, transferCategory, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", transferCategory=" + transferCategory +
                ", amount=" + amount +
                '}';
    }
}


