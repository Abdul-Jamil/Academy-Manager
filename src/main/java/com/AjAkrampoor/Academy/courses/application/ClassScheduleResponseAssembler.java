package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.courses.application.dto.ClassScheduleResponse;
import com.AjAkrampoor.Academy.courses.application.dto.ScheduleEntryResponse;
import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ClassScheduleResponseAssembler {

    private final ScheduleEntryResponseAssembler entryAssembler;

    public ClassScheduleResponseAssembler(ScheduleEntryResponseAssembler entryAssembler) {
        this.entryAssembler = entryAssembler;
    }

    public ClassScheduleResponse toResponse(ClassSchedule schedule, List<ScheduleEntry> entries) {
        List<ScheduleEntryResponse> responses =
                entries.stream()
                        .sorted(Comparator.comparing(ScheduleEntry::getDayOfWeek)
                                .thenComparing(ScheduleEntry::getPeriodNumber))
                        .map(entryAssembler::toResponse)
                        .toList();

        return new ClassScheduleResponse(
                schedule.getId().toString(),
                schedule.getClassId().toString(),
                schedule.getEffectiveFrom(),
                schedule.getEffectiveTo(),
                schedule.getStatus(),
                responses
        );
    }
}
