pipeline {
    agent {
        docker {
            image 'gradle:jdk11'
            args '-v "$PWD":/home/gradle/project -w /home/gradle/project'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                echo 'test'
            }
        }
    }
}