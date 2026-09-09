package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.application.dto.ClassFilter;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;
import com.AjAkrampoor.Academy.courses.presentaion.mapper.CourseClassMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseClassRepositoryImpl
        implements CourseClassRepository {

    private final CourseClassJpaRepository repository;

    public CourseClassRepositoryImpl(
            CourseClassJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ClassId classId) {
        return repository.existsById(classId);
    }

    @Override
    public Optional<CourseClass> findById(ClassId classId) {
        return repository.findById(classId)
                .map(CourseClassMapper::toDomain);
    }

    @Override
    public CourseClass save(CourseClass courseClass) {
        CourseClassJpaEntity saved =
                repository.save(
                        CourseClassMapper.toEntity(courseClass)
                );

        return CourseClassMapper.toDomain(saved);
    }

    @Override
    public Page<CourseClass> findAll(
            Specification<CourseClassJpaEntity> specification,
            Pageable pageable
    ) {
        return repository.findAll(
                        specification,
                        pageable
                )
                .map(CourseClassMapper::toDomain);
    }

    @Override
    public Page<CourseClass> findAll(
            ClassFilter filter,
            BranchId branchId,
            Pageable pageable
    ) {

        List<Specification<CourseClassJpaEntity>> specifications =
                ClassSpecifications.getSpecifications(filter);

        /*
         * Branch restriction is supplied by the application layer
         * for non-super-admin users.
         *
         * If branchId is null, the caller is allowed to see all branches.
         */
        if (branchId != null) {
            specifications.add(
                    ClassSpecifications.hasBranchId(
                            branchId.getId()
                    )
            );
        }

        Specification<CourseClassJpaEntity> combined =
                Specification.allOf(specifications);

        return repository.findAll(
                        combined,
                        pageable
                )
                .map(CourseClassMapper::toDomain);
    }
}
