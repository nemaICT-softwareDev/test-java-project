package com.gildedrose;

import com.gildedrose.Interfaces.GildedRoseInterface;
import com.gildedrose.model.Item;

public class GildedRose implements GildedRoseInterface {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    @Override
    public void updateQuality() {
        /*
            - Once the sell by date has passed, Quality degrades twice as fast
	        - The Quality of an item is never negative
	        - "Aged Brie" actually increases in Quality the older it gets
	        - The Quality of an item is never more than 50
	        - "Sulfuras", being a legendary item, never has to be sold or decreases in Quality

	        - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches
	           Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
	           Quality drops to 0 after the concert

                We have recently signed a supplier of conjured items. This requires an update to our system:

	            - "Conjured" items degrade in Quality twice as fast as normal items
         */

        for (int i = 0; i < items.length; i++) {

			/*
			because "Sulfuras" is a legendary item, and never has to be sold or decreases in quality
			we only handle the other items so we start the decrease of the items sell in right after this check.

			nothing should be done for "Sulfuras.."
			*/
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {

                // sellIn decreases by one for all items
                items[i].sellIn -= 1;

                // "Aged Brie" actually increases in Quality the older it gets, so in from the first iteration as the
                // sellin start to decrease the quality of the Aged Brie start to increase, as more iterations will happen
                // I make sure that the quality stays positive
                if (items[i].name.equals("Aged Brie")) {
                    items[i].quality += 1; // quality increases
                    if (items[i].sellIn < 0) {
                        items[i].quality += 1; // The Quality of an item is never negative
                    }
                }
                // As above by the Aged Brie, Backstage passes increases in quality as its SellIn value approaches
                else if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    // quality increases
                    items[i].quality += 1;
                    // Quality increases by 2 when there are 10 days left
                    if (items[i].sellIn < 11) {
                        items[i].quality += 2;
                    }
                    // by 3 when there are 5 days left or less
                    if (items[i].sellIn < 6) {
                        items[i].quality = items[i].quality + 2;
                    }
                    // Quality drops to 0 after the concert(crazy text)
                    if (items[i].sellIn < 0) {
                        items[i].quality = 0;
                    }

                } else {
                    // At the end of each day our system lowers both values sellin and qualiy for every item
                    items[i].quality -= 1; // quality decrease
                    if (items[i].name.equals("Conjured Mana Cake")) {
                        items[i].quality = items[i].quality - 1; // quality decrease
                    }
                    //  Once the sell by date has passed, Quality degrades twice as fast
                    if (items[i].sellIn < 0) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
                // The Quality of an item is never negative
                if (items[i].quality < 0) { // decrease quality only if > 0 as cannot be negative
                    items[i].quality = 0;
                } else if (items[i].quality > 50) { // The Quality of an item is never more than 50
                    items[i].quality = 50;
                }
            }
        }
    }
}
