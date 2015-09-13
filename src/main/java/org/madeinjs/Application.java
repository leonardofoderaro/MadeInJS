package org.madeinjs;


public class Application {


	public static void main( String[] args ) throws Exception
	{
		MadeInJS engine = new MadeInJS();

		StringBuffer script = new StringBuffer();

		script.append("/*\n\r");
		script.append("@resolve org.apache.solr:solr-solrj:5.3.0 \n\r");
		script.append("@import java.lang.Thread \n\r");
		script.append("@import org.apache.solr.common.SolrDocument \n\r");
		script.append("*/ \n\r");
		script.append("var doc = new SolrDocument();");
		script.append("doc.setField('nome', 'leo');");
		script.append("print(doc);");

		System.out.println(engine.eval(script.toString()));

	}

}
