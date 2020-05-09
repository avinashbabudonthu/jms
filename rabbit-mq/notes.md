## Install Rabbit MQ
* Install Erlang - RabbitMQ requires a 64-bit Erlang - https://www.erlang.org/downloads
	* Download latest version and install
	* Install exe file and follow the commands
* Download Rabbit MQ - https://www.rabbitmq.com/install-windows.html
	* Download latest version - as of now i am using `rabbitmq-server-3.8.3.exe` 
	* Install exe and follow commands
* open cmd in C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.4\sbin
* Run
```
rabbitmq-server.bat start
```
* Run
```
rabbitmq-plugins.bat enable rabbitmq_management
```
* open browser and open this url - http://localhost:15672
	* default username/pwd are guest/guest
	* this is rabbit mq management console

## Rabbit MQ Notes

### Message Broker Flow
![picture](images/message-broker.jpg)