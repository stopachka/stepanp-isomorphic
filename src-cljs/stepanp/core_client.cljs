(ns stepanp.core-client
  (:require-macros [stepanp.detector :as d])
  (:require [cljs.reader]
            [clojure.set]
            [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(defn main-component
  [app owner]
  (reify
    om/IRender
    (render [_]
      (html
        [:div.container
         [:h1 "hello"]]))))

(defn main
  []
  (let [init-om-root #(do
                       (om/root
                         main-component
                         (atom {})
                         {:target (. js/document (getElementById "supper-root"))}))]
    (init-om-root)))

(d/cs (main) nil)
