(ns stepanp.routes
  (:require
    [stepanp.components :as c]
    [reagent.core :as r]))

(defn Route
  [{:keys [component] :as props} & children]
  (apply
    (js/React.createFactory js/ReactRouter.Route)
    (clj->js (assoc props :component (r/reactify-component component)))
    children))

(def routes
  (Route {:name "main" :component c/main-handler}
    (Route {:name "posts" :path "/" :component c/posts-handler})
    (Route {:name "about" :path "about" :component c/about-handler})))
