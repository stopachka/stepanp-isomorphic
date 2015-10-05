(ns stepanp.core-server
  (:require [stepanp.settings :as settings]
            [reagent.core :as r]
            [stepanp.routes :refer [routes]]))

;; Pages

(defn init! []
  (def __dirname (js* "__dirname"))
  (def express (cljs.nodejs/require "express"))
  (def logfmt (cljs.nodejs/require "logfmt"))
  (def port (or (aget cljs.nodejs/process "env" "PORT") settings/default-port))

  (def app (express))
  (.use app (.requestLogger logfmt))
  (.use app "/static" (.static express (str __dirname "/../resources")))

  (.get app "/:params?*"
    (fn [req res]
      (js/ReactRouter.match
        #js {:routes routes :location (js/createLocation (.-url req))}
        (fn [_ _ props]
          (let [str (js/React.renderToString
                      ((js/React.createFactory js/ReactRouter.RoutingContext)
                        props))]
            (.send res (+ "<div id=\"react-view\">" str "</div>"))))))))

(defn -main [& args]
  (init!)
  (.listen app port))

(set! *main-cli-fn* -main)
