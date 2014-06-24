package File;

import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;
import org.mozilla.universalchardet.UniversalDetector;
import Name.Name;
import Exception.MyException;
//import com.codingcatholic.util.*;
import ccl.util.FileUtil;
import ccl.util.Comparable;
//import java.lang.Object.org.openide.filesystems.FileUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;


class FileNameReader {

    private static void p(String s) {
        System.out.println(s);
    }

    public static String getFileExtension(String filename){
        int dotPos = filename.lastIndexOf(".") + 1;
        return filename.substring(dotPos);
    }

    public static void main(String args[]) {
        /*File fl = new File("C:\\Users\\Вова\\Desktop\\тестовая\\03 Старый Самолет.mp3");
        p("Имя файла:" + fl.getName());
        p("Путь:" + fl.getPath());
        p("Полный путь:" + fl.getAbsolutePath());
        p("Родительский каталог:" + fl.getParentFile().getName());
        p(fl.exists() ? "существует" : "не существует");
        p(fl.canWrite() ? "можно записывать" : "нельзя записывать");
        p(fl.canRead() ? "можно читать" : "нельзя читать");
        p("is" + "Директория? " +( fl.isDirectory() ? "да": " нет"));
        p(fl.isFile() ? "обычный файл" : "не обычный файл");
        p("Последняя модификация файла:" + fl.lastModified());
        p("Размер файла:" + fl.length() + " Bytes");
        File[] files = fl.listFiles();       *///renameTo(File dest) к обычным файлам
        /*if (fl.exists()){
            for (int i = 0; i < files.length; i++){
                p(files[i].getParentFile().getName());
                /*
                p(files[i].getName()+"++++++++++++++++");
                p(getFileExtension(files[i].getName()));
                //System.out.println(files[i].delete());
            }
        } */


        String fileNameWay = "C:\\Users\\Вова\\Desktop\\тестовая\\Новая папка";
        String fileNewNameWay = "C:\\Users\\Вова\\Desktop\\тестовая\\куда\\12";

        //FileUtil.deleteRecursively(fileNameWay);
        //FileUtil fileUtil;
        //System.out.println(FileUtil.getSwingHome());


//        String s1 = "Сплин - Двое не спят.mp3";
//        String s2 = "Сплин";
        /*try {
            System.out.println(changeName4(s1,s2));
        } catch (Exception e){
            e.printStackTrace();
        }  */

        File src = new File("C:\\Users\\Вова\\Desktop\\тестовая\\Альтависта.mp3");
        try{
            //byte[] buf = new byte[4096];

            MediaFile oMediaFile = new MP3File(src);
            // any tags read from the file are returned, in an array, in an order which you should not assume
            ID3Tag[] aoID3Tag = oMediaFile.getTags();
            // let's loop through and see what we've got
            // (NOTE:  we could also use getID3V1Tag() or getID3V2Tag() methods, if we specifically want one or the other)

            ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();

            for (int i=0; i < aoID3Tag.length; i++)
            {
                // check to see if we read a v1.0 tag, or a v2.3.0 tag (just for example..)
                if (aoID3Tag[i] instanceof ID3V1_0Tag)
                {
                    ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag)aoID3Tag[i];
                    // does this tag happen to contain a title?
                    if (oID3V1_0Tag.getTitle() != null)
                    {
                        System.out.println("Title1 = " + oID3V1_0Tag.getTitle());
                    }
                    // etc.
                }
                else if (aoID3Tag[i] instanceof ID3V2_3_0Tag)
                {
                    oID3V2_3_0Tag = (ID3V2_3_0Tag)aoID3Tag[i];
                    // check if this v2.3.0 frame contains a title, using the actual frame name
                    if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null)
                    {
                        System.out.println("Title2 = " + oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle());
                        //buf = oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle().getBytes();
                        System.out.println("Titlenew = " + new String(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle().getBytes("cp1251")));
                    }
                    if (oID3V2_3_0Tag.getAlbum() != null){
                        System.out.println("Album = " + oID3V2_3_0Tag.getAlbum());
                    }
                    if (oID3V2_3_0Tag.getArtist() != null){
                        System.out.println("Artist = " + oID3V2_3_0Tag.getArtist());
                    }
                    // but check using the convenience method if it has a year set (either way works)
                    try
                    {
                        System.out.println("Year = " + oID3V2_3_0Tag.getYear());  // reads TYER frame
                    }
                    catch (ID3Exception e)
                    {
                        // error getting year.. if one wasn't set
                        System.out.println("Could get read year from tag: " + e.toString());
                    }

                }
            }
            ID3V2_3_0Tag oID3V2_3_0Tag1 = new ID3V2_3_0Tag();
            oID3V2_3_0Tag1 = oID3V2_3_0Tag;
            oID3V2_3_0Tag1.setArtist(new String("ывомал".getBytes("utf-8"),"windows-1251"));
            System.out.println(new String("ывомал".getBytes("utf-8"),"windows-1251")+ "_0000000000000000");
            oMediaFile.setID3Tag(oID3V2_3_0Tag);
            oMediaFile.sync();


            /////////////////////////////////////////////////////////////////////////////////////




            /////////////////////////////////////////////////////////////////////////////////////

            /*byte[] buf = new byte[4096];
            String fileName = "C:\\Users\\Вова\\Desktop\\тестовая\\04_Памятник.mp3";
            java.io.FileInputStream fis = new java.io.FileInputStream(fileName);     */

            // (1)
            /*UniversalDetector detector = new UniversalDetector(null);
            detector.handleData(buf,0,4);
            // (2)
            /*int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            } */
            // (3)
            /*detector.dataEnd();

            // (4)
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                System.out.println("Detected encoding = " + encoding);
            } else {
                System.out.println("No encoding detected.");
            }

            // (5)
            detector.reset(); */

            //sortID3Tags(src);


            ////////////////////////////////////////////////////////////////////////////////////

            }catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("asdf".indexOf(".") + " ______________________");


    }
    /*public  static String changeName4(String previousName, String containNamePart) throws MyException{  // "Сплин - Двое не спят.mp3"  -> "Двое не спят.mp3"
        if (containNamePart.length() >= previousName.length()){
            throw new MyException("Files is null");
        }
        if (!previousName.contains(containNamePart)){
            return previousName;
        } else {
            if (previousName.indexOf(containNamePart) == 0){
                for(int i = containNamePart.length(); i < previousName.length(); i++){
                    if ( (previousName.charAt(i) != ' ') && (previousName.charAt(i) != '-')){
                        return previousName.substring(i);
                    }
                }
            }
        }
        return previousName;
    }    */
    public static void sortID3Tags(File file){
        try{

            MediaFile oMediaFile = new MP3File(file);
            ID3Tag[] aoID3Tag = oMediaFile.getTags();

            ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();

            for (ID3Tag iaoID3Tag : aoID3Tag){
                if (iaoID3Tag instanceof ID3V2_3_0Tag)
                {
                    oID3V2_3_0Tag = (ID3V2_3_0Tag)iaoID3Tag;
                    System.out.println("......................");
                    if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null){
                        if (!Name.changeName(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle()).contains("?")){
                            System.out.println((Name.changeName(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle())) + "- new. prew: " + (oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle()));
                            oID3V2_3_0Tag.setTIT2TextInformationFrame(new TIT2TextInformationID3V2Frame ("Альтависта")/*(Name.changeName(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle()))*/);
                        }
                        if (oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle().contains(".")){
                            oID3V2_3_0Tag.setTIT2TextInformationFrame(null);
                        }
                    }
                    if (oID3V2_3_0Tag.getAlbum() != null){
                        if (!Name.changeName(oID3V2_3_0Tag.getAlbum()).contains("?")){
                            oID3V2_3_0Tag.setAlbum(Name.changeName(oID3V2_3_0Tag.getAlbum()));
                        }
                        if (oID3V2_3_0Tag.getAlbum().contains(".")){
                            oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                        }
                    } else {
                        oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                    }
                    if (oID3V2_3_0Tag.getArtist() != null){
                        if (!Name.changeName(oID3V2_3_0Tag.getArtist()).contains("?")){
                            oID3V2_3_0Tag.setArtist(Name.changeName(oID3V2_3_0Tag.getArtist()));
                        }
                        if (oID3V2_3_0Tag.getArtist().contains(".")){
                            oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                        }
                    } else {
                        oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                    }
                }
            }
            oMediaFile.setID3Tag(oID3V2_3_0Tag);
            oMediaFile.sync();


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}