package base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Clazz implements Serializable {
    private String classId;
    private String name;
    private transient int numberStudent;
    private transient Set<Student> students = new HashSet<>();

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public int getNumberStudent() {
        return students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clazz)) return false;
        Clazz clazz = (Clazz) o;
        return getClassId().equals(clazz.getClassId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassId());
    }

}
