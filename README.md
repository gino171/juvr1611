# juvr1611
Java library to read data from Universal controller UVR 1611 provided by "Technische Alternative" (http://www.ta.co.at/)

Provides API to access data from an UVR1611 device connected to your local LAN:
- de.ginosoft.juvr.read.UVRVersionReader: read version from device
- de.ginosoft.juvr.read.UVRCurrentDataReader: read the actual values from the device
- de.ginosoft.juvr.read.UVRDataReader: read stored data from device (historical data)
- de.ginosoft.juvr.read.UVRResetData: clear data store on device

To get started:
- Configure in heating.properties:
	UVR1611URL  = <Your device URL>
	UVR1611PORT = <Your device port> (usually 40000)
- call SimpleReader main class.
- You see output like this:

--UVR1611 READING START--
Date = 24.09.2016 09:49:15
Hot water tank (top) = 65.1 [°C]
Hot water tank (bottom) = 57.6 [°C]
Boiler = 46.4 [°C]
Solar in = 55.6 [°C]
Solar out = 55.4 [°C]
Solar collector = 68.7 [°C]
Outside temperature = 11.9 [°C]
Water circulation = 33.7 [°C]
Boiler flow temperature = 22.9 [°C]
Room temperature = 22.7 [°C]
Solar pump = On
Heating pump = Off
Circulation pump = Off
Boiler pump = Off
Hot water charger = Off
Heating mixer up = Off
Heating mixer down = Off
Boiler demand = Off
--UVR1611 READING END--


