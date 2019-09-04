package com.trainingup.trainingupapp.service.user_service;

import com.trainingup.trainingupapp.convertor.CourseConvertor;
import com.trainingup.trainingupapp.convertor.UserConvertor;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.repository.UserRepository;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.smtp_service.SmtpService;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SimpleUserService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    SmtpService smtpService;

    List<UserDTO> userBackend = new ArrayList<>();

    Random random = new Random();

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return this.userBackend;
    }

    @Override
    public List<User> findAllDB() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDTO removeFromWish(UserDTO user, CourseDTO course) {

        User userDB = findByIdDB(user.getId());
        UserDTO userDTO = findById(user.getId());

        if (userDB == null || userDTO == null) {
            return null;
        }

        List<Course> courses = userDB.getWishToEnroll();


        //Update REJECTED LIST
        Course toReject = courseService.findByIdDB(course.getId());
        List<Course> rejectedList = userDB.getRejectedList();
        rejectedList.add(toReject);
        userDB.setRejectedList(rejectedList);

        //REMOVE FROM WISH
        courses.removeIf(c -> c.getId() == course.getId());
        List<Course> newCourses = new ArrayList<>(courses);
        userDB.setWishToEnroll(newCourses);

        //Update REJECTED LIST
        List<CourseDTO> rejectedListBack = userDTO.getRejectedList();
        rejectedListBack.add(course);
        userDTO.setRejectedList(rejectedListBack);

        //UPDATE DB
        saveAndFlush(userDB);
        saveAndFlushBack(userDTO);

        List<CourseDTO> courseDTOS = userDTO.getWishToEnroll();
        //REMOVE FROM WISH
        courseDTOS.removeIf(c -> c.getId() == course.getId());
        userDTO.setWishToEnroll(courseDTOS);


        return userDTO;
    }


    @Override
    public void saveAndFlush(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAndFlushBack(UserDTO user) {
        userBackend = userBackend.stream().map(c -> {
            if (c.getId() == user.getId()) {
                c = user;
            }
            return c;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findWaitByCourse(CourseDTO courseDTO) {
        return userBackend
                .stream()
                .filter(
                        user -> user.getWaitToEnroll().stream()
                                .filter(course -> course.getId() == courseDTO.getId())
                                .findFirst().orElse(null) != null
                ).collect(Collectors.toList());
    }

    @Override
    public UserDTO acceptFromWait(UserDTO user, CourseDTO course) {
        User userDB = findByIdDB(user.getId());
        UserDTO userDTO = findById(user.getId());

        if (userDB == null || userDTO == null) {
            return null;
        }

        List<Course> courses = userDB.getWaitToEnroll();

        //Update COURSE LIST
        Course toAccept = courseService.findByIdDB(course.getId());

        toAccept.setActualCapacity(toAccept.getActualCapacity() - 1);
        course.setActualCapacity(toAccept.getActualCapacity());

        courseService.saveAndFlashBack(course);
        courseService.saveAndFlash(toAccept);


        //ADD COURSE TO USER
        List<Course> acceptedList = userDB.getCourses();
        acceptedList.add(toAccept);
        userDB.setCourses(acceptedList);

        //REMOVE FROM WAIT
        courses.removeIf(c -> c.getId() == course.getId());
        List<Course> newCourses = new ArrayList<>(courses);
        userDB.setWaitToEnroll(newCourses);

        List<CourseDTO> courseDTOS = userDTO.getWaitToEnroll();

        //Update REJECTED LIST
        List<CourseDTO> acceptedListBack = userDTO.getCourses();
        acceptedListBack.add(course);
        userDTO.setCourses(acceptedListBack);

        //REMOVE FROM WAIT
        courseDTOS.removeIf(c -> c.getId() == course.getId());
        userDTO.setWaitToEnroll(courseDTOS);

        //UPDATE DB
        saveAndFlush(userDB);
        saveAndFlushBack(userDTO);

        return userDTO;
    }

    @Override
    public UserDTO rejectFromWait(UserDTO user, CourseDTO course) {
        User userDB = findByIdDB(user.getId());
        UserDTO userDTO = findById(user.getId());

        if (userDB == null || userDTO == null) {
            return null;
        }

        List<Course> courses = userDB.getWaitToEnroll();

        //Update REJECTED LIST
        Course toReject = courseService.findByIdDB(course.getId());

        List<Course> rejectedList = userDB.getRejectedList();
        rejectedList.add(toReject);
        userDB.setRejectedList(rejectedList);

        //REMOVE FROM WAIT
        courses.removeIf(c -> c.getId() == course.getId());
        List<Course> newCourses = new ArrayList<>(courses);
        userDB.setWaitToEnroll(newCourses);
        List<CourseDTO> courseDTOS = userDTO.getWaitToEnroll();

        //Update REJECTED LIST
        List<CourseDTO> rejectedListBack = userDTO.getRejectedList();
        rejectedListBack.add(course);
        userDTO.setRejectedList(rejectedListBack);

        //REMOVE FROM WAIT
        courseDTOS.removeIf(c -> c.getId() == course.getId());
        userDTO.setWaitToEnroll(courseDTOS);

        //UPDATE DB
        saveAndFlush(userDB);
        saveAndFlushBack(userDTO);

        return userDTO;
    }

    @Override
    public UserDTO findById(long id) {
        return this.userBackend
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByIdDB(long id) {
        return userRepository.findAll().stream()
                .filter(u -> u.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public UserDTO addUser(UserDTO user) {

        if (user.getType() == null) {
            user.setType("USER");
            user.setLeader("t.m@trainup.com");
        }

        if (!validate(user.getEmail(), user.getPassword())) {
            return null;
        }

        UserDTO checkUser = userBackend
                .stream()
                .filter(e -> e.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElse(null);

        if (checkUser != null) {
            return null;
        }

        User newUser = UserConvertor.convertToUser(user);
        newUser.setCourses(new ArrayList<>());
        newUser.setWishToEnroll(new ArrayList<>());

        newUser.setToken(String.valueOf(1231121312 + random.nextInt(10000000)));

        //TODO: CAND O SA AVEM ADRESE o sa trimitem catre adresa de la email
        //IN LOC DE TRAINUP.COM, O sa avem GMAIL.COM
        if (!user.isEnable()) {
            smtpService.sendValidateEmail("trainupapply@gmail.com", newUser.getToken());
        }

        userRepository.saveAndFlush(newUser);
        user.setId(newUser.getId());
        userBackend.add(user);
        return user;
    }

    @Override
    public void removeUser(long id) {
        this.userRepository.deleteById(id);
        UserDTO dummy = this.userBackend
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);

        if (dummy != null) {
            this.userBackend.remove(dummy);
        }
    }

    @Override
    public UserDTO loginService(String email, String password) {
        if (!validate(email, password)) {
            return null;
        }

        Optional<UserDTO> user = userBackend.stream().filter(u -> {
            if (u.getEmail().toLowerCase().equals(email.toLowerCase()) &&
                    u.getPassword().equals(password)) {
                return true;
            }
            return false;
        }).findFirst();

        if (user.isPresent()) {
            User userDB = findByIdDB(user.get().getId());
            if (!userDB.isEnable()) {
                return user.orElse(null);
            }
        }

        return user.orElse(null);
    }

    @Override
    public boolean validate(String email, String password) {
        if (!email.contains("@trainup.com")) {
            return false;
        }

        if (email.equals("") || password.equals("")) {
            return false;
        }

        int atIndex = email.indexOf("@");

        if (email.lastIndexOf("@") != atIndex) {
            return false;
        }

        String beforeAt = email.substring(0, atIndex);

        if (!beforeAt.contains(".")) {
            return false;
        }

        if (!beforeAt.matches("[a-zA-Z]+" + "." + "[a-zA-Z]+")) {
            return false;
        }

        return true;
    }

    @Override
    public UserDTO wishToEnroll(UserDTO userDTO, CourseDTO courseDTO) {
        System.out.println(userDTO + " " + courseDTO);
        User userDB = findByIdDB(userDTO.getId());

        Course courseDB = CourseConvertor.convertToCourse(courseDTO);

        UserDTO userDTO1 = findById(userDTO.getId());

        if (userDB == null || userDTO1 == null) {
            return null;
        }

        List<Course> courseList = userDB.getWishToEnroll();

        Course find = courseList.stream().filter(course -> course.getId() == courseDB.getId())
                .findFirst().orElse(null);

        if (find != null) {
            return userDTO;
        }

        List<Course> userCourse = userDB.getWishToEnroll();
        userCourse.add(courseDB);
        userDB.setWishToEnroll(userCourse);


        List<CourseDTO> userCourseDTO = userDTO1.getWishToEnroll();
        userCourseDTO.add(courseDTO);
        userDTO1.setWishToEnroll(userCourseDTO);

        saveAndFlushBack(userDTO1);
        saveAndFlush(userDB);

        return userDTO1;
    }

    @Override
    public UserDTO waitToEnroll(UserDTO userDTO, CourseDTO courseDTO) {

        User userDB = findByIdDB(userDTO.getId());

        Course courseDB = CourseConvertor.convertToCourse(courseDTO);

        UserDTO userDTO1 = findById(userDB.getId());

        if (userDB == null || userDTO1 == null) {
            return null;
        }

        List<Course> courseList = userDB.getWaitToEnroll();

        Course find = courseList.stream().filter(course -> course.getId() == courseDB.getId())
                .findFirst().orElse(null);

        if (find != null) {
            return userDTO;
        }

        //DB
        List<Course> userCourse = userDB.getWaitToEnroll();
        userCourse.add(courseDB);
        userDB.setWaitToEnroll(userCourse);

        //DTO
        List<CourseDTO> userCourseDTO = userDTO1.getWaitToEnroll();
        userCourseDTO.add(courseDTO);
        userDTO1.setWaitToEnroll(userCourseDTO);

        //DB
        List<Course> userCourseWish = userDB.getWishToEnroll();
        userCourseWish.removeIf(c -> c.getId() == courseDB.getId());
        List<Course> newWish = new ArrayList<>(userCourseWish);
        userDB.setWishToEnroll(newWish);


        //DTO
        List<CourseDTO> userDTOWish = userDTO1.getWishToEnroll();
        userDTOWish.removeIf(c -> c.getId() == courseDB.getId());
        userDTO1.setWishToEnroll(userDTOWish);

        saveAndFlushBack(userDTO1);
        saveAndFlush(userDB);
        return userDTO1;
    }

    @Override
    public List<UserDTO> findAllWithLeader(String leader) {
        return userBackend.stream()
                .filter(user -> user.getLeader().toLowerCase().equals(leader.toLowerCase()))
                .collect(Collectors.toList());
    }

}
