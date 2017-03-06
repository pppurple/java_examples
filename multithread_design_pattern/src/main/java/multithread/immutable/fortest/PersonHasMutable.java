package multithread.immutable.fortest;

public class PersonHasMutable {
    private final String name;
    private final String country;
    private final int age;
    private final MyMutable myMutable;

    public PersonHasMutable(String name, String country, int age, MyMutable myMutable) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.myMutable = new MyMutable(myMutable.getMessage(), myMutable.getCode());
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public MyMutable getMyMutable() {
        return myMutable;
    }
}
