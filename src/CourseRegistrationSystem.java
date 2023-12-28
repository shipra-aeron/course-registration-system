import java.util.*;

public class CourseRegistrationSystem {
    private final Map<String, Course> courseMapById = new HashMap<>();
    private final Map<String, Course> courseMapByName = new HashMap<>();
    private final Map<String, List<String>> studentCourseMap = new HashMap<>();
    private final Map<String, Integer> studentCreditsMap = new HashMap<>();
    private final Set<String> departments = new TreeSet<>();


    public boolean createCourse(String courseId, String name, int credits) {
        if (isValidCourseId(courseId) && courseMapById.putIfAbsent(courseId, new Course(courseId, name, credits)) == null) {
            courseMapByName.put(name, courseMapById.get(courseId));
            departments.add(courseId.substring(0, 3));
            return true;
        }
        return false;
    }

    public boolean registerForCourse(String studentId, String courseId) {
        Course course = courseMapById.get(courseId);
        if (course == null || course.credits >= 24) {
            return false;
        }

        List<String> studentCourses = studentCourseMap.computeIfAbsent(studentId, k -> new ArrayList<>());
        int credits = studentCreditsMap.getOrDefault(studentId, 0);
        if (studentCourses.contains(courseId) || credits + course.credits > 24) {
            return false;
        }

        studentCourses.add(courseId);
        studentCourseMap.put(studentId, studentCourses);
        studentCreditsMap.put(studentId, credits + course.credits);
        return true;
    }

    public String getPairedStudents() {
        List<Set<String>> pairedStudents = new ArrayList<>();
        ArrayList<String> studentIds = new ArrayList<>(studentCourseMap.keySet());

        Collections.sort(studentIds);

        for (int i = 0; i < studentIds.size() - 1; i++) {
            for (int j = i + 1; j < studentIds.size(); j++) {
                if (sameCourse(studentCourseMap.get(studentIds.get(i)), studentCourseMap.get(studentIds.get(j)))) {
                    Set<String> pair = new TreeSet<>(Arrays.asList(studentIds.get(i), studentIds.get(j)));
                    pairedStudents.add(pair);
                }
            }
        }

        return pairedStudents.toString();
    }

    private boolean sameCourse(List<String> cList1, List<String> cList2) {
        return cList1.stream().anyMatch(cList2::contains);
    }

    private boolean isValidCourseId(String courseId) {
        return courseId.length() == 6 && !courseMapById.containsKey(courseId) && !courseMapByName.containsKey(courseId);
    }

    private static class Course {
        String courseId;
        String name;
        int credits;

        public Course(String courseId, String name, int credits) {
            this.courseId = courseId;
            this.name = name;
            this.credits = credits;
        }
    }
}
