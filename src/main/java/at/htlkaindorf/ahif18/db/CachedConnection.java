/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author PD
 */
public class CachedConnection {

    private Queue<Statement> statQueue = new LinkedList<>();
    private Connection con;

    public CachedConnection(Connection con) {
        this.con = con;
    }

    public Statement getStatement() throws Exception {
        if (con == null) {
            throw new Exception("not connected");
        }

        if (!statQueue.isEmpty()) {
            return statQueue.poll();
        }
        return con.createStatement();
    }

    public void releaseStatement(Statement stat) throws Exception {
        if (con == null) {
            throw new Exception("not connected");
        }
        statQueue.offer(stat);
    }
}
