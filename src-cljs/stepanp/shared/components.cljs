(ns stepanp.components)

(defn main-handler [props]
  (:children props))

(defn posts-handler [props]
  [:h1 {:on-click (fn [& _] (js/alert "clicked!"))} "posts"])

(defn about-handler [props]
  [:h1 "about"])
