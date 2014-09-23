package GUI;


import File.MusicNameSort;
import Name.Name;
import Exception.MyException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame{

    private JButton button = new JButton("Start");
    private JTextField inputWay1 = new JTextField("C:\\1");
    private JTextField inputWay2 = new JTextField("C:\\2");
    private JLabel labelSyn = new JLabel(" Synchronize");
    private JLabel labelWith = new JLabel(" with");
    private JLabel labelOut = new JLabel("");
    private JPanel jPanel = new JPanel();
    private static String information = "";
    private Timer timer;

    public GUI() {
        super("Music synchronize");
        timer = new Timer(10, taskPerformer);
        this.setBounds(350,250,300,230);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6,1,50,5));
        container.add(labelSyn);
        container.add(inputWay2);
        container.add(labelWith);
        container.add(inputWay1);
        jPanel.add(new JLabel("     "));
        jPanel.add(button);
        container.add(jPanel);
        container.add(labelOut, BorderLayout.WEST);
        button.addActionListener(new ButtonEventListener());
    }

    public void start() {
        timer.start();
    }

    ActionListener taskPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            labelOut.setText(information);
            repaint();
        }
    };

    class MyThread implements Runnable{
        @Override
        public void run() {
            copy();
        }
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            start();
            labelOut.setText("!!!");
            Thread myThread = new Thread(new MyThread());
            myThread.start();
            log("Started changing file names:");
            guiUpdate();
            repaint();

        }
    }

    private void copy(){
        try{
            MusicNameSort.sort(inputWay1.getText());
            log("sorting OK");
            guiUpdate();

            log("Started deleting files");
            guiUpdate();
            Name.deleteFoldersInside(inputWay2.getText());
            log("deleting OK");
            guiUpdate();

            log("Started coping files..");
            guiUpdate();

            File file = new File(inputWay1.getText());
            File[] files = file.listFiles();
            if (files == null){throw new MyException("Files is null");}
            for (File f : files){
                System.out.println(f.getName()+ " " + f.isDirectory());
                Name.copyFolder(f.getAbsolutePath(),inputWay2.getText());
                log("Coping " + f.getName());
                guiUpdate();
            }
            log("coping OK");
            guiUpdate();

        } catch (MyException e1){
            log("error " + e1.toString());}
    }

    public  static void log(String name){
        information = name;
    }

    public  void guiUpdate(){
        repaint();
    }

    public static void main(String[] args) {
        GUI app = new GUI();
        app.setVisible(true);
    }
}
