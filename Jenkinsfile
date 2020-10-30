pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                mvn clean package
            }
        }
        stage('test') {
            steps {
                mvn test
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
