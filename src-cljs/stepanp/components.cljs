(ns stepanp.components)

(defn main-handler [props]
  (:children props))

(defn posts-handler [props]
  [:h1 "posts"])

(defn about-handler [props]
  [:h1 "about"])
