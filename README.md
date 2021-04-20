# Towers of Hanoi

The problem of Hanoi's Towers made with Java and solved using informed and uninformed search methods used in AI. The implemented search methods are:

- Depth First
- Breadth First
- A Star
- Best First
- Hill Climbing
- Optimal

After solve the problem an animations it's played showing the results, in console we can find the  needed movements to solve it and the statistics.

![https://raw.githubusercontent.com/IanPedraza/ianpedraza.github.io/master/readme/ss_hanoi_towers.png](https://raw.githubusercontent.com/IanPedraza/ianpedraza.github.io/master/readme/ss_hanoi_towers.png)

---

## Developers

This software has clean architecture using a repository pattern, so it's so easy to add a new method, implement any problem problem not only towers of hanoi, or even use an remote data source like an API.

### Repsositories:

- Animation Repository
- Informed Repository
- Uninformed Respository

### **Data Sources**:

- Animation Data Source
- Informed Search Local Data Source
- Uninformed Search Local Data Source

### Usecases:

- Animation Control
- Resolve Informed Use Case
- Resolve Uninformed Use Case

---

### How to add a new method

1. Create your class extending from **InformedSearchLocalDataSource** or **UninformedSearchLocalDataSource**, then you have in your class the instance, callback, heuristic (if apply)  in a resolve method where you code the method.
2. Add yur new method in **SearchMethods** file.
3. Instance your method in **Main.**
4. Add the case of your method in **instanceSearchMethod(SearchMethod)** function in **Main.**

That's all ! You have added a new method 🥳

---

### How to add a new problem

1. Create your problems package in **framework** package.
2. Create your state class extending from **Statse.**
3. Create your operators implementing **Operator** interface.
4. Create your instance extending **Instance** here y.ou have to set the initial state, final state, operators and any data yout need
5. If apply create the **heuristic** implementing **Heuristic**
6. Finally you should create your animation extending from **AnimationPanel** overriding the paint component method, implementing AnimationPanel you gonna have the step and the instance to render every frame in paint component.

That's all ! You have all the needed to implement a new problem ! 🥳

To finish the implemntation just following the repository pattern used in main, change any Hanoi thing for your problems instance. You can create a whole new interface base on it.

You should replace the AnimationPanel shown in interface for your problem AnimationPanel

---

## Thanks

Thanks to flaticon for the icons used in the project