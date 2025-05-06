# Graph Search Project - CSE 464 Part 3

## 👤 Author
- **Name:** Aryan Shah

---

## 📦 Overview
This project extends the graph framework to implement and demonstrate search algorithms using design patterns, including:

- ✅ Template Method Pattern for BFS and DFS
- ✅ Strategy Pattern for search algorithm selection
- ✅ Random Walk Search implementation
- ✅ Multiple execution trials to show non-determinism
- ✅ Refactored code using modular, reusable logic

---

## 🧪 Running the Code

### 🔧 Prerequisites
- Java 17
- Apache Maven

### 🚀 Build and Run
```bash
# Compile and package
mvn package

# Run the project
mvn exec:java -Dexec.mainClass="com.graph.GraphHandler"
```
This will:

- Load a graph from a file

- Run BFS and DFS searches

- Perform Random Walk Search 5 times

- Print out all search results

## 🧠 Design Patterns
### 🧩 Template Pattern: BFS and DFS
We used the Template Method Pattern to extract shared logic between BFS and DFS. The method:
```bash
templateSearch(String src, String dst, Collection<List<String>> collection, boolean isBFS)
```
handles:

- Initializing path and visited nodes

- Exploring neighbors

- Checking goal conditions

- Depending on whether a Queue or Stack is passed in, the search behaves as BFS or DFS respectively—cleanly separating common and unique behavior.

### ⚙️ Strategy Pattern: Algorithm Selection
We implemented the Strategy Pattern to allow dynamic selection of search logic.

- ``` strategySearch ``` interface with ```Path search(String src, String dst)``` method

- Implemented in:

  - newBFS

  - newDFS

  - newRandomWalk

The method:

```bash
GraphSearch(String src, String dst, Algorithm algo)
```
instantiates the correct strategy and executes the desired search. This supports easy extension for future algorithms.



### 🔄 Random Walk Search
Random Walk Search uses the same design patterns for consistency.

- In each run:

  - Start at the source node

  - Randomly choose an unvisited neighbor

  - Continue until destination is reached or no neighbors remain

Results are printed as:
```bash
Visiting Path{nodes=[...]}
```
This is implemented in the newRandomWalk class and executed multiple times to demonstrate non-deterministic outcomes.

<img width="1440" alt="Screenshot 2025-05-05 at 7 42 56 PM" src="https://github.com/user-attachments/assets/2e1b6e96-7b29-4df0-9116-7b07572480d0" />


## 🔀 GitHub Branches and Commits
### Branches
- https://github.com/ashah148/CSE-464-2025-ashah148
- https://github.com/ashah148/CSE-464-2025-ashah148/tree/refactor

### 📥 Pull Request
- https://github.com/ashah148/CSE-464-2025-ashah148/pull/1

### 🧾 Commit References
- Refactor 1: 92e82029a09a6f93ce69dba70e44a8ec2afaef02

- Refactor 2: dfb9d76d5988aae0b23fdeadeb13538eec007c78

- Refactor 3: 06c7bff16c80ba6a65f8524ebe7fd7de07c143b6

- Refactor 4: 1ea5a69eebc6ed7b6e7ab5fcd5c0b54c83c0a135

- Refactor 5: 1efd737cf95068536e2c436fc0a644ea531bc1cd

- Template: 241ad3a754fc412e481767280f3e240ed15ecd16

- Strategy: cff6be7c421ffb70db959ae05c5d1d7a458b4db0

- Random Walk: 39bc3e204cb3385bb59e439b82d454f3e64eaae7
