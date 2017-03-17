package multithread.immutable.fortest;

import lombok.Builder;

@Builder
public final class PersonWithBuilder {
    private String name;
    private String country;
    private int age;
}
