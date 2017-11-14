# OpenWoZ
---
## Description
The project involves developing a community-standard open-source framework for a Wizard-of-Oz (WoZ) system. WoZ is a common technique enabling Human-Robot Interaction researchers to explore aspects of interaction not yet backed by autonomous systems.

The system will include a web application running on an embedded platform which controls a robotics system. It also includes a platform-agnostic client framework that gets updated from a user-accessible database. The system needs to be generic enough to ensure that new robot behavior can be added easily and during run-time.

## Softwares Required On Ubuntu / Mac OSX: 
 * Homebrew. 
 * NPM (node) 
 * redis-server.
 * mysql.
 * git
 
## Installation for Ubuntu / Mac OSX : 
* **Install Homebrew**   
Paste the following command on terminal :     
`/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
Refer Homebrew [Webpage](https://brew.sh/)  


* **Install nodejs**  
Once homebrew is installed, install nodejs by following command :   
`brew install node`.

* **Install Redis server**   
Download the redis-server.   
`wget http://download.redis.io/redis-stable.tar.gz`  
`tar xvzf redis-stable.tar.gz`  
`cd redis-stable`  
`make`  

   At this point you can try if your build works correctly by typing make test, but this is an optional step. After the compilation the src directory inside the Redis distribution is populated with the different executables that are part of Redis:  
**redis-server** is the Redis Server itself.  
**redis-sentinel** is the Redis Sentinel executable (monitoring and failover).  
**redis-cli** is the command line interface utility to talk with Redis.  
**redis-benchmark** is used to check Redis performances.  
**redis-check-aof** and **redis-check-dump** are useful in the rare event of corrupted data files.   
    It is a good idea to copy both the Redis server and the command line interface in proper places, either manually using the following commands:    
`sudo cp src/redis-server /usr/local/bin/`  
`sudo cp src/redis-cli /usr/local/bin/`  
 

* Comment out the line "bind 127.0.0.1" in redis.conf file (typically /etc/redis/6379.conf). In the same config file search for requirepass comment it. Let's not use password during intial setup, if you want password uncomment it, and add alphanumeric password.

* **Start the redis-server**  
  The simplest way to start the Redis server is just executing the redis-server binary without any argument.    
  $ `redis-server /usr/local/etc/redis.conf`
  Note that your location of redis.conf can differ. 
    
* **Testing the redis-server is working properly**  
  The first thing to do in order to check if Redis is working properly is sending a PING command using redis-cli:  
  $ `redis-cli ping`    
  PONG    
  
  Refer [Redis Server](https://redis.io/topics/quickstart) for more details.   
* **Installing mysql**  
`brew update`
`brew doctor`  
`brew upgrade`  
`brew install mysql`  
`mysql.server start`  
`sudo mysql -h 127.0.0.1 -u root`    
 If you have setup your password then use : `sudo mysql -h 127.0.0.1 -u root -p` and enter your password.    
  
   For the first time use the following queries to create your database.   
   //Create the database.  
   `CREATE DATABASE openwoz_db;`  
  
  //Create table to store robot’s data.  
  `CREATE TABLE openwoz_robots (robot_key VARCHAR(255) PRIMARY KEY, robot_name VARCHAR(255), channel_name  VARCHAR(255),image_path VARCHAR(255), purpose VARCHAR(255), platform VARCHAR(255));`  
  
  //Check if the schema is created properly  
  `describe openwoz_robots;`  
  
  //Create table to store events related to robots.    
  `CREATE TABLE openwoz_robot_events (event_key VARCHAR(255), robot_key VARCHAR(255), event_name VARCHAR(255),class_name VARCHAR(255), method_name VARCHAR(255),PRIMARY KEY (event_key,robot_key),FOREIGN KEY (robot_key) REFERENCES openwoz_robots(robot_key));`    

  //Check if the schema is created properly.  
  `describe openwoz_robot_events;`    
  
  //Insert robots :   
  `INSERT INTO openwoz_robots VALUES ("vyo_robot","Vyo Robot", "vyo_robot","imgs/vyo.jpg","A social robot designed to manage your smart home.","Raspberry Pi"),("some_robot","Some Robot","some_robot","imgs/some_robot.jpg","A social robot designed to manage your smart home.","Raspberry Pi”),("blossom","Blossom", "blossom","imgs/blossom.jpg","Awesome robot.","Raspberry Pi");`    
  //Insert events related to robots.  
  `INSERT INTO openwoz_robot_events VALUES ("headnod","vyo_robot","headnod","vyo.events.HeadNodEvent","nodHead"),  ("glowled","vyo_robot","glowled","vyo.events.BlinkLedEvent","blinkLed”),("breath","blossom","breath","blossom.events.Breath","breath”),
("glowled","some_robot","glowled","somerobot.events.BlinkLedEvent","blinkLed");`    

  //Test if all the data is inserted properly.  
  `SELECT * from openwoz_robots;`    
  `SELECT * from openwoz_robot_events;`    

  Once the database is created and you login mysql again then use `use openwoz_db` command instead of re-creating database again.   
  
  
  Refer [Blog](https://blog.joefallon.net/2013/10/install-mysql-on-mac-osx-using-homebrew/)  
 
  Once you are done using the mysql :  
  `mysql.server stop`
  
* **Download the code base** 

  `mkdir openwoz`  
  `cd openwoz`  
  `git clone https://github.com/vaidehimurarka/openwoz.git`  
  `cd openwoz/server`  
* **Install node dependencies**   
   `cd openwoz/server`  
   `sudo npm install express`  
   `sudo npm install pug`  
   `sudo npm install mysql`  
   `sudo npm install fs`  
   `sudo npm install redis`  
   `sudo npm install body-parser`  
   `sudo npm install path`  
   `sudo npm install synchronize`    
   `sudo npm install promise`  
     
 * **Run the node js server.**   
   1. Create the new tab in terminal, start the redis-server.  
     `redis-server /usr/local/etc/redis.conf`  
   2. Create the new tab in terminal,start the mysql server.  
      `mysql.server start`   
      `sudo mysql -h 127.0.0.1 -u root`
   3. Create the new tab and start node.js server. 
      `cd openwoz/server` 
      `sudo node server`  
        
 * **Stop the execution**   
   1. Stop the redis-server.  
      Go to redis-server terminal and press `Ctrl+C`.
   2. Stop the mysql server.   
      Go to mysql terminal and press `Ctrl+C`  
      `mysql.server stop`
   3. Go to node.js terminal 
       Go to mysql terminal and press `Ctrl+C`  
       
       
  ## Installation for Raspberry Pi : 
  
        
        
