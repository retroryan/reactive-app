

[Reactive Web App Copied from this article](https://developer.okta.com/blog/2018/09/25/spring-webflux-websockets-react)

[Web Socket Code Copied from this github repo](https://github.com/marios-code-path/spring-web-sockets)

[Knative Workshop](https://docs.google.com/document/d/1QKjyWAJxZahQFUc8FkM_0gVtgDRUDgJq7zcJLiFjjjw/edit#)

```

./mvnw spring-boot:run

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

Follow steps in doc to create a custom domain and then run:

```
echo "http://reactive-web.default.$IP_ADDRESS.nip.io"

http://reactive-web.default.35.224.148.61.nip.io/profiles

```

cluster local

```
kubectl apply -f knative/hello-react.yaml

kubectl label ksvc reactive-web \
  serving.knative.dev/visibility=cluster-local

kubectl get ksvc hello-springboot
```

## License

Apache 2.0, see [LICENSE](LICENSE).
