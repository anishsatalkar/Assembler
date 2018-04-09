package assembler;

//@author Anish
public class LiteralEntry {
    String literal;
    String address;

    LiteralEntry(String literal , String address) {
        this.literal = literal;
        this.address = address;
    }
    
    @Override
    public String toString() {
        return this.literal + " | " + this.address;
    }
    
    
}
