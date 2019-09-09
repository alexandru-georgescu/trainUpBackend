package com.trainingup.trainingupapp.service.statistics.user_statistics;

import com.trainingup.trainingupapp.controller.CourseController;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.statistics.tm_statistics.SortCourse;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimpleUserStatisticsService implements UserStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseController courseController;

    Map<String, Integer> domains = new HashMap<>();

    @Override
    public List<String> findBestCourseFromPast(UserDTO user) {
        domains.clear();
        List<CourseDTO> fromPast = courseController.findBefore(user);
        fromPast.forEach(c -> addDomain(c.getDomain()));

        return toSort();
    }

    @Override
    public List<String> findBestCourse() {
        domains.clear();
        List<UserDTO> allUsers = userService.findAll();
        List<CourseDTO> allCourses = new ArrayList<>();
        allUsers.forEach(u -> allCourses.addAll(u.getCourses()));

        allCourses.forEach(c -> addDomain(c.getDomain()));

        return toSort();
    }

    public List<String> toSort() {
        List<SortCourse> toSort = new ArrayList<>();

        domains.forEach((c, i) -> {
            SortCourse course = new SortCourse();
            course.setName(c);
            course.setNr(i);
            toSort.add(course);
        });

        Collections.sort(toSort, (c1, c2) -> {
            if (c1.getNr() < c2.getNr()) {
                return 1;
            };
            return -1;
        });
        if (toSort.size() == 0) {
            return null;
        }

        int maxValue = toSort.get(0).getNr();
        List<String> toEda = toSort.stream()
                .filter(c -> c.getNr() == maxValue)
                .map(c -> c.getName())
                .collect(Collectors.toList());
        return toEda;
    }

    @Override
    public void addDomain(String domain) {
        if (!domains.containsKey(domain)) {
            domains.put(domain, 1);
            return;
        }

        domains.put(domain, domains.get(domain) + 1);
    }
}
