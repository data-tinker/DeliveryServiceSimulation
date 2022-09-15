## Delivery System Simulation

To run the project, run ```./gradlew run```

Results that I got on my machine:
```
FIFO strategy was used to process orders. Average food waiting time: 591.6515151515151, Average courier waiting time: 605.0909090909091
MATCHED strategy was used to process orders. Average food waiting time: 1901.0530303030303, Average courier waiting time: 2010.3030303030303
```
It is clear that the FIFO strategy is better suited for distributing orders among the couriers
