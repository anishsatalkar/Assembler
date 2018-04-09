package assembler;
//@author Anish

public class MachineOPCodeEntry {

    String mnemonic;
    String className;
    String opcode;
    
    MachineOPCodeEntry(String mnemonic , String className , String opcode){
        this.mnemonic = mnemonic;
        this.className = className;
        this.opcode = opcode;
    }
}
