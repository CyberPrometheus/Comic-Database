package de.hamburg.comicdatabase_backend.series.common.api;

import de.hamburg.comicdatabase_backend.foundation.common.api.YearOptionalMonth;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Embeddable
@NoArgsConstructor
public class PublicationPeriod {

    @Getter
    @Setter
    @AttributeOverride(name = "month", column = @Column(name = "start_month"))
    @AttributeOverride(name = "year", column = @Column(name = "start_year"))
    private YearOptionalMonth startDate;
    @Getter
    @Setter
    @AttributeOverride(name = "year", column = @Column(name = "end_year"))
    @AttributeOverride(name = "month", column = @Column(name = "end_month"))
    private YearOptionalMonth endDate;

    public PublicationPeriod(YearOptionalMonth startDate, YearOptionalMonth endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public boolean isOngoing() {
        return endDate == null; // Beispiel für eine Logik, die prüft, ob der Zeitraum fortlaufend ist
    }


    @Override
    public String toString() {
        String start = startDate != null ? startDate.toString() : "Unknown";
        String end = endDate != null ? endDate.toString() : "Ongoing";
        return start + " - " + end;
    }

}
