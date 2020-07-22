import java.util.NoSuchElementException;

/**
 * <p>Реалізувати свій ArrayList який імплементує інтерфейс List. Дотриматися основних вимог щодо
 * реалізації ArrayList (default capacity, newCapacity...)</p>
 */
public class ArrayList<T> implements List<T> {
    private int size = 0;
    private T[] myList = (T[]) new Object[10];

    public void ensureCapacity(int expected, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (expected > myList.length) {
            T[] oldData = myList;
            int newSize = oldData.length + (oldData.length >> 1);
            myList = (T[]) new Object[newSize];
            System.arraycopy(oldData, index, myList, index == 0 ? 0 : index + 1, size - index);
        } else if (expected > size) {
            System.arraycopy(myList, index, myList, index == 0 ? 0 : index + 1, size - index);
        }

        if (expected < size) {
            int numMoved = size - index - 1;
            System.arraycopy(myList, index + 1, myList, index, numMoved);
        }
    }

    @Override
    public void add(T value) {
        ensureCapacity(size + 1, 0);
        myList[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        ensureCapacity(size + 1, index);
        myList[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            ensureCapacity(size + 1, size);
            myList[size++] = list.get(i);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return myList[index];
    }

    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        myList[index] = value;
    }

    @Override
    public T remove(int index) {
        T value = myList[index];

        ensureCapacity(size - 1, index);
        myList[size--] = null;

        if (size == 0) {
            myList[size] = null;
        }

        if (isEmpty()) {
            return null;
        }

        return value;
    }

    @Override
    public T remove(T t) {
        T value = t;
        boolean isFounded = false;

        for (int i = 0; i < size(); i++) {
            if (myList[i] == t || myList[i].equals(t)) {
                ensureCapacity(size - 1, i);
                myList[size--] = null;
                isFounded = true;
                break;
            }
        }

        if (isFounded) {
            return value;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean isEmpty = true;

        for (int i = 0; i < myList.length; i++) {
            if (myList[i] != null) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }
}
