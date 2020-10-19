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
                echo 'Start building'
                sh 'chmod 777 ./gradlew'
                sh './gradlew clean'
                sh './gradlew --scan build'
            }
        }
    }
}