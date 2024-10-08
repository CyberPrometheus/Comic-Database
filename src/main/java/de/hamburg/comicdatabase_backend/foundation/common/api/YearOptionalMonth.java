package de.hamburg.comicdatabase_backend.foundation.common.api;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;


@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class YearOptionalMonth {

    private int year;


    private Integer month;


    private YearOptionalMonth(int year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public static YearOptionalMonth of(int year, Integer month) {
        YEAR.checkValidValue(year);
        if(month != null) {
            MONTH_OF_YEAR.checkValidValue(month);
        }
        return new YearOptionalMonth(year, month);
    }


    @Override
    public String toString() {
        return month != null ? year + "-" + month : String.valueOf(year);
    }


}
