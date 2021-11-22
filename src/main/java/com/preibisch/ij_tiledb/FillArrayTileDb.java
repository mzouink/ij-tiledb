package com.preibisch.ij_tiledb;

import io.tiledb.java.api.Array;
import io.tiledb.java.api.Context;
import io.tiledb.java.api.NativeArray;
import io.tiledb.java.api.Query;
import io.tiledb.java.api.TileDBError;

import static io.tiledb.java.api.Layout.TILEDB_ROW_MAJOR;
import static io.tiledb.java.api.QueryType.TILEDB_WRITE;

public class FillArrayTileDb {
    public static void main(String[] args) throws TileDBError {
        Context ctx = new Context();
        String array_name = "testdata";
        Array array = new Array(ctx, array_name, TILEDB_WRITE);
        Query query = new Query(array);
        // Prepare some data for the array
        NativeArray d1 = new NativeArray(ctx, new long[]{1l, 2l, 3l, 4l}, Long.class);
        NativeArray d2 = new NativeArray(ctx, new long[]{2l, 1l, 3l, 4l}, Long.class);
        NativeArray a = new NativeArray(ctx, new float[]{1.1f, 1.2f, 2.1f, 2.2f, 3.1f, 3.2f, 4.1f, 4.2f}, Float.class);

        // Create the query
        query.setLayout(TILEDB_ROW_MAJOR)
                .setBuffer("d1", d1)
                .setBuffer("d2", d2)
                .setBuffer("a", a);

        // Submit query
        query.submit();
    }
}

