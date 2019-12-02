import java.io.*;

/**
 * @author Lazar
 */
public class SaveLoadJSON {
    public static final String FILENAME = "savedLevel.json";

    /**
     * Saves the current board state to a JSON file
     * @param board
     * @throws IOException
     */
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

    /**
     * Return String representation of JSON representation of a Space object
     * @param space
     * @param isLast
     * @return JSON String of Space
     */
    private String toJSON(Space space, boolean isLast){

        String line = "     {";
        line = line.concat("\"row\": " + space.getRow() + ", \"column\": " + space.getColumn() + ", \"ID\": \"" + space.toString());
        line = line.concat("\"}");
        if(!isLast){
            line = line.concat(",");
        }
        return line;
    }

    /**
     * Returns the Board based on the current state of the JSON file
     * @return saved Board
     */
    public Board load() throws IOException {
        Board board = new Board();
        File file = new File(FILENAME);
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine(); //First line always {
        br.readLine(); //Second line always "spaces":[
        String line;
        while((line = br.readLine()) != null){
            line = line.replaceAll("\\s",""); //remove whitespace
            Space space = fromJSON(line);
            board.setSpace(space.getRow(),space.getColumn(),space);
        }


        return board;
    }

    /**
     * JSON String parser
     * @param line
     * @return Space corresponding to JSON line
     */
    private Space fromJSON(String line){
        int row = 0;
        int col = 0;
        String ID = "";

        line = line.replace("{","");
        line = line.replace("}","");
        line = line.replace("]","");

        String[] keyValue = line.split(",");
        for(String s : keyValue){
            String[] value = s.split(":");
            row = Integer.parseInt(value[1]);
            col = Integer.parseInt(value[3]);
            ID = value[5];
        }
        if(ID == "MU"){
            return new Mushroom(row, col);
        }
        else if(ID == "RA"){
            return new Rabbit(row, col);
        }
        else if(ID == "ES"){
            return new EmptySpace(row,col);
        }
        else if(ID == "OH"){
            return new Hole(row,col,false);
        }
        else if(ID == "CH"){
            return new Hole(row,col,true);
        }
        /*else if(ID == "FH"){
            return new FoxPart(row, col, true);
        }*/

        return new Rabbit(1,1); //placeholder/default
    }
}
