package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
    private Client(String name, String surname, Long passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }
}
