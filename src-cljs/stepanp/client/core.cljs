(ns stepanp.client.core
  (:require
    [stepanp.routes :refer [routes]]
    [stepanp.settings :as settings]))

(defn ^:export init! []
  (js/React.render
    ((js/React.createFactory js/ReactRouter.Router)
      #js {:history (js/routerHistory.createHistory)}
      routes)
    (js/document.getElementById settings/root-id)))
