package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimpleTmStatisticsService implements TmStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    Map<String, Integer> domains = new HashMap<>();

    @Override
    public int rejected(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getRejected();
    }

    @Override
    public int accepted(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getAccepted();
    }

    @Override
    public List<String> predominantDomain(UserDTO tm) {
        List<UserDTO> allUsers = userService.findAllWithLeader(tm.getEmail());
        Set<CourseDTO> courses = new HashSet<>();

        allUsers.forEach(u -> courses.addAll(u.getCourses()));
        domains.clear();

        courses.forEach(c -> addDomain(c.getDomain()));

        List<SortCourse> toSort = new ArrayList<>();

        domains.forEach((c, i) -> {
            SortCourse course = new SortCourse();
            course.setName(c);
            course.setNr(i);
            toSort.add(course);
        });

        Collections.sort(toSort, (c1, c2) -> {
            if (c1.getNr() >= c2.getNr()) {
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
            domains.put(domain,1);
            return;
        }

        domains.put(domain, domains.get(domain) + 1);
    }
}
