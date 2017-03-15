package multithread.immutable.fortest;

import lombok.Value;

@Value
public final class PersonWithValue {
    private final String name;
    private final String country;
    private final int age;
}
