public enum Exam {
    LITHUANIAN(1),
    MATHEMATICS(2),
    GEOGRAPHY(3),
    HISTORY(4),
    CHEMISTRY(5),
    BIOLOGY(6),
    LATIN(7);

    private int number;
    Exam(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
