# 🚀 Android Fitness App with Jetpack Compose

This is an **Android Fitness App** built using **Kotlin** and **Jetpack Compose**. It provides features like tracking exercises, visualizing performance (steps, calories), viewing history, playing simple games, and sending notifications. The app supports **light/dark themes**, **localization**, and uses **Room** for data persistence.

---

## 📂 **Project Structure**

The project follows a modular architecture ensuring **scalability**, **testability**, and **separation of concerns**.

### 📊 **1. Data Layer (`data/`)**
- **local/**: Room database entities and DAOs.
- **repository/**: Manages data sources (local, remote if added).
- **model/**: Data models for app entities.
- **viewModel/**: preparing and managing the data for ui

### 🖥️ **2. UI Layer (`ui/`)**
- **navigation/**: Handles screen navigation with `NavController`.
- **home/**, **training/**, **settings/**: Screens for core functionalities.
- **components/**: Reusable UI components.
- **theme/**:
    - `Color.kt`: Color definitions.
    - `Theme.kt`: Theme definitions for light and dark modes.
    - `Typography.kt`: Font styles.

### ⚙️ **3. Utilities (`utils/`)**
- Extension functions for context, dates, colors, etc.
- Logging and debugging utilities.


---

## 📦 **Dependencies**

The project leverages the following libraries:

### **Core Libraries**
- [Jetpack Compose](https://developer.android.com/jetpack/compose) – Modern UI toolkit.
- [Material3](https://m3.material.io/) – Material Design for Compose.
- [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) – Navigation system.

### **Data Persistence**
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) – Local storage.

### **Dependency Injection**
- [Kodein-DI](https://kodein.org/Kodein-DI/) – Lightweight DI library.

### **Utilities**
- Kotlin Coroutines – Asynchronous programming.
- Lifecycle-Aware Components.
