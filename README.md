# AI 智能模拟面试系统 (AI Mock Interviewer)

这是一个基于 **Java Spring Boot** 和本地大模型（**Ollama/DeepSeek**）构建的智能化面试模拟系统。它旨在为计算机相关专业的学生或求职者提供沉浸式的技术面试体验，通过多轮追问和实时评估，帮助用户系统性地查漏补缺。

## 🚀 项目亮点

* **沉浸式体验**：完全模拟真实大厂技术面试流程。
* **智能追问**：基于候选人回答内容，AI 自动进行底层原理的延伸追问（如 TCP/IP 协议栈、高并发、内存调优等）。
* **结构化评估**：实时给出考察的技术领域、导师深度点评及下一步面试引导。
* **本地私有化**：通过 Ollama 在本地运行 DeepSeek 模型，数据不出本地，无需担心隐私问题，且响应迅速。
* **现代技术栈**：采用 Spring Boot (Java 17+) + Restful API + 轻量级前端。

## 🛠 技术栈
* **后端**: Spring Boot 3.2.x, Java 17, Jackson (JSON 处理)
* **AI 引擎**: Ollama + DeepSeek-R1 (7b)
* **前端**: 原生 HTML/JS (轻量简洁，无依赖)
* **通信**: HTTP Client (Java 11+)

## 📦 快速启动

### 1. 环境准备
* 安装 [Ollama](https://ollama.com/)，并在命令行运行：
    ```bash
    ollama run deepseek-r1:7b
    ```
* 确保安装了 JDK 17 及以上版本。

### 2. 运行项目
1. 克隆代码到本地：
   ```bash
   git clone [https://github.com/你的用户名/MyFirstAgent.git](https://github.com/yuhangcheng/MyFirstAgent.git)
使用 IDE (IntelliJ IDEA) 打开项目，或者使用 Maven 运行：

Bash
mvn spring-boot:run
打开浏览器访问：http://localhost:8080

💡 使用建议
在面试过程中，尽量结合你的项目经历或底层源码阅读经验来回答，AI 面试官会自动识别你回答中的深度，并针对性地进行压力面试。

🤝 贡献说明
欢迎提交 Issue 或 Pull Request，帮助系统加入更多的面试题库模板或性能优化逻辑。

Powered by SQNU Dev Team


---

### 3. 发布到 GitHub 的小贴士
1.  **添加 `.gitignore`**：在上传前，一定要在项目根目录下创建一个 `.gitignore` 文件，加入以下内容，避免把 `target/` 文件夹和 IDE 配置上传：
    ```text
    /target/
    .idea/
    *.iml
    .settings/
    .classpath
    .project
    ```
2.  **创建仓库**：
    * 在 GitHub 页面点击右上角的 **+** -> **New repository**。
    * 按照提示进行 `git init`, `git add .`, `git commit -m "first commit"`, `git branch -M main`, `git remote add origin ...`, `git push -u origin main` 操作。
