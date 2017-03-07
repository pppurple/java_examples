package multithread.immutable.fortest;

import lombok.Builder;

@Builder
public class PersonWithBuilder {
    private final String name;
    private final String country;
    private final int age;
}
