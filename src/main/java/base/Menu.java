package base;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Set<Student> studentSet = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getStudentId().compareTo(o2.getStudentId());
        }
    });

    private static final Set<Clazz> clazzSet = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(Clazz o1, Clazz o2) {
            return o1.getClassId().compareTo(o2.getClassId());
        }
    });

    private static final Map<Clazz, Set<Student>> clazzMapping = new TreeMap<>(new Comparator<>() {
        @Override
        public int compare(Clazz o1, Clazz o2) {
            if (o1.getNumberStudent() == o2.getNumberStudent()) return 0;
            else return o1.getNumberStudent() < o2.getNumberStudent() ? 1 : -1;
        }
    });

    private static boolean checkIfClassIdExists(String classId) {
        Clazz clazz = new Clazz();
        clazz.setClassId(classId);
        return clazzSet.contains(clazz);
    }

    private static boolean checkIfStudentIdExists(String studentId) {
        Student student = new Student();
        student.setStudentId(studentId);
        return studentSet.contains(student);
    }


    // function 1
    private static void newClass() {
        Clazz clazz = new Clazz();
        // read class id
        while (true) {
            System.out.println("Class Id:");
            String classId = scanner.nextLine();
            if (!checkIfClassIdExists(classId)) {
                clazz.setClassId(classId);
                break;
            } else System.out.println("This class id already exists! Input again!\n");
        }

        System.out.println("Class name:");
        clazz.setName(scanner.nextLine());

        clazzSet.add(clazz);
        clazzMapping.put(clazz, clazz.getStudents());
        System.out.println("Successfully!\n");

        String c;
        do {
            System.out.println("Input again? Y/N");
            c = scanner.nextLine();
            if (c.equalsIgnoreCase("y")) {
                newClass();
            } else if (c.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.println("Invalid! Please retype!\n");
            }
        } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
    }


    // function 2
    private static void newStudent() {
        Student student = new Student();
        // read student id
        while (true) {
            System.out.println("Student Id:");
            String studentId = scanner.nextLine();
            if (!checkIfStudentIdExists(studentId)) {
                student.setStudentId(studentId);
                break;
            } else System.out.println("This student id already exists! Input again!\n");
        }

        System.out.println("Name:");
        student.setName(scanner.nextLine());

        // read birth date
        while (true) {
            System.out.println("Birth date: (yyyy-MM-dd, E.g: 2000-07-15)");
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate dt = LocalDate.parse(scanner.nextLine(), fmt);
                student.setBirthDate(dt);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time format! Input again!\n");
            }
        }

        // read gender
        String c1;
        do {
            System.out.println("Gender: (M: Male, F: Female)");
            c1 = scanner.nextLine();
            if (c1.equalsIgnoreCase("m")) {
                student.setGender('M');
            } else if (c1.equalsIgnoreCase("f")) {
                student.setGender('F');
            } else {
                System.out.println("!!!Error. Please retype!\n");
            }
        } while (!c1.equalsIgnoreCase("m") && !c1.equalsIgnoreCase("f")); // c!= y va c!=n

        // read height
        while (true) {
            System.out.println("Height: (cm, E.g: 175)");
            try {
                student.setHeight(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid! Input again!\n");
            }
        }

        // read weight
        while (true) {
            System.out.println("Weight: (kg, E.g: 55)");
            try {
                student.setWeight(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid! Input again!\n");
            }
        }

        // read class id
        while (true) {
            System.out.println("Class id: ");
            String classId = scanner.nextLine();
            if (checkIfClassIdExists(classId)) {
                student.setClassId(classId);
                break;
            } else System.out.println("This class id does not exist! Input again!\n");
        }

        studentSet.add(student);
        // now find out the clazz of this student, to update data of the class
        Clazz thisClazz = null;
        for (Clazz c : clazzSet) {
            if (c.getClassId().equals(student.getClassId())) {
                thisClazz = c;
                break;
            }
        }
        // now add this student to student set of the class
        thisClazz.getStudents().add(student);
        System.out.println("Successfully!\n");

        String c2;
        do {
            System.out.println("Input again? Y/N");
            c2 = scanner.nextLine();
            if (c2.equalsIgnoreCase("y")) {
                newStudent();
            } else if (c2.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.println("!!!Error. Please retype!\n");
            }
        } while (!c2.equalsIgnoreCase("y") && !c2.equalsIgnoreCase("n")); // c!= y va c!=n
    }


    // function 3
    private static void updateClass() {
        String classId;
        while (true) {
            System.out.println("Enter class id:");
            classId = scanner.nextLine();
            if (checkIfClassIdExists(classId)) {
                System.out.println("Class is found!");
                Clazz thisClazz = null;
                for (Clazz c : clazzSet) {
                    if (c.getClassId().equals(classId)) {
                        thisClazz = c;
                        System.out.printf("%-10s%-30s%-20s\n", "Class Id", "Class name", "Number of stds");
                        System.out.printf("%-10s%-30s%-20d\n", c.getClassId(), c.getName(), c.getNumberStudent());
                        break;
                    }
                }
                System.out.println("Update class name: ");
                thisClazz.setName(scanner.nextLine());
                System.out.println("Successfully!");
                break;
            } else {
                System.out.println("This class id does not exist! Input again!\n");
            }
        }
        String c;
        do {
            System.out.println("Update another class? Y/N");
            c = scanner.nextLine();
            if (c.equalsIgnoreCase("y")) {
                updateClass();
            } else if (c.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.println("!!!Error. Please retype!\n");
            }
        } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
    }


    // function 4
    private static void updateStudent() {
        String studentId;
        while (true) {
            System.out.println("Enter student id:");
            studentId = scanner.nextLine();
            if (checkIfStudentIdExists(studentId)) {
                System.out.println("Student is found!");
                Student thisStudent = null;
                for (Student s : studentSet) {
                    if (s.getStudentId().equals(studentId)) {
                        thisStudent = s;
                        System.out.printf("%-10s%-40s%-20s%-10s%-10s%-10s%-10s%-20s\n", "Std Id", "Name", "Birth date", "Age",
                                "Gender", "Height", "Weight", "Class Id");
                        System.out.printf("%-10s%-40s%-20s%-10d%-10c%-10.1f%-10.1f%-20s\n", s.getStudentId(), s.getName(),
                                s.getBirthDate().toString(), s.getAge(), s.getGender(),
                                s.getHeight(), s.getWeight(), s.getClassId());
                        break;
                    }
                }
                // update name
                System.out.println("Name:");
                thisStudent.setName(scanner.nextLine());

                // update birth date
                while (true) {
                    System.out.println("Birth date: (yyyy-MM-dd, E.g: 2000-07-15)");
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dt = LocalDate.parse(scanner.nextLine(), fmt);
                        thisStudent.setBirthDate(dt);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date-time format! Input again!\n");
                    }
                }

                // update gender
                String c1;
                do {
                    System.out.println("Gender: (M: Male, F: Female)");
                    c1 = scanner.nextLine();
                    if (c1.equalsIgnoreCase("m")) {
                        thisStudent.setGender('M');
                    } else if (c1.equalsIgnoreCase("f")) {
                        thisStudent.setGender('F');
                    } else {
                        System.out.println("!!!Error. Please retype!\n");
                    }
                } while (!c1.equalsIgnoreCase("m") && !c1.equalsIgnoreCase("f")); // c!= y va c!=n

                // update height
                while (true) {
                    System.out.println("Height: (cm, E.g: 175)");
                    try {
                        thisStudent.setHeight(Float.parseFloat(scanner.nextLine()));
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid! Input again!\n");
                    }
                }

                // update weight
                while (true) {
                    System.out.println("Weight: (kg, E.g: 55)");
                    try {
                        thisStudent.setWeight(Float.parseFloat(scanner.nextLine()));
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid! Input again!\n");
                    }
                }

                // update class id and related classes
                while (true) {
                    System.out.println("Class id: ");
                    String classId = scanner.nextLine();
                    if (checkIfClassIdExists(classId)) {
                        // remove the student in old clazz
                        // and add it to new clazz
                        for (Clazz clz : clazzSet) {
                            if (clz.getClassId().equals(thisStudent.getClassId())) {
                                clz.getStudents().remove(thisStudent);
                            }
                            if (clz.getClassId().equals(classId)) {
                                clz.getStudents().add(thisStudent);
                            }
                        }
                        thisStudent.setClassId(classId);
                        break;
                    } else System.out.println("This class id does not exist! Input again!\n");
                }

                System.out.println("Successfully!");
                break;
            } else {
                System.out.println("This class id does not exist! Input again!\n");
            }
        }

        String c;
        do {
            System.out.println("Update another student? Y/N");
            c = scanner.nextLine();
            if (c.equalsIgnoreCase("y")) {
                updateStudent();
            } else if (c.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.println("!!!Error. Please retype!\n");
            }
        } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
    }


    // function 5
    // delete class and its students from clazzSet, studentSet and clazzMapping
    private static void deleteClass() {
        String classId;
        while (true) {
            System.out.println("Enter class id:");
            classId = scanner.nextLine();
            if (checkIfClassIdExists(classId)) {
                System.out.println("Class is found!");
                Clazz thisClazz = null;
                for (Clazz c : clazzSet) {
                    if (c.getClassId().equals(classId)) {
                        thisClazz = c;
                        System.out.printf("%-10s%-30s%-20s\n", "Class Id", "Class name", "Number of stds");
                        System.out.printf("%-10s%-30s%-20d\n", c.getClassId(), c.getName(), c.getNumberStudent());
                        break;
                    }
                }
                // confirm to delete
                String c;
                do {
                    System.out.println("Do you want to delete this class and all students in this class? Confirm: Y/N");
                    c = scanner.nextLine();
                    if (c.equalsIgnoreCase("y")) {
                        // delete all students from studentSet
                        List<Student> deleteStudents = new ArrayList<>();
                        for (Student s : studentSet) {
                            if (s.getClassId() == null) continue;
                            if (s.getClassId().equals(thisClazz.getClassId())) {
                                deleteStudents.add(s);
                            }
                        }
                        deleteStudents.forEach(studentSet::remove);
                        // delete this class from Set students of clazz object
                        clazzSet.remove(thisClazz);
                        clazzMapping.remove(thisClazz);
                        System.out.println("Successfully!");
                        return;
                    }
                    // not delete and back to menu
                    else if (c.equalsIgnoreCase("n")) {
                        return;
                    } else {
                        System.out.println("Invalid! Please retype!\n");
                    }
                } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
            } else {
                System.out.println("This class id does not exist! Input again!");
            }
        }
    }


    // function 6
    // delete student from studentSet and update related clazz and clazzMapping
    private static void deleteStudent() {
        String studentId;
        while (true) {
            System.out.println("Enter student id:");
            studentId = scanner.nextLine();
            if (checkIfStudentIdExists(studentId)) {
                System.out.println("Student is found!");
                Student thisStudent = null;
                for (Student s : studentSet) {
                    if (s.getStudentId().equals(studentId)) {
                        thisStudent = s;
                        System.out.printf("%-10s%-40s%-20s%-10s%-10s%-10s%-10s%-20s\n", "Std Id", "Name", "Birth date", "Age",
                                "Gender", "Height", "Weight", "Class Id");
                        System.out.printf("%-10s%-40s%-20s%-10d%-10c%-10.1f%-10.1f%-20s\n", s.getStudentId(), s.getName(),
                                s.getBirthDate().toString(), s.getAge(), s.getGender(),
                                s.getHeight(), s.getWeight(), s.getClassId());
                        break;
                    }
                }
                // confirm to delete
                String c;
                do {
                    System.out.println("Do you want to delete this student? Confirm: Y/N");
                    c = scanner.nextLine();
                    if (c.equalsIgnoreCase("y")) {
                        // delete student from studentSet
                        if (thisStudent.getClassId() == null) {
                            studentSet.remove(thisStudent);
                        }
                        // thisStudent.classId != null, then find the related clazz
                        else {
                            for (Student s : studentSet) {
                                if (s.getClassId() == null) continue;
                                if (s.getClassId().equals(thisStudent.getClassId())) {
                                    studentSet.remove(s);
                                    break;
                                }
                            }
                            // update related clazz
                            for (Clazz clz : clazzSet) {
                                if (clz.getClassId().equals(thisStudent.getClassId())) {
                                    clz.getStudents().remove(thisStudent);
                                    break;
                                }
                            }
                        }
                        System.out.println("Successfully!");
                        return;
                    }
                    // not delete and back to menu
                    else if (c.equalsIgnoreCase("n")) {
                        return;
                    } else {
                        System.out.println("Invalid! Please retype!\n");
                    }
                } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
            } else {
                System.out.println("This student id does not exist! Input again!");
            }
        }
    }


    // function 7
//    private static void exportJSON() {
//        System.out.println("Export to \"clazz.json\" and \"student.json\"");
//        try (JsonWriter clazzWriter = new JsonWriter(new FileWriter("G:\\clazz.json"))) {
//            clazzWriter.beginArray();
//            for(Clazz c: clazzSet){
//                clazzWriter.beginObject();
//                clazzWriter.name("classId").value(c.getClassId());
//                clazzWriter.name("name").value(c.getName());
//                clazzWriter.endObject();
//            }
//            clazzWriter.endArray();
//        } catch (IOException e){
//            System.out.println("Error! Back to menu!");
//            return;
//        }
//
//        try (JsonWriter studentWriter = new JsonWriter(new FileWriter("G:\\student.json"))) {
//            studentWriter.beginArray();
//            for(Student s: studentSet){
//                studentWriter.beginObject();
//                studentWriter.name("studentId").value(s.getStudentId());
//                studentWriter.name("name").value(s.getName());
//                studentWriter.name("birthDate").value(s.getBirthDate().toString());
//                studentWriter.name("gender").value(s.getGender());
//                studentWriter.name("height").value(s.getHeight());
//                studentWriter.name("weight").value(s.getWeight());
//                studentWriter.name("classId").value(s.getClassId());
//                studentWriter.endObject();
//            }
//            studentWriter.endArray();
//        } catch (IOException e){
//            System.out.println("Error! Back to menu!");
//            return;
//        }
//        System.out.println("Export data successfully!");
//    }
    private static void exportJSON() {
        System.out.println("Export to \"data.json\"");
        try (JsonWriter writer = new JsonWriter(new FileWriter("G:\\data.json"))) {
            writer.beginArray();
            Clazz[] clazzArray = clazzSet.toArray(new Clazz[0]);
            MergeSort<Clazz, ClazzNStudentComparator> clazzSorter = new MergeSort<>();
            clazzSorter.mergeSort(clazzArray, 0, clazzArray.length - 1, new ClazzNStudentComparator());
            for (Clazz c : clazzArray) {
                writer.beginObject();
                writer.name("classId").value(c.getClassId());
                writer.name("name").value(c.getName());
                writer.name("number of student").value(c.getNumberStudent());
                writer.name("students");
                writer.beginArray();
                Student[] studentArray = c.getStudents().toArray(new Student[0]);
                MergeSort<Student, StudentHeightComparator> studentSorter = new MergeSort<>();
                studentSorter.mergeSort(studentArray, 0, studentArray.length - 1, new StudentHeightComparator());
                for (Student s : studentArray) {
                    writer.beginObject();
                    writer.name("studentId").value(s.getStudentId());
                    writer.name("name").value(s.getName());
                    writer.name("birthDate").value(s.getBirthDate().toString());
                    writer.name("gender").value(s.getGender());
                    writer.name("height").value(s.getHeight());
                    writer.name("weight").value(s.getWeight());
                    writer.name("classId").value(s.getClassId());
                    writer.endObject();
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();
        } catch (IOException e) {
            System.out.println("Error! Back to menu!");
            return;
        }
        System.out.println("Export data successfully!");
    }


    // function 8
    private static void exportBin() {
        System.out.println("Export to \"clazz.bin\" and \"student.bin\"");
        try (FileOutputStream fosClazz = new FileOutputStream("G:\\clazz.bin");
             FileOutputStream fosStudent = new FileOutputStream("G:\\student.bin");
             ObjectOutputStream oosClazz = new ObjectOutputStream(fosClazz);
             ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent)) {
            //
            for (Clazz c : clazzSet) {
                oosClazz.writeObject(c);
                oosClazz.flush();
            }
            for (Student s : studentSet) {
                oosStudent.writeObject(s);
                oosStudent.flush();
            }
            System.out.println("Export data successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error! Back to menu!");
        }
//        try(FileInputStream fosClazz = new FileInputStream("G:\\clazz.bin");
//            ObjectInputStream oosClazz = new ObjectInputStream(fosClazz)){
//            //
//            Clazz clazz = (Clazz) oosClazz.readObject();
//            System.out.println(clazz.getClassId() + clazz.getName());
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Error!");
//        }
    }


    // function 9
    private static void importJSON() {
        System.out.println("Import data from \"student.json\" & \"clazz.json\":");
        studentSet.clear();
        clazzSet.clear();
        clazzMapping.clear();

        // import clazz to clazzSet
        try (JsonReader clazzReader = new JsonReader(new FileReader("G:\\clazz.json"))) {
            clazzReader.beginArray();
            while (clazzReader.hasNext()) {
                Clazz clazz = new Clazz();
                clazzReader.beginObject();
                while (clazzReader.hasNext()) {
                    String field = clazzReader.nextName();
                    // id notnull; name nullable
                    switch (field) {
                        case "classId" -> clazz.setClassId(clazzReader.nextString());
                        case "name" -> {
                            try {
                                clazz.setName(clazzReader.nextString());
                            } catch (Exception e) {
                                // name = null
                                clazzReader.skipValue();
                            }
                        }
                        default -> clazzReader.skipValue(); //avoid some unhandle events
                    }
                }
                clazzReader.endObject();
                clazzSet.add(clazz);
            }
            clazzReader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error! Back to menu!");
            return;
        }

        // import student to student set
        try (JsonReader studentReader = new JsonReader(new FileReader("G:\\student.json"))) {
            studentReader.beginArray();
            while (studentReader.hasNext()) {
                Student student = new Student();
                studentReader.beginObject();
                while (studentReader.hasNext()) {
                    String field = studentReader.nextName();
                    // id, name, birthDate, gender notnull; height, weight, class id nullable
                    switch (field) {
                        case "studentId" -> student.setStudentId(studentReader.nextString());
                        case "name" -> student.setName(studentReader.nextString());
                        case "birthDate" -> {
                            String birthDate = studentReader.nextString();
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dt = LocalDate.parse(birthDate, fmt);
                            student.setBirthDate(dt);
                        }
                        case "gender" -> {
                            String gender = studentReader.nextString();
                            if (gender.equalsIgnoreCase("m")) {
                                student.setGender('M');
                            } else {
                                student.setGender('F');
                            }
                        }
                        case "height" -> {
                            try {
                                student.setHeight((float) studentReader.nextDouble());
                            } catch (Exception e) {
                                // height = null
                                studentReader.skipValue();
                            }
                        }
                        case "weight" -> {
                            try {
                                student.setWeight((float) studentReader.nextDouble());
                            } catch (Exception e) {
                                // weight = null
                                studentReader.skipValue();
                            }
                        }
                        case "classId" -> {
                            try {
                                student.setClassId(studentReader.nextString());
                            } catch (Exception e) {
                                // classId = null
                                studentReader.skipValue();
                            }
                        }
                        default -> studentReader.skipValue(); //avoid some unhandle events
                    }
                }
                studentReader.endObject();
                studentSet.add(student);
                // update related clazz
                for (Clazz clz : clazzSet) {
                    if (clz.getClassId().equals(student.getClassId())) {
                        clz.getStudents().add(student);
                    }
                }
            }
            studentReader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error! Back to menu!");
            return;
        }
        System.out.println("Import data successfully!");
    }

    private static void printClasses() {
        System.out.printf("%-10s%-30s%-20s\n", "Class Id", "Class name", "Number of stds");
        for (Clazz tmp : clazzSet) {
            System.out.printf("%-10s%-30s%-20d\n", tmp.getClassId(), tmp.getName(), tmp.getNumberStudent());
        }
    }

    private static void printStudents() {
        System.out.printf("%-10s%-40s%-20s%-10s%-10s%-10s%-10s%-20s\n", "Std Id", "Name", "Birth date", "Age",
                "Gender", "Height", "Weight", "Class Id");
        for (Student tmp : studentSet) {
            System.out.printf("%-10s%-40s%-20s%-10d%-10c%-10.1f%-10.1f%-20s\n", tmp.getStudentId(), tmp.getName(),
                    tmp.getBirthDate().toString(), tmp.getAge(), tmp.getGender(),
                    tmp.getHeight(), tmp.getWeight(), tmp.getClassId());
        }
    }

    public static void menu() {
        while (true) {
            System.out.println("\n\n***************************************");
            System.out.println("**       STUDENT MANAGEMENT          **");
            System.out.println();
            System.out.println("***************MENU********************");
            System.out.println("**	1.  Enter a new class.");
            System.out.println("**	2.  Enter a new student.");
            System.out.println("**	3.  Update class by id.");
            System.out.println("** 	4.  Update student by id.");
            System.out.println("**	5.  Delete class by id.");
            System.out.println("**	6.  Delete student by id.");
            System.out.println("** 	7.  Export data into JSON file.");
            System.out.println("** 	8.  Export data into Binary file.");
            System.out.println("** 	9.  Import data from JSON file.");
            System.out.println("** 	10. Print classes.");
            System.out.println("** 	11. Print students.");
            System.out.println("** 	0. Exit program.");
            System.out.println("***************************************");
            System.out.println("** 	Enter your choice:");

            String key = scanner.nextLine();

            switch (key) {
                case "1" -> {
                    System.out.println("--->Enter a new class:");
                    newClass();
                }
                case "2" -> {
                    System.out.println("--->Enter a new student:");
                    newStudent();
                }
                case "3" -> {
                    System.out.println("--->Update class by id:");
                    updateClass();
                }
                case "4" -> {
                    System.out.println("--->Update student by id:");
                    updateStudent();
                }
                case "5" -> {
                    System.out.println("--->Delete class by id:");
                    deleteClass();
                }
                case "6" -> {
                    System.out.println("--->Delete student by id:");
                    deleteStudent();
                }
                case "7" -> {
                    System.out.println("--->Export data into JSON file:");
                    exportJSON();
                }
                case "8" -> {
                    System.out.println("--->Export data into Binary file:");
                    exportBin();
                }
                case "9" -> {
                    System.out.println("--->Import data from JSON file:");
                    importJSON();
                }
                case "10" -> {
                    System.out.println("--->print classes:");
                    printClasses();
                }
                case "11" -> {
                    System.out.println("--->print students:");
                    printStudents();
                }
                case "0" -> {
                    String c;
                    do {
                        System.out.println("Exit the program? Y/N");
                        c = scanner.nextLine();
                        if (c.equalsIgnoreCase("y")) {
                            return; // out menu
                        } else if (c.equalsIgnoreCase("n")) {
                            break; // break do-while loop
                        } else {
                            System.out.println("Invalid! Please retype!\n");
                        }
                    } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")); // c!= y va c!=n
                }
                default -> System.out.println("There is no function like this!\n\n");
            }
        }
    }
}
