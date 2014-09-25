match (n)
optional match (n)-[r]-()
delete n,r

create (afoo:Author {id: "afoo", firstname: "ach", lastname: "foo", email: "afoo@lulzotron.com"})

create (mfoo:Author {id: "mfoo", firstname: "michael", lastname: "foo", email: "mfoo@lulzotron.com"})

create (spfm:Post {title: "Package Solr 4 Using Maven-hosted...", summary: "...", content: "...", id: "package-solr4-using-maven-hosted", author_date: 1410233513, publish_date: null})

create (ccr:Post {title: "Combining Compojure Routes", summary: "...", content: "...", id: "combining-compojure-routes", author_date: 1410233697, publish_date: null})

create (pb:Post {title: "Writing Python like a boss", summary: "...", content: "...", id: "writing-python-like-a-boss", author_date: 1410234600, publish_date: null})

create (programming:Tag {id: "programming", label: "Programming"})
create (languages:Tag {id: "languages", label: "Languages"})
create (devops:Tag {id: "devops", label: "DevOps"})

match (spfm:Post { id: "solr4-packaging-from-maven" }), (mfoo:Author { id: "mfoo" }), (devops:Tag { id: "devops"})
create (spfm)-[r:authored_by]->(mfoo)
create (mfoo)-[r2:authored]->(spfm)
create (spfm)-[r3:has_tag]->(devops)
create (devops)-[r4:tags]->(spfm)

match (ccr:Post { id: "combining-compojure-routes" }), (mfoo:Author { id: "mfoo" }), (programming:Tag {id: "programming"}), (languages:Tag {id: "languages"})
create (ccr)-[r:authored_by]->(mfoo)
create (mfoo)-[r2:authored]->(ccr)
create (ccr)-[r3:has_tag]->(programming)
create (programming)-[r4:tags]->(ccr)
create (ccr)-[r5:has_tag]->(languages)
create (languages)-[r6:tags]->(ccr)

match (pb:Post { id: "python-boss" }), (afoo:Author { id: "afoo" }), (programming:Tag {id: "programming"}), (languages:Tag {id: "languages"})
create (pb)-[r:authored_by]->(afoo)
create (afoo)-[r2:authored]->(pb)
create (pb)-[r3:has_tag]->(programming)
create (programming)-[r4:tags]->(pb)
create (pb)-[r5:has_tag]->(languages)
create (languages)-[r6:tags]->(pb)
