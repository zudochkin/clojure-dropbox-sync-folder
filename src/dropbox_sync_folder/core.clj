(ns dropbox-sync-folder.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [me.raynes.fs :as fs]
            [clojure.walk :as w]))


(def config (->> "config.edn"
                       io/resource
                       slurp
                       edn/read-string))

(defn download-file-request [path] (client/post "https://content.dropboxapi.com/2/files/download"
                                        {:headers {"Authorization" (str "Bearer " (:access-token config))
                                                   "Dropbox-API-Arg" (json/write-str {:path path})}
                                         :as :byte-array
                                         }))

(defn download-file [path]
  (with-open [out (io/output-stream (io/file (str "./download/" (fs/base-name path))))]
    (.write out (:body (download-file-request path)))))

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
                :entries
                (map :path_display)))

(defn -main
  [& args]
    (do
      (map #(download-file %) files)
      (println "download completed")))
