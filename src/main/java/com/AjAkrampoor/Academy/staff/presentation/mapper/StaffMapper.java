package com.AjAkrampoor.Academy.staff.presentation.mapper;

import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.infrastructure.persistence.StaffJpaEntity;

import java.util.List;

public class StaffMapper {

    public static Staff toDomain(StaffJpaEntity entity) {
        return new Staff(entity.getId(), entity.getName(), entity.getPhoneNumber(), entity.getDescription(), entity.getStatus(), entity.getTerminationDate(), entity.getBranchId());
    }

    public static StaffJpaEntity toJpaEntity(Staff staff) {
        StaffJpaEntity staffJpaEntity = new StaffJpaEntity();
        staffJpaEntity.setId(staff.getStaffId());
        staffJpaEntity.setName(staff.getName());
        staffJpaEntity.setPhoneNumber(staff.getPhoneNumber());
        staffJpaEntity.setDescription(staff.getDescription());
        staffJpaEntity.setStatus(staff.getStatus());
        staffJpaEntity.setTerminationDate(staff.getTerminationDate());
        staffJpaEntity.setBranchId(staff.getBranchId());

        return staffJpaEntity;
    }


    public static List<Staff> toDomainList(List<StaffJpaEntity> entities) {
        return entities.stream()
                .map(StaffMapper::toDomain)
                .toList();
    }

    public static List<StaffJpaEntity> toJpaEntityList(List<Staff> staffs) {
        return staffs.stream()
                .map(StaffMapper::toJpaEntity)
                .toList();
    }
}
