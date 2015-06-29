# AvroTest
A utility for testing out Avro File formats to ensure that are well formed.

Use
---
Using a avro schema file to test out the avro file to makes sure file can be read.

HW10593:Downloads root# java -jar AvroTest.jar -schema=user.avsc -avro=users2.avro

schema of avro file to test user.avsc

avro file to test users2.avro

working on writing Avro File out based on the Schema to see if avro file contents are correct according to the schema defined.....

{"name": "Alyssa", "favorite_number": 256, "favorite_color": null}
{"name": "Ben", "favorite_number": 7, "favorite_color": "red"}
{"name": "Charlie", "favorite_number": null, "favorite_color": "blue"}


This project comes with a sample users2.avro file so that folks can test it

HW10593:Downloads root# java -jar AvroTest.jar -testfile=users2.avro

testfile being created
file written

{"name": "Alyssa", "favorite_number": 256, "favorite_color": null}
{"name": "Ben", "favorite_number": 7, "favorite_color": "red"}
{"name": "Charlie", "favorite_number": null, "favorite_color": "blue"}


