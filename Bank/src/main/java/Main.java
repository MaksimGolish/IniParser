import model.Bank;
import model.Client;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Bank tinkoff = new Bank("Tinkoff", 5);
        Bank sber = new Bank("Sber", 3);
        Client sender = Client
                .builder()
                .name("Money")
                .surname("Sender")
                .build();
        Client receiver = Client
                .builder()
                .name("Money")
                .surname("Receiver")
                .build();
        tinkoff.addClient(sender);
        sber.addClient(receiver);
        UUID senderAccount = tinkoff.createDebitAccount(sender.getId());
        UUID receiverAccount = sber.createDebitAccount(receiver.getId());
        tinkoff.addMoney(senderAccount, 100);
        tinkoff.send(senderAccount, 50, receiverAccount);
        tinkoff.getTransactions().get(0).cancel();
    }
}
