
import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class TestAvro {

	public static void main(String[] args) throws IOException{
		int good = 0;
		String schemaAvroFile="";
		String avroFile="";
			
			for (int x=0; x < args.length; x++)
			{
				if (args[x].substring(0, 10).compareToIgnoreCase("-TESTFILE=") == 0)
				{
					System.out.println("testfile being created");
					TestFile();
				}
				else if (args[x].substring(0, 8).compareToIgnoreCase("-SCHEMA=") == 0)
				{
					//System.out.println("schema of avro file to test");
					good++;
					schemaAvroFile=args[x].substring(8, args[x].length());
					System.out.println("schema of avro file to test " + schemaAvroFile);
				}
				else if (args[x].substring(0, 6).compareToIgnoreCase("-AVRO=") == 0)
				{
					//System.out.println("avro file to test");
					avroFile=args[x].substring(6, args[x].length());
					good++;
					System.out.println("avro file to test " + avroFile);
				}
				else
				{
					System.out.println("arguments are -testfile=, -schema=<path to the avro schema file>, -avro=<path to the avro file");
				}
			}
			
			if (good>1 && good<3){
				TestAvroFile(schemaAvroFile,avroFile);
			}else
			{
				System.out.println("arguments are -testfile=, -schema=<path to the avro schema file>, -avro=<path to the avro file to test against the avro schema>");
			}
	}
	public static void TestFile() throws IOException{
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		
		User user2 = new User("Ben", 7, "red");
		User user3 = new User("Charlie", null, "blue");

		// Serialize user1, user2 and user3 to disk
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		dataFileWriter.create(user1.getSchema(), new File("/Users/drice/Downloads/users2.avro"));
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();
		System.out.println("file written\n");
		
		// Deserialize Users from disk
		DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
		File f = new File("/Users/drice/Downloads/users2.avro");
		DataFileReader<User> dataFileReader = new DataFileReader<User>(f , userDatumReader);
		User user = null;
		while (dataFileReader.hasNext()) {
		// Reuse user object by passing it to next(). This saves us from
		// allocating and garbage collecting many objects for files with
		// many items.
		user = dataFileReader.next(user);
		System.out.println(user);
		
		}
		
		System.out.println("\nfile read");

	}
	public static void TestAvroFile(String schemaAvroFile, String avroFile) throws IOException{
		System.out.println("working on writing Avro File out based on the Schema to see if avro file contents are correct according to the schema defined.....");
		//Schema schema = new Schema.Parser().parse(new File("/Users/drice/Downloads/TransactionTrade.avsc"));
		//Schema schema = new Schema.Parser().parse(new File("/Users/drice/Downloads/user.avsc"));
		Schema schema = new Schema.Parser().parse(new File(schemaAvroFile));
		//File f = new File("/Users/drice/Downloads/users2.avro");
		File f = new File(avroFile);
		// Deserialize users from disk
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(f, datumReader);
		GenericRecord user = null;
		while (dataFileReader.hasNext()) {
		// Reuse user object by passing it to next(). This saves us from
		// allocating and garbage collecting many objects for files with
		// many items.
		user = dataFileReader.next(user);
		System.out.println(user);
		}	
	}
}