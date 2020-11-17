package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CleanerConfig {
    private CleanerMode mode;
    private Integer amount;
    private Integer size;
    private Date date;

    public CleanerConfig(CleanerMode mode) {
        this.mode = mode;
    }

    public CleanerConfig amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public CleanerConfig size(Integer size) {
        this.size = size;
        return this;
    }

    public CleanerConfig date(Date date) {
        this.date = date;
        return this;
    }

    public boolean isDateEnabled() {
        return date != null;
    }

    public boolean isAmountEnabled() {
        return amount != null;
    }

    public boolean isSizeEnabled() {
        return size != null;
    }

    public boolean isNull() {
        return size == null && amount == null && date == null;
    }
}
