$ErrorActionPreference = 'Stop'

$maven = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $maven) {
    throw 'Maven was not found on PATH. Install Maven or configure mvn.cmd before building the desktop application.'
}

& $maven.Source clean package -Pdesktop -DskipTests

$imagePath = Join-Path $PWD 'target\app-image\AIinterview'
if (Test-Path $imagePath) {
    Remove-Item $imagePath -Recurse -Force
}

jpackage --type app-image `
    --name AIinterview `
    --input target `
    --main-jar MyFirstAgent-1.0-SNAPSHOT.jar `
    --main-class org.springframework.boot.loader.launch.JarLauncher `
    --icon src/main/resources/icons/AIinterview.ico `
    --dest target/app-image `
    --java-options '-Dfile.encoding=UTF-8'

foreach ($item in @('AIinterview.exe', 'app', 'runtime')) {
    $destination = Join-Path $PWD $item
    if (Test-Path $destination) {
        Remove-Item $destination -Recurse -Force
    }
}

Copy-Item (Join-Path $imagePath 'AIinterview.exe') '.' -Force
Copy-Item (Join-Path $imagePath 'app') '.' -Recurse -Force
Copy-Item (Join-Path $imagePath 'runtime') '.' -Recurse -Force

Write-Host "Desktop application is ready: $PWD\AIinterview.exe"