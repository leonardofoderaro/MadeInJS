package org.madeinjs;

import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.madein.MadeIn;

@SuppressWarnings("restriction")
public class MadeInJS implements ScriptEngine {

	ScriptEngine engine;

	ScriptEngineManager manager;

	List<String> mavenCoordinates;
	List<String> classesImports;

	MadeIn madein;

	public MadeInJS() {


		madein = new MadeIn();

		//Thread.currentThread().setContextClassLoader(madein.getClassLoader());

		/*	URLClassLoader cl;
 			cl = URLClassLoader.newInstance(madein.getUrls());
			Thread.currentThread().setContextClassLoader(cl);*/


		/*	
		try {
			madein.getClassLoader().loadClass("javax.script.ScriptEngineManager");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */

		/*	try {
			Class<?> managerClass = Class.forName("javax.script.ScriptEngineManager",false,madein.getClassLoader());
			manager = (ScriptEngineManager) managerClass.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */

		manager = new ScriptEngineManager();

		engine = manager.getEngineByName("Nashorn");


		mavenCoordinates = new ArrayList<String>();
		classesImports = new ArrayList<String>();

	}


	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		return engine.eval(script, context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader, context);
	}

	@Override
	public Object eval(String script) throws ScriptException {
		String processed = preprocess(script);
		
		return engine.eval(processed);
	}

	private String preprocess(String script) {

		Scanner scanner = new Scanner(script);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
			if (line.startsWith("@resolve")) {
				mavenCoordinates.add(line.replace("@resolve ", "").replaceAll(" .*", ""));
			}

			if (line.startsWith("@import")) {
				classesImports.add(line.replace("@import ", ""));
			}

		}

		for (String s : mavenCoordinates) {
			try {
				madein.install(s);
			} catch (ArtifactResolutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		try {
			URLClassLoader cl = URLClassLoader.newInstance(new URL[] {new URL("file:///Users/iLeo/personalgit/madeinjs/target/local-repo/org/apache/solr/solr-solrj/3.5.0/solr-solrj-3.5.0.jar")});

			Class<?> c = Class.forName("org.apache.solr.common.SolrDocument", true, cl);


		} catch (ClassNotFoundException | MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		try {
			engine.eval("var Class = Java.type(\"java.lang.Class\");");
			engine.eval("print(Class);");
		//	engine.eval("var Integer=Class.forName(\"java.lang.Integer\");");
		//	engine.eval("var i = Integer.newInstance();");

		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (String s : classesImports) {

			try {
				Class cls = Class.forName(s.trim(), false, this.madein.getClassLoader());
				engine.put("X", cls);
				try {
					engine.eval("print(X);");
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} */


		/*	Thread.currentThread().setContextClassLoader(madein.getClassLoader());

		 */



		/*	URLClassLoader cl;
		cl = URLClassLoader.newInstance(madein.getUrls());
		Thread.currentThread().setContextClassLoader(cl);  */


		URLClassLoader cl;
		//	cl = URLClassLoader.newInstance(new URL[] {new URL("file:///Users/iLeo/personalgit/madeinjs/target/local-repo/org/apache/solr/solr-solrj/3.5.0/solr-solrj-3.5.0.jar")});
		cl = URLClassLoader.newInstance(madein.getUrls());

		Thread.currentThread().setContextClassLoader(cl);
		
		manager = new ScriptEngineManager();

		engine = manager.getEngineByName("Nashorn");
		

		for (String s : classesImports) {
			String cmd = "var " + s.replaceAll(".*\\.",  "") + " = Java.type(\"" + s.trim() + "\");";
			//String cmd = "importClass(" + s.trim() + ");";
			System.out.println(cmd);
			try {
				/*	System.out.println(s.replaceAll(".*\\.",  ""));

				(s.replaceAll(".*\\.",  ""), Class.forName(s.trim(), false, this.madein.getClassLoader())); */


				engine.eval(cmd);
				/* engine.put("classLoader", this.madein.getClassLoader());
				engine.eval("print(classLoader)");
				engine.eval("Thread.currentThread().setContextClassLoader(classLoader)"); */
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		scanner.close();

		return script;
	}


	@Override
	public Object eval(Reader reader) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader);
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(script, n);
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader, n);
	}

	@Override
	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		engine.put(key, value);
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return engine.get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		// TODO Auto-generated method stub
		return engine.getBindings(scope);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		engine.setBindings(bindings, scope);

	}

	@Override
	public Bindings createBindings() {
		// TODO Auto-generated method stub
		return engine.createBindings();
	}

	@Override
	public ScriptContext getContext() {
		// TODO Auto-generated method stub
		return engine.getContext();
	}

	@Override
	public void setContext(ScriptContext context) {
		engine.setContext(context);

	}

	@Override
	public ScriptEngineFactory getFactory() {
		return engine.getFactory();
	}
}
