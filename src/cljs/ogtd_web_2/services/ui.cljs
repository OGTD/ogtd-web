(ns ogtd-web-2.services.ui
  (:require [com.stuartsierra.component :as component]
            [ogtd-web-2.components.greet :refer [GreetComponent]]
            [reagent-material-ui.core :as ui :refer [Card]]
            [reagent.core :as r]
            [koch.core :refer [provide-state inject-state]]))
(defn color [nme] (aget ui/colors nme))

(def theme-defaults {:muiTheme (ui/getMuiTheme (-> ui/darkBaseTheme
                                                       (js->clj :keywordize-keys true)
                                                       (update :palette merge {:textColor (color "amber400")
                                                                               :primary2Color (color "amber700")})
                                                       (clj->js)))})

(defrecord UIService [increment-service write-store count-store]

  component/Lifecycle
  (start [this]
    ;;(r/render [:div "hey" "hello" [(-> GreetComponent (provide-state increment-service)(provide-state write-store) (provide-state count-store))]] (js/document.getElementById "app"))
    (r/render [ui/MuiThemeProvider theme-defaults [:div [Card [ui/CardText "heya "]] [(-> GreetComponent (provide-state increment-service)(provide-state write-store) (provide-state count-store))]]] (js/document.getElementById "app"))
    this)
  (stop [this]
    this))

(defn new-ui-service []
  (map->UIService {}))
