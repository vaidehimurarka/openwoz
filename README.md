# OpenWoZ
---
## Description
The project involves developing a community-standard open-source framework for a Wizard-of-Oz (WoZ) system. WoZ is a common technique enabling Human-Robot Interaction researchers to explore aspects of interaction not yet backed by autonomous systems.

The system will include a web application running on an embedded platform which controls a robotics system. It also includes a platform-agnostic client framework that gets updated from a user-accessible database. The system needs to be generic enough to ensure that new robot behavior can be added easily and during run-time.

## Softwares Required On Ubuntu : 
 * Homebrew. 
 * NPM (node) 
 * redis-server.
 * mysql.
 * git
 
## Installation for Ubuntu
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

Refer [Redis Server](https://redis.io/topics/quickstart) for more details.



> sudo apt-get install build-essential <br/>
> sudo apt-get install tcl8.5 <br/>
> wget http://download.redis.io/releases/redis-stable.tar.gz <br/>
> tar xzf redis-stable.tar.gz <br/>
> cd redis-stable <br/>
> make <br/>
> //Can skip the next step but highly suggested to detect any issues <br/>
> make test <br/>
> sudo make install <br/>
> <br/>
> //Keep pressing enter for the below command to install redis with default config <br/>
> sudo utils/install_server.sh <br/>

* Comment out the line "bind 127.0.0.1" in redis.conf file (typically /etc/redis/6379.conf)
* In the same config file search for requirepass and uncomment it and change the password to an alphanumeric and complicated.

> requirepass "your_redis_password"

* Start the server

> sudo service redis_6379 restart

* Download openwoz latest code using

> cd ~ <br/>
> wget https://github.com/amirajdhawan/openwoz/archive/release.tar.gz <br/>
> tar xzf release.tar.gz <br/>
> cd openwoz-release/server <br/>

* Edit the file server.js and set the redis password in the first line which you set in the redis configuration file

> var redis_pass = "redis_password_that_you_set_above"

* Install npm dependencies.

> sudo npm install <br/>

* Start the server using forever

> forever start server.js

### Make it available across server restarts

> crontab -u {username} -e <br/>

* At the end append the following line

> @reboot /usr/local/bin/forever start /path/to/openwoz/folder/server/server.js

* Restart and the server will automatically start across reboots

The server is accessible in ip_address

## Usage
In the folder server, to restart the server use the below

> forever restart server.js

In the folder server, start the server or to stop the server using the below

> forever start|stop server.js

---
## Available Links

> GET ip_address/

> GET ip_address/robots

> GET ip_address/robots/{profile_name}

> GET ip_address/robots/{profile_name}/{event_name}

> GET ip_address/robots/{profile_name}/{event_name}/trigger
