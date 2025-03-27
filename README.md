# Graph Processing Project - CSE 464 Part 2

## 👤 Author
- **Name:** Aryan Shah

---

## 📦 Overview
This project implements a directed graph system with the following capabilities:

- ✅ Parsing a DOT file to create a graph
- ✅ Adding/removing nodes and edges
- ✅ Exporting the graph as a DOT file and PNG image
- ✅ Performing search using BFS and DFS
- ✅ Unit testing for all core features
- ✅ GitHub Actions CI for automated testing

---

## 📁 Project Structure
```
CSE464ProjectPart2/
├── src/main/java/com/graph/
│   ├── GraphHandler.java
│   └── Path.java
├── src/test/java/com/graph/
│   └── GraphHandlerTest.java
├── .github/workflows/maven.yml
├── test.dot
├── expected.txt
├── output.dot
├── graph.png
├── pom.xml
├── README.md / README.pdf
```

---

## 🧪 Running the Code

### 🔧 Prerequisites
- Java 17
- Apache Maven

### 🛠 Install Maven (if not already installed)
```bash
# For macOS
brew install maven

# For Ubuntu/Linux
sudo apt install maven

# For Windows (use choco or download from Apache)
choco install maven
```

### 🚀 Build and Run
```bash
# Compile and package
mvn clean package

# Run the demo using a sample DOT file
mvn exec:java -Dexec.mainClass="com.graph.GraphHandler" -Dexec.args="test.dot"
```
> This will:
> - Parse `test.dot`
> - Output the graph structure
> - Generate `output.dot` and `graph.png`

### ✅ Run All Unit Tests
```bash
mvn test
```

---

## 🧪 Sample Input (test.dot)
```
digraph G {
    A -> B;
    B -> C;
    C -> A;
}
```

## 📄 Sample Output (expected.txt)
```
digraph G {
    A;
    B;
    C;
    A -> B;
    B -> C;
    C -> A;
}
```

---

## 🔍 Features Demonstrated
- `parseGraph(String path)`
- `addNode(String label)` / `addNodes(String[])`
- `addEdge(String, String)`
- `removeNode(String)` / `removeNodes(String[])` / `removeEdge(String, String)`
- `outputDOTGraph(String path)` / `outputGraphics(String path, String format)`
- `GraphSearch(String src, String dst, Algorithm algo)` using BFS & DFS

---

## 🖼 Screenshots


- ✅ `graph.png` preview: <img width="1440" alt="Screenshot 2025-03-26 at 7 08 49 PM" src="https://github.com/user-attachments/assets/194f050f-c3fc-420c-b0ca-930fe0cb5e3d" />

- ✅ `output.dot` contents preview: <img width="1440" alt="Screenshot 2025-03-26 at 7 08 57 PM" src="https://github.com/user-attachments/assets/1d51017d-d13e-4556-9cd5-af4e1d8d42c9" />

- ✅ Successful `mvn test`: <img width="1440" alt="Screenshot 2025-03-26 at 7 18 39 PM" src="https://github.com/user-attachments/assets/8ae1bd1e-c937-4467-bb8a-a356ddbdcd9f" />
                            <img width="1440" alt="Screenshot 2025-03-26 at 7 18 46 PM" src="https://github.com/user-attachments/assets/5c9607e5-0794-4598-b631-2be81115700d" />
                            <img width="1440" alt="Screenshot 2025-03-26 at 7 18 54 PM" src="https://github.com/user-attachments/assets/f95484a9-2cb1-436f-b01f-e35d99ef9309" />

- ✅ GitHub Actions CI result: <img width="1440" alt="Screenshot 2025-03-26 at 7 19 52 PM" src="https://github.com/user-attachments/assets/c6f1a88e-3e17-4ef9-9833-73ef7a9087c7" />


---

## 🔀 GitHub Workflow and Branching
- Created `bfs` and `dfs` branches
- Each implemented `GraphSearch()` independently
- Merged into `main` with enum-based resolution
- Enum used:
```java
public enum Algorithm {
    BFS,
    DFS
}
```

> Commit references:
- BFS commit: e304f16df1e81135e36b0a649b7596112236b7ac
- DFS commit: 57ad6d84bd3bb4abfc7042dcb8ad503941444523
- Merge commit: 7d53565c7a5d29a8f0ee5fe4132a84b30f2e759f
- CI maven commit: 9e32c8a3c8044dfba662792cd254efea5f0b8608
