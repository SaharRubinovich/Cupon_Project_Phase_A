package beans;

import java.util.function.Predicate;

public enum Category {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION;

    public final Predicate<Integer> value = 1 + ordinal();
}
