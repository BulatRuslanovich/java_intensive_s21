package day01.ex05;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

import static day01.ex05.enums.TransferCategory.DEBIT;

public class Menu {
    private final Scanner scanner;
    private final TransactionsService transactionsService;
    private final static String line = "---------------------------------------------------------";

    public Menu() {
        scanner = new Scanner(System.in);
        transactionsService = new TransactionsService();
    }

    public void start(boolean devMode) {
        System.out.println();

        for (; ; ) {
            selectionMenu(devMode);
            choiceMenu(getAnswer(devMode), devMode);
        }
    }

    private void choiceMenu(int answer, boolean devMode) {
        if (answer == 1) {
            addUser();
        } else if (answer == 2) {
            viewUserBalance();
        } else if (answer == 3) {
            performTransfer();
        } else if (answer == 4) {
            viewAllTransactionsOfUser();
        } else if (answer == 5) {
            if (devMode) {
                removeTransferById();
            } else {
                scanner.close();
                System.exit(0);
            }
        } else if (answer == 6) {
            if (devMode) {
                checkTransferValidity();
            } else {
                scanner.close();
                System.exit(0);
            }
        } else if (answer == 7) {
            scanner.close();
            System.exit(0);
        }
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = transactionsService.checkTransactions();

        if (transactions != null) {
            for (Transaction item : transactions) {
                User userHolder = getUserHolder(item);
                User userSender = (item.getTransferCategory() == DEBIT) ? item.getSender() : item.getRecipient();
                UUID transactionId = item.getTransactionId();
                BigDecimal amount = item.getAmount();

                assert userHolder != null;
                System.out.println(userHolder.getName() + "(id = " + userHolder.getId() +
                        ") has an unacknowledged transfer id = " + transactionId + " from " +
                        userSender.getName() + "(id = " + userSender.getId() +
                        ") for " + amount);
            }
            System.out.println(line);
            return;
        }

        System.out.println("There are no unpaired transactions");
        System.out.println(line);
    }

    private User getUserHolder(Transaction item) {
        UsersList listUsers = transactionsService.getUsersList();

        for (int i = 0; i < listUsers.getUserCount(); i++) {
            Transaction[] listTrans = listUsers.getUserByIndex(i).getTransactionsList().toArray();

            for (int j = 0; listTrans != null && j < listTrans.length; j++) {
                if (listTrans[j].getTransactionId().equals(item.getTransactionId())) {
                    return listUsers.getUserByIndex(i);
                }
            }
        }
        return null;
    }

    private void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");

        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");

            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }

            Long userId = Long.parseLong(inputArr[0]);
            UUID transactionId = UUID.fromString(inputArr[1]);

            Transaction transaction = getTransaction(transactionsService.getTransactionList(userId), transactionId);

            if (transaction == null) {
                throw new RuntimeException("Transaction with id = " + transactionId + " not found");
            }

            transactionsService.removeTransaction(transactionId, userId);
            User user = (transaction.getTransferCategory() == DEBIT) ? transaction.getSender() : transaction.getRecipient();
            String category = (transaction.getTransferCategory() == DEBIT) ? "From " : "To ";
            System.out.println("Transfer " + category + " " + user.getName() +
                    "(id = " + user.getId() + ") " + transaction.getAmount() + " removed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(line);
        }
    }

    private Transaction getTransaction(Transaction[] transactionList, UUID transactionId) {
        if (transactionList == null) {
            throw new RuntimeException("Transaction with id = " + transactionId + " not found");
        }

        for (Transaction item : transactionList) {
            if (item.getTransactionId().equals(transactionId)) {
                return item;
            }
        }

        return null;
    }

    private void viewAllTransactionsOfUser() {
        System.out.println("Enter a user ID");
        try {
            Long id = Long.parseLong(scanner.nextLine().trim());
            Transaction[] transactions = transactionsService.getTransactionList(id);

            if (transactions == null) {
                throw new RuntimeException("User with ID = " + id + " hasn't any transactions");
            }

            for (Transaction item : transactions) {
                String category = (item.getTransferCategory() == DEBIT) ? "From " : "To ";
                int k = (item.getTransferCategory() == DEBIT) ? 1 : -1;


                User user = (item.getTransferCategory() == DEBIT) ? item.getSender() : item.getRecipient();

                System.out.println(category + user.getName() + "(id = " + user.getId() + ") " +
                        item.getAmount().multiply(BigDecimal.valueOf(k)) + " with id = " + item.getTransactionId());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(line);
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");

            if (inputArr.length != 3) {
                throw new RuntimeException("Invalid data. Try again");
            }

            Long senderId = Long.parseLong(inputArr[0]);
            Long recipientId = Long.parseLong(inputArr[1]);
            int amount = Integer.parseInt(inputArr[2]);
            transactionsService.executeTransaction(senderId, recipientId, BigDecimal.valueOf(amount));
            System.out.println("The transfer is completed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(line);
        }
    }

    private void viewUserBalance() {
        System.out.println("Enter a user ID");
        try {
            Long id = Long.parseLong(scanner.nextLine().trim());
            BigDecimal balance = transactionsService.getUserBalance(id);
            System.out.println(transactionsService.getUsersList().getUserById(id).getName() + " - " + balance);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(line);
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");

            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }

            User user = new User(inputArr[0], BigDecimal.valueOf(Integer.parseInt(inputArr[1])));
            transactionsService.addUser(user);
            System.out.println("User with id = " + user.getId() + " is added");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(line);
        }
    }

    private int getAnswer(boolean devMode) {
        try {
            int answer = Integer.parseInt(scanner.nextLine().trim());
            if (answer <= 0 || (devMode && answer > 7 || !devMode && answer > 5)) {
                throw new RuntimeException("Invalid action. Enter a valid number: ");
            }
            return answer;
        } catch (RuntimeException e) {
            System.out.println(e);
            return getAnswer(devMode);
        }

    }

    private void selectionMenu(boolean devMode) {
        System.out.println("1. Add a user\n2. View user balances\n3. Perform a transfer\n" +
                "4. View all transactions for a specific user");
        if (devMode) {
            System.out.println("5. DEV – remove a transfer by ID\n6. DEV – check transfer validity");
        } else {
            System.out.println("5. Finish execution");
        }
    }
}
