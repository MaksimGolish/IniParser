import exception.WithdrawalNotAvailableException;
import model.Client;
import model.account.CreditAccount;
import model.account.DebitAccount;
import model.account.DepositAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.*;

public class AccountTest {
    private final Client tempClient = Client.builder()
            .name("Name")
            .surname("Surname")
            .passport(0L)
            .build();

    @Test
    public void testDebit() {
        DebitAccount account = DebitAccount
                .builder()
                .owner(tempClient)
                .percent(5)
                .build();
        account.add(50000);
        account.setDateProvider(
                () -> LocalDateTime.now().plusDays(15)
        );
        Assertions.assertEquals(50000, account.getBalance(), 0.1);
        account.setDateProvider(
                () -> LocalDateTime.now().plusDays(31)
        );
        Assertions.assertTrue(account.getBalance() > 50000);
    }

    @Test
    public void testDeposit() {
        DepositAccount account = DepositAccount
                .builder()
                .owner(tempClient)
                .money(50000)
                .expirationTime(LocalDateTime.now().plusDays(1))
                .build();
        Assertions.assertThrows(
                WithdrawalNotAvailableException.class,
                () -> account.get(30000)
        );
        account.setDateProvider(
                () -> LocalDateTime.now().plusDays(31)
        );
        Assertions.assertTrue(account.getBalance() > 50000);
    }

    @Test
    public void testCredit() {
        CreditAccount account = CreditAccount
                .builder()
                .owner(tempClient)
                .money(50000)
                .percent(3)
                .build();
        Assertions.assertThrows(
                WithdrawalNotAvailableException.class,
                () -> account.get(60000)
        );
        account.get(30000);
        account.setDateProvider(
                () -> LocalDateTime.now().plusDays(31)
        );
        Assertions.assertTrue(account.getBalance() < 20000);
    }
}
