(defproject dropbox-sync-folder "0.1.0-SNAPSHOT"
  :description "Sync your dropbox folder with the local one"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "2.2.0"]]
  :plugins [[lein-cljfmt "0.5.3"]]
  :main dropbox-sync-folder.core
  :aot :all)
