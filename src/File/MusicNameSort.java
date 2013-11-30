package File;

import Name.Name;
import Exception.MyException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MusicNameSort {

    public static void sort(String filename) throws MyException {
        File file = new File(filename);
        if(!file.exists()){throw new MyException("File does not exist!");}
        if(!file.canRead()){throw new MyException("File is not readable!");}
        if(!file.isDirectory()){throw new MyException("File is not a directory!");}

        File[] files = file.listFiles();

        if (files == null){throw new MyException("File list is empty");}

        for (File f : files){
            System.out.println(f.getName());
            Path source = Paths.get(f.getAbsolutePath());
            try {
            Files.move(source, source.resolveSibling(Name.changeName(f.getName())));
            } catch (IOException e){
                System.out.println(e.toString());}
        }
    }

    public static void main(String args[]) {
        System.out.println("Started changing file names:");
        try{
            MusicNameSort.sort("C:\\Users\\Вова\\Desktop\\Плеер\\Алиса, Ария");
            System.out.print("OK");
        } catch (MyException e){
            System.out.println(e);}
    }
}
