/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author mjuri
 */
public class Event implements Comparable<Event> {

    private String name, description;
    private LocalDateTime date;
    private boolean manual, done;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm EEE d MMM", Locale.GERMAN);

    public Event(String name, String description, LocalDateTime date, boolean manual, boolean done) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.manual = manual;
        this.done = done;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateString() {
        return dtf.format(date);
    }

    public boolean isManual() {
        return manual;
    }

    public String isManualString() {
        String result;
        if (this.isManual()) {
            result = "Yes";
        } else {
            result = "No";
        }
        return result;
    }

    public void markDone() {
        this.done = !this.done;
    }

    public boolean isDone() {
        if (this.isManual()) {
            return this.done;
        } else {
            return LocalDateTime.now().isAfter(date);
        }
    }
    
    public boolean isOverdue() {
        return this.isManual() && !this.done && LocalDateTime.now().isAfter(date);
    }

    public String isDoneString() {
        String result;
        if (this.isDone()) {
            result = "Yes";
        } else {
            result = "No";
        }
        return result;
    }

    @Override
    public int compareTo(Event evt) {
        int dateComp = this.date.compareTo(evt.getDate());
        return dateComp == 0 ? this.name.compareTo(evt.getName()) : dateComp;
    }
}
