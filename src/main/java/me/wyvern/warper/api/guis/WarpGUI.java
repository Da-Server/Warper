package me.wyvern.warper.api.guis;

import me.wyvern.warper.api.GUI;
import me.wyvern.warper.api.elements.MenuGlass;
import me.wyvern.warper.util.Color;

public class WarpGUI extends GUI {
    public WarpGUI() {
        super(Color.colorize("&#fb1010W&#fc480eA&#fc810bR&#fdb909P"), 54);
        init();
    }

    private void init() {
        this.fill(MenuGlass.black);
    }
}
