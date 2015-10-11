(defproject stepanp "0.0.1"
  :dependencies [[hiccups "0.3.0"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/clojurescript "1.7.48"]
                 [reagent "0.5.1" :exclusions [cljsjs/react]]]
  :plugins [[lein-cljsbuild "1.1.0"]]

  :target-path "target/%s/"
  :compile-path "%s/classy-files"
  :clean-targets ^{:protect false}
  ["target" "out-client" "out-server" "deploy/index.js" "resources/js/stepanp.compiled.js"]
  :cljsbuild
  {:builds
   [{:id "server"
     :source-paths ["src-cljs/stepanp/shared" "src-cljs/stepanp/server"]
     :compiler
     {:main "stepanp.server.core"
      :target :nodejs
      :output-to "deploy/index.js"
      :output-dir "out-server"
      :preamble ["js/js-bundle.js"]
      :optimizations :simple
      :language-in :ecmascript5
      :language-out :ecmascript5}}
    {:id "client"
     :source-paths ["src-cljs/stepanp/shared" "src-cljs/stepanp/client"]
     :compiler
     {:main "stepanp.client.core"
      :preamble ["js/js-bundle.js"]
      :output-to "resources/js/stepanp.compiled.js"
      :output-dir "out-client"
      :optimizations :simple
      :pretty-print false}}]})
