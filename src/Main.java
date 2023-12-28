import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[][] queries = new String[5][4];
        String[] result = new String[queries.length];
        CourseRegistrationSystem crs = new CourseRegistrationSystem();
        for (int i = 0; i < queries.length; i++) {
            String[] query = queries[i];
            switch (query[0]) {
                case "CREATE_COURSE" ->
                        result[i] = crs.createCourse(query[1], query[2], Integer.parseInt(query[3])) ? "true" : "false";
                case "REGISTER_FOR_COURSE" -> result[i] = crs.registerForCourse(query[1], query[2]) ? "true" : "false";
                case "GET_PAIRED_STUDENTS" -> result[i] = crs.getPairedStudents();
                case "GET_GPA" -> result[i] = crs.calculateGpa(Integer.parseInt(query[1]), query[2]);
                default -> result[i] = ""; // Handle unknown queries
            }
        }
    }
}
