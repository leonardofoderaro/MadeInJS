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

print("creating JDBC connection...");
var driver = new JDBC();
var conn = driver.connect("jdbc:sqlite:test.db", new Properties());


var sql = "";
var stmt = null;

try {
	stmt = conn.createStatement();
	sql = "CREATE TABLE TEST (MESSAGE TEXT)";
	print("creating table...");
	stmt.executeUpdate(sql);

	stmt.close();

} catch (sqlex) {
   print(sqlex);
}

sql = "insert into test(message) values('hello world! - "  + new Date() + "')";
print("executing insert...");
stmt.executeUpdate(sql);
stmt.close();

sql = "select message from test";

print("executing select...");
var rs = stmt.executeQuery(sql);

print("iterating results...")
while (rs.next()) {
	var msg = rs.getString("message");
	print(msg);
}


print("closing resultset and connection...");
rs.close();
conn.close();

print("thanks!");

