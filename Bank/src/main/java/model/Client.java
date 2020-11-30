package model;

import lombok.*;
import java.util.UUID;

@Data
public class Client {
    private final UUID id = UUID.randomUUID();
    @NonNull
    private final String name;
    @NonNull
    private final String surname;
    private Long passport;

    @Builder
    private Client(String name, String surname, long passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }
}
