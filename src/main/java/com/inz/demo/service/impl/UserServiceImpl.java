package com.inz.demo.service.impl;

import com.inz.demo.domain.User;
import com.inz.demo.domain.UserKids;
import com.inz.demo.repository.ClassRepository;
import com.inz.demo.repository.UserRepository;
import com.inz.demo.service.IUserService;
import com.inz.demo.util.DTOs.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public UserServiceImpl(UserRepository userRepository, ClassRepository classRepository) {
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByUserLogin(login);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = User.builder()
                .birthDate(userDTO.getBirthDate())
                .isUserParent(userDTO.getIsUserParent())
                .isUserStudent(userDTO.getIsUserStudent())
                .isUserTeacher(userDTO.getIsUserTeacher())
                .userLogin(userDTO.getLogin())
                .userName(userDTO.getName())
                .userSurname(userDTO.getSurname())
                .userEmail(userDTO.getEmail())
                .userTimestamp(Calendar.getInstance().getTime())
                .userModificationDate(Calendar.getInstance().getTime())
                .phoneNumber(userDTO.getPhone())
                .build();

        if (user.getIsUserStudent()) {
            user.setUserParent(userRepository.findByUserId(userDTO.getParentId()));
            user.setUserClass(classRepository.getOne(userDTO.getClassId()));
        }

        if (user.getIsUserParent()) {
            userRepository.save(user);
            User addKids = userRepository.findByUserLogin(userDTO.getLogin()).get();

            int[] arr = Arrays.stream(userDTO.getKidsIds().substring(1, userDTO.getKidsIds().length() - 1).split(","))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
            List<UserKids> kids = new ArrayList<>();
            for (int id : arr) {
                kids.add(UserKids.builder()
                        .kid(userRepository.findByUserId((long) id))
                        .user(addKids)
                        .build());
            }
            addKids.setUserKids(kids);
            user.setUserPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            userRepository.save(addKids);
        }

        user.setUserPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findByUserId(id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, User user) {
        User toEdit = userRepository.findByUserId(id);
        toEdit.setUserName(user.getUserName());
        toEdit.setUserSurname(user.getUserSurname());
        toEdit.setPhoneNumber(user.getPhoneNumber());
        toEdit.setUserEmail(user.getUserEmail());
        userRepository.save(toEdit);
    }

}