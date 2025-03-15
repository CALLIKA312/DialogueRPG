package com.mygame.dialogue;

public class NPC {
    private String name;
    private String dialogue;

    public NPC(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
    }

    public String getDialogue() {
        return dialogue;
    }
}
