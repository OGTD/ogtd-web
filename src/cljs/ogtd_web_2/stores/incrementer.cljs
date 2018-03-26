(ns ogtd-web-2.stores.incrementer
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async :refer [chan go <! close! timeout]]
            [koch.core :refer [kupdate]]
            [reagent.core :as r]))

;Persist state across dev changes
(defonce initial-state {:count 0})
(defrecord IncrementService [state effects]
  component/Lifecycle
  (start [this]
    (let [effects {:inc (kupdate (fn [state]{:count (inc (:count state))}))
                   :dec (kupdate (fn [state]{:count (dec (:count state))}))
                   :inc-slow (kupdate (fn [state] (go (<! (timeout 5000)) {:count (inc (:count state))})))}]
         (assoc this :effects effects)))
  (stop [this]
    this))
(defn new-increment-service []
  (map->IncrementService {:state initial-state :effects {} }))
