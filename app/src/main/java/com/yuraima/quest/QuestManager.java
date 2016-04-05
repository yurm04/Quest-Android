package com.yuraima.quest;

import java.util.List;

/**
 * Created by yestevez on 4/4/16.
 */
public class QuestManager {
    final static String TAG = "QuestManagerClass";
    List<Quest> quests;

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    /* GETTERS AND SETTERS */
    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public List<Quest> getQuests() {
        return this.quests;
    }
}
