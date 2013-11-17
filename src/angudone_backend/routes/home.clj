(ns angudone-backend.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
             :refer [defresource resource request-method-in]]
            [cheshire.core :refer [generate-string]]
            [angudone-backend.models.db :as db]
            [noir.io :as io]
            [clojure.java.io :refer [file]]))

(defresource home
  :available-media-types ["text/html"]

  :exists?
  (fn [context]
    [(io/get-resource "/home.html")
     {::file (file (str (io/resource-path) "/home.html"))}])

  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/home.html")))

  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/home.html")))))


(defresource get-todos
  :method-allowed? (request-method-in :get)
  :handle-ok (fn [_] (generate-string (db/get-all-todos)))
  :available-media-types ["application/json"])

(defresource new-todo
  :method-allowed? (request-method-in :post)
  :malformed? (fn [ctx]
                (println ctx)
                (let [params (get-in ctx [:request :form-params])]
                  (empty? (get params "txt"))))
  :handle-malformed "To do text cannot be empty!"
  :post! (fn [ctx]
           (let [params (get-in ctx [:request :form-params])]
             (db/create-todo (get params "txt"))))
  :handle-created (fn [_] (generate-string (db/get-all-todos)))
  :available-media-types ["application/json"])

(defroutes home-routes
  (ANY "/" request home)
  (GET "/todos" request get-todos)
  (POST "/todos" request new-todo))
