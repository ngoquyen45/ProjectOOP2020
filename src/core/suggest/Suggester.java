package core.suggest;

import java.util.ArrayList;

public interface Suggester {
    ArrayList<String> suggest(String statement);
}
