/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.db;

import at.htlkaindorf.ahif18.data.Event;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reapie
 */
public class DBAccess {

    private DataBase db;
    private Connection con;

    private static final String SQL_INSERT_EVENT = "INSERT INTO event (name, description, date, done, manual) VALUES (?, ?, ?, ?, ?) ON conflict ON CONSTRAINT event_pkey "
            + "DO update SET description = ?, manual = ?, done = ?";
    private final PreparedStatement psInsertEvent;

    private static final String SQL_GET_EVENTS = "SELECT * FROM event";

    private static final String SQL_CLEAR_EVENTS = "DELETE FROM event";

    public DBAccess() throws ClassNotFoundException, IOException, SQLException {
        db = DataBase.getInstance();
        con = db.getConnection();
        psInsertEvent = con.prepareStatement(SQL_INSERT_EVENT);
    }

    public void disconnect() throws SQLException {
        db.disconnect();
    }

    //Zugriffsfunktionen
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList();
        try {
            Statement stat = db.getStatement();
            ResultSet rs = stat.executeQuery(SQL_GET_EVENTS);
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                boolean done = rs.getBoolean("done");
                boolean manual = rs.getBoolean("manual");
                Event evt = new Event(name, description, date, manual, done);
                events.add(evt);
            }
        } catch (Exception ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public void insertEvent(Event e) {
        try {
            psInsertEvent.setString(1, e.getName());
            psInsertEvent.setString(2, e.getDescription());
            psInsertEvent.setTimestamp(3, Timestamp.valueOf(e.getDate()));
            psInsertEvent.setBoolean(4, e.isDone());
            psInsertEvent.setBoolean(5, e.isManual());
            // repeat for replacing data
            psInsertEvent.setString(6, e.getDescription());
            psInsertEvent.setBoolean(7, e.isManual());
            psInsertEvent.setBoolean(8, e.isDone());
            psInsertEvent.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearEvents() {
        try {
            Statement stat = db.getStatement();
            stat.executeUpdate(SQL_CLEAR_EVENTS);
        } catch (Exception ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
