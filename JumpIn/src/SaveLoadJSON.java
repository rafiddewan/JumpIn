import java.io.*;

public class SaveLoadJSON {
    public static final String FILENAME = "savedLevel.json";


    public void save(Board board) throws IOException {
        File file = new File(FILENAME);
        FileOutputStream fos = new FileOutputStream(file);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //write String "{" to json
        bw.write("{"); bw.newLine();
        //write String ""Spaces":{[" to JSON
        bw.write("  \"spaces\":["); bw.newLine();
        boolean isLast = false;
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                if(i == 4 && j == 4){
                    isLast = true;
                }
                bw.write(toJSON(board.getSpace(i,j),isLast));
                bw.newLine();
            }
        }

        //write String "]}" to JSON
        bw.write("  ]"); bw.newLine();
        //write String "}" to JSON
        bw.write("}");

        bw.close();

    }

    private String toJSON(Space space, boolean isLast){

        String line = "     {";
        line = line.concat("\"row\": " + space.getRow() + ", \"column\": " + space.getColumn() + ", \"ID\": \"" + space.toString());
        line = line.concat("\"}");
        if(!isLast){
            line = line.concat(",");
        }
        return line;
    }

    public Board load(){
        return new Board();
    }

    private Space fromJSON(String s){
        return new Rabbit(1,1);
    }
}
