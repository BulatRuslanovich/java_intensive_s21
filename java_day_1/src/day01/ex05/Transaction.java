package day01.ex05;


import day01.ex05.enums.TransferCategory;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private final UUID transactionId;
    private final User recipient;
    private final User sender;
    private final TransferCategory transferCategory;
    private BigDecimal amount;

    public Transaction(User sender, User recipient, TransferCategory transferCategory, BigDecimal amount) {
        this.transferCategory = transferCategory;
        this.recipient = recipient;
        this.sender = sender;
        this.transactionId = UUID.randomUUID();
        checkAmount(amount);
    }

    public Transaction(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.recipient = transaction.getRecipient();
        this.sender = transaction.getSender();
        this.transferCategory = transaction.getTransferCategory() == TransferCategory.CREDIT ? TransferCategory.DEBIT : TransferCategory.CREDIT;
        this.amount = transaction.getAmount();
    }

    private void checkAmount(BigDecimal amount) {
        if (transferCategory == TransferCategory.CREDIT && (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(sender.getBalance()) > 0)) {
            this.amount = BigDecimal.ZERO;
            sender.getTransactionsList().add(this);
        } else if (transferCategory == TransferCategory.DEBIT && (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(recipient.getBalance()) > 0)) {
            this.amount = BigDecimal.ZERO;
            this.recipient.getTransactionsList().add(this);
        } else {
            this.amount = amount;
            processTransaction();
        }
    }

    private void processTransaction() {
        if (transferCategory == TransferCategory.CREDIT) {
            sender.setBalance(sender.getBalance().subtract(amount));
            sender.getTransactionsList().add(this);
            recipient.setBalance(recipient.getBalance().add(amount));
            recipient.getTransactionsList().add(new Transaction(this));
        } else if (transferCategory == TransferCategory.DEBIT) {
            sender.setBalance(sender.getBalance().add(amount));
            sender.getTransactionsList().add(new Transaction(this));
            recipient.setBalance(recipient.getBalance().subtract(amount));
            recipient.getTransactionsList().add(this);
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


