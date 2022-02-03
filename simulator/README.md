# LMR, LTR with simulated data examples

Minimalist example showing off the usage of ltr-in, ltr-out and lmr-in in action:

* data forwarding (using our docker base simulator image) to **ltr-in** agent;
* **ltr-out** will be processing the received flow and store it using the lumberjack protocol;

## Commands

**All resources will be created in mytenant namespace**

```sh
# use kubectl port-forward
kubectl port-forward ltr-**** 8880

# run services
kubectl apply -f punchlines

# simulate data generation
./simulate.sh
```