import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

// класс организует вывод формы
public class View {
    Controller controller;
    public View(Controller controller){
        this.controller = controller;
    }

    public void show(){
        JFrame frame = new JFrame( "Калькулятор" );

        JMenuBar jmb = new JMenuBar();

        form form = new form(controller);
        frame.setContentPane( form.getMainPanel() );
        frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        frame.pack();
        JMenu jmFile = new JMenu("Файл");
        JMenuItem jmiOpen = new JMenuItem("Сохранить");
        jmFile.add(jmiOpen);
        JMenuItem jmiExit = new JMenuItem("Выход");
        jmFile.add(jmiExit);
        jmb.add(jmFile);
        frame.setLocationRelativeTo( null );
        frame.setJMenuBar(jmb);
        frame.setVisible( true );

        jmiExit.addActionListener(e -> {
            frame.dispose();
        });

        jmiOpen.addActionListener(e ->{
            JList jl = form.getLogArea();
            int[] selectedIx = jl.getSelectedIndices();
            if(selectedIx.length > 0){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Текстовые файлы", "txt", "log", "md"));
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filepath  = selectedFile.getAbsolutePath();
                    File fout = new File(filepath);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(fout);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                        for (int ix : selectedIx) {
                            String v = jl.getModel().getElementAt(ix).toString();
                            bw.write(v);
                            bw.newLine();
                        }
                        bw.close();
                        JOptionPane.showMessageDialog(null, "Данные записаны в файл");
                    } catch (IOException ignored) {
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Журнал хранится в файле log.log");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Нужно выбрать хотя бы одну строку");
            }
        });

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                File f = new File("log.log");
                if(f.exists() && f.isFile()) {
                    BufferedReader reader;
                    DefaultListModel model;
                    model = form.getModel();
                    int i = 0;
                    try {
                        reader = new BufferedReader(new FileReader("log.log"));
                        String line = reader.readLine();
                        while (line != null) {
                            model.add(i, line);
                            line = reader.readLine();
                            i++;
                        }
                        reader.close();
                    } catch (IOException ignored) {
                    }
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                DefaultListModel model;
                model = form.getModel();
                File fout = new File("log.log");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(fout);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    for (int i = 0; i < model.getSize(); i++) {
                        bw.write(model.elementAt(i).toString());
                        bw.newLine();
                    }
                    bw.close();
                } catch (IOException ignored) {
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
