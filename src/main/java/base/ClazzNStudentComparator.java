package base;

import java.util.Comparator;

public class ClazzNStudentComparator implements Comparator<Clazz> {
    @Override
    public int compare(Clazz o1, Clazz o2) {
        if(o1.getNumberStudent() == o2.getNumberStudent()) return 0;
        return o1.getNumberStudent() < o2.getNumberStudent() ? 1 : -1;
    }
}
