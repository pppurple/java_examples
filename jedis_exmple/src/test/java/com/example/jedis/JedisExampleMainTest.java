package com.example.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JedisExampleMainTest {

    private Jedis jedis;

    @Before
    public void setUp() {
        jedis = new Jedis("localhost", 6379);
    }

    @Test
    public void stringTest() throws Exception {
        // set, get, del
        jedis.set("my_key", "my_value");
        System.out.println(jedis.get("my_key"));

        Long delResult = jedis.del("my_key");
        System.out.println(delResult);
        System.out.println(jedis.get("my_key"));

        // xx, nx
        // nx 存在しないキーの場合は設定される
        jedis.del("new_key");
        jedis.set("new_key", "new_val", "nx");
        System.out.println(jedis.get("new_key"));

        // xx 存在しているキーの場合は設定される
        jedis.set("new_key", "new!!!", "xx");
        System.out.println(jedis.get("new_key"));

        // incr, decr
        // incrで値をインクリメント、
        jedis.set("my_count", "100");
        jedis.incr("my_count");
        System.out.println(jedis.get("my_count"));
        jedis.incrBy("my_count", 10);
        System.out.println(jedis.get("my_count"));

        // decrで値をデクリメント。
        jedis.decr("my_count");
        System.out.println(jedis.get("my_count"));
        jedis.decrBy("my_count", 10);
        System.out.println(jedis.get("my_count"));

        // mset, mget
        // msetで複数の値を一度にセットできる。
        // mgetで複数の値を一度にフェッチできる。
        jedis.mset("AAA", "111", "BBB", "222", "CCC", "333");
        System.out.println(jedis.mget("AAA", "BBB", "CCC"));
        System.out.println(jedis.mget("AAA", "BBB", "CCC", "DDD"));

        // exists
        // existsで指定したキーが存在する場合1、存在しない場合0
        System.out.println(jedis.exists("AAA"));
        System.out.println(jedis.exists("DDD"));

        // expire
        // expireで対象キーの存続時間(秒)を指定できる。
        // 存続時間を過るとキーは自動的に削除される。
        jedis.set("my_key", "abcde");
        jedis.expire("my_key", 5);
        System.out.println(jedis.get("my_key"));
        System.out.println("Wait for 6 sec...");
        Thread.sleep(6_000L);
        System.out.println(jedis.get("my_key"));
    }

    @Test
    public void listTest() {
        jedis.del("my_list");

        jedis.rpush("my_list", "aaa");
        jedis.rpush("my_list", "bbb");
        jedis.rpush("my_list", "ccc");
        jedis.rpush("my_list", "ddd");
        System.out.println(jedis.lindex("my_list", 0));
        System.out.println(jedis.lindex("my_list", 2));

        System.out.println(jedis.lrange("my_list", 0, 2));
        System.out.println(jedis.lrange("my_list", 1, 3));
        System.out.println(jedis.lrange("my_list", 0, -1));

        jedis.del("my_list");
        jedis.rpush("my_list", "aaa", "bbb", "ccc", "ddd");
        System.out.println(jedis.lrange("my_list", 0, -1));

        jedis.lpush("my_list", "aa");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.lpop("my_list");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.rpop("my_list");
        System.out.println(jedis.lrange("my_list", 0, -1));

        jedis.del("my_list");
        jedis.rpush("my_list", "aaa");
        jedis.rpush("my_list", "bbb");
        jedis.rpush("my_list", "ccc");
        jedis.rpush("my_list", "ddd");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.ltrim("my_list", 0, 1);
        System.out.println(jedis.lrange("my_list", 0, -1));

        // brpop, blpop
        jedis.lrange("empty_list", 0, -1);
        jedis.brpop("empty_list", "5");
        jedis.rpush("empty_list", "abc");

        /*

brpop
blpop
> lrange empty_list 0 -1
(empty list or set)
> brpop empty_list 5
(nil)
(5.08s)
> rpush empty_list abc
(integer) 1
> brpop empty_list 5
1) "empty_list"
2) "abc"
> blpop empty_list 5
(nil)
(5.00s)
> rpush empty_list abc
(integer) 1
> rpush empty_list def
(integer) 2
> blpop empty_list 5
1) "empty_list"
2) "abc"
> lrange empty_list 0 -1
1) "def"

         */

    }
}
