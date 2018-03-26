(ns ogtd-web-2.utils
  (:require [clojure.core.async :as async :refer [chan go >!]]))


(defn atom->chan
  "Turn an atom into a channel, returns a vector containing the channel and a function to remove the watch from the atom.
  Optionally takes in the key to use for the watch, BE CAREFUL TO NOT USE THE SAME KEYS!"
  ([at]
   (atom->chan at (random-uuid)))
  ([at key]
   (let [c (chan)]
     (add-watch at key
                (fn [key at old new] (go (>! c new))))
     [c #(remove-watch at key)])))
