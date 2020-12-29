package com.example.arogya.models.Requests;

public class ListRequest {

    private String category, propagation, cultivation, rootSystem, havingFruits, fruitshape, flowerBlooming, flowerBehaviour,
            flowerColor, leafBase, venation, leafShape, leafType, phyllotaxy, partsUsed;

    public ListRequest(String plantCategory, String propagation, String cultivation, String rootSystem, String havingFruits, String fruitShape, String bloomingPeriod, String flowerSize, String flowerColor, String leafBase, String venation, String leafShape, String leafType, String phyllotaxy, String partsOfUsed) {
        this.category = plantCategory;
        this.propagation = propagation;
        this.cultivation = cultivation;
        this.rootSystem = rootSystem;
        this.havingFruits = havingFruits;
        this.fruitshape = fruitShape;
        this.flowerBlooming = bloomingPeriod;
        this.flowerBehaviour = flowerSize;
        this.flowerColor = flowerColor;
        this.leafBase = leafBase;
        this.venation = venation;
        this.leafShape = leafShape;
        this.leafType = leafType;
        this.phyllotaxy = phyllotaxy;
        this.partsUsed = partsOfUsed;
    }
}
