package base;

import java.util.Comparator;

public class StudentHeightComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getHeight() == o2.getHeight()) return 0;
        return o1.getHeight() < o2.getHeight() ? 1 : -1;
    }
}
