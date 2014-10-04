(ns ploxblog.repositories.any
    (:require [clojurewerkz.neocons.rest :as nr]
              [clojurewerkz.neocons.rest.transaction :as tx]
              [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def conn (nr/connect "http://127.0.0.1:7474/db/data"))

(defn resp-single [tx_resp]
  (debug "raw response", tx_resp)
  (map (fn [ob] (first (:row ob)))
               (:data (first tx_resp))))

(defn trans [& stmts]
  (let [st (map #(if (coll? %1) %1 (tx/statement %1)) stmts)]
    (debug "in-transaction" st)
      (apply (partial tx/in-transaction conn) st)))

(defn by-label [label]
  (resp-single
    (trans
      (format "match (n:%s) return n" label))))

(defn by-id [label id]
  (resp-single
    (trans
      (tx/statement
        (format "match (n:%s {id: {id} }) return n" label)
        {:id id}))))

(defn delete [label id]
  (resp-single
    ; deletes posts and relationships to it; idempotent
    (trans
      (tx/statement
        (format "MATCH (n:%s {id: {id} })-[r]-() DELETE n, r" label)
        {:id id}))))
