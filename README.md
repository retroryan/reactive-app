

[Copied from this article](https://developer.okta.com/blog/2018/09/25/spring-webflux-websockets-react)

[Knative Workshop](https://docs.google.com/document/d/1QKjyWAJxZahQFUc8FkM_0gVtgDRUDgJq7zcJLiFjjjw/edit#)

```
kubectl apply -f knative/hello-react.yaml

export IP_ADDRESS=$(kubectl get svc istio-ingressgateway -n istio-system \
  -o 'jsonpath={.status.loadBalancer.ingress[0].ip}')

export HOST_NAME=$(kubectl get route reactive-web \
  -o 'jsonpath={.status.url}' | cut -c8-)

curl -w'\n' -H "Host: $HOST_NAME" http://${IP_ADDRESS}/profiles

kubectl apply -f knative/react-service-cnb.yaml

kubectl get taskruns/reactive-app-mvn-build -o yaml

kubectl logs -f $(kubectl get taskruns/hello-springboot-mvn-build \
  -ojsonpath={.status.podName}) -c step-build


```
## License

Apache 2.0, see [LICENSE](LICENSE).
