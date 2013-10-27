(ns angudone-backend.models.schema
  (:require [clojure.java.jdbc :as sql]
            [noir.io :as io]))

(def db-store "site.db")

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str (io/resource-path) db-store)
              :user "sa"
              :password ""
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str (io/resource-path) db-store ".h2.db"))))

(defn create-todos-table
  []
  (sql/with-connection db-spec
    (sql/create-table
     :todos
     [:id "INTEGER PRIMARY KEY AUTO_INCREMENT"]
     [:text "varchar(100)"]
     [:done :boolean]
     [:created_ts :timestamp :default "NOW()"]
     [:modified_ts :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-todos-table))
