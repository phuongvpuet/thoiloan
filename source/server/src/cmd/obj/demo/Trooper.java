package cmd.obj.demo;

public enum  Trooper {
    WARRIOR((short)1),
    ARCHER((short)2),
    GIANT((short)3),
    FLYING_BOMB((short)4),
    ERROR((short)5);

    public final short value;
    private Trooper(short value) {this.value = value;}
    public final short getValue() {return this.value;}

    public static Trooper fromCode(short code){
        for (Trooper a: Trooper.values()){
            if (a.value == code) return a;
        }
        throw new IllegalArgumentException("Param is not valid");
    }

}
