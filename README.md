# springboothelloworld #
This repository features code from a personal fun project that includes a Springboot API builder base, that is hosted on GCP.

### API base IP ###
http://34.126.202.193 (__NOT SECURE__)

### API Functions ###
* __Hello World__   : /hello?name={Insert Name}                --> Returns "Hello {Name}!"
* __Now__           : /now                                     --> Returns the Date+Time currently, on GMT
* __Binary Message__: /tmpsckt1?binMsg={Insert Binary Message} --> Returns decoded binary message based on decoding principles
* __Hex Message__   : /tmpsckt2?hexMsg={Insert Hex Message}    --> Returns decoded hexadecimal message based on binary decoding principles

### Message Decoding Principles ###
The messages coming through are currently capped at the following limits:
* Has to be 16 bits in binary length
* Has to translate to 16 bits in binary length if Hex message is passed

The message is broken down into 3 subsections based on binary message. Below you will find an example of what passing "1100110011001010" would be interpreted:

Device ID  | Message Type | Message Value
---------- | ------------ | -------------
1100       | 1100         | 11001010

* __Device ID__    :Decode into an integer
* __Message Type__ :Decode into an integer; Can take one of 3 forms:
* * MessageType.Temperature == integer 14
* * MessageType.Pressure == integer 12
* * MessageType.Humidity == integer 10

### Purpose ###
The purpose for developing the project is primarily to help the author with learning Docker, Kubernetes, GCP, Deployments, Java, SQL, Server management, IP forwarding, and HTTP protocols. It also, will eventually feature integration from a Raspberry Pi device as an "IoT Publisher" and will have the capacity to work with multiple at a time and commit to the same table with different device IDs. 


