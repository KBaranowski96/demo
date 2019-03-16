package com.inz.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    @JoinColumn(name = "teacher_id")
    @ManyToOne(targetEntity = User.class)
    private User teacher;

    @JoinColumn(name = "student_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User student;

    @Column(name = "gr_mod_date", length = 50)
    private Date gradeModificationDate;

    @Column(name = "gr_add_date", length = 50)
    private Date gradeTimestamp;

    @Column(name = "gr_grade")
    private Double gradeValue;

}