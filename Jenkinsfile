pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh clean package
            }
        }
        stage('test') {
            steps {
                sh test
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
