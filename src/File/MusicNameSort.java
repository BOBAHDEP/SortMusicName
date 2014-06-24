package File;

import GUI.GUI;
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
        if (files == null){throw new MyException("Files is null");}
        if (files.length == 0){throw new MyException("File list is empty");}


        for (File f : files){   // name sort
            GUI.log(f.getName());
            GUI.guiUpdate();
            Path source = Paths.get(f.getAbsolutePath());
            try {
                //Name.sortID3Tags(f);       //works wrong
                Files.move(source,source.resolveSibling(Name.changeName4(f.getName(),f.getParentFile().getName())));
                if (f.exists()){
                    Files.move(source, source.resolveSibling(Name.changeName(f.getName())));
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            if (f.isDirectory()){
                sort(f.getAbsolutePath());
            }
        }
        files = file.listFiles();// get new list of files with new names
        if (files == null){throw new MyException("Files is null");}
        for (File f : files){
            if(!f.getName().equals(Name.changeName(f.getName())) || !f.getName().equals(Name.changeName4(f.getName(),f.getParentFile().getName()))){ // to delete repeated files
                GUI.log(f.getName());
                GUI.guiUpdate();
                if (f.delete()){
                    GUI.log(" : File deleted");
                    GUI.guiUpdate();
                }
            }
        }
    }

    public static void mainSort(String musicFolder, String playerFolder) {

        //String musicFolder = "C:\\Users\\Вова\\Desktop\\MusicFolder";
        //String playerFolder = "E:\\Music";

//        GUI.log("Started changing file names:");
//        try{
//            MusicNameSort.sort(musicFolder);
//            GUI.log("sorting OK");
//
//            GUI.log("Started deleting files");
//            Name.deleteFoldersInside(playerFolder);
//            GUI.log("deleting OK");
//
//            GUI.log("Started coping files..");
//            File file = new File(musicFolder);
//            File[] files = file.listFiles();
//            if (files == null){throw new MyException("Files is null");}
//            for (File f : files){
//                Name.copyFolder(f.getAbsolutePath(),playerFolder);
//            }
//            GUI.log("coping OK");
//        } catch (MyException e){
//            GUI.log("error " + e.toString());}
    }
}
