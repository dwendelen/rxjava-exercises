package starters;

public class SomeData {
    private String id;

    public SomeData(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SomeData{" +
                "id='" + id + '\'' +
                '}';
    }
}
