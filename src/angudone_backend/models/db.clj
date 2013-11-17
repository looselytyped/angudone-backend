(ns angudone-backend.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [angudone-backend.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity todos)

(defn create-todo [todo]
  (insert todos
          (values {:text todo
                   :created_ts (java.util.Date.)
                   :modified_ts (java.util.Date.)})))

(defn update-todo [id text done]
  (update todos
          (set-fields {:text text
                       :done done
                       :modified_ts (java.util.Date.)})
          (where {:id id})))

(defn get-todo [id]
  (first (select todos
                 (where {:id id})
                 (limit 1))))

(defn get-all-todos
  []
  (select todos))
