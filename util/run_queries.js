var async = require('async');
var neo4j = require('node-neo4j');
var fs = require('fs');
var db = new neo4j('http://localhost:7474');

async.eachSeries(fs.readFileSync('init.cypher', 'utf-8').split('\n\n'),
  function(q, cb) {
    var joined = q;
//    var joined = q.replace(/\n/, ' ');

    db.cypherQuery(joined, function(err, result) {
      if (err) throw err;

      if (result.hasOwnProperty('data')) console.log("Query result:", result);
      console.log("\nRan query:", joined);
      cb();
    });
  },
  function(error) {
    if (error) {
      console.log("Failed to execute query");
    } else {
      console.log("All queries executed successfully");
    }
  });
