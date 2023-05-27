// класс ноды для очереди
public class DoubleNode<AnyType> {
    // Нода хранит данные элемента, сыылки на предыдущую и следующую ноды
    public AnyType data;
    public DoubleNode<AnyType> next;
    public DoubleNode<AnyType> prev;

    public DoubleNode() {
        data = null;
        next = null;
        prev = null;
    }
}
