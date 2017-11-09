var redis_pass = "foo";

//Get Instance of ExpressJS
var express = require('express');
var app = express();
var fs = require('fs');
var path = require('path');
var redis = require('redis');
var pug = require('pug');
var bodyParser = require('body-parser')
var mysql = require('mysql');
var Promise = require('promise');
var sync = require('synchronize');
//add the entries.
app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
}));

//Custom
var routes = require('./routes');
var robo_profiles_ds = require('./models/robot_profiles')
var config = require('./config');

//Access static content from assets folder
app.use(express.static('assets'));
app.set('views', __dirname + '/views');
app.set('view engine', 'pug');

//Redis channels
var redisChannels = {};

//Connecting to database.
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  //password: "root",
  database: "openwoz_db"
});

function get_robot_names_and_events(finalResult){
	con.connect(function(err){
		if(err)throw err;
		var resultJson = {};
		var queryString = "select * from openwoz_robots left join openwoz_robot_events on openwoz_robots.robot_key=openwoz_robot_events.robot_key";
		con.query(queryString,function(err,res){
			if(err)throw err;
			for(var i= 0;i<res.length;i++){
				//TOBE REMOVED....
				if(res[i].robot_key == undefined){
					continue;
				}
				if(resultJson[res[i].robot_key] == undefined){
					redisChannels[res[i].robot_key] = res[i].robot_key;
					var profile = {};
					profile["profileKey"] = res[i].robot_key; 
					profile["profileName"] = res[i].robot_name;
					profile["channelName"] = res[i].channel_name;
					profile["image"] = res[i].image_path;
					profile["purpose"] = res[i].purpose;
					profile["platform"] = res[i].platform;
					var events = {};
					var event = {};
					if(res[i].event_key == undefined){
						profile["events"] = events;
						//profile["events"] = ;
						resultJson[res[i].robot_key] = profile;
						continue;
					}
					event["name"] = res[i].event_key;
					event["className"] = res[i].class_name;
					event["methodName"] = res[i].event_name;
					events[res[i].event_key] = event;
					profile["events"] = events;
					//profile["events"] = ;
					resultJson[res[i].robot_key] = profile;
				}else{
					var profile = resultJson[res[i].robot_key];
					var events = profile["events"];
					var event = {};
					if(res[i].event_key == undefined){
						continue;
					}
					event["name"] = res[i].event_key;
					event["className"] = res[i].class_name;
					event["methodName"] = res[i].event_name;
					events[res[i].event_key] = event;
					profile["events"] = events;
					resultJson[res[i].robot_key] = profile;
				}	
			}
			finalResult["robot_profiles"] = resultJson;
		});
	});
}

var result_ds = {};
var redisChannels = {}
get_robot_names_and_events(result_ds,redisChannels);


//Read all robot profiles

//var fileNames = fs.readdirSync(path.join(__dirname, 'robotprofiles/'));
/*
for(var i = 0; i < fileNames.length; i++){
	var fileContents = JSON.parse(fs.readFileSync('robotprofiles/' + fileNames[i], 'utf8'));
	var filename = fileNames[i].split(".js")[0];
	robo_profiles_ds.robot_profiles[filename] = fileContents
	redisChannels[filename] = filename;
}*/

publisher = redis.createClient();
publisher.auth(redis_pass);
//publisher.publish(redisChannels["vyo_robot"], JSON.stringify(message));

//Map all controllers to URL's
[
'test'
].map(function(controllerName){
	var controller = require('./controllers/' + controllerName);
	controller.setup(app);
});

app.get('/', function(req, res){
	res.render("index");

});

app.get('/robots/:profile_name/:event_name', function(req, res){
	//Actually send it to the redis channel
	//console.log(req.body);
	var profile = req.params.profile_name;
	var event = req.params.event_name;
	//console.log("Value: " + req.body.newVal);
	//var new_value = parseFloat(req.body.newVal);
	var result = [];
	var message = result_ds.robot_profiles[profile].events[event];
	message["time"] = 0;
	result.push(message);
	//message.value = new_value;
	console.log("Sending redis channel: " + redisChannels[profile] + " message:" + JSON.stringify(result));
	publisher.publish(redisChannels[profile], JSON.stringify(result));
	
	//res.render("profile", {profile: robo_profiles_ds.robot_profiles[profile], msg: "Event successfully triggered!"});
	res.json({msg:"Event successfully triggered!"});
	//res.render("event", {msg: "Message successfully passed!", profile: robo_profiles_ds.robot_profiles[profile], 
	//	event_name: req.params.event_name});
});

app.get('/robots/:profile_name/:event_name', function(req, res){
	res.render("event", {profile: result_ds.robot_profiles[req.params.profile_name], event_name: req.params.event_name});
});

app.get('/robots/:profile_name', function(req, res){
	res.render("profile", {profile: result_ds.robot_profiles[req.params.profile_name]});
});

app.get('/robots', function(req, res){
	res.render("robots",{profiles:result_ds.robot_profiles});
});

app.get('/sendevent', function(req, res){
	//"events/index.html");
});

app.post('/sequences/:profile_key',function(req,res){
	//console.log(req.params.profile_key);
	console.log(req.body);
	var dict = req.body;
	var result = {};
	var profile = req.params.profile_key;
	var result = [];
	for(var key in dict){
		console.log(result_ds.robot_profiles[profile].events[dict[key][0]] +
			" "+dict[key][1]);
		var eventDict = result_ds.robot_profiles[profile].events[dict[key][0]];
		var event = {};
		for(var eventDictKey in eventDict){
			event[eventDictKey] = eventDict[eventDictKey];
		}
    	event["time"] = dict[key][1];
    	result.push(event);
	}
	console.log("Sending redis channel: " + redisChannels[profile] + " message:" + JSON.stringify(result));
	publisher.publish(redisChannels[profile],JSON.stringify(result));
	res.json({msg:"Sequence successfully triggered!"});
	//res.json({msg:"Event successfully triggered!"});
	//res.render("profile", {profile: result_ds.robot_profiles[req.params.profile_name]});
});

/*app.get('/test', routes.index);
app.get('/partials/:name', routes.partials);*/

//Route for '/'
/*app.get('/profiles/view', function(req, res){
	//res.send('Got get request, serving from Server Root!');
	var string = "";
	for(profile in robo_profiles_ds.robot_profiles){
		string = string + "<br/>" + JSON.stringify(robo_profiles_ds.robot_profiles[profile])
	}
	res.json({profiles:robo_profiles_ds.robot_profiles});
});*/

app.listen(config.server.port, function(){
	console.log('Starting openwoz server at port ' + config.server.port + '!');
});

