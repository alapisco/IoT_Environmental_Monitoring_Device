# IoT Smart Environmental Monitoring Device

A IoT Java based project developed for the Raspberry Pi used to participate in the AT&T Developer Summit on January 2016 

https://devsummit.att.com/PostEvent


Features:

- Real time monitoring of Temperature, Humidity , Noise Levels and Luminosity 
- Cloud Syncronization to the AT&T M2X platform
- Real time alert services triggered by dangerous environmental measurements


## Java Code

### Sensors Library

A library was implemented from scratch using the Pi4J project.

This library reads the different sensors used in the project, interprets
the raw analog data and converts it to human readable measurements units.

It also includes code to send data and sync with the M2X platform.

This code is located in the sensors-lib folder.


### Additional utilities

Other utilities were developed in order to automate and configure the sensor
reading, logging, and communicaiton with M2X.
