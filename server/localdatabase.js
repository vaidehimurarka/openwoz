var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  //password: "root",
  //database: "openwoz_db"
});

/*
con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  con.query("CREATE DATABASE openwoz_db", function (err, result) {
    if (err) throw err;
    console.log("Database created");
  });
  /*con.query("Select * from openwoz_robots",function(err,result){
	if(err)throw err;  	
	console.log(result);
  });*/
});
*/

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  var sql = "CREATE TABLE openwoz_robots (robot_key VARCHAR(255) PRIMARY KEY, robot_name VARCHAR(255), channel_name VARCHAR(255),image_path VARCHAR(255), purpose VARCHAR(255), platform VARCHAR(255))";  
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("Openwoz_robots Table created");
  });
  var sql2 = "CREATE TABLE openwoz_robot_events (event_key VARCHAR(255), robot_key VARCHAR(255), event_name VARCHAR(255),class_name VARCHAR(255), method_name VARCHAR(255),PRIMARY KEY (event_key,robot_key),FOREIGN KEY (robot_key) REFERENCES openwoz_robots(robot_key))";  
  con.query(sql2, function (err, result) {
    if (err) throw err;
    console.log("openwoz_robot_events  Table created");
  });
});



