(ns starter.browser
  (:require
    ["regl" :as regl-init]
    ))

;; direct port of http://regl.party/examples/?basic

(def regl (regl-init))

(defn clear []
  (.clear regl #js {:color #js [0 0 0 1]
                    :depth 1}))

(def draw-triangle
  (-> {:frag
       "precision mediump float;
        uniform vec4 color;
        void main () {
          gl_FragColor = color;
        }"
       :vert
       "precision mediump float;
        attribute vec2 position;
        void main () {
          gl_Position = vec4(position, 0, 1);
        }"
       :attributes
       {:position [[-1 0]
                   [0 -1]
                   [1 1]]}
       :uniforms
       {:color [1 0 0 1]}
       :count 3}
      (clj->js)
      (regl)))

(defn start []
  ;; start is called by init and after code reloading finishes
  ;; this is controlled by the :after-load in the config
  (js/console.log "start")
  (clear)
  (draw-triangle))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

(defn stop []
  (.destroy regl)
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
