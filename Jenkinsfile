pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Start building'
                sh 'chmod 777 ./gradlew'
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}