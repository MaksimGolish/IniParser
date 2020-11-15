package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@Builder
public class CleanerConfig {
    @NonNull
    private CleanerMode mode;
    private Integer amount;
    private Integer size;
    private Date date;
}
