package GUI;


import File.MusicNameSort;
import Name.Name;
import Exception.MyException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;

public class GUI extends JFrame{

    private JButton button = new JButton("Start");
    private JTextField inputWay1 = new JTextField("C:\\Users\\Вова\\Desktop\\MusicFolder");
    private JTextField inputWay2 = new JTextField("E:\\Music");
    private JLabel labelSyn = new JLabel(" Synchronize");
    private JLabel labelWith = new JLabel(" with");
    private static JLabel labelOut = new JLabel("");
    private JPanel jPanel = new JPanel();

    public GUI() {
        super("Music synchronizer");
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

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //MusicNameSort.mainSort(inputWay1.getText(), inputWay2.getText());

            log("Started changing file names:");
            guiUpdate();
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
                    Name.copyFolder(f.getAbsolutePath(),inputWay2.getText());
                }
                log("coping OK");
                guiUpdate();
            } catch (MyException e1){
                log("error " + e1.toString());}


        }
    }

    public static void log(String name){
        labelOut.setText(name);
    }

    public static void guiUpdate(){
        labelOut.updateUI();
    }

    public static void main(String[] args) {
        GUI app = new GUI();
        app.setVisible(true);
    }
}
