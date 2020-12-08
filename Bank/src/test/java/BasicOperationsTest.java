import controller.ExampleBank;
import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import exception.NotEnoughMoneyException;
import exception.UnverifiedTransferException;
import model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class BasicOperationsTest {
    @Test
    public void createDifferentAccounts() {
        ExampleBank bank = ExampleBank
                .builder()
                .name("bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client client = Client.builder()
                .name("Max")
                .surname("Golish")
                .passport(1L)
                .build();
        bank.addClient(client);
        bank.createDebitAccount(client.getId());
        bank.createCreditAccount(client.getId(), 50000);
        bank.createDepositAccount(client.getId(), 50000, LocalDateTime.now());
    }

    @Test
    public void testWithdrawal() {
        ExampleBank bank = ExampleBank
                .builder()
                .name("bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client client = Client.builder()
                .name("Max")
                .surname("Golish")
                .passport(1L)
                .build();
        bank.addClient(client);
        UUID debit = bank.createDebitAccount(client.getId());
        UUID credit = bank.createCreditAccount(client.getId(), 50000);
        UUID deposit = bank.createDepositAccount(client.getId(), 50000, LocalDateTime.now());
        bank.add(debit, 50000);
        bank.withdraw(debit, 30000);
        Assertions.assertEquals(20000, bank.currentBalance(debit), 1);
        bank.withdraw(credit, 30000);
        Assertions.assertEquals(20000, bank.currentBalance(credit), 1);
        bank.withdraw(deposit, 30000);
        Assertions.assertEquals(20000, bank.currentBalance(debit), 1);
    }

    @Test
    public void testInnerOperationCancelling() {
        ExampleBank bank = ExampleBank
                .builder()
                .name("bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client client = Client.builder()
                .name("Max")
                .surname("Golish")
                .passport(1L)
                .build();
        TransactionProcessor.getInstance().addBank(bank);
        bank.addClient(client);
        UUID debit = bank.createDebitAccount(client.getId());
        UUID credit = bank.createCreditAccount(client.getId(), 50000);
        UUID deposit = bank.createDepositAccount(client.getId(), 50000, LocalDateTime.now());
        bank.add(debit, 50000);
        bank.withdraw(debit, 30000);
        bank.getTransactions().get(0).cancel();
        Assertions.assertEquals(50000, bank.currentBalance(debit), 1);
        bank.withdraw(credit, 30000);
        bank.getTransactions().get(0).cancel();
        Assertions.assertEquals(50000, bank.currentBalance(credit), 1);
        bank.withdraw(deposit, 30000);
        bank.getTransactions().get(0).cancel();
        Assertions.assertEquals(50000, bank.currentBalance(debit), 1);
    }

    @Test
    public void testMoneyTransfer() {
        ExampleBank firstBank = ExampleBank
                .builder()
                .name("First bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client max = Client.builder()
                .name("Max")
                .surname("Golish")
                .passport(1L)
                .build();
        firstBank.addClient(max);
        UUID maxAccount = firstBank.createDebitAccount(max.getId());
        firstBank.add(maxAccount, 1000);
        ExampleBank secondBank = ExampleBank
                .builder()
                .name("Second bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client fredi = Client.builder()
                .name("Fredi")
                .surname("Kats")
                .passport(1L)
                .build();
        TransactionProcessor.getInstance().addBank(firstBank, secondBank);
        secondBank.addClient(fredi);
        UUID frediAccount = secondBank.createDebitAccount(fredi.getId());
        firstBank.send(maxAccount, 500, frediAccount);
        Assertions.assertEquals(500, firstBank.currentBalance(maxAccount), 1);
        Assertions.assertEquals(500, secondBank.currentBalance(frediAccount), 1);
    }

    @Test
    public void testTransferCancelling() {
        ExampleBank firstBank = ExampleBank
                .builder()
                .name("First bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client max = Client.builder()
                .name("Max")
                .surname("Golish")
                .passport(1L)
                .build();
        firstBank.addClient(max);
        UUID maxAccount = firstBank.createDebitAccount(max.getId());
        firstBank.add(maxAccount, 1000);
        ExampleBank secondBank = ExampleBank
                .builder()
                .name("Second bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client fredi = Client.builder()
                .name("Fredi")
                .surname("Kats")
                .passport(1L)
                .build();
        secondBank.addClient(fredi);
        TransactionProcessor.getInstance().addBank(firstBank, secondBank);
        UUID frediAccount = secondBank.createDebitAccount(fredi.getId());
        firstBank.send(maxAccount, 500, frediAccount);
        firstBank.getTransactions().get(0).cancel();
        Assertions.assertEquals(1000, firstBank.currentBalance(maxAccount), 1);
        Assertions.assertEquals(0, secondBank.currentBalance(frediAccount), 1);
    }

    @Test
    public void testTransferExceptions() {
        ExampleBank firstBank = ExampleBank
                .builder()
                .name("First bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        // Unverified
        Client max = Client.builder()
                .name("Max")
                .surname("Golish")
                .build();
        firstBank.addClient(max);
        UUID maxAccount = firstBank.createDebitAccount(max.getId());
        firstBank.add(maxAccount, 20000);
        ExampleBank secondBank = ExampleBank
                .builder()
                .name("Second bank")
                .creditPercent(15d)
                .debitPercent(3d)
                .build();
        Client fredi = Client.builder()
                .name("Fredi")
                .surname("Kats")
                .passport(2L)
                .build();
        secondBank.addClient(fredi);
        UUID frediAccount = secondBank.createDebitAccount(fredi.getId());
        TransactionProcessor.getInstance().addBank(firstBank, secondBank);
        Assertions.assertThrows(
                UnverifiedTransferException.class,
                () -> firstBank.send(maxAccount, 15000, frediAccount)
        );
        Assertions.assertThrows(
                NotEnoughMoneyException.class,
                () -> secondBank.send(frediAccount, 10000, maxAccount)
        );
        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> firstBank.send(maxAccount, 100, UUID.randomUUID())
        );
    }
}
