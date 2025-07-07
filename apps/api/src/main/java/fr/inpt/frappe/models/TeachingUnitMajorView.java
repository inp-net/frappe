package fr.inpt.frappe.models;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teaching_units_all_majors")
@Immutable
@Data
@NoArgsConstructor
@Getter
public class TeachingUnitMajorView {

    @Id
    @Column(name = "teaching_unit_id")
    private Long teachingUnitId;

    @Column(name = "major_id")
    private Long majorId;
}
