(ns ogtd-web-2.stores.writer
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async :refer [chan go <! close!]]
            [koch.core :refer [kupdate]]
            [reagent.core :as r]))

;Persist state across dev changes
(defonce initial-state {:text "hey"})
(defrecord WriteStore [state effects]
  component/Lifecycle
  (start [this]
    (let [effects {:change (kupdate (fn [state text](update-in state [:text] (constantly text))))}]
         (assoc this :effects effects)))
  (stop [this]
    this))
(defn new-write-store []
  (map->WriteStore {:state initial-state :effects {}}))
