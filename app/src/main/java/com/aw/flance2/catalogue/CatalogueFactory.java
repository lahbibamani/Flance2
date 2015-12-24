package com.aw.flance2.catalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wadi-chemkhi on 12/24/15.
 */
public class CatalogueFactory {
    public static List<Product> createProducts(){
        List<Product> products= Arrays.asList(
                 new Product("P0","desc0",15.6d,"img")
                ,new Product("P1","desc1",15.6d,"img")
                ,new Product("P2","desc2",15.6d,"img")
                ,new Product("P3","desc3",15.6d,"img")
                ,new Product("P4","desc4",15.6d,"img")
                ,new Product("P5","desc5",15.6d,"img")
                ,new Product("P6","desc6",15.6d,"img")
                ,new Product("P7","desc7",15.6d,"img")

        );

        return products;
    }
}
