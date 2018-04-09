package assembler;

//@author Anish
public class SymbolTableEntry {
    String symbol;
    String address;

    SymbolTableEntry(String symbol, String address) {
        this.symbol = symbol;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.symbol + " | " + this.address;
    }
    
}
