(ns stepanp.core-server
  (:require
    [cljs.nodejs :as nodejs]
    ;[reagent.core :as r]
    [stepanp.settings :as settings]
    [stepanp.routes :refer [routes]]
    ))

(nodejs/enable-util-print!)

(defonce __dirname (js* "__dirname"))
(defonce express (cljs.nodejs/require "express"))
(defonce logfmt (cljs.nodejs/require "logfmt"))
(defonce port (or (aget cljs.nodejs/process "env" "PORT") settings/default-port))
(defonce http (nodejs/require "http"))

(.log js/console js/ReactRouter.match)

(defn get-frontend [req res]
  (js/ReactRouter.match
    #js {:routes routes
         :location (.-url req)}
    (fn [_ _ props]
      (let [str (js/React.renderToString
                  ((js/React.createFactory js/ReactRouter.RoutingContext)
                    props))]
        (.send res (+ "<div id=\"react-view\">" str "</div>"))))))


(def app (express))

(. app (use (.requestLogger logfmt)))
(. app (use "/static" (. express (static (str __dirname "/../resources")))))
(. app (get "/:params?*" get-frontend))

(defn -main []
  (doto (.createServer http #(app %1 %2))
    (.listen port)))

(set! *main-cli-fn* -main)
