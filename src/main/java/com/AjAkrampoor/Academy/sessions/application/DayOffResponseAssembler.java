package com.AjAkrampoor.Academy.sessions.application;

import com.AjAkrampoor.Academy.sessions.application.dto.DayOffResponse;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import org.springframework.stereotype.Component;

@Component
public class DayOffResponseAssembler {

    public DayOffResponse toResponse(DayOff domain) {
        String description = (domain.getDescription() == null) ? null : domain.getDescription().getValue();
        String branchId = (domain.getBranchId() == null) ? null : domain.getBranchId().toString();
        return new DayOffResponse
                (
                        domain.getDayOffId().toString(),
                        domain.getDate(),
                        description,
                        domain.getStatus(),
                        domain.getCreatedBy().toString(),
                        branchId
                );
    }
}
