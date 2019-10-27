export WS_SERVER=http://localhost:8080/ws/feed
export BOOTSTRAP_SERVER=localhost:9092

export WS_SERVER=http://websocket-server.default.35.224.148.61.nip.io/ws/feed
export BOOTSTRAP_SERVER=my-cluster-kafka-bootstrap:9092
export KAFKA_TOPIC=my-topic
export STORE_KAFKA=false

./mvnw spring-boot:run

kubectl apply -f knative/ws-server.yaml

kubectl label ksvc websocket-server \
  serving.knative.dev/visibility=cluster-local

kubectl get ksvc websocket-server

export HOST_NAME=$(kubectl get route websocket-server \
  -o 'jsonpath={.status.url}' | cut -c8-)

Lab 7 - Custom Domain


export WS_SERVER=http://websocket-server.default.35.224.148.61.nip.io/ws/feed


kubectl logs webscocket-client-59vhl-deployment-658cc6fdf5-skv45 -c user-container

kubectl get revision
kubectl get ksvc
kubectl get routes

kubectl get pods -n knative-serving
kubectl describe ksvc/websocket-server


kubectl -n knative-serving --as=system:serviceaccount:knative-serving:controller auth can-i get configmaps