package Name;

public class Name {

    private String fileName;
    private String fileExtension;

    public Name(String name){
        this.fileExtension = getFileExtension(name);
        this.fileName = getFileName(name);
    }

    private  String getFileExtension(String filename){
        int dotPos = filename.lastIndexOf(".") + 1;
        return filename.substring(dotPos);
    }

    private  String getFileName(String filename){
        int dotPos = filename.lastIndexOf(".");
        return filename.substring(0,dotPos);
    }

    public void changeName(){
        fileName  =  changeName(fileName);
    }

    public  static String changeName(String previousName){  //todo sort2
        String result = previousName;
        result = changeName1(result, '-'); // "03 - asd" - > "asd"
        result = changeName1(result, '.'); // "02. Четверо парней" -> "Четверо парней"
        result = changeName2(result);      // " asd" -> "asd"
        return result;

    }

    public  static String changeName1(String previousName, char symbol){  // "1 'Symbol' Name"  -> " Name"
        int index =  previousName.indexOf(symbol) + 1;
        if(index <= 0 || index > 5){
            return previousName;
        }
        for(int i = 0; i < index; i++){
            if((previousName.charAt(i) != ' ') || (previousName.charAt(i) <= '0') || (previousName.charAt(i) >= '9')){
                return previousName;
            }
        }
        return previousName.substring(index + 1);
    }

    public  static String changeName2(String previousName){           // " asd" -> "asd"
        for (int i = 0; i < previousName.length(); i++){
            if (previousName.charAt(i) != ' '){
                return previousName.substring(i);
            }
        }
    return "NaN";
    }

    @Override
    public String toString (){
        return fileName + " | " + fileExtension;
    }
}
