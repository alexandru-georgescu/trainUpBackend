
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.*;
import com.trainingup.trainingupapp.enums.CourseType;
import com.trainingup.trainingupapp.enums.Domains;
import com.trainingup.trainingupapp.enums.UserType;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    int course = 1;

    @GetMapping("/user")
    public List<UserDTO> introProject() {
        return userService.findAll();
    }

    @GetMapping("/userDB")
    public List<User> introProject1() {
        return userService.findAllDB();
    }


    @PostConstruct
    public void createAdmin() {
        UserDTO admin = new UserDTO();
        admin.setType(UserType.ADMIN);
        admin.setLeader("ALEX");
        admin.setEmail("admin.admin@trainup.com");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPassword("Admin123456");
        admin.setEnable(true);

        UserDTO pm = new UserDTO();
        pm.setType(UserType.PM);
        pm.setEmail("p.m@trainup.com");
        pm.setFirstName("p");
        pm.setLastName("m");
        pm.setPassword("Pm123456");
        pm.setLeader("admin.admin@trainup.com");
        pm.setEnable(true);

        UserDTO tm = new UserDTO();
        tm.setType(UserType.TM);
        tm.setEmail("t.m@trainup.com");
        tm.setFirstName("t");
        tm.setLastName("m");
        tm.setPassword("Tm123456");
        tm.setLeader("p.m@trainup.com");
        tm.setEnable(true);

        UserDTO user = new UserDTO();
        user.setType(UserType.USER);
        user.setEmail("u.s@trainup.com");
        user.setFirstName("u");
        user.setLastName("s");
        user.setPassword("User123456");
        user.setLeader("t.m@trainup.com");
        user.setEnable(true);

        UserDTO user2 = new UserDTO();
        user2.setType(UserType.USER);
        user2.setEmail("eda.ibram@trainup.com");
        user2.setFirstName("eda");
        user2.setLastName("ibram");
        user2.setPassword("Eda123456");
        user2.setLeader("t.m@trainup.com");
        user2.setEnable(true);

        UserDTO user3 = new UserDTO();
        user3.setType(UserType.USER);
        user3.setEmail("liviu.ibram@trainup.com");
        user3.setFirstName("liviu");
        user3.setLastName("ibram");
        user3.setPassword("Eda123456");
        user3.setLeader("t.m@trainup.com");
        user3.setEnable(true);

        synchronized (userService) {
            userService.addUser(user);
            userService.addUser(pm);
            userService.addUser(tm);
            userService.addUser(user2);
            userService.addUser(user3);
        }
    }
    @GetMapping("/in")
    public List<CourseDTO> in() {

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setActualCapacity(10);
        courseDTO1.setCapacity(10);
        courseDTO1.setCourseName("Curs" + course++);
        courseDTO1.setStartDate(LocalDate.now());
        courseDTO1.setEndDate(LocalDate.now().plusMonths(1));
        courseDTO1.setProjectManager("p.m@trainup.com");
        courseDTO1.setDomain(Domains.RCA);
        courseDTO1.setType(CourseType.PROCESS);

        CourseDTO courseDTO2 = new CourseDTO();
        courseDTO2.setActualCapacity(10);
        courseDTO2.setCapacity(10);
        courseDTO2.setCourseName("Curs" + course++);
        courseDTO2.setStartDate(LocalDate.now().plusMonths(5));
        courseDTO2.setEndDate(LocalDate.now().plusMonths(10));
        courseDTO2.setProjectManager("p.m@trainup.com");
        courseDTO2.setDomain(Domains.GTB);
        courseDTO2.setType(CourseType.PROCESS);

        CourseDTO courseDTO3 = new CourseDTO();
        courseDTO3.setActualCapacity(10);
        courseDTO3.setCapacity(10);
        courseDTO3.setCourseName("Curs" + course++);
        courseDTO3.setStartDate(LocalDate.now().minusWeeks(10));
        courseDTO3.setEndDate(LocalDate.now().minusWeeks(5));
        courseDTO3.setProjectManager("p.m@trainup.com");
        courseDTO3.setDomain(Domains.NFR);
        courseDTO3.setType(CourseType.PROCESS);

        synchronized (courseService) {
            courseService.addCourse(courseDTO1);
            courseService.addCourse(courseDTO2);
            courseService.addCourse(courseDTO3);
        }

        userService.findAll().stream().filter(u -> u.getType().equals(UserType.USER))
                .forEach(uu -> {
            List<CourseDTO> cc = uu.getWaitToEnroll();
            cc.addAll(courseService.findAll());
                    uu.setWaitToEnroll(cc);
            userService.saveAndFlushBack(uu);
        });

        userService.findAllDB().stream().filter(u -> u.getType().equals(UserType.USER))
                .forEach(u -> {
            List<Course> cc = new ArrayList<>();
            cc.addAll(u.getWaitToEnroll());
            cc.addAll(courseService.findAllDB());
            u.setWaitToEnroll(cc);
            userService.saveAndFlush(u);
        });

        return courseService.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/login")
    public UserDTO loginPage(@RequestBody UserDTO user) {
        return userService.loginService(user.getEmail(), user.getPassword());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/update_users")
    public boolean updateUsers(@RequestBody List<UserDTO> users) {
        return userService.updateUsers(users);
    }

    @PostMapping("/user/findWaitByCourse")
    public List<UserDTO> findWaitByCourse(@RequestBody CourseDTO courseDTO) {
        return userService.findWaitByCourse(courseDTO);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/user/wish")
    public UserDTO wishToEnroll(@RequestBody CourseUserDTO array) {
        return userService.wishToEnroll(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/waitToEnroll")
    public UserDTO waitToEnroll(@RequestBody CourseUserDTO array) {
        return userService.waitToEnroll(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/findByName")
    public UserDTO findByName(@RequestParam("name") String name) {
        return userService.findAll().stream().filter(u -> u.getEmail().toLowerCase().equals(name.toLowerCase()))
                .findFirst().orElse(null);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/user/register")
    public UserDTO registerPage(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @GetMapping("/user/remove")
    public void removeUserByIdPage(@RequestParam("id") long id) {
        userService.removeUser(id);
    }

    @GetMapping("/user/find")
    public UserDTO findUserByIdPage(@RequestParam("id") long id) {
        return userService.findById(id);
    }

    @GetMapping("/user/findByLeader")
    public List<UserDTO> findByLeader(@RequestParam("leader") String leader) {
        return userService.findAllWithLeader(leader);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/refuseToEnroll")
    public UserDTO refuseToEnroll(@RequestBody CourseUserDTO array) {
        return userService.removeFromWish(array.getUser(),array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/rejectFromWait")
    public UserDTO rejectFromWait(@RequestBody CourseUserDTO array) {
        return userService.rejectFromWait(array.getUser(),array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/acceptFromWait")
    public UserDTO acceptFromWait(@RequestBody CourseUserDTO array) {
        return userService.acceptFromWait(array.getUser(),array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/acceptAll")
    public List<UserDTO> acceptAll(@RequestBody LUsersCourse array) {
        return userService.acceptAllUsers(array.getUsers(),array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/moveToAccepted")
    public UserDTO moveToAccepted(@RequestBody CourseUserDTO array) {
        return userService.swapRejectedAccepted(array.getUser(),array.getCourse());
    }


    @CrossOrigin(origins = "*")
    @PostMapping("user/moveToRejected")
    public UserDTO moveToRejected(@RequestBody CourseUserDTO array) {
        return userService.swapAcceptedRejected(array.getUser(),array.getCourse());
    }
}