(ns dropbox-sync-folder.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.walk :as w]))



(defn download-file-request [path] (client/post "https://content.dropboxapi.com/2/files/download"
                                        {:headers {"Authorization" (str "Bearer " (:access-token config))
                                                   "Dropbox-API-Arg" (json/write-str {:path path})}
                                         :as :byte-array
                                         }))

(defn write-file []
  (with-open [out (io/output-stream (io/file "./1.txt"))]
    (.write out (:body (download-file-request "/hello/1.txt")))))

(def config (->> "config.edn"
                       io/resource
                       slurp
                       edn/read-string))

(def response (client/post "https://api.dropboxapi.com/2/files/list_folder"
                           {:body (json/write-str {:path "/hello"})
                            :headers {"Authorization" (str "Bearer " (:access-token config))
                                      "Content-Type" "application/json"}
                            :content-type :json
                            :accept :json
                            }))

(def files (->> (:body response)
                json/read-str
                w/keywordize-keys
                :entries))

(defn -main
  "I don't do a whole lot...yet."
  [& args]
    (println "hello"))
