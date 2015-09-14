/*
@resolve org.apache.httpcomponents:httpclient:4.4.1
@resolve org.apache.solr:solr-solrj:4.8.0 
@import org.apache.solr.client.solrj.impl.CloudSolrServer
@import org.apache.solr.client.solrj.SolrQuery
@import org.apache.http.impl.client.SystemDefaultHttpClient
@import org.apache.http.impl.client.CloseableHttpClient
*/

var zkHost = "src-app-dev.eprice.local:2181";
var server = new CloudSolrServer(zkHost);

//server.setParser(new XMLResponseParser());
var parameters = new SolrQuery();
parameters.add("q", "*:*");
parameters.add("rows", "2");
//parameters.add("qt", "/select"); 
parameters.add("collection", "0"); 
var response = server.query(parameters);
var list = response.getResults();
print(list);


