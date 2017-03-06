package multithread.immutable.fortest;

import lombok.Value;

@Value
public class PersonWithValue {
    private final String name;
    private final String country;
    private final int age;
}
