export WS_SERVER=http://localhost:8080/ws/feed
export WS_SERVER=http://websocket-server.default.svc.cluster.local/ws/feed

./mvnw spring-boot:run

kubectl apply -f knative/ws-server.yaml

kubectl label ksvc websocket-server \
  serving.knative.dev/visibility=cluster-local

kubectl get ksvc websocket-server

export HOST_NAME=$(kubectl get route websocket-server \
  -o 'jsonpath={.status.url}' | cut -c8-)

Lab 7 - Custom Domain


export WS_SERVER=http://websocket-server.default.35.224.148.61.nip.io/ws/feed