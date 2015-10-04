(ns stepanp.detector
  (:require [cljs.env]))

(def is-node? (= :nodejs (-> @cljs.env/*compiler* :options :target)))

(defmacro cs
  [client server]
  (if is-node? server client))
