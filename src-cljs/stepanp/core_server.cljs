(ns stepanp.core-server
  (:require [stepanp.settings :as settings]
            [reagent.core :as r]))

;; Pages

(defn main-handler [props]
  (:children props))

(defn posts-handler [props]
  [:h1 "posts"])

(defn about-handler [props]
  [:h1 "about"])

;; ReactRouter


(defn Route
  [{:keys [name component path]} & args]
  (apply
    (js/React.createFactory js/ReactRouter.Route)
    (->
      {}
      (cond->
        name (assoc :name name)
        component (assoc :component (r/reactify-component component))
        path (assoc :path path))
      clj->js)
    args))

(def routes
  (Route {:name "main" :component main-handler}
    (Route {:name "posts" :path "/" :component posts-handler})
    (Route {:name "about" :path "about" :component about-handler})))

(defn init! []
  (def __dirname (js* "__dirname"))
  (def express (cljs.nodejs/require "express"))
  (def logfmt (cljs.nodejs/require "logfmt"))
  (def port (or (aget cljs.nodejs/process "env" "PORT") settings/default-port))

  (def app (express))
  (.use app (.requestLogger logfmt))
  (.use app "/static" (.static express (str __dirname "/../resources")))

  (.get
    app
    "/:params?*"
    (fn [req res]
      (js/ReactRouter.match
        #js {:routes routes :location (js/createLocation (.-url req))}
        (fn [err _ props]
          (.send res (js/React.renderToString
                       ((js/React.createFactory js/ReactRouter.RoutingContext) props)
                       )))))))

(defn -main [& args]
  (init!)
  (.listen app port))

(set! *main-cli-fn* -main)
