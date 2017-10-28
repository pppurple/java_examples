package com.example.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        // set(), get()
        // set()で指定したキーに値を設定。
        // get()で指定したキーの値をフェッチ。
        jedis.set("my_key", "my_value");
        System.out.println(jedis.get("my_key"));

        // del()
        // del()で指定したキーの値を削除。
        Long delResult = jedis.del("my_key");
        System.out.println(delResult);
        System.out.println(jedis.get("my_key"));

        // xx, nx
        // set()の第3引数にxx, nxを指定可能。
        // nx 存在しないキーの場合は設定される
        jedis.del("new_key");
        jedis.set("new_key", "new_val", "nx");
        System.out.println(jedis.get("new_key"));

        // xx 存在しているキーの場合は設定される
        jedis.set("new_key", "new!!!", "xx");
        System.out.println(jedis.get("new_key"));

        // incr(), decr()
        // incr()で値をインクリメント、
        // incrBy()で指定した値だけインクリメント
        jedis.set("my_count", "100");
        jedis.incr("my_count");
        System.out.println(jedis.get("my_count"));
        jedis.incrBy("my_count", 10);
        System.out.println(jedis.get("my_count"));

        // decr()で値をデクリメント。
        // decrBy()で指定した値だけデクリメント
        jedis.decr("my_count");
        System.out.println(jedis.get("my_count"));
        jedis.decrBy("my_count", 10);
        System.out.println(jedis.get("my_count"));

        // mset(), mget()
        // mset()で複数の値を一度にセットできる。
        // mget()で複数の値を一度にフェッチできる。
        jedis.mset("AAA", "111", "BBB", "222", "CCC", "333");
        System.out.println(jedis.mget("AAA", "BBB", "CCC"));
        System.out.println(jedis.mget("AAA", "BBB", "CCC", "DDD"));

        // exists()
        // exists()で指定したキーが存在する場合1、存在しない場合0
        System.out.println(jedis.exists("AAA"));
        System.out.println(jedis.exists("DDD"));

        // expire()
        // expire()で対象キーの存続時間(秒)を指定できる。
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

        // rpush()
        // rpush()でListの末尾に追加
        jedis.rpush("my_list", "aaa");
        jedis.rpush("my_list", "bbb");
        jedis.rpush("my_list", "ccc");
        jedis.rpush("my_list", "ddd");

        // 複数の値を一度にrpush可能
        jedis.del("my_list");
        jedis.rpush("my_list", "aaa", "bbb", "ccc", "ddd");

        // lindex()
        // 指定したインデックスの要素を取得
        System.out.println(jedis.lindex("my_list", 0));
        System.out.println(jedis.lindex("my_list", 2));

        // lrange()
        // 指定した範囲の要素を取得。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        System.out.println(jedis.lrange("my_list", 0, 2));
        System.out.println(jedis.lrange("my_list", 1, 3));
        System.out.println(jedis.lrange("my_list", 0, -1));


        // lpush()
        // リストの先頭に指定された要素を挿入。
        jedis.lpush("my_list", "aa");
        System.out.println(jedis.lrange("my_list", 0, -1));

        // lpop(), rpop()
        // lpop()でリストの最初の要素を削除して取得。
        // rpop()でリストの末尾の要素を削除して取得。
        System.out.println(jedis.lpop("my_list"));
        System.out.println(jedis.lrange("my_list", 0, -1));
        System.out.println(jedis.rpop("my_list"));
        System.out.println(jedis.lrange("my_list", 0, -1));

        // ltrim()
        // 指定した範囲を残すようにリストをトリムする。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        jedis.del("my_list");
        jedis.rpush("my_list", "aaa", "bbb", "ccc", "ddd");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.ltrim("my_list", 0, 1);
        System.out.println(jedis.lrange("my_list", 0, -1));
    }

    @Test
    public void setTest() {
        jedis.del("my_set");

        // sadd(), smembers()
        // sadd()でセットに要素を追加。
        // smembers()でセットに含まれるすべての要素を取得。
        jedis.sadd("my_set", "AAA");
        jedis.sadd("my_set", "BBB");
        jedis.sadd("my_set", "CCC");
        jedis.sadd("my_set", "AAA");
        System.out.println(jedis.smembers("my_set"));

        // 複数の要素を一度に追加可能
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        System.out.println(jedis.smembers("my_set"));

        // spop()
        // spop()でランダムに要素を削除して取得。
        jedis.spop("my_set");
        System.out.println(jedis.smembers("my_set"));

        // sismember()
        // 指定した要素が格納されているかどうか。
        // 格納されている場合は1、そうでない場合0
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        Boolean existAAA = jedis.sismember("my_set", "AAA");
        System.out.println(existAAA);
        Boolean existDDD = jedis.sismember("my_set", "DDD");
        System.out.println(existDDD);

        // srem()
        // 指定した要素を削除.
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        jedis.srem("my_set", "BBB");
        System.out.println(jedis.smembers("my_set"));
    }

    @Test
    public void hashTest() {
        jedis.del("my_hash");

        // hset(), hget(), hgetall(), hdel()
        // hset()で指定したフィールドに値を設定。
        // hget()で指定したフィールドの値を取得。
        // hgetAll()で指定したキーのすべてのフィールドと値を取得。
        // hdel()で指定したフィールドを削除。
        jedis.hset("my_hash", "aaa", "111");
        jedis.hset("my_hash", "bbb", "222");
        jedis.hset("my_hash", "ccc", "333");
        System.out.println(jedis.hget("my_hash", "aaa"));
        System.out.println(jedis.hget("my_hash", "bbb"));
        System.out.println(jedis.hgetAll("my_hash"));
        jedis.hdel("my_hash", "ccc");
        System.out.println(jedis.hgetAll("my_hash"));

        // hmset(), hmget()
        // hmset()で指定したフィールドと値をまとめて設定。
        // hmget()で指定したフィールドの値をまとめて取得。
        jedis.del("my_hash");
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "111");
        map.put("bbb", "222");
        map.put("ccc", "333");
        jedis.hmset("my_hash", map);
        System.out.println(jedis.hmget("my_hash", "aaa", "bbb", "ccc"));

        // hincrBy()
        // 指定したフィールドの値をインクリメント。
        System.out.println(jedis.hget("my_hash", "aaa"));
        jedis.hincrBy("my_hash", "aaa", 1);
        System.out.println(jedis.hget("my_hash", "aaa"));
        jedis.hincrBy("my_hash", "aaa", 100);
        System.out.println(jedis.hget("my_hash", "aaa"));
    }

    @Test
    public void zsetTest() {
        jedis.del("my_zset");

        // zadd()
        // zsetに第1引数で指定したスコアで、第2引数のメンバを登録。
        jedis.zadd("my_zset", 111, "member1");
        jedis.zadd("my_zset", 222, "member2");
        jedis.zadd("my_zset", 333, "member3");

        // まとめて登録も可能
        jedis.del("my_zset");
        Map<String, Double> map = new HashMap<>();
        map.put("member1", 111D);
        map.put("member2", 222D);
        map.put("member3", 333D);
        jedis.zadd("my_zset", map);

        // zrange(), zrangeWithScores()
        // zrange()で指定した範囲のメンバを返す。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        // zrangeWithScores()でスコアも同時に返す。
        System.out.println(jedis.zrange("my_zset", 0, -1));
        jedis.zrangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getElement() + " : " + t.getScore()));

        // zrangeByScore(), zrangeByScoreWithScores()
        // zrangeByScore()で第2引数と第3引数の間のスコアを持つ要素を返す。
        // zrangeByScoreWithScores()でスコアも同時に返す。
        System.out.println(jedis.zrangeByScore("my_zset", 0, 150));
        jedis.zrangeByScoreWithScores("my_zset", 0, 150)
                .forEach(t -> System.out.println(t.getElement() + " : " + t.getScore()));
        jedis.zrangeByScoreWithScores("my_zset", 100, 230)
                .forEach(t -> System.out.println(t.getElement() + " : " + t.getScore()));

        // redis-cliだと、第2引数と第3引数には無限大(+inf/-inf)を指定することが可能だが、
        // jedisではDouble.MIN_VALUE/MAX_VALUEを使用する
        System.out.println(jedis.zrangeByScore("my_zset", Double.MIN_VALUE, 200));
        System.out.println(jedis.zrangeByScore("my_zset", 200, Double.MAX_VALUE));

        // zrem()
        // 指定されたメンバを削除する。
        System.out.println(jedis.zrange("my_zset", 0, -1));
        jedis.zrem("my_zset", "member1");
        System.out.println(jedis.zrange("my_zset", 0, -1));

        // zremrangeByScore()
        // 第1引数と第2引数の間のスコアを持つ要素を削除する。
        jedis.del("my_zset");
        jedis.zadd("my_zset", map);
        jedis.zrangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getElement() + " : " + t.getScore()));
        jedis.zremrangeByScore("my_zset", 200, 400);
        jedis.zrangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getElement() + " : " + t.getScore()));
    }

    @Test
    public void transactionTest() {
        jedis.del("user_list");
        jedis.del("counter");
        // multi(), exec()
        // multi()でトランザクションを開始し、後続のコマンドはキューに入ります。
        // exec()でキューにあるすべてのコマンドを実行し、トランザクションを終了します。
        System.out.println(jedis.lrange("user_list", 0, -1));
        System.out.println(jedis.get("counter"));

        Transaction t = jedis.multi();
        t.rpush("user_list", "bob");
        t.incr("counter");
        t.rpush("user_list", "alice");
        t.incr("counter");
        t.exec();

        System.out.println(jedis.lrange("user_list", 0, -1));
        System.out.println(jedis.get("counter"));

        // discard()
        // キューに入れられたすべてのコマンドをトランザクション内でフラッシュします。
        // トランザクションは解除されます。
        jedis.del("counter");
        System.out.println(jedis.get("counter"));

        Transaction t2 = jedis.multi();
        t2.incr("counter");
        t2.incr("counter");
        t2.discard();

        System.out.println(jedis.get("counter"));

        // watch()
        // watch()で指定したキーを監視します。
        // 監視していたキーが他のクライアントから更新されると、exec()した際にエラーになります。
        jedis.del("counter");
        System.out.println(jedis.get("counter"));

        jedis.watch("counter");
        Transaction t3 = jedis.multi();
        t3.incr("counter");
        t3.exec();

        System.out.println(jedis.get("counter"));

        // unwatch()
        // watch()で監視対象となったすべてのキーをフラッシュします。
        // exec()かdiscard()を呼び出した場合は自動でフラッシュされます。(手動でunwatch不要)
        jedis.del("counter");
        System.out.println(jedis.get("counter"));

        jedis.watch("counter");
        jedis.unwatch();
        Transaction t4 = jedis.multi();
        t4.incr("counter");
        t4.exec();

        System.out.println(jedis.get("counter"));
    }
}
