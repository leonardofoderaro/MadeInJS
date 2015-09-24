Made in JS
===

Enjoy your favorite Maven packages in Javascript.

Assuming you have a working SolrCloud instance:

    /*
    @resolve org.apache.httpcomponents:httpclient:4.4.1
    @resolve org.apache.solr:solr-solrj:4.8.0 
    @import org.apache.solr.client.solrj.impl.CloudSolrServer
    @import org.apache.solr.client.solrj.SolrQuery
    @import org.apache.http.impl.client.SystemDefaultHttpClient
    @import org.apache.http.impl.client.CloseableHttpClient
    */

    var zkHost = "<your-zookeeper>:2181";
    var server = new CloudSolrServer(zkHost);

    var qs = {
        "defType": "edismax",
        "qf": "name",
        "fl": "name, score",
        "rows": "10",
        "collection": "<your-collection>",
        "q": "lightsaber"
    }

    var parameters = new SolrQuery();

    for (var q in qs) {
        parameters.add(q, qs[q]);
    }

    var response = server.query(parameters);
    var results = response.getResults();

    print("Found " + results.getNumFound() + " results:");

    for (var i in results) {
        print(results[i].name + ": " + results[i].score);
    }

    print("\n\n");


You'll need the [MadeIn](https://github.com/leonardofoderaro/MadeIn) package to run it. 
