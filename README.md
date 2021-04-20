# Towers of Hanoi

The problem of Hanoi's Towers made with Java and solved using informed and uninformed search methods used in AI. The implemented search methods are:

- Depth First
- Breadth First
- A Star
- Best First
- Hill Climbing
- Optimal

After solving the problem an animation it's played showing the results, in console we can find the  needed movements to solve it and the statistics.

![https://raw.githubusercontent.com/IanPedraza/ianpedraza.github.io/master/readme/ss_hanoi_towers.png](https://raw.githubusercontent.com/IanPedraza/ianpedraza.github.io/master/readme/ss_hanoi_towers.png)

---

## Developers

This software has clean architecture using a repository pattern, so it's so easy to add a new method and implement any problem not only towers of Hanoi, or even use a remote data source like an API.

### Repositories

- Animation Repository
- Informed Repository
- Uninformed Repository

### **Data Sources**

- Animation Data Source
- Informed Search Local Data Source
- Uninformed Search Local Data Source

### Use Cases

- Animation Control
- Resolve Informed Use Case
- Resolve Uninformed Use Case

---

### How to add a new method

1. Create your class extending from **InformedSearchLocalDataSource** or **UninformedSearchLocalDataSource**, then you have in your class the instance, callback, heuristic (if apply)  in a resolve method where you code the method.
2. Add your new method in **SearchMethods** file.
3. Instance your method in **Main.**
4. Add the case of your method in **instanceSearchMethod(SearchMethod)** function in **Main.**

That's all! You have added a new method 🥳

---

### How to add a new problem

1. Create your problems package in **framework** package.
2. Create your state class extending from **State.**
3. Create your operators implementing **Operator** interface.
4. Create your instance extending **Instance** here you have to set the initial state, final state, operators and any data you need
5. If apply create the **heuristic** implementing **Heuristic**
6. Finally you should create your animation extending from **AnimationPanel** overriding the paint component method, implementing AnimationPanel you going to have the step and the instance to render every frame in paint component.

That's all! You have all the needed to implement a new problem! 🥳

To finish the implementation just following the repository pattern used in main, change any Hanoi thing for your problems instance. You can create a whole new interface base on it.

You should replace the AnimationPanel shown in interface for your problem AnimationPanel

---

## Thanks

Thanks to **flaticon** for the icons used in the project.