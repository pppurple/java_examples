package com.example.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.Map;

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

        // rpush
        // rpushでListの末尾に追加
        jedis.rpush("my_list", "aaa");
        jedis.rpush("my_list", "bbb");
        jedis.rpush("my_list", "ccc");
        jedis.rpush("my_list", "ddd");

        // 複数の値を一度にrpush可能
        jedis.del("my_list");
        jedis.rpush("my_list", "aaa", "bbb", "ccc", "ddd");

        // index
        // 指定したインデックスの要素を取得
        System.out.println(jedis.lindex("my_list", 0));
        System.out.println(jedis.lindex("my_list", 2));

        // lrange
        // 指定した範囲の要素を取得。
        // 第1引数は開始するインデックス、第2引数は終了するインデックス。
        System.out.println(jedis.lrange("my_list", 0, 2));
        System.out.println(jedis.lrange("my_list", 1, 3));
        System.out.println(jedis.lrange("my_list", 0, -1));


        // lpush
        // リストの先頭に指定された要素を挿入。
        jedis.lpush("my_list", "aa");
        System.out.println(jedis.lrange("my_list", 0, -1));

        // lpop, rpop
        // lpopでリストの最初の要素を削除して取得。
        // rpopでリストの末尾の要素を削除して取得。
        jedis.lpop("my_list");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.rpop("my_list");
        System.out.println(jedis.lrange("my_list", 0, -1));

        // ltrim
        // 指定した範囲を残すようにリストをトリムする。
        // 第1引数は開始するインデックス、第2引数は終了するインデックス。
        jedis.del("my_list");
        jedis.rpush("my_list", "aaa");
        jedis.rpush("my_list", "bbb");
        jedis.rpush("my_list", "ccc");
        jedis.rpush("my_list", "ddd");
        System.out.println(jedis.lrange("my_list", 0, -1));
        jedis.ltrim("my_list", 0, 1);
        System.out.println(jedis.lrange("my_list", 0, -1));
    }

    @Test
    public void setTest() {
        jedis.del("my_set");

        // sadd, smembers
        // saddでセットに要素を追加。
        // smembersでセットに含まれるすべての要素を取得。
        jedis.sadd("my_set", "AAA");
        jedis.sadd("my_set", "BBB");
        jedis.sadd("my_set", "CCC");
        jedis.sadd("my_set", "AAA");
        System.out.println(jedis.smembers("my_set"));

        // 複数の要素を一度に追加可能
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        System.out.println(jedis.smembers("my_set"));

        // spop
        // spopでランダムに要素を削除して取得。
        jedis.spop("my_set");
        System.out.println(jedis.smembers("my_set"));

        // sismember
        // 指定した要素が格納されているかどうか。
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        Boolean existAAA = jedis.sismember("my_set", "AAA");
        System.out.println(existAAA);
        Boolean existDDD = jedis.sismember("my_set", "DDD");
        System.out.println(existDDD);

        // srem
        // 指定した要素を削除.
        jedis.del("my_set");
        jedis.sadd("my_set", "AAA", "BBB", "CCC");
        jedis.srem("my_set", "BBB");
        System.out.println(jedis.smembers("my_set"));
    }

    @Test
    public void hashTest() {
        jedis.del("my_hash");

        // hset, hget, hgetall, hdel
        // hsetで指定したフィールドに値を設定。
        // hgetで指定したフィールドの値を取得。
        // hgetallで指定したキーのすべてのフィールドと値を取得。
        // hdelで指定したフィールドを削除。
        jedis.hset("my_hash", "aaa", "111");
        jedis.hset("my_hash", "bbb", "222");
        jedis.hset("my_hash", "ccc", "333");
        System.out.println(jedis.hget("my_hash", "aaa"));
        System.out.println(jedis.hget("my_hash", "bbb"));
        System.out.println(jedis.hgetAll("my_hash"));
        jedis.hdel("my_hash", "ccc");
        System.out.println(jedis.hgetAll("my_hash"));

        // hmset, hmget
        // hmsetで指定したフィールドと値をまとめて設定。
        // hmgetで指定したフィールドの値をまとめて取得。
        jedis.del("my_hash");
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "111");
        map.put("bbb", "222");
        map.put("ccc", "333");
        jedis.hmset("my_hash", map);
        System.out.println(jedis.hmget("aaa", "bbb", "ccc"));

        // hincrby
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

        // zadd
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

        // zrange, zrangeWithScores
        // zrangeで指定した範囲のメンバを返す。
        // 第1引数は開始するインデックス、第2引数は終了するインデックス。
        // zrangeWithScoresでスコアも同時に返す。
        System.out.println(jedis.zrange("my_zset", 0, -1));
        System.out.println(jedis.zrangeWithScores("my_zset", 0, -1));

        // zrangebyscore
        // 第1引数と第2引数の間のスコアを持つ要素を返す。
        // withscoresオプションを指定すると、スコアも同時に返す。
        System.out.println(jedis.zrangeByScore("my_zset", 0, 100));
        System.out.println(jedis.zrangeByScoreWithScores("my_zset", 0, 150));
        System.out.println(jedis.zrangeByScoreWithScores("my_zset", 100, 230));


        // 第1引数と第2引数にはinfを指定することが可能。
        // +infでプラスの無限大を表し、-infでマイナスの無限大を表す。
        System.out.println(jedis.zrangeByScore("my_zset", Double.MIN_VALUE, 100));
        System.out.println(jedis.zrangeByScore("my_zset", 200, Double.MAX_VALUE));

        // zrem
        // 指定されたメンバを削除する。
        System.out.println(jedis.zrangeWithScores("my_zset", 0, -1));
        jedis.zrem("my_zset", "member1");
        System.out.println(jedis.zrangeWithScores("my_zset", 0, -1));

        // zremrangebyscore
        // 第1引数と第2引数の間のスコアを持つ要素を削除する。
        jedis.del("my_zset");
        jedis.zadd("my_zset", map);
        jedis.zremrangeByScore("my_zset", 200, 400);
        System.out.println(jedis.zrangeWithScores("my_zset", 0, -1));
    }

    @Test
    public void transactionTest() {
        // MULTI, EXEC
        // MULTIでトランザクションを開始し、後続のコマンドはキューに入ります。
        // EXECでキューにあるすべてのコマンドを実行し、トランザクションを終了します。
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

        // DISCARD
        // キューに入れられたすべてのコマンドをトランザクション内でフラッシュします。
        // トランザクションは解除されます。
        System.out.println(jedis.get("counter"));

        Transaction t2 = jedis.multi();
        t2.incr("counter");
        t2.incr("counter");
        t2.discard();

        System.out.println(jedis.get("counter"));

        // WATCH
        System.out.println(jedis.get("counter"));
        jedis.watch("counter");
        Transaction t3 = jedis.multi();
        t3.incr("counter");
        t3.exec();
        System.out.println(jedis.get("counter"));

        // UNWATCH
        // WATCHで監視対象となったすべてのキーをフラッシュします。
        // execかdiscardを呼び出した場合は自動でフラッシュされます。(手動でunwatch不要)
        System.out.println(jedis.get("counter"));
        jedis.watch("counter");
        jedis.unwatch();
        Transaction t4 = jedis.multi();
        t4.incr("counter");
        t4.exec();
        System.out.println(jedis.get("counter"));
    }
}
