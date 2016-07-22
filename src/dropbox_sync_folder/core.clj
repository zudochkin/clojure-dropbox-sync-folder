(ns dropbox-sync-folder.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]

            [clojure.data.json :as json]

            [clj-http.client :as client]))

(def config (->> "config.edn"
                       io/resource
                       slurp
                       edn/read-string))

(def body-json (json/write-str {:path "/hello"}))

(defn -main
  "I don't do a whole lot...yet."
  [& args]
    (println (client/post "https://api.dropboxapi.com/2/files/list_folder"
               {:body body-json
                ;; {"path": "/hello","recursive": false,"include_media_info": false,"include_deleted": false,"include_has_explicit_shared_members": false}
                :headers {"Authorization" (str "Bearer " (:access-token config))
                          "Content-Type" "application/json"}
                :content-type :json
                :accept :json})))
