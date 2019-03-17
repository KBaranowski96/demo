insert into classes(class_id, class_sign, class_year, teacher)
VALUES (1, '1A', '2019', null);

insert into users(user_id, user_birth_date, user_parent_flag, user_student_flag, user_teacher_flag, user_phone,
                  user_email, user_login, user_mod_date, user_name, user_password, user_surname, user_add_date,
                  user_class, user_parent_id)
VALUES (1, current_date, true, true, true, 'none', 'admin@gmail.com', 'admin', current_date, 'name',
        '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'surname', current_date, null, null),
       (2, current_date, false, true, false, 'none', 'student@gmail.com', 'student', current_date, 'name',
        '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'surname', current_date, 1, 4),
       (3, current_date, false, false, true, 'none', 'teacher@gmail.com', 'teacher', current_date, 'name',
        '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'surname', current_date, null, null),
       (4, current_date, true, false, false, 'none', 'parent@gmail.com', 'parent', current_date, 'name',
        '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'surname', current_date, null, null);

update classes set teacher = 3 where class_id = 1;

insert into user_kids(id, kid_id, user_id)
VALUES (1, 2, 4);

insert into subjects(subject_id, name, subject_date_start, class, teacher)
VALUES (1, 'Math', 'Monday/8', 1, 3);

insert into grades(grade_id, grade_modification_date, grade_timestamp, grade_value, student_id, subject_id, teacher_id)
VALUES (1, current_date, current_date, 4.5, 2, 1, 3),
       (2, current_date, current_date, 3.0, 2, 1, 3);

insert into lessons(lesson_id, lesson_date, lesson_topic, lesson_class, subject)
VALUES (1, current_date, '#1 Math', 1, 1),
       (2, current_date, '#2 Math', 1, 1),
       (3, current_date, '#3 Math', 1, 1),
       (4, current_date, '#4 Math', 1, 1);;

insert into presences(presence_id, presence_modification, presence_type, lesson, student, teacher)
VALUES (1, current_date, 'PRESENT', 1, 2, 3),
       (2, current_date, 'ABSENT', 2, 2, 3),
       (3, current_date, 'JUSTIFIED', 3, 2, 3),
       (4, current_date, 'LATE', 4, 2, 3);

