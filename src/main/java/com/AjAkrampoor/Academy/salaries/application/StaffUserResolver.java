package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

public interface StaffUserResolver {

    UserId resolveUserId(StaffId staffId);
}