namespace java com.example.thrift
namespace perl exampleThrift

enum Country
{
    AMERICA=0,
    JAPAN=1,
    CANADA=2
}

struct Person {
    1: required string name;
    2: required i32 age;
    3: required Country country;
    4: string hobby;
}

service PeopleService
{
    list<Person> searchByName(1:string query)
}
