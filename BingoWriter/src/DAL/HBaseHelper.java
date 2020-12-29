package DAL;

import BLL.Common.LogManager;
import UI.BingoWriterUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.*;

public class HBaseHelper {
    private LogManager logManager = new LogManager();

    private Connection GetConnection(String host, String port) {
        Connection conn = null;
        final String url = "jdbc:phoenix:" + host + ":" + port;
        try {
            final ExecutorService exec = Executors.newFixedThreadPool(1);
            Callable<Connection> call = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return DriverManager.getConnection(url);
                }
            };
            Future<Connection> future = exec.submit(call);
            conn = future.get(5000, TimeUnit.MILLISECONDS);
            exec.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean ConnectToHbase(String host, String port) {
        boolean ret = true;
        Connection conn = null;
        try {
            conn = GetConnection(host, port);
            if (conn == null) {
                ret = false;
            }
        } catch (Exception e) {
            ret = false;
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public int UpsertData(BingoWriterUI ui, List<String> sqlList) {
        Statement statement = null;
        Connection conn = null;
        int errorCount = 0;
        try {
            conn = DriverManager.getConnection("jdbc:phoenix:" + ui.modelConfigInfo.getZKHost() + ":" + String.valueOf(ui.modelConfigInfo.getZKPort()));
            statement = conn.createStatement();
            for (String sql : sqlList) {
                try {
                    statement.executeUpdate(sql);
                } catch (Exception e) {
                    logManager.LogRecord(
                            ui.modelConfigInfo.getLogFilePath(),
                            ui.modelConfigInfo.getInputFileName(),
                            "[upsert error] " + e.getMessage() + "\n[SQL] " + sql,
                            "HB"
                    );
                    errorCount++;
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorCount;
    }

    public String CreateOrUpdateTable(String host, String port, String tbName, List<String> columnsList, String pk, int saltBuckets) {
        String ret = "[T]";
        Statement statement = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:phoenix:" + host + ":" + port);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + tbName;
            String tmp = "";
            for (String s : columnsList) {
                tmp += (s + ",");
            }
            if (tmp.contains(",")) {
                tmp = tmp.substring(0, tmp.lastIndexOf(","));
            }
            sql += "(" + tmp + " CONSTRAINT PK PRIMARY KEY (" + pk + ")) salt_buckets=" + saltBuckets;
            statement.executeUpdate(sql);
            conn.commit();
        } catch (Exception e) {
            ret = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public String AddTableColumn(String host, String port, String tbName, String columns) {
        String ret = "[T]";
        Statement statement = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:phoenix:" + host + ":" + port);
            statement = conn.createStatement();
            String sql = "ALTER TABLE " + tbName + " ADD " + columns;
            statement.executeUpdate(sql);
            conn.commit();
        } catch (Exception e) {
            ret = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
