package assembler;

//@author Anish
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assembler {

    static List<SymbolTableEntry> SymbolTable;
    static List<MachineOPCodeEntry> MOT;
    static List<LiteralEntry> LT;
    static List<String> Tokens;
    static List<String> PoolTable;
    static int LC = 0;

    static void init() {

        MOT = new ArrayList<>();
        MOT.add(new MachineOPCodeEntry("STOP", "IS", "00"));
        MOT.add(new MachineOPCodeEntry("START", "AD", "01"));
        MOT.add(new MachineOPCodeEntry("DS", "DL", "01"));
        MOT.add(new MachineOPCodeEntry("DC", "DL", "02"));
        MOT.add(new MachineOPCodeEntry("STOP", "RG", "01"));
        MOT.add(new MachineOPCodeEntry("EQ", "CC", "01"));
        MOT.add(new MachineOPCodeEntry("MOVR", "IS", "04"));
        MOT.add(new MachineOPCodeEntry("ADD", "IS", "01"));
        MOT.add(new MachineOPCodeEntry("MOVM", "IS", "05"));
        MOT.add(new MachineOPCodeEntry("END", "AD", "02"));
        MOT.add(new MachineOPCodeEntry("LTORG", "AD", "05"));

        SymbolTable = new ArrayList<>();
        LT = new ArrayList<>();
        PoolTable = new ArrayList<>();

    }

    static void doPassOne() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader("C:\\Users\\Anish\\Documents\\NetBeansProjects\\Assembler\\src\\assembler\\source_code.txt");
        BufferedReader br = new BufferedReader(fileReader);
        BufferedReader startTemp = br;
        String instruction, tokens[], lastToken , firstToken;
        tokens = startTemp.readLine().trim().split(" ");
        boolean literalExists = false, symbolExists = false;

        if (tokens[0].equals("START")) {
            LC = Integer.parseInt(tokens[1]);
            LC++;
        }
        
        while ((instruction = br.readLine()) != null) {
            if(instruction.equals("END")){
                break;
            }
            
            tokens = instruction.trim().split(" ");
            lastToken = tokens[tokens.length - 1];
            
            //Extract last token and check if its a symbol(var) or literal
            if (lastToken.contains("=")) {
                for (LiteralEntry entry : LT) {
                    if (entry.literal.equals(lastToken)) {
                        literalExists = true;
                        break;
                    }
                }
                if (!literalExists) {
                    LT.add(new LiteralEntry(lastToken, ""));
                }
            } else {
                for (SymbolTableEntry entry : SymbolTable) {
                    if (entry.symbol.equals(lastToken)) {
                        symbolExists = true;
                        break;
                    }
                }
                if (!symbolExists) {
                    SymbolTable.add(new SymbolTableEntry(lastToken, "tempAddress"));
                }
            }
            
            //Extract first token see if its an OPCODE or label
            firstToken = tokens[0].trim();
            boolean isOPCode = false;
            for(MachineOPCodeEntry entry : MOT){
                if(entry.mnemonic.equals(firstToken)){
                     isOPCode = true;
                     break;
                }
            }if(!isOPCode){
                if(tokens.length > 1){
                    if(tokens[1].equals("DC") || tokens[1].equals("DS")){
                        for(SymbolTableEntry entry : SymbolTable){
                            if(entry.symbol.equals(firstToken)){
                                entry.address = String.valueOf(LC);
                                LC = LC + Integer.parseInt(tokens[2]);
                            }
                        }
                        continue;
                    }
                }
            }else{
                if(firstToken.trim().equals("LTORG")){
                    for(LiteralEntry entry : LT){
                        if(entry.address.equals("")){
                            entry.address = String.valueOf(LC);
                            LC++;
                        }
                    }
                    continue;
                }
            }
            LC++;
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        doPassOne();
        
        System.out.println("Symbol table : ");
        for (SymbolTableEntry entry : SymbolTable) {
            System.out.println(entry);
        }

        System.out.println("Literal table : ");
        for (LiteralEntry entry : LT) {
            System.out.println(entry);
        }
    }

}
