package starters;

public enum Mode {
    UPPER {
        @Override
        public String transform(String input) {
            return input.toUpperCase();
        }
    },
    LOWER {
        @Override
        public String transform(String input) {
            return input.toLowerCase();
        }
    },
    AS_IS {
        @Override
        public String transform(String input) {
            return input;
        }
    };

    public abstract String transform(String input);
}
