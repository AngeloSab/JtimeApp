package it.unicam.cs.mpgc.jtime119200.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekPeriodConstructor {
    private final int offset;
    private final LocalDate today = LocalDate.now();


    public WeekPeriodConstructor(int offset) {
        this.offset = offset;
    }

    public List<LocalDate> getDays() {
        LocalDate start = today.plusDays(offset * 7L);
        List<LocalDate> days = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            days.add(start.plusDays(i));
        }
        return days;
    }

    WeekPeriodConstructor(){
        this(0);
    }


}
