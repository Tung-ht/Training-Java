package base;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Student implements Serializable {
    private String studentId;
    private String name;
    private LocalDate birthDate;
    private transient int age;
    private char gender; // Female: F, Male: M
    private float height;
    private float weight;
    private String classId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStudentId().equals(student.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId());
    }
}
