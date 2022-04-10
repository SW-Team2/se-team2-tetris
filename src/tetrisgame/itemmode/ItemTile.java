package tetrisgame.itemmode;

import tetrisgame.enumerations.eItemID;
import tetrisgame.parts.Tile;

public class ItemTile extends Tile implements IIsItem {
    private eItemID meID;

    public ItemTile(String name, eItemID id) {
        super(name);
        meID = id;
    }

    public eItemID isItem() {
        return meID;
    }
}
