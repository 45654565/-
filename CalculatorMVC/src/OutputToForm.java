import javax.swing.*;
import java.util.Objects;

// класс выводит на форму результаты расчета
public class OutputToForm implements IOutputResult {
    JTextField expressionField;
    DefaultListModel model;

    public OutputToForm(JTextField expressionField, DefaultListModel model) {
        this.expressionField = expressionField;
        this.model = model;
    }

    @Override
    public void show(String expression, String result) {
        result = removeTrailingZeroes(result);
        if(Objects.equals(result, "0"))
            result = "0";
        if(result.charAt(result.length() - 1) == ',')
            result = result.substring(0, result.length() - 1 );
        expressionField.setText(result);
        int n = model.getSize();
        model.add(n, expression + " = " + result);
    }

    String removeTrailingZeroes(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '0') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
