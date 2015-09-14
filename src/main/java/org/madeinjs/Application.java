package org.madeinjs;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Application {


	public static void main( String[] args ) throws Exception
	{
		MadeInJS engine = new MadeInJS();

		List<String> l = Files.readAllLines( Paths.get(engine.getClass().getResource("script.js").toURI()), Charset.defaultCharset());


		String joined = String.join("\n\r", l);
		engine.eval(joined);
	}

}
