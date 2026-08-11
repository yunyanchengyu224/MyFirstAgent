# AI 智能模拟面试系统 (AI Mock Interviewer)

这是一个基于 **Java Spring Boot** 和本地大模型（**Ollama/DeepSeek**）构建的智能化面试模拟系统。它旨在为计算机相关专业的学生或求职者提供沉浸式的技术面试体验，通过多轮追问和实时评估，帮助用户系统性地查漏补缺。

## 🚀 项目亮点

* **沉浸式体验**：完全模拟真实大厂技术面试流程。
* **智能追问**：基于候选人回答内容，AI 自动进行底层原理的延伸追问（如 TCP/IP 协议栈、高并发、内存调优等）。
* **结构化评估**：实时给出考察的技术领域、导师深度点评及下一步面试引导。
* **本地私有化**：通过 Ollama 在本地运行 DeepSeek 模型，数据不出本地，无需担心隐私问题，且响应迅速。
* **现代技术栈**：采用 Spring Boot + Java 25 LTS + Restful API + 轻量级前端。

## 🛠 技术栈
* **后端**: Spring Boot 3.5.x, Java 25 LTS, Jackson (JSON 处理)
* **AI 引擎**: Ollama + DeepSeek-R1 (7b)
* **前端**: 原生 HTML/JS (轻量简洁，无依赖)
* **通信**: HTTP Client (Java 11+)

## 📦 快速启动

### 1. 环境准备
* 安装 [Ollama](https://ollama.com/)，并在命令行运行：
    ```bash
    ollama run deepseek-r1:7b
    ```
* 确保安装了 JDK 25（最新 LTS）或更高版本。

### 2. 运行项目
1. 克隆代码到本地：
   ```bash
   git clone [https://github.com/你的用户名/MyFirstAgent.git](https://github.com/yuhangcheng/MyFirstAgent.git)
使用 IDE (IntelliJ IDEA) 打开项目，或者使用 Maven 运行桌面窗口：

```powershell
mvn javafx:run
```

启动后会打开独立的 JavaFX 应用窗口，页面不会交给系统浏览器打开。若要生成可以直接双击运行的 Windows 桌面程序，先执行：

```powershell
.\build-desktop.ps1
```

脚本会把可直接双击的 `AIinterview.exe` 放在项目根目录，同时保留它依赖的 `app` 和 `runtime` 目录。图标来自 `src/main/resources/icons/AIinterview.ico`。启动前请先确认 Ollama 正在运行，并已安装 `deepseek-r1:7b`。如需更换地址或模型，可在 PowerShell 中设置 `OLLAMA_ENDPOINT` 和 `OLLAMA_MODEL` 环境变量。

### 3. 构建容器镜像

项目包含面向 Linux/amd64 的多阶段 `Dockerfile`。容器运行的是 Spring Boot HTTP 服务，不启动 JavaFX 桌面窗口：

```bash
docker build --platform linux/amd64 -t myfirstagent:java25 .
docker run --rm -p 8080:8080 -e OLLAMA_ENDPOINT=http://host.docker.internal:11434/api/generate myfirstagent:java25
```

容器需要能访问 Ollama。部署到云环境时，请将 `OLLAMA_ENDPOINT` 设置为可从容器访问的 Ollama 服务地址。

根目录的 `AIinterview.exe` 就是桌面入口，可以为它创建桌面快捷方式。`app` 和 `runtime` 是 exe 的运行时依赖，不要单独移动或删除。它们由构建脚本生成并被 Git 忽略；重新克隆项目后执行 `.\build-desktop.ps1` 即可恢复。若需要单文件安装包，另外安装 WiX Toolset 后再将脚本中的 `--type app-image` 改为 `--type exe`。

旧的 `AIinterview.exe` 是之前的二进制文件，需要用新构建的桌面程序替换。

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
