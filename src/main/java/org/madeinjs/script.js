/*
@resolve org.xerial:sqlite-jdbc:3.8.11.1
@import org.sqlite.JDBC
@import java.sql.Connection
@import java.sql.Driver
@import java.sql.ResultSet
@import java.sql.SQLException
@import java.sql.Statement
@import java.util.Properties
*/


var driver = new JDBC();
var conn = driver.connect("jdbc:sqlite:test.db", new Properties());

var sql = "";
var stmt = null;

try {
	stmt = conn.createStatement();
	sql = "CREATE TABLE TEST (MESSAGE TEXT)"; 
	stmt.executeUpdate(sql);

	stmt.close();

} catch (sqlex) {
 //  print(sqlex);
}

sql = "insert into test(message) values('hello world!')";
stmt.executeUpdate(sql);
stmt.close();

sql = "select message from test";

var rs = stmt.executeQuery(sql);

while (rs.next()) {
	var msg = rs.getString("message");
	print(msg);
}

rs.close();

conn.close();

