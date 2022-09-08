package guru.qa;

public enum EnumLanguage {
    DE("Deutsch"),
    PT("Português");
    private final String desc;

    EnumLanguage(String desc) {
        this.desc=desc;
    }
    public String getDesc(){
        return this.desc;
    }
}
