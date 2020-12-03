package model.account;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateProvider {
    LocalDateTime now();
}
