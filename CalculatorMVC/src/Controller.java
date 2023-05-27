// контроллер, контролирует вывод и обработку данных
public class Controller {
    View view;
    Model model;

    public Controller() {
        view = new View(this);
        model = new Model();
    }

    public void showForm(){
        view.show();
    }

    public void setData(String expr, IOutputResult result){
        String res = model.calculation(expr);
        result.show(expr, res);
    }
}
