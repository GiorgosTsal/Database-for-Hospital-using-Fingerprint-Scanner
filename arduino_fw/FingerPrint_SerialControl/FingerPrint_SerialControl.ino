/*********************************
 * Fingerprint Sensor / Serial Interface
 * Team28 - 2015
 * Published under The MIT License
 *
 * Sensor connection pins:
 *
 * Red connects to +5V
 * Black connects to Ground
 * Green goes to Digital 2
 * White goes to Digital 3
 *********************************/

/*
 *  Current serial commands:
 * 1001 Enroll [id]
 * 1002 Delete [id]
 * 1003 Identify (10s timeout)
 */

#include <Adafruit_Fingerprint.h>
#include <SoftwareSerial.h>


// General setup
uint8_t ledPin = 13;
unsigned long timeout = 0;


// Fingerprint Sensor Setup
SoftwareSerial mySerial(2, 3);
Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);


// Function declarations
int enrollFingerprint();
uint8_t deleteFingerprint();
int getFingerprint();


// Startup code
void setup() {
	pinMode(ledPin, OUTPUT);
	Serial.begin(9600);
	Serial.setTimeout(100);

	// set the data rate for the sensor serial port
	finger.begin(57600);
	// check if sensor is connected properly
	if(finger.verifyPassword()) {
		Serial.println(1);
	} else {
		Serial.println(0);
		while(true);
	}
}

// Main loop
void loop() {
	// Read command from serial
	while(!Serial.available());
	int command = Serial.parseInt();

	switch (command) {
		// 1001 Enroll fingerprint
		case 1001:
			Serial.println(enrollFingerprint());
			break;
		// 1002 Delete fingerprint
		case 1002:
			Serial.println(deleteFingerprint());
			break;
		// 1003 Identify fingerprint
		case 1003: {
			timeout = millis() + 10000;
			while(timeout > millis() && getFingerprint() == -1)
				delay(333);
			break;
			}
		// Indicate wrong command by flashing the led
		default:
			for(int i=0; i<5; i++) {
				digitalWrite(ledPin, HIGH);
				delay(50);
				digitalWrite(ledPin, LOW);
				delay(50);
			}
			digitalWrite(ledPin, HIGH);
			delay(1000);
			digitalWrite(ledPin, LOW);
	}
}

