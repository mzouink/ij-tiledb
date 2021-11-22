package com.preibisch.ij_tiledb;


import io.tiledb.java.api.Array;
import io.tiledb.java.api.ArraySchema;
import io.tiledb.java.api.Attribute;
import io.tiledb.java.api.Context;
import io.tiledb.java.api.Dimension;
import io.tiledb.java.api.Domain;
import io.tiledb.java.api.FilterList;
import io.tiledb.java.api.GzipFilter;
import io.tiledb.java.api.LZ4Filter;
import io.tiledb.java.api.Pair;
import io.tiledb.java.api.ZstdFilter;

import static io.tiledb.java.api.ArrayType.TILEDB_DENSE;
import static io.tiledb.java.api.Constants.TILEDB_VAR_NUM;
import static io.tiledb.java.api.Layout.TILEDB_ROW_MAJOR;

public class DenseCreate {
    public static void main(String[] args) throws Exception {

        // Create TileDB context
        Context ctx = new Context();
        // Create getDimensions
        Dimension<Long> d1 =
                new Dimension<Long>(ctx, "d1", Long.class, new Pair<Long, Long>(1l, 4l), 2l);
        Dimension<Long> d2 =
                new Dimension<Long>(ctx, "d2", Long.class, new Pair<Long, Long>(1l, 4l), 2l);

        // Create getDomain
        Domain domain = new Domain(ctx);
        domain.addDimension(d1);
        domain.addDimension(d2);

        // Create and add getAttributes
        Attribute a1 = new Attribute(ctx, "a1", Integer.class);
        Attribute a2 = new Attribute(ctx, "a2", String.class);
        a2.setCellValNum(TILEDB_VAR_NUM);
        Attribute a3 = new Attribute(ctx, "a3", Float.class);
        a3.setCellValNum(2);
        a1.setFilterList(new FilterList(ctx).addFilter(new LZ4Filter(ctx)));
        a2.setFilterList(new FilterList(ctx).addFilter(new GzipFilter(ctx)));
        a3.setFilterList(new FilterList(ctx).addFilter(new ZstdFilter(ctx)));

        // Create array schema
        ArraySchema schema = new ArraySchema(ctx, TILEDB_DENSE);
        schema.setTileOrder(TILEDB_ROW_MAJOR);
        schema.setCellOrder(TILEDB_ROW_MAJOR);
        schema.setDomain(domain);
        schema.addAttribute(a1);
        schema.addAttribute(a2);
        schema.addAttribute(a3);

        // Check array schema
        schema.check();

        // Print array schema contents
        schema.dump();

        Array.create("my_dense_array", schema);
    }
}