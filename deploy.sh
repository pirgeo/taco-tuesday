kubectl create namespace tacos
kubectl apply -f k8s/deliveries.yaml -n tacos
kubectl apply -f k8s/orders.yaml -n tacos