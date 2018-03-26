(ns ogtd-web-2.stores.counter
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async :refer [chan go go-loop <! >! close! timeout]]
            [koch.core :refer [kupdate logger-middleware]]
            [reagent.core :as r]))

;Persist state across dev changes
(defonce initial-state {:counter 0 :counting false})
(defrecord CountStore [state effects]
  component/Lifecycle
  (start [this]
    (let [effects {:toggle (fn [{:keys [start-counting inc-counter]}]
                             (fn [state]
                               (if (:counting state)
                                 {:counting false}
                                 (go
                                   (loop [state (start-counting)]
                                     (if (and (:counting state)(< (:counter state) 16))
                                       (do
                                         (<! (timeout 1000))
                                         (recur (inc-counter)))))))))
                   :start-counting (kupdate {:counting true})
                   :inc-counter (kupdate (fn[state] {:counter (inc (:counter state))}))}
          middleware [logger-middleware]]
      (assoc this :effects effects :middleware middleware)))
  (stop [this]
    this))
(defn new-count-store []
  (map->CountStore {:state initial-state :effects {}}))
