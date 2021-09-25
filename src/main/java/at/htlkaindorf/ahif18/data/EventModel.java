/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.data;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.AbstractListModel;

/**
 *
 * @author mjuri
 */
public class EventModel extends AbstractListModel {

    private ArrayList<Event> allEvents = new ArrayList();
    private ArrayList<Event> activeEvents = new ArrayList();

    @Override
    public int getSize() {
        return activeEvents.size();
    }

    @Override
    public Event getElementAt(int index) {
        return activeEvents.get(index);
    }

    public void loadEvents(ArrayList<Event> events) {
        allEvents = (ArrayList<Event>) events.clone();
        activeEvents = (ArrayList<Event>) allEvents.clone();
        sort();
        this.fireContentsChanged(this, 0, this.getSize());
    }

    public void addEvent(Event evt) {
        allEvents.add(evt);
        activeEvents.add(evt);
        this.fireContentsChanged(this, 0, this.getSize());
    }

    public void sort() {
        activeEvents.sort(Event::compareTo);
        this.fireContentsChanged(this, 0, this.getSize());
    }

    public void filter(String filter) {
        switch (filter) {
            case "All":
                activeEvents = (ArrayList<Event>) allEvents.clone();
                break;
            case "Unfinished":
                activeEvents = allEvents.stream().filter(e -> !e.isDone())
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case "Done":
                activeEvents = allEvents.stream().filter(e -> e.isDone())
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
        }
        sort();
        this.fireContentsChanged(this, 0, this.getSize());
    }
}
