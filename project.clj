(defproject stepanp "0.0.1"
  :description "stepanp.com"
  :url "https://stepanp.com"
  :clean-targets ^{:protect false}
  ["out-client" "out-server" "out-test" "deploy/index.js" "resources/js/stepanp.compiled.js" "stepanp.compiled.test.js" "target"]
  :profiles {:default [:cljs-shared]
             :cljs-shared
             {:dependencies [[org.clojure/clojure "1.6.0"]
                             [org.clojure/clojurescript "0.0-3058"]
                             [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                             [org.omcljs/om "0.8.8"]
                             [sablono "0.3.3"]
                             [hiccups "0.3.0"]
                             [cljs-http "0.1.25"]]
              :plugins [[lein-cljsbuild "1.0.5"]]
              :cljsbuild
              {:builds [{:id "server"
                         :source-paths ["src-cljs"]
                         :compiler
                         {:main "stepanp.core-server"
                          :target :nodejs
                          :output-to "deploy/index.js"
                          :output-dir "out-server"
                          :preamble ["include.js"]
                          :optimizations :simple
                          :language-in :ecmascript5
                          :language-out :ecmascript5}}
                        {:id "client"
                         :source-paths ["src-cljs"]
                         :compiler
                         {:main "stepanp.core-client"
                          :output-to "resources/js/stepanp.compiled.js"
                          :output-dir "out-client"
                          :externs ["externs.js"]
                          :optimizations :advanced
                          :pretty-print false}}]}}})
