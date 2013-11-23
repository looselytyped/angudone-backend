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
  :exists? (fn [context]
             [(io/get-resource "/home.html")
              {::file (file (str (io/resource-path) "/home.html"))}])
  :handle-ok(fn [_]
              (clojure.java.io/input-stream (io/get-resource "/home.html")))
  :last-modified (fn [_]
                   (.lastModified (file (str (io/resource-path) "/home.html")))))


(defresource todos-resource
  :allowed-methods [:post :get]
  :malformed? (fn [{{params :params method :request-method} :request}]
                (and (= :put method) (empty? (:txt params))))
  :handle-malformed "To do text cannot be empty!!"
  :handle-ok (fn [_] (generate-string (db/get-all-todos)))
  :handle-malformed "To do text cannot be empty!"
  :post! (fn [ctx]
           (let [params (get-in ctx [:request :params])]
             (db/create-todo (:txt params))))
  :handle-created (fn [_] (generate-string (db/get-all-todos)))
  :available-media-types ["application/json"])

(defresource todo-resource [id]
  :allowed-methods [:get :put :delete]
  :malformed? (fn [{{params :body-params method :request-method} :request}]
                (and (= :put method) (empty? (:txt params))))
  :handle-malformed "To do text cannot be empty!!"
  :exists? (fn [_] (when-let [todo (db/get-todo id)]
                     { ::todo todo }))
  :put! (fn [ctx]
          (let [{{params :body-params method :request-method} :request} ctx
                text (:txt params)
                done (:done params)
                updated (assoc (ctx ::todo) :text text :done done)]
            (db/update-todo updated)
            { ::todo updated}))
  :handle-ok (fn [ctx] (generate-string (ctx ::todo)))
  :respond-with-entity? false
  :available-media-types ["application/json"])

(defroutes home-routes
  (ANY "/" request home)
  (ANY "/todos" request todos-resource)
  (ANY "/todos/:id" [id] (todo-resource id)))
