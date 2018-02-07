package starters;

import java.util.Objects;

public class Configuration {
    private String setting1;
    private String setting2;

    public Configuration(String setting1, String setting2) {
        this.setting1 = setting1;
        this.setting2 = setting2;
    }

    public String getSetting1() {
        return setting1;
    }

    public String getSetting2() {
        return setting2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(setting1, that.setting1) &&
                Objects.equals(setting2, that.setting2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setting1, setting2);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "setting1='" + setting1 + '\'' +
                ", setting2='" + setting2 + '\'' +
                '}';
    }
}
