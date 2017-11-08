package choHan;

public enum Guess {
    CHO("Cho (even)"),
	HAN("Han (odd)"),
	NULL("null");

    private String name;

    Guess(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
