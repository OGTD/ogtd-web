(ns ogtd-web.system
  (:require [com.stuartsierra.component :as component]
            [ogtd-web.stores.incrementer :refer [new-increment-service]]
            [ogtd-web.stores.writer :refer [new-write-store]]
            [ogtd-web.stores.counter :refer [new-count-store]]
            [ogtd-web.services.ui :refer [new-ui-service]]))

(enable-console-print!)

(declare sys)

(defn new-system []
  (component/system-map
   :increment-service (new-increment-service)
   :write-store (new-write-store)
   :count-store (new-count-store)
   :app-root (component/using (new-ui-service) [:increment-service :write-store :count-store])))

(defn init []
  (set! sys (new-system)))

(defn start []
  (set! sys (component/start sys)))

(defn stop []
  (set! sys (component/stop sys)))

(defn ^:export go []
  (init)
  (start))

(defn reset []
  (init)
  (start))
