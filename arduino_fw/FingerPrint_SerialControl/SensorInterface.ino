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

// enrollFingerprint() return values:
// 0	OK
// -1	No finger timeout
// -2	Proccessing error
// -3	No match
// -4	Can't store
int enrollFingerprint() {
	// Read id from serial
	while(!Serial.available());
	uint8_t id = Serial.parseInt();

	// Read fingerprint
	timeout = millis() + 10000;
	while(timeout > millis() && finger.getImage() != FINGERPRINT_OK)
		delay(333);
	if(millis() >= timeout) return -1;
	Serial.println(1);
	// Step 1 successful

	// Proccess fingerprint
	if(finger.image2Tz(1) != FINGERPRINT_OK) return -2;
	Serial.println(2);
	// Step 2 successful

	// Wait for finger removal
	delay(1000);
	while(finger.getImage() != FINGERPRINT_NOFINGER);

	// Read fingerprint again
	timeout = millis() + 10000;
	while(timeout > millis() && finger.getImage() != FINGERPRINT_OK)
		delay(333);
	if(millis() >= timeout) return -1;
	Serial.println(3);
	// Step 3 successful

	// Proccess second fingerprint
	if(finger.image2Tz(2) != FINGERPRINT_OK) return -2;
	Serial.println(4);
	// Step 4 successful

	// Match fingerprints
	if(finger.createModel() != FINGERPRINT_OK) return -3;
	Serial.println(5);
	// Step 5 successful

	// Store fingerprint
	if(finger.storeModel(id) != FINGERPRINT_OK) return -4;
	Serial.println(6);
	// Step 6 successful

	return FINGERPRINT_OK;
}

uint8_t deleteFingerprint() {
	// Read id from serial
	while(!Serial.available());
	uint8_t id = Serial.parseInt();

	return finger.deleteModel(id);
}

int getFingerprint() {
	uint8_t p = finger.getImage();
	if(p != FINGERPRINT_OK) return -1;
	p = finger.image2Tz();
	if(p != FINGERPRINT_OK) return -1;
	p = finger.fingerFastSearch();
	if(p != FINGERPRINT_OK) return -1;

	// if it reaches this point it has a match
	Serial.println(finger.fingerID);
	return finger.fingerID;
}

