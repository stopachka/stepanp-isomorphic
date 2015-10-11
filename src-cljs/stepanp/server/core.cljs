(ns stepanp.server.core
  (:require-macros [hiccups.core :as hiccups])
  (:require
    [cljs.nodejs :as nodejs]
    [hiccups.runtime]
    [stepanp.js-bundle]
    [stepanp.settings :as settings]
    [stepanp.routes :refer [routes]]))

(nodejs/enable-util-print!)

(defonce __dirname (js* "__dirname"))
(defonce express (cljs.nodejs/require "express"))
(defonce logfmt (cljs.nodejs/require "logfmt"))
(defonce port (or (aget cljs.nodejs/process "env" "PORT") settings/default-port))
(defonce http (nodejs/require "http"))

(hiccups/defhtml main-templ [react-str]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:title "Stepan Parunashvili"]
   [:body
    [:div {:id settings/root-id} react-str]
    [:script {:type "text/javascript" :src "/static/js/stepanp.compiled.js"}]
    [:script {:type "text/javascript"} "stepanp.client.core.init_BANG_()"]]]])

(defn get-frontend [req res]
  (js/ReactRouter.match
    #js {:routes routes
         :location (.-url req)}
    (fn [_ _ props]
      (let [react-str (js/React.renderToString
                        ((js/React.createFactory js/ReactRouter.RoutingContext)
                          props))]
        (. res (send (main-templ react-str)))))))


(def app (express))

(. app (use (.requestLogger logfmt)))
(. app (use "/static" (.static express (str __dirname "/../resources"))))
(. app (get "/:params?*" get-frontend))

(defn -main []
  (doto (.createServer http #(app %1 %2))
    (.listen port)))

(set! *main-cli-fn* -main)
