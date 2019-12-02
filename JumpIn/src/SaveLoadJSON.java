import java.io.*;

/**
 * @author Lazar
 */
public class SaveLoadJSON {
    public static final String FILENAME = "savedLevel.json";
    int holesFilled = 0;

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
        if(space.toString().equals("FH") || space.toString().equals("FT")){
            line = line.concat("\"row\": " + space.getRow() + ", \"column\": " + space.getColumn() + ", \"ID\": \"" + space.toString());
            line = line.concat("\", \"vertical\": \"" + ((FoxPart) space).getIsVertical());
            line = line.concat("\", \"other\": \"(" +((FoxPart) space).getOtherFoxPart().getRow() + " | ");
            line = line.concat("" + ((FoxPart) space).getOtherFoxPart().getColumn());
            line = line.concat(")\"}");
        }
        else {
            line = line.concat("\"row\": " + space.getRow() + ", \"column\": " + space.getColumn() + ", \"ID\": \"" + space.toString());
            line = line.concat("\"}");
        }
        if(!isLast){
            line = line.concat(",");
        }
        return line;
    }

    /**
     * Parses and returns the saved board from file
     * @return saved Board
     */
    public Board load() throws IOException {
        Board board = new Board();
        board.emptyBoard();
        try {
            File file = new File(FILENAME);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); //First line always {
            br.readLine(); //Second line always "spaces":[
            String line;
            for (int i = 0; i < 25 ; i++) {
                line = br.readLine();
                line = line.replaceAll("\\s", ""); //remove whitespace

                int row = 0;
                int col = 0;
                String ID = "";
                String vertical = "false";
                String other = "";

                line = line.replace("{", "");
                line = line.replace("}", "");
                line = line.replace("]", "");

                String[] keyValue = line.split(","); //["key":value,"key":value,"key":value]


                Space space = new Space(0, 0);

                for (int j = 0; j < keyValue.length; j++) {
                    String[] value = keyValue[j].split(":"); //["key",value]

                    if (j == 0) {
                        row = Integer.parseInt(value[1]);
                    } else if (j == 1) {
                        col = Integer.parseInt(value[1]);
                    } else if (j == 2) {
                        ID = value[1];
                    } else if (j == 3) {
                        vertical = value[1];
                    } else if (j == 4) {
                        other = value[1];
                    }
                }
                space = fromJSON(row, col, other, vertical, ID);
                board.setSpace(space.getRow(), space.getColumn(), space);

            }

            board.setHolesEmpty(5 - holesFilled);


        }
        catch(Exception e){
            e.printStackTrace();
        }
        return board;
    }

    /**
     * String space identifier
     * @param row, col, ID
     * @return Space corresponding to JSON line
     */

    private Space fromJSON(int row, int col, String other, String vertical, String ID){

        boolean v;
        if(vertical.equals("true")){ v = true;}
        else { v = false; }

        if(ID.equals("\"MU\"")){
            return new Mushroom(row, col);
        }
        else if(ID.equals("\"RA\"")){
            return new Rabbit(row, col);
        }
        else if(ID.equals("\"ES\"")){
            return new EmptySpace(row,col);
        }
        else if(ID.equals("\"OH\"")){
            return new Hole(row,col,false);
        }
        else if(ID.equals("\"CH\"")){
            holesFilled++;
            return new Hole(row,col,true);
        }
        else {
            String[] coord = other.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\"", "").split("\\|");

             int friendRow = Integer.parseInt(coord[0]);
             int friendCol = Integer.parseInt(coord[1]);

            if(ID.equals("\"FH\"")){
                FoxPart otherFoxPart = new FoxPart(friendRow,friendCol,v,false);
                return new FoxPart(row, col, v, true, otherFoxPart);
            }
            else if(ID.equals("\"FT\"")){
                FoxPart otherFoxPart = new FoxPart(friendRow,friendCol,v,true);
                return new FoxPart(row, col, v, false, otherFoxPart);
            }
        }

        return new EmptySpace(1,1); //placeholder/default
    }
}
