(ns ogtd-web-2.components.greet
  (:require [clojure.core.async :as async :refer [go  >!]]
            [koch.core :refer [inject-state]]))


(defn _GreetComponent [{:keys [count counting text counter effects]}]
   (let [increment (:inc effects)
         decrement (:dec effects)
         toggle (:toggle effects)
         change (:change effects)]
     [:div
      [:h1 count]
      [:input {:type "button"
               :value "dec"
               :onClick #(decrement)}]
      [:input {:type "button"
               :value "inc"
               :onClick #(increment)}]
      [:h1 text]
      [:input {:onChange #(-> % (.-target)
                              (.-value)
                              (change))}]
      [:h1 counter]
      [:input {:type "button"
               :value (if counting "stop" "start")
               :onClick #(toggle)}]]))

(def GreetComponent (inject-state _GreetComponent :count :counting :text :counter))
