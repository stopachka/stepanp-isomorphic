(ns stepanp.core-server
  (:require [clojure.string :as string]
            [stepanp.settings :as settings]))

(defn init
  []
  (def __dirname (js* "__dirname"))

  (def express (cljs.nodejs/require "express"))
  (def logfmt (cljs.nodejs/require "logfmt"))
  (def cookie-parser (cljs.nodejs/require "cookie-parser"))
  (def http-proxy (cljs.nodejs/require "http-proxy"))

  (def app (express))

  (def port (or (aget cljs.nodejs/process "env" "PORT") settings/default-port))

  (def proxy-server (.createProxyServer http-proxy #js {}))

  ; Logger
  (.use app (.requestLogger logfmt))

  ; Cookie parser
  (.use app (cookie-parser))

  ; Set assets folder
  (.use app "/static" (.static express (str __dirname "/../resources")))

  ; Direct Supper routing
  (.get app "/"
        (fn [req res]
          (.send res "<html>hello there!</html>")))

(defn -main [& args]
  (init)
  (.listen app port))

(set! *main-cli-fn* -main)
