package com.AjAkrampoor.Academy.sessions.presentation.mapper;

import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.DayOffJpaEntity;

public class DayOffMapper {

    public static DayOff toDomain(DayOffJpaEntity entity) {
        return new DayOff
                (
                        entity.getDayOffId(),
                        entity.getDate(),
                        entity.getDescription(),
                        entity.getStatus(),
                        entity.getCreatedBy(),
                        entity.getBranchId()
                );
    }

    public static DayOffJpaEntity toEntity(DayOff domain) {
        DayOffJpaEntity entity = new DayOffJpaEntity();

        entity.setDayOffId(domain.getDayOffId());
        entity.setDate(domain.getDate());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setBranchId(domain.getBranchId());
        return entity;
    }
}
