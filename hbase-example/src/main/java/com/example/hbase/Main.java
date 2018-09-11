package com.example.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RowMutations;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");

        try (Connection connection = ConnectionFactory.createConnection(conf);
             Table table = connection.getTable(TableName.valueOf("myspace:mytable"))) {

            cleanup(table); // cleanup
            exists(table);

            // put
            System.out.println("******put");
            put(table);

            // exists
            System.out.println("******exists");
            exists(table);

            // get
            System.out.println("******get");
            get(table);

            // delete (row)
            System.out.println("******delete (row)");
            cleanup(table); // cleanup
            deleteRow(table);

            // delete (column)
            System.out.println("******delete (column)");
            cleanup(table); // cleanup
            deleteColumn(table);

            // scan
            System.out.println("******scan");
            cleanup(table); // cleanup
            scan(table);

            // filter
            System.out.println("******filter");
            cleanup(table); // cleanup
            filter(table);

            // mutateRow
            System.out.println("******mutateRow");
            cleanup(table); // cleanup
            mutateRow(table);

            // increment
            System.out.println("******increment");
            cleanup(table); // cleanup
            increment(table);

            // compare and swap
            System.out.println("******compare and swap");
            cleanup(table); // cleanup
            cas(table);
        }
    }

    private static void put(Table table) throws IOException {
        Put put = new Put(Bytes.toBytes("row1"));
        // row1.add(Bytes.toBytes(""), Bytes.toBytes(""), Bytes.toBytes(""))
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        table.put(put);
    }

    private static void exists(Table table) throws IOException {
        Get getRow = new Get(Bytes.toBytes("row1"));
        boolean existsRow = table.exists(getRow);
        System.out.println("row1 exists?: " + existsRow);
        getRow.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"));
        boolean existsCol = table.exists(getRow);
        System.out.println("row1 f name exists?: " + existsCol);
    }

    private static void get(Table table) throws IOException {
        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"));
        get.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"));
        Result getResult = table.get(get);
        String name = Bytes.toString(getResult.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
        int age = Bytes.toInt(getResult.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
        System.out.println("row1 name: " + name);
        System.out.println("row1 age: " + age);
    }

    private static void deleteRow(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        table.put(put);

        // delete (row)
        Delete deleteRow = new Delete(Bytes.toBytes("row1"));
        table.delete(deleteRow);
    }

    private static void deleteColumn(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        table.put(put);

        // delete (column)
        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"));
        table.delete(delete);
    }

    private static void scan(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        table.put(put);
        Put put2 = new Put(Bytes.toBytes("row2"));
        put2.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Bobby"));
        put2.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(33));
        table.put(put2);
        Put put3 = new Put(Bytes.toBytes("row3"));
        put3.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Cindy"));
        put3.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(19));
        table.put(put3);

        // scan
        Scan scan = new Scan();
        ResultScanner resultScanner = table.getScanner(scan);
        resultScanner.forEach(result -> {
            String scanName = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
            int scanAge = Bytes.toInt(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
            System.out.println("scan name: " + scanName);
            System.out.println("scan age: " + scanAge);

        });
    }

    private static void filter(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(22));
        table.put(put);
        Put put2 = new Put(Bytes.toBytes("row2"));
        put2.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Bobby"));
        put2.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(33));
        table.put(put2);
        Put put3 = new Put(Bytes.toBytes("row3"));
        put3.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Cindy"));
        put3.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(19));
        table.put(put3);

        // row filter
        Scan scan = new Scan();
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("row2")));
        scan.setFilter(rowFilter);
        ResultScanner resultRowFilter = table.getScanner(scan);
        resultRowFilter.forEach(result -> {
            String scanName = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
            int scanAge = Bytes.toInt(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
            System.out.println("scan name(row filter): " + scanName);
            System.out.println("scan age(row filter): " + scanAge);
        });

        // qualifier filter
        QualifierFilter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("name")));
        scan.setFilter(qualifierFilter);
        ResultScanner resultQualifierFilter = table.getScanner(scan);
        resultQualifierFilter.forEach(result -> {
            String scanName = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
            int scanAge = -1;
            if (result.containsColumn(Bytes.toBytes("f"), Bytes.toBytes("age"))) {
                scanAge = Bytes.toInt(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
            }
            System.out.println("scan name(qualifier filter): " + scanName);
            System.out.println("scan age(qualifier filter): " + scanAge);
        });

        // value filter
        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes(20)));
        scan.setFilter(valueFilter);
        ResultScanner resultValueFilter = table.getScanner(scan);
        resultValueFilter.forEach(result -> {
            String scanName = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
            int scanAge = -1;
            if (result.containsColumn(Bytes.toBytes("f"), Bytes.toBytes("age"))) {
                scanAge = Bytes.toInt(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
            }
            System.out.println("scan name(value filter): " + scanName);
            System.out.println("scan age(value filter): " + scanAge);

        });
    }

    private static void mutateRow(Table table) throws IOException {
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"), Bytes.toBytes("Alice"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumn(Bytes.toBytes("f"), Bytes.toBytes("name"));

        // mutateRow
        RowMutations rowMutations = new RowMutations(Bytes.toBytes("row1"));
        rowMutations.add(put);
        rowMutations.add(delete);
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("country"), Bytes.toBytes("Japan"));
        rowMutations.add(put);
        table.mutateRow(rowMutations);

        // scan
        Scan scan = new Scan();
        ResultScanner resultMutateRow = table.getScanner(scan);
        resultMutateRow.forEach(result -> {
            String scanName = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("name")));
            int scanAge = Bytes.toInt(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
            String scanCountry = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("country")));
            System.out.println("scan name: " + scanName);
            System.out.println("scan age: " + scanAge);
            System.out.println("scan country: " + scanCountry);
        });
    }

    private static void increment(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(33L));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("stamina"), Bytes.toBytes(100L));
        table.put(put);

        Increment increment = new Increment(Bytes.toBytes("row1"));
        increment.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), 1L);
        increment.addColumn(Bytes.toBytes("f"), Bytes.toBytes("stamina"), -10L);

        Result result1 = table.increment(increment);
        long age1 = Bytes.toLong(result1.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
        long stamina1 = Bytes.toLong(result1.getValue(Bytes.toBytes("f"), Bytes.toBytes("stamina")));
        System.out.println("age: " + age1);
        System.out.println("stamina: " + stamina1);

        table.increment(increment);
        Result result2 = table.increment(increment);
        long age2 = Bytes.toLong(result2.getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
        long stamina2 = Bytes.toLong(result2.getValue(Bytes.toBytes("f"), Bytes.toBytes("stamina")));
        System.out.println("age: " + age2);
        System.out.println("stamina: " + stamina2);
    }

    private static void cas(Table table) throws IOException {
        // prepare
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20));
        table.put(put);

        // check and put
        Put update = new Put(Bytes.toBytes("row1"));
        update.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(18));
        boolean updateResult = table.checkAndPut(Bytes.toBytes("row1"), Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(20), update);
        System.out.println("update result: " + updateResult);

        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("f"), Bytes.toBytes("age"));
        int age = Bytes.toInt(table.get(get).getValue(Bytes.toBytes("f"), Bytes.toBytes("age")));
        System.out.println("row1 age: " + age);

        // check and delete
        Delete delete = new Delete(Bytes.toBytes("row1"));
        boolean deleteResult = table.checkAndDelete(Bytes.toBytes("row1"), Bytes.toBytes("f"), Bytes.toBytes("age"), Bytes.toBytes(18), delete);
        System.out.println("delete result: " + deleteResult);

        boolean exists = table.exists(get);
        System.out.println("row1 exists?: " + exists);
    }

    private static void cleanup(Table table) {
        List<Delete> deleteAll = Arrays.asList(
                new Delete(Bytes.toBytes("row1")),
                new Delete(Bytes.toBytes("row2")),
                new Delete(Bytes.toBytes("row3"))
        );
        deleteAll.forEach(delete -> {
            try {
                table.delete(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
