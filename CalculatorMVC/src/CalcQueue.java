
// структура данных очередь, первый пришел, первый ушел
public class CalcQueue <AnyType>{
    // голова и хвост очереди
    public DoubleNode<AnyType> head;
    public DoubleNode<AnyType> tail;

    public CalcQueue() {
        head = new DoubleNode<>();
        tail = new DoubleNode<>();
        head.next = tail;
        head.prev = null;
        tail.next = null;
        tail.prev = head;
    }

    // вставка элементов
    public void enqueue(AnyType x) {

        DoubleNode<AnyType> temp = new DoubleNode<>();

        temp.data = x;
        temp.prev = tail.prev;
        temp.next = tail;
        (temp.prev).next = temp;
        (temp.next).prev = temp;
    }

    // удаление элементов
    public AnyType dequeue() {

        DoubleNode<AnyType> temp = new DoubleNode<>();

        if(isEmpty()) {
            return null;
        }
        else {
            temp.data = head.next.data;
            head.next = head.next.next;
            head.next.prev = head;

            return temp.data;
        }
    }

    // смотрим на первый элемент без удаления
    public AnyType peek() {

        if(isEmpty()) {
            return null;
        }
        else {
            return head.next.data;
        }
    }

    // проверка на пустоту
    public boolean isEmpty() {
        return head.next == tail;
    }
}
