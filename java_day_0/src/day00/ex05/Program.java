package day00.ex05;


import java.util.*;

public class Program {
    private final static int MAX_STUDENTS = 10;
    private final static int MAX_LENGTH_OF_NAME = 10;
    private final static int MAX_NUM_OF_CLASSES = 10;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        List<String> students = new ArrayList<>();
        int[] classTimesPerDay = new int[8];
        String[] daysOfWeek = new String[8];

        while (!line.equals(".") && students.size() < MAX_STUDENTS) {
            nameLengthCheck(line);

            students.add(line);
            line = sc.nextLine();
        }

        HashMap<String, Integer> dayIndexMap = new HashMap<>();
        dayIndexMap.put("MO", 7);
        dayIndexMap.put("TU", 1);
        dayIndexMap.put("WE", 2);
        dayIndexMap.put("TH", 3);
        dayIndexMap.put("FR", 4);
        dayIndexMap.put("SA", 5);
        dayIndexMap.put("SU", 6);

        while (sc.hasNextInt()) {
            int classTime = sc.nextInt();
            String day = sc.next();
            updateClassSchedule(classTimesPerDay, daysOfWeek, classTime, dayIndexMap.get(day));
        }

        int[][][][] attendance = new int[MAX_STUDENTS][30 + 1][MAX_NUM_OF_CLASSES + 1][1];
        sc.next();
        for (String name = sc.next(); !".".equals(name); name = sc.next()) {
            int studentIndex = students.indexOf(name);
            int classTime = sc.nextInt();
            int date = sc.nextInt();
            String status = sc.next();
            int attendanceValue = getAttendanceValue(status);
            attendance[studentIndex][date][classTime][0] = attendanceValue;
        }

        printHeader(classTimesPerDay, daysOfWeek);

        for (var student : students) {
            printStudentAttendance(student, classTimesPerDay, attendance, students.indexOf(student));
        }
    }

    private static void updateClassSchedule(int[] classTimesPerDay, String[] daysOfWeek, int classTime, int dayIndex) {
        if (classTimesPerDay[dayIndex] == 0) {
            classTimesPerDay[dayIndex] = classTime;
            daysOfWeek[dayIndex] = getDayAbbreviation(dayIndex);
        } else {
            classTimesPerDay[dayIndex] += classTime;
        }
    }

    private static String getDayAbbreviation(int dayIndex) {
        String[] abbreviations = {"", "TU", "WE", "TH", "FR", "SA", "SU", "MO"};
        return abbreviations[dayIndex];
    }

    private static void nameLengthCheck(String line) {
        if (line.length() > MAX_LENGTH_OF_NAME) {
            System.err.println("Illegal Argument!");
            System.exit(-1);
        }
    }

    private static void printHeader(int[] classTimesPerDay, String[] daysOfWeek) {
        for (int l = 0; l < Program.MAX_LENGTH_OF_NAME; ++l) {
            System.out.print(" ");
        }

        for (int count = 0; count <= 28; count += 7) {
            for (int k = 1; k < 8; ++k) {
                if (classTimesPerDay[k] != 0 && k + count < 31) {
                    if (k + count >= 10) {
                        System.out.printf("%d:00 %s %d|", classTimesPerDay[k], daysOfWeek[k], k + count);
                    } else {
                        System.out.printf(" %d:00 %s %d|", classTimesPerDay[k], daysOfWeek[k], k + count);
                    }
                }
            }
        }
        System.out.println();
    }

    private static void printStudentAttendance(String name, int[] classTimesPerDay, int[][][][] attendance, int studentIndex) {
        System.out.printf("%-" + Program.MAX_LENGTH_OF_NAME + "s", name);

        for (int count = 0; count <= 28; count += 7) {
            for (int k = 1; k < 8; ++k) {
                if (classTimesPerDay[k] != 0 && k + count < 31) {
                    int attendanceValue = attendance[studentIndex][k + count][classTimesPerDay[k]][0];
                    System.out.printf("%10d|", attendanceValue);
                }
            }
        }
        System.out.println();
    }


    private static int getAttendanceValue(String status) {
        if ("HERE".equals(status)) {
            return 1;
        } else if ("NOT_HERE".equals(status)) {
            return -1;
        } else {
            return 0;
        }
    }
}
