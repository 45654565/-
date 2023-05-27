import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class form {
    private JTextField expressionField;
    private JList logArea;
    private JButton b_bl;
    private JButton b_br;
    private JButton b_c;
    private JButton b_back;
    private JButton b_pow1;
    private JButton b_pow2;
    private JButton b_mod;
    private JButton b_div;
    private JButton b_7;
    private JButton b_8;
    private JButton b_9;
    private JButton b_mult;
    private JButton b_4;
    private JButton b_5;
    private JButton b_6;
    private JButton b_minus;
    private JButton b_1;
    private JButton b_2;
    private JButton b_3;
    private JButton b_plus;
    private JButton b_div2;
    private JButton b_0;
    private JButton b_comma;
    private JButton b_result;
    private JPanel mainPanel;
    private JScrollPane panelLog;

    enum Ops{
        INITIAL,
        OP,
        NUMBER,
        BRACKETS_LEFT,
        BRACKETS_RIGHT,
        COMMA
    }

    Stack<Ops> ops_seq = new Stack<>();
    Ops last = Ops.INITIAL;
    int bracket_counter = 0;
    int comma = 0;

    Controller controller;

    DefaultListModel model;

    // класс формы
    public form(Controller controller) {
        model = new DefaultListModel();

        logArea.setModel(model);

        this.controller = controller;
        b_result.addActionListener(e -> {
            if(last == Ops.INITIAL)
                return;
            String expression = expressionField.getText();
            if(bracket_counter != 0){
                JOptionPane.showMessageDialog(null, "Неправильно расставлены скобки");
                return;
            }
            if(ops_seq.peek() == Ops.NUMBER || ops_seq.peek() == Ops.BRACKETS_RIGHT){
                controller.setData(expression, new OutputToForm(expressionField, model));
                bracket_counter = 0;
                ops_seq.clear();
                last = Ops.INITIAL;
                comma = 0;
            }else{
                JOptionPane.showMessageDialog(null, "Неправильное выражение");
            }
        });
        b_bl.addActionListener(e -> {
            String expression = expressionField.getText();
            if(last == Ops.INITIAL) {
                expression = "(";
                ops_seq.push(Ops.BRACKETS_LEFT);
                last = Ops.BRACKETS_LEFT;
                expressionField.setText(expression);
                bracket_counter++;
                comma = 0;
            }else if(last == Ops.OP){
                expression += "(";
                ops_seq.push(Ops.BRACKETS_LEFT);
                last = Ops.BRACKETS_LEFT;
                bracket_counter++;
                expressionField.setText(expression);
            }
        });
        b_br.addActionListener(e -> {
            String expression = expressionField.getText();
            if(bracket_counter <= 0)
                return;
            if(last == Ops.NUMBER || last == Ops.BRACKETS_RIGHT) {
                expression += ")";
                ops_seq.push(Ops.BRACKETS_RIGHT);
                last = Ops.BRACKETS_RIGHT;
                expressionField.setText(expression);
                bracket_counter--;
            }
        });
        b_c.addActionListener(e -> {
            expressionField.setText("0");
            bracket_counter = 0;
            ops_seq.clear();
            last = Ops.INITIAL;
            comma = 0;
        });
        b_back.addActionListener(e -> {
            String expression = expressionField.getText();
            expression = expression.substring(0, expression.length() - 1);
            if(ops_seq.empty()) {
                expressionField.setText("0");
                bracket_counter = 0;
                last = Ops.INITIAL;
            }else{
                Ops t = ops_seq.pop();
                if(t == Ops.BRACKETS_LEFT)
                    bracket_counter--;
                else if(t == Ops.BRACKETS_RIGHT)
                    bracket_counter++;
                else  if(t == Ops.COMMA)
                    comma = 0;
                expressionField.setText(expression);
                if(ops_seq.empty()) {
                    expressionField.setText("0");
                    bracket_counter = 0;
                    last = Ops.INITIAL;
                    comma = 0;
                } else{
                    t = ops_seq.pop();
                    last = t;
                    ops_seq.push(last);
                }
            }
        });

        b_comma.addActionListener(e -> {
            String expression = expressionField.getText();
            if(last == Ops.NUMBER && comma == 0) {
                expression += ",";
                ops_seq.push(Ops.COMMA);
                last = Ops.COMMA;
                expressionField.setText(expression);
                comma = 1;
            }
        });

        b_0.addActionListener(e -> {
            addNumber(b_0.getText());
        });

        b_1.addActionListener(e -> {
            addNumber(b_1.getText());
        });

        b_2.addActionListener(e -> {
            addNumber(b_2.getText());
        });

        b_3.addActionListener(e -> {
            addNumber(b_3.getText());
        });

        b_4.addActionListener(e -> {
            addNumber(b_4.getText());
        });

        b_5.addActionListener(e -> {
            addNumber(b_5.getText());
        });

        b_6.addActionListener(e -> {
            addNumber(b_6.getText());
        });

        b_7.addActionListener(e -> {
            addNumber(b_7.getText());
        });

        b_8.addActionListener(e -> {
            addNumber(b_8.getText());
        });

        b_9.addActionListener(e -> {
            addNumber(b_9.getText());
        });

        b_div2.addActionListener(e -> {
            addOp(b_div2.getText());
        });

        b_plus.addActionListener(e -> {
            addOp(b_plus.getText());
        });

        b_minus.addActionListener(e -> {
            addOp(b_minus.getText());
        });

        b_mult.addActionListener(e -> {
            addOp(b_mult.getText());
        });

        b_div.addActionListener(e -> {
            addOp(b_div.getText());
        });

        b_mod.addActionListener(e -> {
            addOp(b_mod.getText());
        });

        b_pow2.addActionListener(e -> {
            addOp(b_pow2.getText());
        });

        b_pow1.addActionListener(e -> {
            addOp(b_pow1.getText());
        });
    }

    private void addNumber(String number) {
        String expression = expressionField.getText();
        if(last == Ops.INITIAL) {
            expression = number;
            ops_seq.push(Ops.NUMBER);
            last = Ops.NUMBER;
            expressionField.setText(expression);
        }else if(last == Ops.OP || last == Ops.COMMA || last == Ops.BRACKETS_LEFT || last == Ops.NUMBER){
            expression += number;
            ops_seq.push(Ops.NUMBER);
            last = Ops.NUMBER;
            expressionField.setText(expression);
        }
    }

    private void addOp(String op) {
        String expression = expressionField.getText();
        if(last == Ops.NUMBER || last == Ops.BRACKETS_RIGHT) {
            expression += op;
            ops_seq.push(Ops.OP);
            last = Ops.OP;
            expressionField.setText(expression);
            comma = 0;
        }
    }

    public JList getLogArea() {
        return logArea;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
