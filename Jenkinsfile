pipeline {
    agent { label 'agent-jdk21' }

    tools {
        git 'Default'
    }

    environment {
        DOTENV_FILE = "/var/agent-jdk21/env/.env.develop"
    }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                }
            }
        }
        stage('Checkstyle Main') {
            steps {
                script {
                    sh './gradlew checkstyleMain -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Checkstyle Test') {
            steps {
                script {
                    sh './gradlew checkstyleTest -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Compile') {
            steps {
                script {
                    sh './gradlew compileJava -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh './gradlew test -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Package') {
            steps {
                script {
                    sh './gradlew build -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('JaCoCo Report') {
            steps {
                script {
                    sh './gradlew jacocoTestReport -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('JaCoCo Verification') {
            steps {
                script {
                    sh './gradlew jacocoTestCoverageVerification -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Update DB') {
            steps {
                script {
                    sh './gradlew update -P"dotenv.filename"="$DOTENV_FILE"'
                }
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t job4j_devops .'
            }
        }

        stage('Check Git Tag') {
            steps {
                script {
                    // Получаем текущий тег, если он есть
                    def gitTag = sh(script: 'git describe --tags --exact-match || true', returnStdout: true).trim()

                    if (gitTag) {
                        // Создаем имя Docker-образа с тегом
                        def imageName = "192.168.6.52:8081/my-docker-repo/job4j_devops:${gitTag}"
                        echo "Tag found: ${gitTag}. Proceeding with Docker build and push to Nexus."

                        // Строим Docker образ с тегом
                        sh "docker build -t ${imageName} ."

                        // Входим в Docker репозиторий Nexus
                        sh "docker login 192.168.0.106:8081 -u devops -p password"

                        // Публикуем образ в Nexus
                        sh "docker push ${imageName}"
                    } else {
                        echo "No Git tag found. Skipping Docker build and push."
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Отправляем информацию о билде в Telegram
                def buildInfo = "Build number: ${currentBuild.number}\n" +
                                "Build status: ${currentBuild.currentResult}\n" +
                                "Started at: ${new Date(currentBuild.startTimeInMillis)}\n" +
                                "Duration so far: ${currentBuild.durationString}"
                telegramSend(message: buildInfo)
            }
        }
    }
}
